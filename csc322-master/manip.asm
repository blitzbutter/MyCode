; Jacob Bryant
; Program 3: Matrix Manipulation
; 10/10/2018
; Desc: We iterate through the rows and columns of an array, storing each row sum, column sum, and then conclusively storing the sum of all integers in the array.

ROWS: EQU 5
COLS: EQU 7

SECTION .data
MyMatrix:       dd       1,  2,  3,  4,  5,  6,  7
                dd       8,  9, 10, 11, 12, 13, 14
                dd      15, 16, 17, 18, 19, 20, 21
                dd      22, 23, 24, 25, 26, 27, 28
                dd      29, 30, 31, 32, 33, 34, 35
;MyMatrix: dd 1, 2, 3
;         dd 4, 5, 6

counter: dd 0 ; Saves the ecx register for outer loop

point_in: dd 0 ; Current row index
point_mul: dd 0 ; Expression to keep point_in * ROWS

SECTION .bss
RowSums: RESD ROWS
ColSums: RESD COLS
Sum: RESD 1

SECTION .text
global _main
_main:

                ;---Row Sums---;
; Uses a nested for-loop to deal with sum of each row
mov ecx, ROWS ; Set counter to the number of rows
xor eax, eax ; Zeroes out eax

outer_row: ; Outer loop to index through the rows
        ; Current row index saved in edx
        mov edx, ROWS
        sub edx, ecx

        xchg [point_in], edx ; Stores the row index pointer
        mov [counter], ecx ; Saves the current row counter
        mov ecx, COLS ; Sets the counter for the inner loop

        ; Formula getting row changes
        ; Current Row = (# of Columns * Row Index)

        ; Multiply the current row index by the COLS size
        mov eax, [point_in]
        mov ebx, COLS
        mul ebx

        mov [point_mul], eax ; Saves the result of the multiplication
        xor eax, eax ; Zeroes out eax register

        inner_row:
                ; Current column index saved in edx
                mov edx, COLS
                sub edx, ecx

                ; Sets to proper row using the cols-row_index product from earlier
                add edx, [point_mul]
                mov ebx, [MyMatrix+(edx*4)]
                add eax, ebx
                loop inner_row ; Loop back to inner_row

                ; End of current inner loop

                ; Stores row index pointer in ebx and then stores the row sum (in eax) in that cell location
                mov ebx, [point_in]
                mov [RowSums+(ebx*4)], eax
                check:
                ; Zeroes out variables
                xor eax, eax
                xor ebx, ebx
                xor edx, edx

                ; Restore outer loop counter
                mov ecx, [counter]
        loop outer_row ; Loop back to outer_row
        ; End of current outer loop

                ;---Column Sums---;
mov ecx, COLS ; Moves # of columns into ecx
xor eax, eax ; Zeroes out eax

; Zeroes out point_in to use with column sums
mov eax, 0
mov [point_in], eax

outer_col:
        ; Gets current column
        mov edx, COLS
        sub edx, ecx

        xchg [point_in], edx ; Stores the column index pointer
        mov [counter], ecx ; Saves the current column counter
        mov ecx, ROWS ; Sets the counter for the inner loop

        inner_col:
                ; Get current column cell
                mov edx, ROWS
                sub edx, ecx

                ; (# of Columns * Current Column Cell) + Current Column = Current Column Cell Iteration
                ; Basically, this will adjust the index pointer to the correct spot in memory
                mov eax, edx
                mov ebx, COLS
                mul ebx
                add eax, [point_in]

                ; Stores current cell into ebx
                mov ebx, [MyMatrix+(eax*4)]

                ; Adds the value in the current cell to the current cell in ColSums
                mov eax, [point_in]
                add [ColSums+(eax*4)], ebx

                ; Loops back to inner_col
                loop inner_col

                ; Zeroes out variables
                xor eax, eax
                xor ebx, ebx
                xor edx, edx

                ; Restores outer loop counter
                mov ecx, [counter]
        loop outer_col ; Loops back to outer_col

                ;---Grand Total---;
; Sums all the data in RowSums to get grand total
mov ecx, ROWS ; # of Rows as counter
outer_tot:
        ; Gets the current cell
        mov edx, ROWS
        sub edx, ecx

        ; Adds the value at the current cell in RowSums to the Sum variable
        mov ebx, [RowSums+(edx*4)]
        add [Sum], ebx
        loop outer_tot
; Label to check validity of arrays in debugging
answer:

; Exit code
mov eax, 1
mov ebx, 0
int 80h
