; Jacob Bryant
; Happy Python
; 12/09/2018
; Desc: The user plays a game of Happy Python (Snake) by typing in directions for the snake to travel

;;;;;  OpenFile needs permissions   0=readonly   101h = write
%macro OpenFile 1
        pusha
        mov     eax,5
        mov     ebx,fileName
        mov     ecx,%1
        mov     edx,777h
        int     80h
        mov     [fileDescriptor],eax
        popa
%endmacro

;;;; ReadFile from file needs 3 parms:  file descriptor (var), input buffer (var), length
%macro ReadFile 3
        pusha
        mov     eax,3 ;sys read
        mov     ebx,[%1]
        mov     ecx,%2
        mov     edx,%3
        int     80h
        popa
%endmacro

;;;; WriteFile needs 3 parms: file descriptor (var), buffer (var), length
%macro WriteFile 3
        pusha
        mov     eax,4
        mov     ebx,[%1]
        mov     ecx,%2
        mov     edx,%3
        int     80h
        popa
%endmacro

;;;;; CloseFile needs no arguments
%macro CloseFile 0
        pusha
        mov     eax,6
        mov     ebx,[fileDescriptor]
        int     80h
        popa
%endmacro

;;;;; NormalTermination needs no args
%macro NormalTermination 0
	Write conColor, 4

        mov eax, 1
        mov ebx, 0
        int 80h
%endmacro

;;;;; Finds the tenths and ones position
%macro CalcTenth 1
	pusha
	
	; -- Divides the number by ten, sets the ones place to the remainder and tenths to the dividend -- ;	
	mov ecx, %1
	xor edx, edx
	mov eax, ecx
	mov ebx, 10
	div ebx
	
	mov DWORD[ones], edx
	mov DWORD[tenths], eax
	popa
%endmacro

;;;;; Finds the hundreds, tenths, and ones position
%macro CalcHundred 1
	pusha
	
	; -- Divides the number by ten, sets the ones place to the remainder and tenths to the dividend -- ;	
	mov ecx, %1
	xor edx, edx
	mov eax, ecx
	mov ebx, 10
	div ebx
	
	mov DWORD[ones], edx
	
	xor edx, edx	
	mov ebx, 10
	div ebx 
	
	mov DWORD[tenths], edx
	mov DWORD[hundreds], eax
	popa
%endmacro

;;;;; Macro to write the coordinates of the cursor position
%macro FillCoord 2
	pusha
	; -- Sets up the x ord -- ;
	CalcTenth %1

	; - Tenths place of x ord - ;
	mov ah, [tenths]
	add ah, '0'
	mov [cursor + 2], ah

	; - Ones place of y ord - ;
	mov ah, [ones]
	add ah, '0'
	mov [cursor + 3], ah
	
	; -- Sets up the y ord -- ;
	CalcTenth %2 

	; - Tenths place of y ord - ;
	mov ah, [tenths] 
	add ah, '0' 
	mov [cursor + 5], ah 
	 
	; - Ones place of y ord - ;
	mov ah, [ones] 
	add ah, '0' 
	mov [cursor + 6], ah
	popa
%endmacro

;;;;; Clears the screen
%macro Cls 0
	pusha
	mov eax, 4
	mov ebx, 1
	mov ecx, cls
	mov edx, 4
	int 80h
	popa
%endmacro

;;;;; Prints a word to the screen
%macro Write 2
	pusha
	mov eax, 4
	mov ebx, 1
	mov ecx, %1 ; Pointer to the string
	mov edx, %2 ; Length of the string to print
	int 80h
	popa
%endmacro

;;;;; Writes a character a position
%macro WriteChar 3
	FillCoord %2,%3 
        mov ah, [%1]
        mov [cursor+8], ah
        Write cursor,9
%endmacro

;;;; WriteWord pointerWord, wordLength, row
%macro WriteWord 3
	pusha
	mov ebx, 1
	mov ecx, [%2]
	mov edx, %1
	mov eax, %3
	call _loopChar
	popa
%endmacro

;;;;; Struct to contain the snake's body parts
STRUC sStruct
	.esc:   RESB 2 ; There wasn't a colon here
	.row:  RESB 2 ; Two digit number
	.semi: RESB 1 ; Space for ;
	.col:  RESB 2 ; Two digit number
	.H:     RESB 1 ; Space for H
	.char: RESB 1 ; Character to store at location
	.size:
