; Jacob Bryant
; CSC 322 Fall 2018
; Summing Arrays

; Description: In this program, we find the sum of elements for three arrays of
; types byte, word, and double word respectively, then we find the sum of those sums.

SECTION .data
; Here we define three arrays of each respective type
bArray: DB -1, -2, -3, -4, -5
wArray: DW 0ah, 0bh, 0ch, 0dh, 0eh
dArray: DD -10, 20, -30, 40, -50

; We define a sum variable for each respective array and a grandTotal variable which will hold the sum of sums
bArraySum: DB 0
wArraySum: DW 0
dArraySum: DD 0
grandTotal: DD 0

SECTION .text
global _main
_main:

; First, we find the sum of the byte array

; === Section 1: Byte Array Sum ===

; We use movsx for these operations to preverse the signed format of data
movsx eax, BYTE[bArray]

; In each of the next four pairs of two instructions, we first fill ebx with the value the cell in the array
; , we then increment by the size of that data to get to the next value in the array, and we do this until we have
; completely iterated the array and summed each element

; Note: In this section and section 2 where we find the word array sum, we use
; the keyword BYTE to describe a single BYTE value, and the keyword WORD to describe
; a single WORD value. Necessary for moving these data types in the larger 32-bit registers eax and ebx

movsx ebx, BYTE[bArray+1]
add eax, ebx

movsx ebx, BYTE[bArray+2]
add eax, ebx

movsx ebx, BYTE[bArray+3]
add eax, ebx

movsx ebx, BYTE[bArray+4]
add eax, ebx

; We use the instruction xchg to switch the values of eax and the array sum variable,
; which effectively puts the sum into its necessary variable and zeroes out the eax register
xchg [bArraySum], eax

result1:

; === Section 2: Word Array Sum ===

; We use movsx for these operations to preverse the signed format of data
movsx eax, WORD[wArray]

movsx ebx, WORD[wArray+1*2]
add eax, ebx

movsx ebx, WORD[wArray+2*2]
add eax, ebx

movsx ebx, WORD[wArray+3*2]
add eax, ebx

movsx ebx, WORD[wArray+4*2]
add eax, ebx

; We use the instruction xchg to switch the values of eax and the array sum variable,
; which effectively puts the sum into its necessary variable and zeroes out the eax register.
xchg [wArraySum], eax

result2:

; === Section 3: Double Word Array Sum ===

; Note: Since we are using the 32-bit register eax and dealing with a 32-bit (DWORD) array,
; the instruction sequence in this section is much simpler than the last two. We may directly
; add each array element to eax without type conversions or the like.
mov eax, 0
add eax, [dArray]
add eax, [dArray+4]
add eax, [dArray+8]
add eax, [dArray+12]
add eax, [dArray+16]

; We use the instruction xchg to switch the values of eax and the array sum variable,
; which effectively puts the sum into its necessary variable and zeroes out the eax register.
xchg [dArraySum], eax

result3:

; === Section 4: Grand Total Sum ===

; Here we store the byte array sum into eax, the word array sum into ebx,
; add the two, store the double word array sum into ebx, and finally add that
; to the sum in eax, which we use our xchg trick to put into variable grandTotal.
movsx eax, BYTE[bArraySum]

movsx ebx, WORD[wArraySum]
add eax, ebx

; No need to use movsx here, since it is not a larger data size
mov ebx, [dArraySum]
add eax, ebx

; We use the instruction xchg to switch the values of eax and the array sum variable,
; which effectively puts the sum into its necessary variable and zeroes out the eax register.
xchg [grandTotal], eax

; Our last label here, exactly before the traditional exit sequence, for debugging purposes
lastBreak:

mov eax, 1
mov ebx, 0
int 80h
