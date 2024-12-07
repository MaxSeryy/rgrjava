@echo off
echo Compiling Java project...
javac -d bin src\*.java
echo Copying resources...
xcopy src\foto.jpg bin\ /Y
xcopy src\d.png bin\ /Y
echo Creating JAR file...
jar cfe ComputerStore.jar Main -C bin .
echo Build complete: ComputerStore.jar