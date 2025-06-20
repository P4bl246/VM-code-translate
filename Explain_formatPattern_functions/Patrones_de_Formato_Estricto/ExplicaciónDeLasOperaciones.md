 # Explicación de las Operaciones de el formato estricto

 -------------------------------------

 * En el ***formato estricto*** hay una serie de operaciones matemáticas, que estan hay para tranformar cadenas a una representación numérica, esto se hace debido a que los ***patrones de formato estricto*** **No** solo estan pensados para ser **exactos**, sino ***también dinámicos***, es decir que se puedan generar rangos con estos, por esta razón se transforman cadenas a una representación numérica, y ***no de cadena como en el formato flexible***, otorgando por esto, variaciones entre las cadenas, pero siempre dentro de un rango.

 **Ejemplo:**
 ~~~~
 double n = identifyTheStrictFormat("hola1234", 0);//donde n = 27.792222734086767
 double h = identifyTheStrictFormat("holaestoesmaslargo", d); //donde h =132.96807276933654
 double s = identifyTheStrictFormat("hola1234esto", 0); //donde s = 60.95010857443558
 //cualquier cadena dentro de el rango de n-h, es aceptada
 if(s <= h && s >= n){
    System.out.println("The String are in the range\n");
 }
 else System.out.println("The String are out of the range\n");
 ~~~~

 En el ejemplo anterior podemos considerar estas colisión no deseada seria "@@#12", porque entra en el rango, aunque la estructura sea difenrente, porque su valor seria 24.729518880175622

 ## Consideraciones Importantes

 -------------------------------------
 
 * Los formatos estrictos debido a que son operaciones determinísticas, y que usan logaritmos, puede haber colisiones entre cadenas de diferentes estructuras, pero que generan la misma ***huella***, esto puede suceder principalmente cuando se comparan valores UNICODE muy altos, con caracteres alfa-numéricos, siendo suceptible a *compensaciones*, y causar este tipo de ***colisiones no deseadas*** (aunque estas sean escasas y con cadenas específicas, ya que como vemos en el método, la posicion y el tipo de caracter altera el resultado final, y la cantidad de estos), por esta razón, no asegura una unicidad de el 100%, y tampoco es un algoritmo diseñado para esto, ni para criptografía, además hay que tener en cuenta que por la naturaleza de no observar los caracteres literales, sino sus ***tipos***, por lo que generar valores unicos para ***cadenas con diferentes estructuras*** se complica aun más.

 * debido a las colisiones no deseadas son muy frecuentes cuando se usan ***rangos*** se recomienda que si usa rangos, sea sipmlemente una validación superficial, y se recomienda robustecer las operaciones, evitando usar logaritmos, que son la principal causa de estas.

 