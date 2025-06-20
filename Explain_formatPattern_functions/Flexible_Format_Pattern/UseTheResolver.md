# Conflict Resolver for Flexible Formats

-----------------------------------------------------
* The conflict resolver is **specific** to this type of **formats**, and was created for them. This is the `resolveConflicts` method of the `syntaxParsing` class.

## Explanation of the Resolver

-----------------------------------------------------

* The resolver is responsible for resolving potential conflicts between **flexible formats** (*generated*). This is done by removing characters from the format to be resolved, based on the format used. This becomes useful when the string or the format generated for it **does not** exactly match the expected format, because there are variations between characters, but ***without these characters they would match***.

**Example:**
~~~~
String pattern = "N|L|P|WSN";
ArrayList<Character> specials = new ArrayList<>();
specials.add('-');
String identify = idenitfyTheFlexibleFormat("HiThisIsANas124562di1i1hnn2oi/-54515, 1, specials, null); //This will generate a "PWPWPWPWNWNWNWNWLSN" format
Parser f = new Parser();
MutableTypeData<String> patternOfString = f.new MutableTypeData<>(identify);
MutableTypeData<Boolean> match= f.new MutableTypeData<>(false);
try{
checkFlexibleFormat(patternOfString.getValor(), null, null, null, pattern, specials, '|', match, 1, true, null, true);
//The result will be false in match because they are not identical. This is when the resolver comes in handy. If it were just "PSN," it would match.
Map<Character, Integer> map = new HashMap<>();
//What we're saying next is that we want to remove all instances of these characters, because in the integer we indicated 0, that is, not leave any occurrences of this

map.put('W', 0);//where character is the character we want to remove and its integer, how many of these characters we want to keep to the right. If the character doesn't appear in the map, it's not deleted
map.put('S', 0);
map.put('L', 0);
mpa.put('N', 0);

resolveConflicts(patternOfString.getValor(), pattern, map, 'S', '|');
//After execution, the value of patternOfString is now "PSN"

checkFlexibleFormat(patternOfString.getValor(), null, null, null, pattern, specials, '|', match, 1, true, null, true);//Now match will be true

}catch(ParsingException e){System.out.println("e.getMessage()");}
~~~~

* In the previous example we saw how after using `resolveConflicts` the string now matches, this is because the method works internally by removing characters, but ***leaving the first N characters, which correspond to the pattern to resolve***, that is, as in the previous example the pattern to resolve was `"N|L|P|WSN"`, this is because as we saw in the **explanation of the OR gates**, this translates to `NSN`, `LSN`, `PSN` and `WSN`, and as we see in all the strings there is ***1 character always before the `S`***, and as in the previous example we told it **not** to check anything after finding the `S` character in the format to be resolved, then it understands that before the `S` in the ***format to resolve*** and ***to resolve***, this same rule applies, that is, stop *viewing* or *executing* when it finds the `S` character (excluding this one too). So going back to the above there is always ***1 character before `S`*** so internally the method ***reserves or keeps these characters intact***, that is, it ***ignores the 1st character of the format to resolve*** (if there were more it would ignore all those **N characters before** the `S`), then the string that would be generated internally to resolve would be the result of **what is between these 2 delimiters** (the ***N corresponding characters of the pattern to resolve***, and the `S` delimiter that we explicitly put), the result in the previous example would be `"WPWPWPWNWNWNWNWL"`, and then only after this would it start to ***remove the characters***, and ***reserve the ones that we specified*** in the `map`, and as in the example We didn't want to reserve any, or in other words, we decided to eliminate all the stops of these, then the result would be `""` (an empty string), and after this, it takes the ***original string and replaces the string it took with the modified one*** then the final result would be `PSN`, on the other hand without the `map` we would have put a **1** in the **W** character, so this means, *"reserve 1 character 'W' at the end"*, which would not result in `""`, but `"W"` and the final result would be `"PWSN"`.

## Important Considerations

* To reserve characters you must keep in mind that you must ensure that the string will always have a quantity greater than or equal to the characters you want to reserve.

* If you don't put a delimiter, the resolver will do this process with the entire string after the **N characters of the format to resolve**, that is, after these, what is until the end of the chain

* If you use a delimiter, you must make sure that it will appear from where you don't want it to.