ENDSTRUC

;;;;; Write a macro that takes two argument, x and y ord, checks to see the character at the position, returns it

SECTION .data
; define data/variables here.  Think DB, DW, DD, DQ
board:  db "********************************************************************************" 
        db "*.           .             *              .            *           .          .*" 
        db "*      *************       *        *************      *       *********       *" 
        db "*            .             *              .            *           .           *" 
        db "*                          *                           *                       *" 
        db "*                          *                           *                       *" 
        db "*                                                                              *" 
        db "*           **************************        ***********************          *" 
        db "*                                * ........      *                             *" 
        db "*                                * .   ***********                             *" 
        db "*                          *     * ...........   *     *                       *" 
        db "*                .         *     **********  .   *     *        .              *" 
        db "*                          *     * ...........   *     *                       *" 
        db "*                          *     *      **********     *                       *" 
        db "*                          *                           *                       *" 
        db "*                                                                              *" 
        db "*           ***   ***   ***   ***   ***   ***   ***   ***   ***   ***          *" 
        db "*                                                                              *" 
        db "*            *     *     *     *     *  .  *     *     *     *     *           *" 
        db "*               *     *     *     *  *******  *     *     *     *              *" 
        db "*            *  .  *     *     *     * 000 *     *     *     *  .  *           *" 
        db "*               *     *  .  *     *  *******  *     *  .  *     *              *" 
        db "*            *     *     *     *     *  .  *     *     *     *     *           *" 
        db "*.              *     *     *     *     *     *     *     *     *             .*" 
        db "********************************************************************************" 


boardSize: dd $-board
board_width: dd 25
board_height: dd 80

python: db "@*****> " ; Note that this includes an extra space!
pythonLen: dd $-python
python_start: dd 40
python_stop: dd 40+pythonLen

pythonColor: db 1bh, "[32m"
dotColor: db 1bh, "[31m"
regColor: db 1bh, "[96m"
scColor: db 1bh, "[35m"
conColor: db 1bh, "[0m"

stdin   dd      0  ; used for read from stdin
stdout  dd      1  ; used for write to stdout
sleeping: db 'z'

cls: db 1bh, '[2J'
sec: dd 1, 0

cursor: db 1bh, "[00;00H "
ret_cursor: db 1bh, "[00;00H "

hundreds: dd 0
tenths: dd 0
ones: dd 0

fileName:       db      './move.txt',0 ; Change file name later
fileDescriptor: dd      0

out_flag: dd 0
counter: dd 0

user_input: db 'a' ; Start left
head_col: db ' ' ; Holds what character the head has run into

lose: db "You lose!", 0ah
loseLen: dd $-lose

win: db "You win!", 0ah
winLen: dd $-win

dotCount: dd 0 ; Keeps tracks of how many dots are on board
dotScore: dd 0 ; Keeps track of score
score: db "000" ; Edit this to update score

SECTION .bss
LEN     equ     1024
inputBuffer     RESB LEN
childPID        RESD 1

snake:	RESB sStruct.size*(pythonLen-python) ; Array of snake body structs

SECTION .text
global _main, _sleep1
_main:    
	Cls  

	Write regColor, 5
	mov ecx, [board_width]    
	out:   
		push ecx    

		; -- Gets the current board_width value -- ;
		mov edx, [board_width]
		sub edx, ecx
		inc edx ; Increase by 1 to center properly
		mov [out_flag], edx

		mov ecx, [board_height]    
    
		in:    
		push ecx 

		; -- Fills the coordinates of the cursor -- ; 
		mov edx, [board_height]
		sub edx, ecx
		mov ecx, edx
		inc ecx ; Increase by 1 to center properly
		
		FillCoord [out_flag], ecx ; Fill the coordinates   
		
		; -- Sets the character at the end of cursor from board -- ; 
		mov ecx, board
		add ecx, [counter] 
		mov ah, [ecx] 
		mov [cursor+8], ah 
		
		; -- Counts the dots on the board -- ;
		cmp ah, '.'
		je dotAdd
		dotSpawn: ; Place to return to
	
		; -- Score count color -- ;
		cmp ah, '0'
		je scoreColor
		scoreSpawn:
	
		; -- Prints the character of the board -- ; 
		Write cursor, 9
		Write regColor, 5	
		pop ecx	  
 
		; -- Decrement the counter -- ; 
		mov eax, [counter]    
		inc eax    
		mov [counter], eax   
    
		; -- Jump back to inner loop -- ;
		dec ecx    
		jnz in
		    
		; -- Jump back to outer loop -- ;
		pop ecx    
		dec ecx    
		jnz out  


