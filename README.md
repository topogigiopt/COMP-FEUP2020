# Java-- compiler

 This project is an assignment from the Compilers class at FEUP. The goal was to build a compiler of the Java-- language to bytecodes, using a JavaCC parser and jasmin to translate JVM instructions to Java bytecodes, as seen in the image below:
 
![](https://github.com/topogigiopt/COMP-FEUP2020/blob/master/docs/compilerflow.png)

For more specifications about the project read the [assignment pdf](https://github.com/topogigiopt/COMP-FEUP2020/blob/master/docs/jmm-CompilerProject2020-v1.2.pdf).

## Build

To compile the program, run ``gradle build``. This will compile your classes to ``classes/main/java`` and copy the JAR file to the root directory. The JAR file will have the same name as the repository folder.

### Execute

The generated classes given Java--code can be integrated in a Java application. Those classes can invoke Java methods previously compiled to bytecodes(the utility of this concept will be detailed later in this document).The jmmcompiler is executed using the following notation:

```
java main.jmm [-d] [-r=<num>] [-o] <input_file.jmm>
```
or

```
java –jar jmm.jar [-d] [-r=<num>] [-o] <input_file.jmm>
```
Where <input_file.jmm> is the Java-- class we would like to compile.

The -d option tells the compiler to not print the AST generated by the parser and to not throw the exceptions created during the semantic analysis and the syntatic analysis.

The "–r" option tells the compiler to use only the first <num> registers of the JVM when assigning the local variables used in each Java-- function to the local JVM variables.
  
Without the "–r" option (similar to –r=0), the compiler will use the available JVM local variables to store the local variables used in each Java-- function.

With the "–o" option, the compiler would perform a set of code optimizations, except we didn't implement it.
