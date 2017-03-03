touch example.txt

echo "The cow went to the moon." > example.txt
echo "Redirect Output: " 
cat example.txt

echo "Pipe Output: "
cat example.txt |grep "The cow" example.txt |cut -d" " -f 2 
