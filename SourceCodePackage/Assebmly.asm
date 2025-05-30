
//lable command
(hola1234)


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
D=A+D
@SP
A=M
M=D
@SP
A=M
M=D
@SP
A=M-1
D=M
M=0
@SP
A=M
M=D
@SP
M=M-1


//push command
@5
D=A
@SP
A=A+D
D=M
M=0
@SP
A=M
M=D
@SP
M=M+1


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
@true0
D;JGT
@false0
D;JLE

(true0)
@SP
A=M-1
A=A-1
M=1
@SP
M=M-1
@SP
A=M
M=0
@Continue0
0;JMP
(false0)
@SP
A=M
M=0
@SP
M=M-1
@SP
A=M
M=0

(Continue0)

//eq command
@SP
A=M-1
A=A-1
D=M-D
@true1
D;JEQ
@false1
D;JLG

(true1)
@SP
A=M-1
A=A-1
M=1
@SP
M=M-1
@SP
A=M
M=0
@Continue1
0;JMP
(false1)
@SP
A=M
M=0
@SP
M=M-1
@SP
A=M
M=0

(Continue1)
