from socket import *
import threading
import time

# --- Global variables here --- #
server_list = []
connectionSocketArr = {}
addrArr = []
users_list = {}
name_counter = 0

# --- Game Server object to keep track states for each independent game --- #
class Server:
    board = ''
    player_list = None
    player_sock_list = None
    player_count = 0
    player_count_tic = 0
    player_turn = 0 # Keeps track of whose turn it is
    player_won = 0
    server_code = 9

# --- Threaded lobby system to accept and handle incoming conections --- #
def lobby():   
    global connectionSocketArr
    global addrArr
    global users_list
    global name_counter
    
    connectionSocketArr = {}
    addrArr = []
    users_list = {}
    name_counter = 1
    broke = False
    
    # -- Gets two players and starts a thread for each -- #
    while(True):
        if(len(users_list.keys())!=8):
            connectionSocket, addr = serverSocket.accept()
            name_counter = 1
            broke = False
        else:
            connectionSocket, addr = serverSocket.accept()
            try:
                threading.Thread(target=sock_f, args=(connectionSocket, 0, name_counter)).start()
            except:
                print("Error: Unable to start connection socket thread")
            continue
        while True:
            if(name_counter in list(users_list.keys()) and name_counter < 9): #and name_counter ):
                name_counter+=1
                continue
            elif(name_counter >= 9):
                connectionSocket.close()
                break
                #elif(name_counter == 9):
                #    name_counter = 1 # Reset if it gets to 9
            else:
                users_list[name_counter] = "Player_%d" % int(name_counter)
                break
        if(broke is False):
            try:
                threading.Thread(target=sock_i, args=(connectionSocket, 0, name_counter)).start()
            except:
                print("Error: Unable to start connection socket thread")
            
            # -- Data for sending state info -- #
            connectionSocketArr[name_counter] = connectionSocket
            addrArr += [addr]

    # Closes the socket
    if(broke is False):
        connectionSocket.close()


# --- Function to find an open game server --- #
def find_open_server():
    open_server = ''
    for i in range(0, len(server_list)):
        if(len(server_list[i].player_list) < 2):
            open_server = server_list[i]
            print(open_server.player_list)

    # -- Informs on rather or not there must be a new server (If open_server string is empty -- #
    if open_server == '':
        open_server = 'New Server'
    return open_server


def toe_board(s_name, s_id, s_num, s_server):
    if(s_num == 1):
        print("First player")
        s_server.tic_first_player = s_id
        sentence = "\rGame Ready\n"
        new_sentence = sentence + str(s_server.server_code) # Fix this if you want more than ten players
        s_name.send(new_sentence.encode())
        while(len(s_server.player_list)<2):
            pass
        
    if(len(s_server.player_list)<=2):
        if(s_num == 2):
            print("Second player")
            sentence = "\rGame Ready\n"
            new_sentence = sentence + str(s_server.server_code)
            s_name.send(new_sentence.encode())
        if(s_num == 1):
            sentence = "\r!play\n"
            new_sentence = sentence + str(s_id)
            s_server.player_sock_list[s_server.tic_first_player].send(new_sentence.encode())

        print("Ready to play!\n")
        toe_game(s_name, s_id, s_num, s_server)
        print("Done with game")
        
        s_name.send(("Done..." + str(0)).encode()) # Sends an empty message to reset chat

        s_server.player_list.clear()
        s_server.tic_first_player = ''
        s_server.player_count_tic = 0
        s_server.player_turn = 1
        s_server.player_won = 0
    else:
        pass

# --- Function to notify player of their turn --- #
def notify_player_turn(n_name, n_id, n_num, n_server):
    current_turn = n_server.player_turn
    if(n_server.player_turn == 1):
        n_server.player_turn = 2
    else:
        n_server.player_turn = 1
    if(n_num == current_turn):
        sentence = "Your Turn" + str(n_server.server_code)
        print("Player %d turn!" % n_server.player_turn)

        # Copies the player_sock_list and removes the current player
        player_turn_dict = n_server.player_sock_list.copy()
        del player_turn_dict[n_id]
        
        #send_all(n_server.player_sock_list, sentence, n_server.server_code)
        send_all(player_turn_dict, sentence, n_server.server_code)
        