;;; The next function should spawn the snake, making the array of structs and such 
spawn: 
	mov ebx, snake ; Array of structs for the snake's body 
	mov ecx, [pythonLen] ; Length of the python 
	mov edx, python ; Pointer to python string 
spawnTop: 
	; -- Convert the first part of the cursor, including the column -- ; 
	mov BYTE[ebx], 1bh 
	mov BYTE[ebx+1], '[' 
	mov BYTE[ebx+2], '0'
	mov BYTE[ebx+3], '7'
	mov BYTE[ebx+4], ';' 
	
	; -- Calculates the column 
	CalcTenth [python_start] 
	
	; -- Calculates the tenths place
	mov ah, [tenths] 
	add ah, '0' 
	mov BYTE[ebx+5], ah 

	; -- Calculates the ones place
	mov ah, [ones] 
	add ah, '0' 
	mov BYTE[ebx+6], ah
	
	mov BYTE[ebx+7], 'H' 
	 
	; -- Sets the character of the snake -- ; 
	mov ah, BYTE[edx] 
	mov BYTE[ebx+8], ah

	; -- Increase the snake right position -- ; 
	mov eax, [python_start] 
	inc eax 
	mov [python_start], eax 
	 
	inc edx ; Next character in the string 
	add ebx, sStruct.size
	
	dec ecx 
	jnz spawnTop 
	
	Write pythonColor, 5
	call _displaySnake
	Write regColor, 5
	
;;;;; Forks the child process from the parent process 
proc:		    
        ;;;;   FORK 2 PROCS    
        mov     eax,2    
        int     80h     
        cmp     eax,0    
        je      childProc    
        mov     [childPID],eax    
parent:    
        call    _getCode ; Checks the file to see what to do    
	
	mov BYTE[user_input], al ; Stores the user input    
	
	call _snakeMove
	call _checkChar ; Checks what character the head is at
	
	; -- If it is a * then end it
	mov bh, [head_col]
	
	cmp bh, '*'
	je gameLose
	
	cmp bh, 'W'
	je gameWin
	
	mov edx, [dotCount]
	cmp edx, [dotScore]
	je gameWin

        cmp     al,'q' ; Compares to check if equal to q    
        je      theEnd ; Quits if equal to q    
    
        ; sleep one second    
; --- This is where the framerate can be controlled    
time2sleep:    
        call _sleep1    
        jmp parent    
    
; --- The childProc reads user input and writes to a file, looping continously --- ;    
childProc:    
    
        ReadFile stdin, inputBuffer, LEN    
    
        ;; Open a file for communication    
        OpenFile 101h  ; for writing    
    
        ;;; write  something to  bob.txt    
        WriteFile fileDescriptor, inputBuffer, 1    
    
        ;;;  close the file    
        CloseFile    
    
        jmp childProc    
    
; Normal termination code    
theEnd:    
        ;;;;;;  Kill the child process    
        mov     eax,37 ; sys_kill    
        mov     ebx,[childPID] ; process ID : int pid    
        mov     ecx,9  ; kill signal : int sig    
        int     80h ; Call system interrupt    
        NormalTermination ; Normal exit code  
  
; Adds to the number of dots needed to win
dotAdd:
	Write dotColor, 5
	mov eax, [dotCount]
	inc eax
	mov [dotCount], eax
	jmp dotSpawn

scoreColor:
	Write scColor, 5
	jmp scoreSpawn

