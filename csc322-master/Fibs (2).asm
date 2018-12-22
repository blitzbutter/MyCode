; Jacob Bryant
; Program 2: Fibonacci Sequence
; 10/2/2018
; Desc: We calculate the first sixteen Fibonacci numbers by using the first two known numbers

; Use p (short[16]) Fibs at break done to see completed array

SECTION .data
Fibs            DW      0,1,0,0,0,0,0,0,0,0,0,0,0,0,0,0

SECTION .text
global _main
_main:

mov ecx, 14 ; Start from 14 since we know the first two numbers

outer:
        ; Here we get the current index by subtracting ecx from the array size
        mov edx, 16 ; The number of elements in the array
        sub edx, ecx ; Subtract count ecx from array size edx to get current index

        ; edx = current index

        xor eax, eax ; Zeroes out eax

        movsx ebx, WORD[Fibs+((edx-1)*2)] ; Move element in index-1 into ebx
        add eax, ebx ; Add element at index-1 to eax

        movsx ebx, WORD[Fibs+((edx-2)*2)] ; Move element in index-2 into ebx
        add eax, ebx ; Add element at index-2 to eax

        mov WORD[Fibs+(edx*2)], ax ; Store the sum of the previous two integers into current index

        loop outer ; Continue loop

done: ; Fibs array is completed

; Exit sequence
mov eax, 1
mov ebx, 0
int 80h