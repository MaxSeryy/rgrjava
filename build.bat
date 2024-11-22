@echo off
setlocal

rem
set SRC_DIR=src
set BIN_DIR=bin

rem
if not exist %BIN_DIR% (
    mkdir %BIN_DIR%
)

rem 
javac -d %BIN_DIR% %SRC_DIR%\*.java

rem
jar cfe ComputerStore.jar Main -C %BIN_DIR% .

echo Build completed.
endlocal