; Function to handle when player loses
gameLose:
	mov eax, 37
	mov ebx, [childPID]
	mov ecx, 9
	int 80h

	Cls

	WriteWord lose, loseLen, 31
	
	FillCoord 32, 1
	mov ah, ''
	mov [cursor+8], ah
	Write cursor,9
	
	FillCoord 33, 1
	mov ah, ''
	mov [cursor+8], ah
	Write cursor,9

	Write conColor, 4	
	NormalTermination

; Function to handle when player wins
gameWin:
	mov eax, 37
	mov ebx, [childPID]
	mov ecx, 9
	int 80h

	Cls

	WriteWord win, winLen, 31
	
	FillCoord 32, 1
	mov ah, ''
	mov [cursor+8], ah
	Write cursor,9
	
	FillCoord 33, 1
	mov ah, ''
	mov [cursor+8], ah
	Write cursor,9

	Write conColor, 4	
	NormalTermination

;;;  GetCode - reads file to see what to do - no args passed in, returns char in AL    
_getCode:    
        OpenFile 0  ; readonly    
        ReadFile        fileDescriptor, inputBuffer, 2    
        CloseFile    
        mov     al, BYTE [inputBuffer]
        ret    
 
;;; Checks char at head 
_checkChar:
	pusha
	mov ebx, snake
	
	; -- Get ROW
	mov ah, BYTE[ebx + 2]
	sub ah, '0' ; Turn into an int
	mov al, 10 
	mul ah  

	mov dh, BYTE[ebx + 3] 
	sub dh, '0'

	movsx cx, dh
	add ax, cx
	push ax ; Stores the row value for later multiplication

	; -- Get COL  
	mov ah, BYTE[ebx + 5]
	sub ah, '0'
	mov al, 10 
	mul ah
	
	mov dh, BYTE[ebx + 6]  
	sub dh, '0'

	movsx cx, dh
	add ax, cx

	; -- (Row * 80) + Col -- ;
	pop bx
	dec bx ; Must decrease column
	push ax
	
	mov ax, 80
	mul bx
	
	pop bx
	add ax, bx
	dec ax ; Must decrease row
		
	; -- Jump to character at location -- ;
	movsx edx, ax
	mov ecx, board
	add ecx, edx
	mov ah, BYTE[ecx]
	
	cmp ah, '.'
	je dotHit
	dotBack:

	mov [head_col], ah ; Stores the character that the head hit
	popa
	ret

;;; Handles when a dot is hit
dotHit:
	Write scColor, 5
	mov ebx, [dotScore]

	; -- Gets rid of the dot
	mov ecx, board
	add ecx, edx
	mov dh, ' '
	mov [ecx], dh
	inc ebx
	mov [dotScore], ebx
	
	; -- Stores the current cursor
	mov ah, [cursor+2]
	mov [ret_cursor+2], ah
	
	mov ah, [cursor+3]
	mov [ret_cursor+3], ah

	mov ah, [cursor+5]
	mov [ret_cursor+5], ah

	mov ah, [cursor+6]
	mov [ret_cursor+6], ah

	; -- Generates the score board
	mov ecx, [dotScore]
	CalcHundred ecx

	mov ah, [hundreds]
	add ah, '0'
	mov [score], ah

	mov ah, [tenths]
	add ah, '0'
	mov [score+1], ah

	mov ah, [ones]
	add ah, '0'
	mov [score+2], ah

	mov ecx, score

	WriteChar ecx, 21, 40
	
	inc ecx
	WriteChar ecx, 21, 41

	inc ecx
	WriteChar ecx, 21, 42
	
	Write ret_cursor, 9
	
	Write regColor, 5
	jmp dotBack

;;; Sleep function - sleeps for one second    
_sleep1:    
        pusha    
        mov     eax,162    
        mov     ebx,sec    
        mov     ecx,0    
        int     80h    
        popa    
        ret    

_snakeMove:
	pusha
	Write pythonColor, 5
	; -- Checks for time change informationi
	mov ah, '1'
	cmp BYTE[user_input], ah
	je _timeOne
	
	mov ah, '2'
	cmp BYTE[user_input], ah
	je _timeHalf
	
	mov ah, '3'
	cmp BYTE[user_input], ah
	je _timeThird
	
	mov ah, '4'
	cmp BYTE[user_input], ah
	je _timeFourth

	; -- Checks for movement information
	mov ah, 'a'
	cmp BYTE[user_input], ah
	je _moveLeft 
	
	mov ah, 'w'
	cmp BYTE[user_input], ah
	je _moveUp
	
	mov ah, 'd'
	cmp BYTE[user_input], ah
	je _moveRight
	
	mov ah, 's'
	cmp BYTE[user_input], ah
	je _moveDown

	snakeRet: ; Return place for the other functions
	popa
	Write regColor, 5
	ret

