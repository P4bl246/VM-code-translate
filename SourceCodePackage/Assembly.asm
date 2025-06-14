//Bootstrap code
//Iinitialize SP
@256
D=M
@SP
M=D
/*push return address
*code after the 'call' or the next line*/
@Sys.init$ret.0
D=A
@SP
A=M
A=D
@SP
M=M+1
//push LCL pointer value
@LCL
D=M
@SP
A=M
M=D
@SP
M=M+1
//push ARG pointer value
@ARG
D=M
@SP
A=M
M=D
@SP
M=M+1
//push THIS pointer value
@THIS
D=M
@SP
A=M
M=D
@SP
M=M+1
//push THAT pointer value
@THAT
D=M
@SP
A=M
M=D
@SP
M=M+1
@SP
A=M
//reposition ARG pointer value
@SP
D=M
@5
D=D-A
@0
D=D-A
@ARG
M=D
//repostion LCL pointer value
@SP
D=M
@LCL
M=D
//goto Sys.init
@Sys.init
0;JMP
(Sys.init$ret.0)
//functionSimpleFunction.test~2
(SimpleFunction.test)
@2
D=A
($Esp_Lo~opñ0)
@inyectLocal-ñ-~♫0
D;JLE
@SP
A=M
M=0
@SP
M=M+1
D=D-1
@$Esp_Lo~opñ0
0;JMP
(inyectLocal-ñ-~♫0)
//push command
@0
D=A
@LCL
A=M+D
D=M
@SP
A=M
M=D
@SP
M=M+1
@SP
A=M
//push command
@1
D=A
@LCL
A=M+D
D=M
@SP
A=M
M=D
@SP
M=M+1
@SP
A=M
//add command
@SP
A=M-1
D=M
A=A-1
D=M+D
@SP
A=M-1
A=A-1
M=D
@SP
M=M-1
//push command
@0
D=A
@ARG
A=M+D
D=M
@SP
A=M
M=D
@SP
M=M+1
@SP
A=M
//add command
@SP
A=M-1
D=M
A=A-1
D=M+D
@SP
A=M-1
A=A-1
M=D
@SP
M=M-1
//push command
@1
D=A
@ARG
A=M+D
D=M
@SP
A=M
M=D
@SP
M=M+1
@SP
A=M
//sub command
@SP
A=M-1
D=M
A=A-1
D=M-D
@SP
A=M-1
A=A-1
M=D
@SP
M=M-1
@SP
A=M-1
D=M
@ARG
A=M
M=D
@ARG
D=M
//reposition SP
@SP
M=D+1
/*restore and recuperate the values*/
//restore THAT
@LCL
A=M-1
D=M
@THAT
M=D
//restore THIS
@2
D=A
@LCL
A=M-D
D=M
@THIS
M=D
//restore ARG
@3
D=A
@LCL
A=M-D
D=M
@ARG
M=D
//retore ReturnAddress
@5
D=A
@LCL
A=M-D
D=M
@RFRNAD~
M=D
//restore LCL
@4
D=A
@LCL
A=M-D
D=M
@LCL
M=D
@RFRNAD~
A=M
0;JMP
