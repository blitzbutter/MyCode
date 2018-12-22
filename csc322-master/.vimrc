:"*Highlight the lines where the compiler says there in error
:"*Add command to get help page of function with like wget
:"*Check for signed instructions (parse for negatives)
:"*Print helper file and all debugging functions
:
: " To do
: " Add more comments and also look over identifier
: " Write How-To page, shell script, and header with name and description
: " Make shell commands to change things like highlighting and such
: " Stores vim variables into vim vars
:
:
:" Refactor code
:" Make Github project
:" Add more commentation
:" Handle static arrays AutoCompile
:" Use the third part of the array split
:
:function MultilineComment()
:       call system('touch vim_vars.txt')
:       let s:start = line(".")
:
"Dep":  let save = s:start " Save the current line for later use
"Dep":  let comsave = 'echo ' . save . ' >> vim_vars.txt'
"Dep":  call system(comsave)
:
:       execute line(".") . ',$ ij/;@ml'
:
:       let s:end = line(".")-1
:
"Dep":  let save = s:end
"Dep":  let comsave = 'echo ' . save . ' >> vim_vars.txt'
"Dep":  call system(comsave)
:
:       execute s:start . 's/$/' . ';   \'
:
:       execute s:start+1 . ',' . s:end . 's/$/ \'
:
:       nohl " Need to add customized highlighting...
:endfunction
:
:function RemoveComment()
:       let s:start = line(".")
:       execute line(".") . ',$ ij/;@ml'
:       let s:end = line(".")
:       execute s:start . ',' . s:end . 's/\\/'
:       execute s:start . ',' . s:end . 's/;@ml/'
:       execute s:start . ',' . s:end . 's/;    /'
:endfunction
" Print array contents at the specified breakpoint
" Set where it can tell if user put int or dword or DWORD (use .upper()), translates it appropriately
:function GPrintReg(break)
:       call AutoCompile()
:       let f_name = FObjName()
:       let com = "rm gdb.txt;gdb -ex \'set logging on\' -ex \'break " . a:break . "\' -ex \'run\' -ex \'i r" . "\' -ex \'cont\' -ex \'quit\' " . f_name . ";clear;cat gdb.txt"
:       let com_sys = system(com)
:       let com_read = "cat gdb.txt|grep -v Cont|grep -v Break|grep -v exited|grep -v Start"
:       let com_sys_r = system(com_read)
:       echo com_sys_r
:endfunction
:function GPrintArray(name, size, type, break) " Give custom port argument?
:       call AutoCompile()
:       let f_name = FObjName()
:       let com = "rm gdb.txt;gdb -ex \'set logging on\' -ex \'break " . a:break . "\' -ex \'run\' -ex \'p /d (signed " . a:type . "[" . a:size . "])" . a:name . "\' -ex \'cont\' -ex \'quit\' " . f_name . ";clear;cat gdb.txt|grep \\$1"
:       let com_sys = system(com)
:       let com_read = "cat gdb.txt|grep -v Cont|grep -v Start|grep -v Break|grep -v exited|grep -h \'.\'"
:       let com_sys_r = system(com_read)
:       echo com_sys_r
:endfunction
" Maybe use execute for redir and echo
:function AutoCompile()
:       "Added commands to store error messages of both ld and nasm and then echo it in console
:       execute 'w'
:       let com_rm = 'rm nasm.log; rm ld.log'
:       let com_add = 'touch nasm.log; touch ld.log'
:       call system(com_rm)
:       call system(com_add)
:
:       let f_name = FObjName()
:       let com = './asm_com ' . f_name
:       call system(com)
:
:       let com_nasm = 'cat nasm.log'
:       let com_nasm_ec = system(com_nasm)
:       echo com_nasm_ec
:
:       let com_ld = 'cat ld.log'
:       let com_ld_ec = system(com_ld)
:       echo com_ld_ec
:endfunction
:
" Work on default breakpoint function and also a function to log all debugging
" in Vim
:function AutoReg()
:       let arr_break = "answer"
:       call GPrintReg(arr_break)
:endfunction
:function AutoArray()
:       let arr_break = "answer" " The default breakpoint which will be read at
:
:       let lineText = getline('.') " Current array line
:       let lineTextNum = line('.') " Number of current array line
:       let lineArg = split(lineText) " Splits it name, type, and size
:
:       let arrName = lineArg[0] " First index is the name
:       let arrNameSplit = split(arrName, ":") " Gets rid of pesky : character
:" Changes arrType to the appropriate C data type equivalent
:       let arrType = tolower(lineArg[1]) " Automatically translates type to lower
:       if arrType == 'dd'
:               let arrType = 'int'
:       endif
:       if arrType == 'dw'
:               let arrType = 'short'
:       endif
:       if arrType == 'db'
:               let arrType = 'char'
:       endif
:       if arrType == 'dq'
:               let arrType = 'long'
:       endif
:
:       let c_flag = 0
:       if len(lineArg)==3 " If the line brokes into three parts like normal
:               let arrSize = lineArg[2] " Last argument is array size
:               let arrSizeSplit = split(arrSize, ",") " Splits by comma
:               let arrSizeCalc = len(arrSizeSplit) " The len of the resulting arrSizeSplit is the number of array elements
:       else " Else, there might be a space
":              echo lineArg The problem is here with comments"
:               if index(lineArg, ";")!= -1
:                       let arrIn = index(lineArg, ";")-1
:                       let arrSize = lineArg[2:arrIn]
:                       let arrSizeCalc = len(arrSize)
:               else
:                       let arrSize = lineArg[2:len(lineArg)] " Ignores the first two and takes the rest to be numbers of the array
:                       let arrSizeCalc = len(arrSize) " Same as the if statement last line
:               endif
:       endif
":      let t_line = getline(lineTextNum+1)
":      echo t_line
:       execute '.+1,$ ij/^\a'
:       let jump = '.-1,. ij//'
:
:       let line = getline(".")
:       let line_num = line(".")
:
:       let line_s = split(line)
":      if c_flag == 0
:       if line_num == lineTextNum
:               call GPrintArray(arrNameSplit[0], arrSizeCalc, arrType, arr_break)
:       else
:               let flag = 0
:               while(flag == 0)
:                       execute jump
:
:                       let line = getline(".")
:                       let line_num_c = line(".")
:                       if strlen(line)>0
:                               let end = line(".")
:                               let flag = 1
:                               break
:                       endif
"Look at DW in Fibs case
":                              let line_s = split(line)
":                              let line_start = line_s[0]
":                              let line_s_num = strlen(line_start)-1
":                      endif
":                      if line_num_c == lineTextNum
:                               "call GPrintArray(arrNameSplit[0], arrSizeCalc, arrType, arr_break)
":                              let end = line(".")
":                              let flag = 2
":                              break
":                      endif
:                       if strlen(line)>0 && line_start[line_s_num] == ':' && flag != 2 && line_num_c == lineTextNum
:                               let end = line(".")
:                               let flag = 1
:                               break
:                       endif
:               endwhile
:               let iter = 0
:               let g_flag = 0
:               if flag == 1
:                       while iter < (end-lineTextNum)
:                               let lineSplit = split(line)
:                               " If the next line is a comment
:                               if lineSplit[0] == ';'
:                                       call GPrintArray(arrNameSplit[0], arrSizeCalc, arrType, arr_break)
:                                       let iter = end-lineTextNum
:                                       let g_flag = 1

