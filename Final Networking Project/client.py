from socket import *
import threading
import time
import os
import sys
import logging
import signal

# --- Global variables here --- #
flag = False # Flag to check if connection is still open
play_flag = False
stop_play = False
clear = lambda: os.system('cls') # Used to clear the screen

check = False
modifiedSentence = ''
mSentence = ''
playerId = ''
turn = False # Variable for your player turn
file_server = open('logging.txt', 'w')

    
board = "\n|1|2|3|\n"\
        "|_|_|_|\n"\
        "|4|5|6|\n"\
        "|_|_|_|\n"\
        "|7|8|9|\n"\
        "| | | |\n"
board = "\n 1|2|3 \n"\
        " _|_|_ \n"\
        " 4|5|6 \n"\
        " _|_|_ \n"\
        " 7|8|9 \n"\
        "  | |  \n"

def write_word():
    word = ("\rFrom Player %d: %s" % (playerId, mSentence)) # Prints the message with the playerId, carriage return to erase current line

    if(playerId == 0):
        sys.stdout.flush()
    elif(mSentence.upper().strip('\r\n') == "Quit".upper()):
        print_you()
    elif(playerId == 9 and mSentence.strip('\r\n').upper()):
        word = ("\r%s" % mSentence)
        server_print(word)
        print_you() # This extra You: is necessary for presentation
    elif(mSentence.strip('\r\n').upper() != "!Play".upper() and mSentence.strip('\r\n').upper() != "From Player 9: Game Ready"):
        server_print(word)
        print_you() # This extra You: is necessary for presentation
    
def print_you():
    print('\rYou: ', end='', flush=True)
    
def sentence_rcv():
    global modifiedSentence
    global mSentence
    global playerId
    global turn
    global stop_play
    global play_flag
    
    modifiedSentence = clientSocket.recv(1024)
    mSentence = modifiedSentence.decode()
    mSentenceId = mSentence.strip('\r\n')
    playerId = int(mSentenceId[-1]) # Sets the player ID
    mSentence = mSentence.strip(str(playerId)) # Strips the Id from the message
        
    if(mSentence.upper() == 'Quit'.strip('\r\n').upper()):
        stop_play = True
        play_flag = False
        #mSentence = ''
    if(mSentence.strip('\r\n').upper() == 'Your Turn'.upper()):
        mSentence == ''
        server_print("\rYour turn\n")
        print_you()
        turn = True
        
# --- Tic Tac To game --- #
def tic_game_send():
    global stop_play
    global turn
    global modifiedSentence
    global mSentence
    global playerId
    
    sys.stdout.flush()
    while(stop_play is False):
        print_you()
        
        sentence = sys.stdin.readline()
        turn = False

        if stop_play is True or sentence.upper().strip('\r\n') == "Quit".upper():
            clientSocket.send(sentence.encode())
            sys.stdout.flush()
            clear()
            stop_play = True
            
        clientSocket.send(sentence.encode())
        sys.stdout.flush()

# --- Used to print words --- #
def server_print(word):
    sys.stdout.flush()
    sys.stdout.write(word)
    sys.stdout.flush()


# --- The recieving function for the tic-tac-tac game --- #    
def tic_game_recv(player_two):
    global stop_play
    global turn
    global play_flag
    global check

    tic_flag = 0
    play_flag = True
    sys.stdout.flush()
    write_word()
    clear()
    print_you()
    
    sentence = "!Play"
    clientSocket.send(sentence.encode())
    server_print("\n")

    sentence_rcv()
    
    write_word()
    clear()
    print_you()
    clear()
    
    server_print("\rWelcome to the Tic-Tac-Toe game!!!\n")
    server_print("\rType and enter the letter you want your mark on\n\n")
    server_print("\rEnter quit to quit\n\n")
    
    server_print(board + '\n')
    
    print_you()
    while(stop_play is False):
        sentence_rcv()
        if stop_play is True or mSentence.strip('\r\n').upper() == "Quit".upper():
            stop_play = True
            play_flag = False
            turn = False
            break
        if(mSentence.strip('\r\n').upper() == 'Your Turn'.upper()):
           turn = True
           continue
        if(mSentence != ''):
            write_word()
    
    
# --- The normal client_send function to handle chat room messages ---#
def client_send():
    global play_flag
    clear()
    word = ("\rWelcome to the Tic-Tac-Toe chat room!!!\n\n")
    server_print(word)

    word = ("\rType !play to play Tic-Tac-Toe\n")
    server_print(word)

    word = ("\rType !help for list of commands\n")
    server_print(word)

    word = ("\rType !close to leave server\n\n")
    server_print(word)
    
    while True:
        if(flag is False and play_flag is False):
            print_you()
            sentence = sys.stdin.readline()
            clientSocket.send(sentence.encode())
            if(sentence.strip('\r\n').upper() == "!play".upper()):
                play_flag = True
                tic_game_send()
                play_flag = False
            if(sentence.strip('\r\n').upper() == "!help".upper()):
                server_print("\n!play: Plays a game\n!help: Prints commands\n!close: Leaves the server\n\n")
            if(sentence.strip('\r\n').upper() == "!close".upper()):
                return
            
            #if(sentence.strip('r\n').upper() == "Quit".upper()):
            #    play_flag = False
                
            sys.stdout.flush()
        elif(play_flag is True):
            tic_game_send()
            play_flag = False
        else:
            break


def client_recv():
    global flag
    global play_flag
    global mSentence
    global stop_play
    global file_server
    while True:
        if(flag is False):
            stop_play = False
            # -- Receives and decodes the modified sentence -- #
            sentence_rcv()
            if(mSentence.upper() == "!kill".upper() and playerId == 9):
                exit()
            if(mSentence.upper() == "!close".upper()):
                return
            if(mSentence.upper().strip('\r\n') ==  'Game Ready'.upper()):
                play_flag = True
            if(play_flag is False):
                if(mSentence.strip('\r\n').upper() != "Quit".upper()):
                    write_word()
                    
                # -- Checks to see if the disconnect has happened -- #
                if(mSentence == "Disconnect"):
                    flag = True
                    break
            else:
                play_flag = True
                word = ("\rWaiting for another player")
                server_print(word)
                while(True):
                    sentence_rcv()
                    if(mSentence.strip('\r\n').upper() == "!play".upper()):
                        mSentence = ''
                        break
                other_player = playerId
                tic_game_recv(other_player)
                file_server.write("Out of tic_game_recv now\n")
                if(mSentence.strip('\r\n').upper() != "Quit"):
                    write_word()
                print_you()

                play_flag = False
                stop_play = False
        else: 
            break

# --- Server information for client to connect here --- #
clientSocket = socket(AF_INET, SOCK_STREAM)

if len(sys.argv) != 3:
    print("Usage: python client.py [Server IP Address] [Server Port]")
    exit()
    
server_ip = str(sys.argv[1])
server_port = int(sys.argv[2])

not_con = False

threads = [] # Keeps track of the client_send and client_recv threads

# -- Tries to connect to the server first -- #
try:
    clientSocket.connect((server_ip, server_port))
except Exception as e:
    exit()
    print(e)
    not_con = True

# -- If connect then start client_send and client_recv threads -- #
if(not_con is False):
    try:
        thread_send = threading.Thread(target=client_send)
        thread_send.start()
        thread_recv = threading.Thread(target=client_recv)
        thread_recv.start()
        
        threads.append(thread_send)
        threads.append(thread_recv)
    except Exception as e:
        print(e)


    for t in threads:
        t.join()
    clientSocket.close()

    clear()
    file_server.close()
    exit()