def toe_choice(let, symbol, p_name, p_id, p_num, p_server, squares):
    if(p_server.board.find(let) == -1):
        sentence = "\rSquare %c has already been taken\n" % let
        sentence = sentence + str(p_server.server_code)
        p_name.send(sentence.encode())
    elif(p_num == p_server.player_turn):
        p_server.board = p_server.board.replace(let, symbol) # Replaces the symbol on the board

        # -- Sends the updated board to all the players -- #
        sentence = p_server.board + str(p_server.server_code)
        p_name.send(sentence.encode())
        
        # -- Lets the other player know what character was chosen -- #
        let_sentence = "\n\rThe other player played %c\n" % let
        sentence = p_server.board + let_sentence + str(p_server.server_code)
        send_all(p_server.player_sock_list, sentence, p_id)

        squares += [let] # Updates letter list
        notify_player_turn(p_name, p_id, p_num, p_server) # Notifies next player of their turn
    else:
        sentence = "\rNot your turn yet\n" + str(p_server.server_code)
        p_name.send(sentence.encode())
    if(('1' in squares and '2' in squares and '3' in squares) or ('4' in squares and '5' in squares and '6' in squares) or ('7' in squares and '8' in squares and '9' in squares) or ('1' in squares and '4' in squares and '7' in squares) or ('2' in squares and '5' in squares and '8' in squares) or ('3' in squares and '6' in squares and '9' in squares) or ('1' in squares and '5' in squares and '9' in squares) or ('3' in squares and '5' in squares and '7' in squares)):
        p_server.player_won = p_num
        
    if(p_server.player_won == 0 and p_server.board.find('1') == -1 and p_server.board.find('2') == -1 and p_server.board.find('3') == -1 and p_server.board.find('4') == -1 and p_server.board.find('5') == -1 and p_server.board.find('6') == -1 and p_server.board.find('7') == -1 and p_server.board.find('8') == -1 and p_server.board.find('9') == -1):
        p_server.player_won = -1 # No one won
        
def toe_game(p_name, p_id, p_num, p_server):
    global connectionSocketArr
    
    sentence = p_name.recv(1024).decode()
    send_all(p_server.player_sock_list, sentence, p_server.server_code)
    send_all(p_server.player_sock_list, "", p_server.server_code)
    
    save_board = p_server.board # Saves the default game board
    squares = [] # Keeps track of which squares the player has 
    game_stop = False # Flag used to stop game logic
    
    time.sleep(.2)
    
    if p_num == 1:
        symbol = 'x'
        notify_player_turn(p_name, p_id, p_num, p_server)
    else:
        symbol = 'o'
    while True:
        try:
            if(p_server.player_won != 0 and game_stop is False):
                if(p_server.player_won != -1):
                    sentence = "\rPlayer %d won!!!\n" % p_server.player_won
                else:
                    sentence = "\rNobody won!!!\n"
                
                send_all(p_server.player_sock_list, sentence, p_server.server_code)

                sentence = "\rEnter quit to quit\n"
                send_all(p_server.player_sock_list, sentence, p_server.server_code)
                game_stop = True
  
            
            sentence = p_name.recv(1024).decode()
            if(sentence.upper().rstrip() == 'Quit'.upper()):
                print("Quitting...")
                sentence = "\rQuit\n" + str(9)
                p_name.send(sentence.encode())
                connectionSocketArr.update({p_id:p_name})
                p_server.board = save_board
                p_server.player_won = 0
                
                del p_server.player_sock_list[p_id]
                break
            elif(sentence.upper().rstrip() == 'Board'.upper()):
                sentence = p_server.board + str(p_server.server_code)
                p_name.send(sentence.encode())
            # ADD help options

            # -- Handles the choices -- #
            elif(sentence.upper().rstrip() == '1' and game_stop is False):
                toe_choice('1', symbol, p_name, p_id, p_num, p_server, squares)
            elif(sentence.upper().rstrip() == '2' and game_stop is False):
                toe_choice('2', symbol, p_name, p_id, p_num, p_server, squares)
            elif(sentence.upper().rstrip() == '3' and game_stop is False):
                toe_choice('3', symbol, p_name, p_id, p_num, p_server, squares)
            elif(sentence.upper().rstrip() == '4' and game_stop is False):
                toe_choice('4', symbol, p_name, p_id, p_num, p_server, squares)
            elif(sentence.upper().rstrip() == '5' and game_stop is False):
                toe_choice('5', symbol, p_name, p_id, p_num, p_server, squares)
            elif(sentence.upper().rstrip() == '6' and game_stop is False):
                toe_choice('6', symbol, p_name, p_id, p_num, p_server, squares)
            elif(sentence.upper().rstrip() == '7' and game_stop is False):
                toe_choice('7', symbol, p_name, p_id, p_num, p_server, squares)
            elif(sentence.upper().rstrip() == '8' and game_stop is False):
                toe_choice('8', symbol, p_name, p_id, p_num, p_server, squares)
            elif(sentence.upper().rstrip() == '9' and game_stop is False):
                toe_choice('9', symbol, p_name, p_id, p_num, p_server, squares)
            else:
                send_all(p_server.player_sock_list, sentence, p_id)
                #notify_player_turn(p_name, p_id, p_num) # Change for later
        except Exception as e:
            print(e)
            break
        