:                               endif
:                               if len(lineSplit)==2
:                                       let arrS = lineSplit[1]
:                                       let arrSSplit = split(arrS, ",")
:                                       let arrSizeCalc = arrSizeCalc + len(arrSSplit)
:                               else
":                                      call GPrintArray(arrNameSplit[0], arrSizeCalc, arrType, arr_break)
":                                      let iter = end-lineTextNum
":                                      let g_flag = 1

:                                       let arrSA = lineSplit[1:len(lineSplit)]
:                                       for i in range(0, len(arrSA)-1)
:                                               let arrWA = split(arrSA[i], ',')
:                                               let arrSizeCalc = arrSizeCalc + len(arrWA)
:                                       endfor
:                               endif
:
:                               execute jump
:                               let line = getline(".")
:                               let iter = iter + 1
:                       endwhile
:                       if g_flag == 0
:                               call GPrintArray(arrNameSplit[0], arrSizeCalc, arrType, arr_break)
:
:                       endif
:               endif
:       endif
:endfunction
:
:"Change default breakpoint function
:function DefBreak(BreakName)
:       " Use file function to get breakpoint from file
:       let g:GDefName = a:BreakName
:endfunction
:" Gets name of Object File for use in functions
:function FObjName()
:       redir @a
:       echo expand('%:t')
:       redir END
:       let str_b = @a
:       let str_c = strlen(str_b)-1
:       let str_d = strpart(str_b, 1, str_c)
:       let str_d = substitute(str_d, ".asm", "", "")
:       return str_d
:endfunction
" --- This function is deprecated but I could probably do something with it
"  later... ---"
":function RemoveComment()
":      let s_ret = 'head -1 vim_vars.txt'
":      let s_start = system(s_ret)
":
":      let s_ret = 'head -2 vim_vars.txt | tail -1'
":      let s_end = system(s_ret)
":
":      let s_end = s_end+1
":
":      execute s_start . 's/;/'
":      execute s_end . 's/;@ml/'
":      execute s_start . ',' . s_end . 's/\\/ '
":
":      call system('rm vim_vars.txt')
":      nohl
":endfunction
:map <F10> :call MultilineComment()<CR>
:map <F11> :call RemoveComment()<CR>
":map <C-q> :call DebugMode()<CR> Gives a function that adds a constant value
"and use conditions on hotkeys associate with debugging to check if you are in
"that mode
" Add a debugging mode that checks if user entered a key
:map <C-w> :call AutoArray()<CR>
:map <C-e> :call AutoReg()<CR>
" Don't put it on <F12>, that one is airplane mode -.-
