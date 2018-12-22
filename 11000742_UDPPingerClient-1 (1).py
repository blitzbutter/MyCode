# Jaocb Bryant
# Simple UDP Pinger program
# 9/14/2018
# Theory of Data Communications

from socket import *
import time

# Server IP and port
s_Name = '127.0.0.1'
s_Port = 12000

# Client socket setup
c_Socket = socket(AF_INET, SOCK_DGRAM) # Creates a UDP socket
c_Socket.settimeout(1) # Timeout of socket set to 1 second

for i in range(1,11):
    send_time = time.time()
    c_Msg = 'Ping ' + str(i) + ' '
    c_Socket.sendto(c_Msg.encode(),(s_Name, s_Port))
        
    try:
        modified_Msg, s_Address = c_Socket.recvfrom(2048)
        recv_time = time.time()
        rtt = round(recv_time - send_time, 3) # Calculates the round time
        new_Msg = modified_Msg.decode()
        print(str(new_Msg)+str(rtt))
    except OSError as e: # Since the exception is a subclass of OSError, use OSError
        # Prints a socket timeout message to inform user of packet loss
        print("Request timed out")
        continue

# Closes our UDP socket
c_Socket.close()
