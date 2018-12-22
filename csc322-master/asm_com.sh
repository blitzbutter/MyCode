#!/bin/bash
a=".o"
b=".asm"
nasm -f elf32 -o $1$a $1$b -Z nasm.log
ld -e _main -melf_i386 -o $1 $1$a 2> ld.log
