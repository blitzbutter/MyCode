### Jacob Bryant ###
### 11/12/2018   ###
### ASCII Art Pt1###

# --- Variables for multiple purposes --- #
row_count = 0 # Counter for rows
col_count = 0 # Counter for columns

# --- String format parts for data entries --- #
# start = "db \'" # Start of data entry
start = "db 1bh, \"" # Start of data entry

end = "\"" # End of data entry
num = "{0:02d}" # For the row and column text entries [Use num.format(row|col)]

# --- File processing -- #
# !!! Maybe nest the open calls in try-catch statements for error detection
tank_f = open('tank.txt') # Tank file--the ascii art
ascii_f = open('ascii_data.txt', 'w')

row = ""
col = ""
word = ""

line = tank_f.readline()

while(line is not ''):
    # print(len(line))
    
    for i in range(0, len(line)):
        row = num.format(row_count)
        col = num.format(col_count)
        # -- Escape newline -- #
        if(line[i] == '\n'):
            break
        # word = start + line[i] + row + col + end
        word = start + '[' + row + ';' + col + 'H' + line[i] + end
        
        ascii_f.write(word+'\n')
        col_count += 1
        
    line = tank_f.readline()
    row_count += 1
    col_count = 0

print("Done!")
tank_f.close()
ascii_f.close()