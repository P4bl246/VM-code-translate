# Strict Formatting Patterns Explained

------------------------------------------------
A ***strict formatting pattern*** refers to a *format* that can be used as a ***pattern*** to follow.

* A ***strict format*** in this context is defined by the ***character type*** of a string (**NOT** its *literal characters*) and its position in the string, which *define the ***strict format* of the string. (This is obtained through the ***identifyTheStrictFormat*** method of the ***syntaxParsing*** class)

* A ***pattern*** refers to the fact that this *format* obtained from a string * **can** be used* as a ***pattern*** or **rule** to be followed by the rest of the strings, meaning that *other strings* must have the ***same format***, or can be in a **range** between **formats**

**Example:**

If we have a string like "HOLA123-1223-1233", we can obtain its *format* by passing it through the ***identifyTheStrictFormat*** function

~~~~
double n;
try{
n = identifyTheStrictFormat("HOLA123-1223-1233", 0);
/*
*The result of this will be 111.41709729841867 (This is because if you look at the inside of the function and its documentation, you can see that the operations ensure that characters with high UNICODE values ​​are exponentially decreased, and their position affects the final result)
*/
}catch(ParsingException e){
System.out.println(e.getMessage());
}
~~~~
Then this result or the ***strict format*** obtained from the string can be used as a ***pattern to follow*** by other strings, before performing more expensive operations such as a ***classifier*** or ***preflight***, because if the *string pattern* ***does not match the expected one*** or a **range**, then you can simply *ignore it or throw an error or whatever you want to do*, **without** having to add these checks in the more expensive operations.

**Example:**
~~~~
/*
* This obtained format will not match the expected *pattern*, because it is 399.2038843968486, and the expected one is "111.41709729841867"
*/
if(identifyTheStrictFormat("HOadfadeLA1654123-14561623-1456123", 0) == n){
System.out.println("Formats match\n");
//other operations...
}
else System.out.println("Formats do not match, correct the string\n");//error message
~~~~

* For this reason, as we noted in the previous examples, it has this name of ***strict***, because in this one, the number of characters in it is important, not just their type and order. In other words, the length of the string is important, as well as the ***type of characters in it, their order, and position within it***
