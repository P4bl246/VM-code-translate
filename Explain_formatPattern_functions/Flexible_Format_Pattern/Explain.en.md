# Flexible Formatting Patterns Explained

------------------------------------------------
A ***flexible formatting pattern*** refers to a *format* that can be used as a ***pattern*** to follow.

* A ***flexible format*** in this context is defined by the ***character type*** of a string (**NOT** its *literal characters*), which *define its ***flexible format***. (This is obtained through the ***identifyTheFlexibleFormat*** method of the ***syntaxParsing*** class)

* A ***pattern*** refers to the fact that this *format* obtained from a string * **can** be used* as a ***pattern*** or **rule** to be followed by the rest of the strings, meaning that *other strings* must have the ***same format***

**Example:**

If we have a string like "HELLO123-1223-1233", we can obtain its *format* by passing it through the ***identifyTheFlexibleFormat*** function.
~~~~
ArrayList<Character> specialsForIdentify = new ArrayList<>();
specialsForIdentify.add('-');
String n = "";
try{
n = identifyTheFlexibleFormat("HELLO123-1223-1233", 0, specialsForIdentify, null);
/*
*The result of this will be "LNSNSN" (This is because if you look at the inside of the function and its documentation, you can see that characters are only added to the final result (i.e., the *FlexibleFormat*) when the character type changes in the string while it is being parsed)
*/
}catch(ParsingException e){
System.out.println(e.getMessage());
}
~~~~
Then this result or the ***flexible format*** obtained from the string can be used as a ***pattern to follow*** by other strings, before performing more expensive operations such as a ***classifier*** or ***preflight***, because if the *string pattern* ***does not match the expected one***, then it can simply be *ignored or an error thrown or whatever you want to do*, **without** having to add these checks in the more expensive operations.

**Example:**
~~~~
/*
* this obtained format would match the expected *pattern*, because it is "LNSNSN" (because as we said before, the *flexible format* is obtained from the character type and is only changed when the character in the string changes)
*/
if(identifyTheFlexibleFormat("HOadfadeLA1654123-14561623-1456123", 0, specialsForIdentify, null). equals(n)){
System.out. println("Formats match\n");
//other operations...
}
else System.out. println("Formats do not match, correct the string\n"); //error message
~~~~

* For this reason, as we noted in the previous examples, it has this ***flexible*** name, because the number of characters doesn't matter, only their type and order. In other words, the length of the string doesn't matter, but the type of characters and their order do.