def sock_f(s_name, s_delay, s_id):
    sentence = "!kill" + str(9)
    s_name.send(sentence.encode())
    s_name.close()
    
def sock_i(s_name, s_delay, s_id):
    global server_list
    global users_list
    global name_counter
    
    while(True):
        try:
            sentence = s_name.recv(1024).decode()
            # -- Handles when the client quits -- #
            if(sentence.upper().rstrip() == '!help'.upper()):
                continue
            
            if(sentence.upper().rstrip() == '!close'.upper()):
                sentence = "!close" + str(9)
                s_name.send(sentence.encode())
                s_name.close()
                del users_list[s_id] # Remove from users list
                print(len(users_list.keys()))
                del connectionSocketArr[s_id]
                name_counter = name_counter - 1
                break
            
            # -- Handles Tic Tac To Game -- #
            if(sentence.upper().rstrip() == '!play'.upper()):
                #sentence = "!play" + str(9)
                #s_name.send(sentence.encode)
                if(len(server_list) == 0):
                    new_server = Server()
                    new_server.board = "\n 1|2|3 \n"\
                                            " _|_|_ \n"\
                                            " 4|5|6 \n"\
                                            " _|_|_ \n"\
                                            " 7|8|9 \n"\
                                            "  | |  \n"

                    new_server.player_list = []
                    new_server.player_sock_list = {}
                    new_server.player_count = 2
                    new_server.player_count_tic = 0
                    new_server.player_turn = 1 # Keeps track of whose turn it is
                    new_server.player_won = 0
                    new_server.server_code = 9
                    
                    server_list += [new_server]
                    new_server.player_sock_list.update({s_id:s_name})
                    print("%d wants to play!" % s_id)
                    del connectionSocketArr[s_id]
                    new_server.player_list+=[s_id]
                    new_server.player_count_tic += 1
                    #print(new_server.player_list)
                    s_num = 1
                    toe_board(s_name, s_id, s_num, new_server)
                else:
                    server = find_open_server()
                    if(server != "New Server"):
                        server.player_sock_list.update({s_id:s_name})
                        print("%d wants to play!" % s_id)
                        del connectionSocketArr[s_id]
                        server.player_list+=[s_id]
                        server.player_count_tic += 1

                        if(len(server.player_list)==1):
                            s_num = 1
                        else:
                            s_num = 2
                        toe_board(s_name, s_id, s_num, server)
                    else:
                        new_server = Server()
                        new_server.board = "\n 1|2|3 \n"\
                                            " _|_|_ \n"\
                                            " 4|5|6 \n"\
                                            " _|_|_ \n"\
                                            " 7|8|9 \n"\
                                            "  | |  \n"

                        new_server.player_list = []
                        new_server.player_sock_list = {}
                        new_server.player_count = 2
                        new_server.player_count_tic = 0
                        new_server.player_turn = 1 # Keeps track of whose turn it is
                        new_server.player_won = 0
                        new_server.server_code = 9
                        
                        server_list += [new_server]
                        new_server.player_sock_list.update({s_id:s_name})
                        
                        print("%d wants to play!" % s_id)
                        
                        del connectionSocketArr[s_id]
                        
                        new_server.player_list+=[s_id]
                        new_server.player_count_tic += 1
                        
                        s_num = 1
                        toe_board(s_name, s_id, s_num, new_server)
                        
            send_all(connectionSocketArr, sentence, s_id) # Send the message to all other users
        except Exception as e:
            print(e)
            break
    
# --- Function to send messages to ever player --- #
def send_all(arr, sentence, s_id):
    for key, value in arr.items():
        if(int(key) != int(s_id)):
            new_sentence = sentence + str(s_id)
            value.send(new_sentence.encode())

# --- Server information --- #
serverPort = 12000
serverSocket = socket(AF_INET, SOCK_STREAM)
serverSocket.bind(('', serverPort))
serverSocket.listen(1)

print('The server is ready to receive')

# -- Spawns the lobby system -- #
try:
    threading.Thread(target=lobby, args=()).start()
except:
    print("Error: Unable to start server thread")
