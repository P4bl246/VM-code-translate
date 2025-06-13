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
M=0
//reposition ARG pointer value
@SP
D=M
@5
D=D-A
@0
A=A-1
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


//function12h132llo23Panda~123
(12h132llo23Panda)
@123
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


//if-goto command
@SP
A=M-1
D=M-1
@12h132llo23Panda$hola1234
D;JEQ


//label command
(12h132llo23Panda$hola1234)


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
@SP
A=M
M=0


//pop command
@10
D=A
@LCL
D=M+D
@SP
A=M
M=D
@SP
A=M-1
D=M
@SP
A=M//go to the last value store in the stack
A=M//go to this value
M=D
@SP
A=M
M=0
@SP
A=M-1
M=0
@SP
M=M-1


//push command
@5
D=A
@SP
A=M+D
D=M
M=0
@SP
A=M
M=D
@SP
M=M+1
@SP
A=M
M=0


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
A=M
M=0

//gt command
@SP
A=M-1
D=M
A=A-1
D=M-D
@true♫0
D;JGT
@false~0
D;JLE

(true♫0)
@SP
A=M-1
A=A-1
M=1
@SP
M=M-1
@SP
A=M
M=0
@Continue~0
0;JMP
(false~0)
@SP
A=M-1
A=A-1
M=0
@SP
M=M-1
@SP
A=M
M=0

(Continue~0)

//eq command
@SP
A=M-1
A=A-1
D=M-D
@true♫1
D;JEQ
@false~1
D;JNE

(true♫1)
@SP
A=M-1
A=A-1
M=1
@SP
M=M-1
@SP
A=M
M=0
@Continue~1
0;JMP
(false~1)
@SP
A=M-1
A=A-1
M=0
@SP
M=M-1
@SP
A=M
M=0

(Continue~1)

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
//restore LCL
@4
D=A
@LCL
A=M-D
D=M
@LCL
M=D
@Sys.init$ret.0
0;JMP

