# Explanation of Strict Format Operations

-------------------------------------

* In ***strict formatting***, there are a series of mathematical operations that are used to transform strings into a numeric representation. This is done because ***strict formatting patterns*** are **not** designed to be **exact**, but ***also dynamic***, meaning they can generate ranges. For this reason, strings are transformed into a numeric representation, and ***not a string as in flexible formatting***. This allows for variations between strings, but always within a range.

**Example:**
~~~~
double n = identifyTheStrictFormat("hello1234", 0);//where n = 27.792222734086767
double h = identifyTheStrictFormat("hellothisislonger", d); //where h =132.96807276933654 
double s = identifyTheStrictFormat("hello1234this", 0); //where s = 60.95010857443558 
//any string within the range n-h is accepted 
if(s <= h && s >= n){ 
System.out.println("The String are in the range\n"); 
} 
else System.out.println("The String are out of the range\n"); 
~~~~

In the previous example, we can consider this unwanted collision to be "@@#12", because it falls within the range, even though the structure is different, since its value would be 24.729518880175622

## Important Considerations

-------------------------------------

* Strict formats, because they are deterministic operations and use logarithms, can cause collisions between strings of different structures that generate the same ***fingerprint***. This can occur mainly when comparing very high UNICODE values ​​with alphanumeric characters, being susceptible to *offsets* and causing this type of ***unwanted collisions*** (although these are rare and with specific strings, since, as we see in the method, the position and type of character alters the final result, as well as their quantity). For this reason, it does not guarantee 100% uniqueness, nor is it an algorithm designed for this, nor for Cryptography also requires taking into account that, due to the nature of not observing literal characters, but rather their ***types***, generating unique values ​​for ***strings with different structures*** becomes even more complicated.

* Because unwanted collisions are very common when using ***ranges***, it is recommended that if you use ranges, it be simply a superficial validation, and it is recommended to strengthen the operations, avoiding the use of logarithms, which are the main cause of these.
