README - Java VM Code Translator
# Java Virtual Machine Code Translator
**Author:** Pablo Riveros Perea

**Project:** VM Code Translator

**Profile:** [https://github.com/P4bl246]

------------------------------------------------------

**NOTE:** If you can't use any extension to translate in your IDE, or you don't have an IDE installed or any development tool that allows you to run this code, you can *create a `codespace` on GitHub* by copying this repository or the [source code](SourceCodePackage), or cloning the repository.

[***I don't know how to create a `codespace`***](https://docs.github.com/en/codespaces/developing-in-a-codespace/creating-a-codespace-for-a-repository)

## Dependencies

------------------------------------------------------
* You must have *JDK 1.8* or later installed.

[Download the JDK 24.0](https://www.oracle.com/java/technologies/downloads/)

* A modern Java compiler *preferably*

## Installation

------------------------------------------------------
***NOTE:*** All executables or ***.class*** are already included in the `SourceCodePackage` package, so recompilation is not required. (**Does not** require an installation process)

## Usage

-----------------------------------------------------
* Run `main` in the *file* `executable.java`, and have the path or name of the file or directory you want to compile or translate into machine language ready. **HACK***

***NOTE:*** If the path is outside the current folder in which it is located, you must enter the ***absolute path***, or in other words, the full path of the file or directory to be compiled.

* After you get your ***.asm*** file if you want to run it in the Nand2Tetris emulator, you need to use the [assembler]()

## Recommendations, Observations, and Considerations

------------------------------------------------
* The documentation in ***JavaDoc*** is written in English, but within the functions or methods there are comments in English and Spanish.

* The `SourceCodePackage` package contains 2 *test files*, these are:
   * ***File.vm***
   * ***VMcodeFolder*** (*folder*)

* The code compiles unsigned numbers. ie 0-32767

* This project was created for the *VM to assembler code translator* project for the *Nand2Tetris* course, so it was created with an academic focus, and does not use best practices for this reason.

* This project focuses on (with the ***exception*** of the `TranslateToAssembly` class):

    * ***Functionality***
    * ***Maintainability***
    * ***Reusability***

* The code is open to improvements. Some ideas could include:

     * Separating utility functions from those specific to the class in a more explicit and organized way.

     * Adding unit, integration, and functionality tests to each method and class.

     * ***AND OTHERS...***

* If you want know more about [flexibles formats](Explain_formatPattern_functions/Flexible_Format_Pattern), or [stricts formats](Explain_formatPattern_functions/Strict_Format_Patterns), go to this.

-------------------------------------------------
## License

This repository and its contents are licensed under a ***Creative Commons Attribution-NonCommercial-ShareAlike 4.0 International (CC BY-NC-SA 4.0)*** license - See the [LICENSE](LICENCIA-LICENSE/LICENSE) for more details.
