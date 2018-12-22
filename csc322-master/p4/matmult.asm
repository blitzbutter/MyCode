; Jacob Bryant
; Assignment 4
; 10/29/2018
; This program takes two arrays and performs matrix multiplication on them, returning the resultant product array

X		EQU	4;4	;Rows for Mat1 and Mat3
Y		EQU	3;4	;Cols for Mat1 and Rows for Mat2
Z		EQU	2;2	;Cols for Mat2 and Mat3

SECTION .data
; MY test case
;M1		dd	 1, 2, 3, 4 
;		dd	 2, 3, 6, 7 
;		dd	 4, 7, 4, 1 
;		dd	 6, 3, 1, 2 
 
;M2		dd	 5, 6 
;		dd	 42, 6 
;		dd	 6, 78 
;		dd	 1, 5 

; YOUR test case
M1		dd	 1, 2, 2  
		dd	 3, 2, 1  
		dd	 1, 2, 3  
		dd	 2, 2, 2	  
  
M2		dd	 2, 4  
		dd	 3, 3  
		dd	 4, 6  

MAdd		dd	 0, 0, 0 ; Carry array for the three products

flag		dd	 0 ; General carry variable
hold		dd	 0 ; Holds the ecx of of the middle loop

in_f		dd	 0 ; Holds the ecx of the inner loop
out_f		dd	 0 ; Holds the ecx of the outer loop

a		dd	 0 ; Iterator for M2
b		dd	 0 ; Iterator for M1
c		dd	 0 ; Holds final value for M3


SECTION .bss
M3		RESD	 X*Z ; Result multiplication array

SECTION .text
global _main
_main:

mov eax, 0
mov ecx, X ; Starts with X in the outer loop
outer:
	push ecx ; Pushes outer loop ecx
	mov ecx, Z ; The middle loop will hold Z
	middle:
		push ecx ; Pushes the middle loop ecx
		mov ecx, Y ; Finally, the inner loop will have the value of Y
		inner:
			;============; Gets the index for M2
			; Stores the current ecx into two variables and pops the middle into it
			mov DWORD[flag], ecx
			mov DWORD[in_f], ecx
			pop ecx
			
			; Gets the current iteration of the middle loop
			mov eax, Z
			sub eax, ecx
			mov DWORD[a], eax
			
			; Gets the current iteration of the inner loop
			mov eax, DWORD[flag]
			mov edx, Y
			sub edx, eax
			xchg eax, edx
			
			; Multiplys the inner ecx by the value Z
			mov ebx, Z
			mul ebx
			
			; (Inner Ecx * Z) + (Middle Ecx)
			add eax, DWORD[a]
			xchg DWORD[a], eax
			;==============;
			push ecx ; Pushes the popped ecx back onto the stack
			
			;~~~~~~~~~~~~~~; Gets the index for M1
			; Stores the values of current index pointers into variables
			mov DWORD[flag], ecx
			pop ecx
			mov DWORD[hold], ecx
			pop ecx
			mov DWORD[out_f], ecx
			
			; Gets the outer loop ecx index pointer
			mov eax, X
			sub eax, ecx
			
			; Gets the inner loop ecx index pointer 
			mov edx, DWORD[in_f]
			mov ebx, Y
			sub ebx, edx
			
			; (Outer Ecx * Y)
			mov DWORD[b], ebx
			mov ebx, Y
			mul ebx
			
			; (Outer Ecx * Y) + (Inner Ecx)
			mov edx, DWORD[b]
			add eax, edx
			xchg DWORD[b], eax
			;~~~~~~~~~~~~~~;
			
			; --- Restores the stack from previous pops --- ;
			push ecx
			mov ecx, DWORD[hold]
			push ecx
			
			;/////////////; Adds the products together
			
			; M2 Element Reference
			mov eax, [a]	
			mov ecx, [M2+(eax*4)]
			
			; M1 Element Reference
			mov eax, [b]
			mov edx, [M1+(eax*4)]
			
			jmp multiply ; Multiplys the value at [a] and at [b] together
			mult: ; Where we will return after our jump to multiply
			;/////////////;

			;~~~~~~~~~~~~~~~~~~~~~~~~;
			; Stores the product in the MAdd array
			mov edx, DWORD[in_f]
			mov ebx, Y
			sub ebx, edx
			mov DWORD[MAdd + (ebx*4)], eax	
			;~~~~~~~~~~~~~~~~~~~~~~~~;

			;==============;
			mov ecx, DWORD[in_f]
			dec ecx
			jnz inner ; Decrements inner ecx and jmps to inner
			
			; Adds products together
			jmp add_arr 
			ret_add:
			
			
			mov DWORD[c], eax ; C will hold the value to be put in M3
			mov eax, Z ; Set eax to Z
			mov edx, DWORD[out_f] ; Edx will be the outer ecx pointer
			mov ebx, X ; Ebx is X
			sub ebx, edx ; X - Ecx of X 
			mul ebx ; (X - Ecx of X) * Z
			
			mov ebx, DWORD[hold] ; Set ebx ot Ecx of Z
			mov edx, Z ; Sets edx to Z
			sub edx, ebx ; Z - Ecx of Z
			
			; Sets C to its appropriate spot in array M3
			add edx, eax ; ((Z - Ecx of Z) + ((X - Ecx of X) * Z))
			mov eax, [c] ; Set eax to c for mov instruction below
			mov DWORD[M3 + (edx*4)], eax ; Sets the value at its appropriate spot
			
			; Pops ecx off the stack so that the middle loop may continue
			pop ecx
			dec ecx
			jnz middle
			
			; Pops ecx off the stack so that the outer loop may continue
			pop ecx
			dec ecx
			jnz outer
			
			; Jumps if the three loops are finished
			jmp answer
			;==============;
add_arr: ; Adds the values in the product array
	mov eax, 0
	mov ecx, Y ; Loops through Y amount of times
	add_loop:
		; Gets the current ecx index pointer
		mov edx, Y
		sub edx, ecx
		
		; Moves the values from the product array into ebx
		mov ebx, [MAdd+(edx*4)]
		; Adds each of the products together
		add eax, ebx
		loop add_loop
		jmp ret_add ; Jumps back to the main sequence
; === Multiply === ; 
multiply: 
	mov eax, 0
	; --- Checks if ecx or edx is zero --- ;	
	sub ecx, 0
	jz multiply_zero 
	sub edx, 0
	jz multiply_zero 
multiply_out: 
	push ecx ; Push current multiply index 
	mov ecx, edx ; Stores the outer loop index 
	multiply_in: 
		inc eax ; Increment eax the number of times before 
		loop multiply_in ; Keep incrementing 
		 
		pop ecx ; Set multiply_out index 
		loop multiply_out 
	jmp multiply_end 
multiply_zero: 
	mov eax, 0  ; If edx or ecx is zero, the resulting product is 0
multiply_end: 
	jmp mult
; === Multiply === ; 

answer: ; Answer flag for debugging

; --- Normal exit sequence --- ;
mov eax, 1
mov ebx, 0
int 80h

