 # Explicación de los Patrones de Formato Flexible

 ------------------------------------------------
 Un ***patrón de formato flexible***, se refiere a un *formato* que puede utilizarse como un ***patrón*** a seguir.
 
 * Un ***formato flexible*** en este contexto, se define por el ***tipo de carácteres*** de una cadena (**NO** de sus *carácteres literales*), que *definen el ***formato flexible*** * de esta.(Este se obtiene a traves de el método `identifyTheFlexibleFormat` de la clase `sintaxParsing`)

 * Un ***patrón*** se reifere a que este *formato* obtenido de una cadena * **puede** ser usado* como un ***patrón*** o **regla** a seguir por el resto de cadenas, es decir que *otras de cadenas* deben tener el ***mismo formato***

 **Ejemplo:**

 Si tenemos una cadena como "HOLA123-1223-1233", podemos obtener el *formato de esta*, pasandolo por la función `identifyTheFlexibleFormat`
 ~~~~
 ArrayList<Character> specialsForIdentify = new ArrayList<>();
 specialsForIdentify.add('-');
 String n = "";
 try{
    n = identifyTheFlexibleFormat("HOLA123-1223-1233", 0, specialsForIdentify, null);
    /*
    *el resultado de esto sera "LNSNSN"(Esto debido a que si miras el interior de la función y su documentación se puede ver que solo se añaden carácteres a el resultado final(es decir el *formatoFlexible*), cuando el tipo de carácter cambia en la cadena, mientras la recorre)
    */
 }catch(ParsingException e){
    System.out.println(e.getMessage());
 }
 ~~~~
 Entonces este resultado o el ***formato flexible*** obtenido de la cadena se puede utilizar como un ***patrón a seguir*** por otras cadenas, antes de hacer operaciones más costosas, sirviendo para funciones como un ***clasificador*** o ***comprobación previa***, porque si el *patrón de la cadena* ***no coincide con el esperado***, entonces simplemente se *puede ignorar o lanzar un error o lo que quieras hacer*, **sin** tener que añadir estas verificaciones en las operaciones más costosas
 
 **Ejemplo:**
 ~~~~
 /*
 * este formato obtenido si coincidira con el *patrón* esperado, porque es "LNSNSN"(porque como dijimos anteriormente el *formato flexible* se obtiene de el tipo de carácteres y solo se cambia cuando cambia el carácter en la cadena)
 */
 if(identifyTheFlexibleFormat("HOadfadeLA1654123-14561623-1456123", 0,specialsForIdentify, null).equals(n)){
    System.out.println("Coinciden los formatos\n");
    //otras operaciones...
 }
 else System.out.println("No coinciden los formatos corrija la cadena\n");//mensaje de error
 ~~~~
 
 * Por esta razón que notamos en los ejemplos anteriores, tiene este nombre de ***flexible***, porque no interesa la cantidad de carácteres que haya, solo su tipo, y orden, en otras palabras, ***no interesa*** la longitud de la cadena, pero **si** el ***tipo de carácters en esta y su orden***
