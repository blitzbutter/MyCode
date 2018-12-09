### Jacob Bryant
### 12/8/2018
### Program to calculate the network address

# --- Takes a string to ask a user as input --- #
def calc_bin(user_in):
    bin_f = "{0:08d}" # Pads binary numbers with 8 bytes

    # --- Formats the input IP address and converts it to binary --- #
    ip_in = input(user_in)
    ip = ip_in.split('.')
    ip_bin = ""

    for i in range(0, len(ip)):
        ip[i] = bin(int(ip[i])) # Converts it to binary
        ip[i] = ip[i].replace('0b','') # Removes the leading '0b'
        ip[i] = bin_f.format(int(ip[i])) # Formats the input to pad with eight zeroes
        ip_bin += ip[i] # Adds it to the string of binary numbers
        
    return ip_bin

# --- Ask the user for the IP and netmask --- #
ip_str = calc_bin("Enter IP: ")
net_str = calc_bin("Enter netmask: ")

# --- Lets the user know what operation is happening --- #
print("ANDing %s" % ip_str)
print("       %s" % net_str)

network_ip = "" # Network IP variable

# --- And each individual bit in the IP and netmask string of bits, then store in network_ip --- #
for i in range(0, len(ip_str)):
    bit = int(ip_str[i]) & int(net_str[i]) # Ands each individual bit
    network_ip += str(bit)

# --- Splits the string of bits into 4 parts, to convert back to their decimal counterparts --- #
n = 8
network_ip = [network_ip[i:i+n] for i in range(0, len(network_ip), n)]

# --- Converts back to decimal format and formats the output nicely --- #
network_ip_out = ""
for i in range(0, len(network_ip)):
    network_ip_out += str(int(network_ip[i], 2))
    if i != len(network_ip)-1:
        network_ip_out += '.'

print("Network address is %s" % network_ip_out) # Prints the network IP
