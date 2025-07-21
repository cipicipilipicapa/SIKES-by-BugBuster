@echo off
echo Compiling JavaFX Application with XStream...
echo.

REM Compile the Java files with XStream library
javac -d bin -cp "src;lib/xstream-1.4.20.jar" src/sikes/*.java

if %errorlevel% neq 0 (
    echo Compilation failed!
    pause
    exit /b 1
)

echo Compilation successful!
echo.
echo Running Sikes Application...
echo.

REM Run the application with XStream library
java -cp "bin;lib/xstream-1.4.20.jar" sikes.Main

pause 