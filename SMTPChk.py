// SMTP Checker in Python
# Script for scanning
# By Jacob Bryant


#!/usr/bin/python

import socket
import sys
import os

os.system("touch results-file.txt")

ip = ['']
place = 0
i = 0
read_results = open("results-file.txt", "w+")

def res_file(res,des):	# res=results file, des=file descriptor
	des = open(res, "w+")

def ip_array(ip_file):
	place=0
	i=0
	with open(ip_file, "r+") as f:
        	lines = f.readlines()
        	for i in range(0, len(lines)):
                	line = lines[i]
                	line_final = line.replace("\n", "")
                	line_final_var = "%s" %line_final
                	ip.insert(place, line_final_var)
                	place+=1

def test_socket(ip_num):
	s=socket.socket(socket.AF_INET, socket.SOCK_STREAM)

	try:
		connect=s.connect((ip[ip_num],25))
		banner=s.recv(1024)
		read_results.write(banner)

		s.send('VRFY ' + sys.argv[1] + '\r\n')
		result=s.recv(1024)
		read_results.write(result)
		read_results.write('\n\n')
	except:
		read_results.write('\n')
		read_results.write("-----------")
		read_results.write(ip[ip_num] + " did not respond")
		read_results.write("-----------")
		read_results.write('\n')
	s.close()

def scan_snmp(pString, ip_arr):
	for i in range(0, len(ip_arr)-1):
		sys_par = "%s " %ip_arr[i] + "%s" %pString	
		os.system("echo " + "---------" + ">> SNMP-Check.txt")	
		os.system("echo " + "---------" + ">> SNMP-Check.txt")	
		os.system("echo " + sys_par + " >> SNMP-Check.txt")	
		os.system("echo " + "---------" + ">> SNMP-Check.txt")	
		os.system("echo " + "---------" + ">> SNMP-Check.txt")
		os.system("snmpwalk -c public -v1 " + sys_par + ">> SNMP-Check.txt")	
		
res_file("results-file.txt", read_results)
ip_array("snmp-ip.txt")

scan_snmp('', ip)
scan_snmp('1.3.6.1.4.1.77.1.2.25 ', ip)
scan_snmp('1.3.6.1.2.1.25.4.2.1.2 ', ip)
scan_snmp('1.3.6.1.2.1.6.13.1.3 ', ip)
scan_snmp('1.3.6.1.2.1.25.6.3.1.2 ', ip)

# for bounds checking
# range(len(ip)/2)
# range(0, (len(ip)-1)/2)

# for i in range(0, (len(ip)):
	# test_socket(i)
	# print ip[i]

read_results.close()