; --- Time change functions --- ;
_timeOne:
	mov DWORD[sec], 1
	mov DWORD[sec+4], 0
	jmp snakeRet

_timeHalf:
	mov DWORD[sec], 0
	mov DWORD[sec+4], 500000000
	jmp snakeRet

_timeThird:
	mov DWORD[sec], 0
	mov DWORD[sec+4], 333333333
	jmp snakeRet

_timeFourth:
	mov DWORD[sec], 0
	mov DWORD[sec+4], 250000000
	jmp snakeRet

; --- Moves snake to left ---;
_moveLeft:
	call _adjustMessage	
        cmp     BYTE [ebx + sStruct.col + 1],'0'
        je      carryLeft
        dec     BYTE [ebx + sStruct.col + 1]            ;; move first char to left
        jmp     adjustLeft
carryLeft:
        dec     BYTE [ebx + sStruct.col]
        mov     BYTE [ebx + sStruct.col + 1],'9'
adjustLeft:
	call _displaySnake
	jmp snakeRet

; --- Moves snake up --- ;
_moveUp:
	call _adjustMessage	
        cmp     BYTE [ebx + sStruct.row + 1],'0'
        je      carryUp
        dec     BYTE [ebx + sStruct.row + 1]            ;; move first char to left
        jmp     adjustUp
carryUp:
        dec     BYTE [ebx + sStruct.row]
        mov     BYTE [ebx + sStruct.row + 1],'9'
adjustUp:
	call _displaySnake
	jmp snakeRet

; --- Moves snake right --- ;
_moveRight:
	call _adjustMessage	
        cmp     BYTE [ebx + sStruct.col + 1],'9'
        je      carryRight
        inc     BYTE [ebx + sStruct.col + 1]            ;; move first char to left
        jmp     adjustRight
carryRight:
        inc	BYTE [ebx + sStruct.col]
        mov     BYTE [ebx + sStruct.col + 1],'0'
adjustRight:
	call _displaySnake
	jmp snakeRet

; --- Moves snake down --- ;
_moveDown:
	call _adjustMessage	
        cmp     BYTE [ebx + sStruct.row + 1],'9'
        je      carryDown
        inc     BYTE [ebx + sStruct.row + 1]            ;; move first char to left
        jmp     adjustDown
carryDown:
        inc     BYTE [ebx + sStruct.row]
        mov     BYTE [ebx + sStruct.row + 1],'0'
adjustDown:
	call _displaySnake
	jmp snakeRet

; --- Adjust the message --- ;
_adjustMessage:
        mov     ecx,[pythonLen]
        dec     ecx
        mov     ebx,snake+((pythonLen-python)-1)*sStruct.size

_amTop: mov     dx,[ebx - sStruct.size + sStruct.row]   ;; get row above
        mov     [ebx + sStruct.row],dx                  ;; copy to this row

        mov     dx,[ebx - sStruct.size + sStruct.col]   ;; get col above
        mov     [ebx + sStruct.col],dx                  ;; copy to this col

        sub     ebx,sStruct.size
        loop    _amTop
	ret

; --- Displays the snake --- ;
_displaySnake:
	mov ebx, snake
	mov ecx, [pythonLen]

sOut:
	push ecx
	push ebx
	
	mov eax, 4
	mov ecx, ebx
	mov ebx, 1
	mov edx, 9
	int 80h
	
	pop ebx
	pop ecx

	add ebx, sStruct.size
	loop sOut
	
	; -- Sets cursor to below the board - ;
	FillCoord 28, 1
	mov ah, ''
	mov [cursor+8], ah
	Write cursor, 9
	ret

_loopChar:
	charLoop:
		WriteChar edx, 31, ebx
	
		inc ebx
		inc edx	
		dec ecx
		jnz charLoop
	ret
answer:


