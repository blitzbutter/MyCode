// Simple ping sweep in bash
#bin/bash

# Jacob Bryant
# 11/16/2016

helpme ()
{
	echo "Usage: -s to ping sweep"
	return
}

sweep_network () {	# 3
	for ip in $(seq 1 24); do
		ping -c 1 10.11.1.$ip >> pdata.txt & # 2
	done
	wait
	echo "Up:"
	grep "bytes from" pdata.txt |cut -d" " -f 4| cut -d ":" -f 1|sort -u
	# grep "Destination Host Unreachable	
	return
}


sweep=
help=
love=

while [[ -n $1 ]]; do
	case $1 in
		-h | --help)		shift
					help=1
					;;
		-s | --sweep)		sweep=1
					;;
		-l | --love)		love=1
					;;
	esac
	shift
done

if [[ -n $help ]]; then
	helpme
fi

if [[ -n $sweep ]]; then
	sweep_network	
fi	

if [[ -n $love ]]; then
	echo "I love RHIA <3 <3 <3 <3 <3"
	echo "~~~More than the moon and the stars~~~"
	echo "~~~Thank God I have someone who undestands~~~"
	echo "~~~Why I am passionate for stupid stuff like this~~~"
	echo "------mmmmmm  mmmmmm-----"
	echo "----m       mm      m----"
	echo "---m        mm        m---"
	echo "---m                  m---"
	echo "----m   Mush Mush:)  m----"
	echo "-----m              m-----"
	echo "------m            m------"
	echo "-------m          m-------"
	echo "--------m        m--------"
	echo "---------m      m---------"
	echo "----------m    m----------"
	echo "-----------m  m-----------"
	echo "------------mm------------"
fi

