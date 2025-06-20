 # Explicación de los Patrones de Formato Estricto

 ------------------------------------------------
 Un ***patrón de formato estricto***, se refiere a un *formato* que puede utilizarse como un ***patrón*** a seguir.
 
 * Un ***formato estricto*** en este contexto, se define por el ***tipo de caracteres*** de una cadena (**NO** de sus *caracteres literales*) y su posición en la cadena, que *definen el ***formato estricto**** de esta.(Este se obtiene a traves de el método `identifyTheStrictFormat` de la clase `sintaxParsing`)

 * Un ***patrón*** se reifere a que este *formato* obtenido de una cadena * **puede** ser usado* como un ***patrón*** o **regla** a seguir por el resto de cadenas, es decir que *otras de cadenas* deben tener el ***mismo formato***, o pueden estar en un **rango** entre **formatos**

 **Ejemplo:**

 Si tenemos una cadena como "HOLA123-1223-1233", podemos obtener el *formato de esta*, pasandolo por la función `identifyTheStrictFormat`
 ~~~~
 double n;
 try{
    n = identifyTheStrictFormat("HOLA123-1223-1233", 0);
    /*
    *el resultado de esto sera 111.41709729841867 (Esto debido a que si miras el interior de la función y su documentación se puede ver que las operaciones aseguran que caracters con valores UNICODE altos se disminuyen exponencialmente, ademas que su poscición afecta el resultado final)
    */
 }catch(ParsingException e){
    System.out.println(e.getMessage());
 }
 ~~~~
 Entonces este resultado o el ***formato estricto*** obtenido de la cadena se puede utilizar como un ***patrón a seguir*** por otras cadenas, antes de hacer operaciones más costosas, sirviendo como un ***clasificador*** o ***comprobación previa***, porque si el *patrón de la cadena* ***no coincide con el esperado*** o con un **rango**, entonces simplemente se *puede ignorar o lanzar un error o lo que quieras hacer*, **sin** tener que añadir estas verificaciones en las operaciones más costosas
 
 **Ejemplo:**
 ~~~~
 /*
 * este formato obtenido no coincidira con el *patrón* esperado, porque es 399.2038843968486, y el esparado es "111.41709729841867"
 */
 if(identifyTheStrictFormat("HOadfadeLA1654123-14561623-1456123", 0) == n){
    System.out.println("Coinciden los formatos\n");
    //otras operaciones...
 }
 else System.out.println("No coinciden los formatos corrija la cadena\n");//mensaje de error
 ~~~~
 
 * Por esta razón que notamos en los ejemplos anteriores, tiene este nombre de ***estricto***, porque en este interesa la cantidad de caracteres que haya, no solo su tipo, y orden, en otras palabras, ***si interesa*** la longitud de la cadena, y también el ***tipo de caracters en esta y su orden, y poscición en esta***
