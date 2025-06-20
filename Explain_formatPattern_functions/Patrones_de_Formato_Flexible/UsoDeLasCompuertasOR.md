 # Explicación de las compuertas OR en el Formato Flexible
 
 ---------------------------------------------------------

 * Las ***compuertas OR*** en el ***formato flexible*** se toman en cuenta por índices, es decir por caracter no por opciones completas, sino por opciones de el indice específico, en otras palabras, las multiples ***opciones se aplican a el índice***, **no** a opciones de formatos completas

 **Ejemplo:**
  Si queremos que la primera parte de una cadena pueda tener multiples opciones podemos usar la compuerta OR (las **funciones** se encutran dentro de la clase ***sintaxParsing***)
  
  ~~~~
  String pattern = "N|LSN"; //esto se traduce a que el índice 0 puede ser N(número) o L(letra), pero los demas deben ser iguales, lo que se traduce a crear 2 tipo de patrones dentro de la función, estos serian "NSN" O "LSN"

  String forCompare = "1234-1234";
  ArrayList<Character> specials = new ArrayList<>();
  specials.add('-');
  Parser f = new Parser();
  Parser.MutableTypeData<Boolean> match = f.new MutableTypeData<>(false);
  Parser.MutableTypeData<Boolean> match2 = f.new MutableTypeData<>(false);
  checkFlexibleFormat("Ahdionw-12348925", null, null, null, pattern, specials, '|', match, null, 0, true, null, false);//esta coincidiria con el patrón "LSN"
  checkFlexibleFormat("1234-1234", null, null, null, pattern, specials, '|', match, null, 0, true, null, false); //esta coincidiria con el patrón "NSN"
  
  //en este caso saldra el primer mensaje, porque ambas cadenas coincidieron, esto debido a que ambas coinciden con alguno de los patrones esperados
  if(match || match2){
    System.out.println("Some of string match with the pattern expected\n");
  }
  else System.out.println("Another string match with the pattern expected\n");
  ~~~~

 * Como notamos en el ejemplo anterior, las ***compuertas OR*** lo que hacen es *crear* o ***permitir otros tipos de patrones***, que **internamente se crean**, y **funcionan por índices**, **no** por opciones como comunmente se usan, es decir usar OR en este contexto **no** es que pueda ser toda la cadena de un formato o otro, sino que quiere decir que la ***cadena o mejor dicho el formato flexible interno generado para esta***, en el **índice indicado** puede ser de **multiples opciones**, pero **solo ese**, el ***resto de los índices se quedan intactos***, es decir deben ser **identicos**.

 ## Considereciones Importantes

 ---------------------------------------------

 * Se recomienda ***no usar repeticiones de caracteres cuando uses compuertas OR***, porque pueden haber **comportamientos no esperados**, es decir ***no poner cosas como `"N|LLN"`*** porque pueden haber problemas, porque si volvemos a la ***explicación de los formatos flexibles y como se generan***, sabemos que eso ***nunca puede suceder***, entonces si quieres separar que deba ser la primera parte un `"N"` pero luego debe seguir un `"L"`, entonces, se ***recomienda usar los caracteres especiales o `"S"`*** para **dividir entre palabras** es decir debe ser `"N|LSLN"` O `"N|LN"`, pero ***nunca debes repetir caracteres consecutivamente***.
 
 * **No pongas múltiples compuertas OR consecutivas en diferentes índices sin estar separadas por algún tipo de carácter.**  
  **Ejemplo incorrecto:** `"N|L|P|WL|N"`  
    En este caso, implica que podrías tener dos letras seguidas en la misma posición, pero el sistema nunca generará `"LL"` porque solo cambia el tipo cuando cambia el carácter.  
   Si necesitas separar partes, utiliza un carácter especial (`"S"`) o un delimitador claro en la cadena original para marcar la división

   Entonces deberias separarlo o eliminar el "L" o opcion "L" en alguno de los dos OR
   **Ejemplo correcto:** `"N|L|P|WSL|N"` o `"N|L|P|WN"` o `"N|P|WL|N"`

 * Las compuertas OR en la implemenación acutal de las funciones que la manejan, solo se puede utilizar en ***formatos flexibles creados manualmente***, es decir no se pueden extraer compuertas OR de cadenas, es decir ***no puedes poner ejemplos de cadenas que generen compuertas OR***

 