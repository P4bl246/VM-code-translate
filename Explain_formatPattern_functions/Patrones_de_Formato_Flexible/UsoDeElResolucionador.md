 # Resolucionador de Conflictos para Formatos Flexibles

 -----------------------------------------------------
 * El resolucionador de conflictos es **propio** de este tipo de **formatos**, y se creo para estos, este es el método `resolveConflicts` de la clase `sintaxParsing`

 ## Explicación de el Resolucionador

 -----------------------------------------------------
 
 * El resolucionador se encaraga de resolver posibles conflictos entre **formatos flexibles**(*generados*), esto lo hace eliminado caracteres de el formato a resolver, en base a el formato para hacer esto. Este se vuelve útil cuando la cadena o el formato generado para esta **no** conincide exactamente con el formato esperado, porque hay variaciones entre caracteres, pero ***sin estos coincidirian***.

 **Ejemplo:**
 ~~~~
 String pattern = "N|L|P|WSN";
 ArrayList<Character> specials = new ArrayList<>();
 specials.add('-');
 String idenitfy = idenitfyTheFlexibleFormat("HiThisIsANas124562di1i1hnn2oi/-54515, 1, specials, null);//Esto generar un formato "PWPWPWPWNWNWNWNWLSN"
 Parser f = new Parser();
 MutableTypeData<String> patternOfString = f.new MutableTypeData<>(identify);
 MutableTypeData<Boolean> match= f.new MutableTypeData<>(false);
 try{
 checkFlexibleFormat(patternOfString.getValor(), null, null, null, pattern, specials, '|', match, 1, true, null, true);
 //el resultado sera false, en match porque no son identicas, aqui es cuando sirve el resolucionador, porque si solo fuera "PSN" ahora si coincidira
 Map<Character, Integer> map = new HashMap<>();
 //lo que decimos a continuación es que quermos remover todas las paraciones de estos caracteres, porque en el entero indicamos 0, es decir no dejar ninguna aparacion de este

 map.put('W', 0);//donde el caracter es el caracter que queremos remover y su entero, cauntos de estos caracters queremos mantener a la derecha, si no aparece el caracter en el map, no lo elimina
 map.put('S', 0);
 map.put('L', 0);
 mpa.put('N', 0);

 resolveConflicts(patternOfString.getValor(), pattern, map, 'S', '|');
 //despues de ejecutarse el valor de patternOfString es ahora "PSN"

checkFlexibleFormat(patternOfString.getValor(), null, null, null, pattern, specials, '|', match, 1, true, null, true);//ahora match sera true

 }catch(ParsingException e){System.out.println("e.getMessage()");}
 ~~~~

 * En el ejemplo anterior vimos como despues de usar `resolveConflicts` la cadena ahora si coincide, esto se debe a que el métod funciona internametne removiendo caracters, pero ***dejando los primeros N caracters, que corresponden a el patron para resolver***, es decir, como en el ejemplo anterior el patrón para resolver era `"N|L|P|WSN"`, esto debido a que como vimos en la **explicación de las compuertas OR**, esto se traduce a `NSN`, `LSN`, `PSN` y `WSN`, y como vemos en todas la cadenas hay ***1 caracter simpre antes de el `S`***, y como en el ejemplo anterior le dijimos que **no** revisara nada despues de encontrar el caracter `S` en el formato a resolver, entonces este entiende que antes de el `S` en el ***formato a resolver*** y ***para resolver***, se aplica esta misma regla, es decir parar de *ver* o *ejecutar* cuando encuentre el caracter `S` (excluyendo este también). Entonces volviendo a lo anterior siempre hay ***1 caracter antes de `S`*** entonces internamente el método ***reserva o mantinen intactos estos caracteres***, es decir que ***ignora el 1 caracter de el formato a resolver***(si hubieran más ignoraria todos esos **N caracteres antes** de el `S`), luego entonces, la cadena que genraria internamente para resolver seria el resultado de **lo que hay entre estos 2 delimitadores** (los ***N caracters correspondientes de el patón para resolver***, y el delimitar `S` que pusimos explícitamete), el resultado en el ejemplo anterior seria `"WPWPWPWNWNWNWNWL"`, y luego solo despues de esto comenzaria a ***remover los caracteres***, y a ***reservar los que les especificamos*** en el `map`, y como en el ejemplo no quisimos reservar ningun, o en otras palabras, decidimos eliminar todas las paraciones de estos, entonces el resultado seria `""`(una cadena vacia), y despues de esto, toma la ***cadena orignial y reemplaza la cadena que tomo por la modificada*** entonces el resultado final seria `PSN`, en cambio sin en el `map` hubiramos puesto un **1** en el caracter **W**, entonces esto quiere decir, *"reserva 1 caracter 'W' a el final"*, lo que daria como resultado no `""`, sino `"W"` y el resultado final seria `"PWSN"`.

 ## Consideraciones Importantes

 * Para resrevar caracteres debes tener en cuenta que debes asegurar que la cadena simpre va a tener una cantidad mayor o igual, a los caracteres que quieres reservar

 * Si no pones un delimitador el resolucionador hara este proceso con toda la cadena despues de los **N caracteres de el formato para resolver**, es decir, despues de estos, lo que hay hasta el final de la cadena

 * Si usa un delimitador, debe asegurarse de que este aparecera desde donde no quiere continuar con la resolución de la cadena

 * Este resolucionador esta diseñado para manipular los ***formatos flexibles ya generados previamente***, no obtiene estos de cadenas

 * Como el resolucionador elimina caracters, la cadena o el formato a resolver (para ser mas preciso), no puede ser menor o igual a la cadena de el formato para resolver