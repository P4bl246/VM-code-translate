 README-Traductor de código de VM en Java
 # Traductor de código de Maquina Virtual en Java
  **Autor:** Pablo Riveros Perea
  
  **Proyecto:** Traductor de código VM
  
  **Perfil:**  [https://github.com/P4bl246]
  
  ------------------------------------------------------

**[Don't understand ![English](https://flagcdn.com/w40/gb.png)](README.es.md)**



**NOTA:** Si no puedes usar ninguna extensión para traducir en tu IDE, o no tienes un IDE instalado o alguna herramienta de desarrollo que te permita ejecutar este código, puedes *crear un `codespace` en github* copiando este repositorio o el [código fuente](SourceCodePackage), o clonando el repositorio

[***No se crear un `codespace`***](https://docs.github.com/en/codespaces/developing-in-a-codespace/creating-a-codespace-for-a-repository)

 ## Dependencias

 ------------------------------------------------------
 * Debe tener instalado *JDK v.9* o posteriores
 
 [Descargue el JDK de su preferencia](https://www.oracle.com/java/technologies/downloads/)

 * Un compilador de java *preferiblemente* moderno

## Instalación 

------------------------------------------------------
***NOTA:*** Todos los ejecutables o ***.class*** ya estan incluidos en el paquete `SourceCodePackage` entonces no requiere recompilación. (**NO** nececita proceso de intalación)

## Uso

------------------------------------------------------
* Ejecute el `main` que esta en el *archivo* `executable.java`, y tenga lista la ruta o el nombre de el archivo o directiorio que quiere "compilar" o *traducir a lenguaje de máquina **HACK***
  
***NOTA:*** Si la ruta esta por fuera de la carpeta actual en la que esta debe poner la ***ruta absoulta***, o en otras palabras, la ruta completa de el archivo o directorio a compilar
  
## Recomendaciones, Observaciones y Consideraciones

------------------------------------------------
* La documentación en ***JavaDoc*** esta escrita en inglés, pero dentro de las funciones o métodos hay comentarios en inglés y español

* El paquete `SourceCodePackage` contine 2 *archivos de prueba*, estos son:
   * ***Archivo.vm***
   * ***VMcodeFolder*** (*carpeta*)

* El codigo compila números sin signo es decir de 0-32767
  
* Este proyecto fue creado para el proyecto de la creación de el *traductor de código VM a ensamblador* de el curso *Nand2Tetris*, por lo que se creo con un enfoque académico, y no utiliza las mejores prácticas por esta razón

* Este proyecto se enfoca en la (a ***excepción*** de la clase `TranslateToAssembly`):
  
   * ***Funcionalidad***
   * ***Mantenibilidad***
   * ***Reusabilidad***
       
* El código esta abierto a mejoras, algunas ideas de estas podrian ser:
  
   * Dividir de forma mas explicita y organizada las funciones utilitarias, de las que son propias de la clase
     
   * Agregar test unitarios y de integración y funcionalidad a cada método y clase
     
   * ***Y OTROS...***

-------------------------------------------------
## Licencia

 
