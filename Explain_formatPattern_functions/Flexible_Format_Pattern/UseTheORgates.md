# Explanation of OR Gates in Flexible Format

---------------------------------------------------------

* OR gates in flexible format are considered by index, that is, by character, not by complete options, but by options of the specific index. In other words, multiple options apply to the index, not to complete format options.

**Example:**
If we want the first part of a string to have multiple options, we can use the OR gate (the functions are found within the syntaxParsing class).

~~~~
String pattern = "N|LSN"; //This means that index 0 can be N (number) or L (letter), but the others must be the same. This means creating two types of patterns within the function: "NSN" or "LSN".

String forCompare = "1234-1234";
ArrayList<Character> specials = new ArrayList<>();
specials.add('-');
Parser f = new Parser();
Parser.MutableTypeData<Boolean> match = f.new MutableTypeData<>(false);
Parser.MutableTypeData<Boolean> match2 = f.new MutableTypeData<>(false);
checkFlexibleFormat("Ahdionw-12348925", null, null, null, pattern, specials, '|', match, null, 0, true, null, false); //This would match the pattern "LSN"
checkFlexibleFormat("1234-1234", null, null, null, pattern, specials, '|', match, null, 0, true, null, false); //This would match the pattern "NSN"

//In this case, the first message will appear because both strings matched. This is because they both match one of the expected patterns.
if(match || match2){
System.out.println("Some of the strings match the pattern expected\n");
}
else System.out.println("Another string match the pattern expected\n");
~~~~

* As we noted in the previous example, ***OR gates*** what they do is *create* or ***allow other types of patterns***, which **are created internally**, and **work by indexes**, **not** by options as they are commonly used. In other words, using OR in this context does not mean that the entire string can be of one format or another, but rather it means that the ***string, or rather the internal flexible format generated for it***, at the **indicated index** can have **multiple options**, but **only that one**. The ***rest of the indexes remain intact***, that is, they must be **identical**.

## Important Considerations

---------------------------------------------

* It is recommended not to use character repetitions when using OR gates***, because **unexpected behaviors** may occur, that is, ***do not put things like `"N|LLN"`*** because they can cause problems, because if we go back to the ***explanation of flexible formats and how they are generated***, we know that this ***can never happen***, so if you want to separate the first part so that it should be an `"N"` but then an `"L"`, then it is ***recommended to use the special characters or `"S"`*** to **split between words**, that is, it should be `"N|LSLN"` OR `"N|LN"`, but ***you should never repeat characters consecutively***.

* **Don't put multiple consecutive OR gates at different indices without separating them by some character type.**
**Incorrect example:** `"N|L|P|WL|N"`
In this case, it implies that you could have two letters in a row at the same position, but the system will never generate `"LL"` because the type only changes when the character changes.
If you need to separate parts, use a special character (`"S"`) or a clear delimiter in the original string to mark the split.

Then you should separate it or remove the "L" or "L" option in one of the two OR statements.
**Correct example:** `"N|L|P|WSL|N"` or `"N|L|P|WN"` or `"N|P|WL|N"`

* OR gates, in the current implementation of the functions that handle them, can only be used in ***manually created flexible formats***, i.e., you cannot extract OR gates from strings. In other words, you cannot provide examples of strings that generate OR gates***
