// Simple ping sweep in python
# Jacob Bryant
# 11/18/2016

import os 
import sys
import getopt
import time
# import threading

# threads = 1
subnet = "10.11.1."
sBool = ""

try: 
	opts, args = getopt.getopt(sys.argv[1:], "s", ["scan"])
except:
	print("Usage: -s or --scan to ping sweep")

for o,a in opts:
	if o in ("-s","--scan"):
		sBool = True
	
def scanner():
	for x in range(1, 25): 
		n = "%d" % (x)
		response = os.system("ping -c 1 " + subnet + n + " >>" + " pdata.txt" + " &")
	file_c()

def file_c():
	time.sleep(1)
	os.system("echo UP:")
	os.system("grep \"bytes from\" pdata.txt |cut -d\" \" -f 4 |cut -d \":\" -f 1 |sort -u")

if sBool:
	try:
		os.system("rm pdata.txt")	
	except: 
		print("Usage: pdata.txt does not exist yet")
	for i in range(threads):
 		si = threading.Thread(target=scanner)
 		si.start()
