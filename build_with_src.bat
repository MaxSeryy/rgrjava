@echo off
setlocal

rem Set the source and target directories
set SRC_DIR=src
set BIN_DIR=bin

rem Create the bin directory if it doesn't exist
if not exist %BIN_DIR% (
    mkdir %BIN_DIR%
)

rem Compile the Java source files
javac -d %BIN_DIR% %SRC_DIR%\*.java

rem Copy source files to bin directory
xcopy %SRC_DIR% %BIN_DIR%\src /E /I /Y

rem Create the JAR file including source files
jar cfe ComputerStoreWithSrc.jar Main -C %BIN_DIR% .

echo Build completed.
endlocal