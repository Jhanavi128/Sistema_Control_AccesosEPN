@echo off
REM ========================================
REM Sistema de Control de Accesos EPN
REM ========================================

SETLOCAL EnableDelayedExpansion

REM Navegar al directorio del script
cd /d "%~dp0"

echo.
echo ========================================
echo  Sistema de Control de Accesos EPN
echo ========================================
echo.

REM === VALIDACION 1: Verificar que Java este instalado ===
java -version >nul 2>&1
if %ERRORLEVEL% NEQ 0 (
    echo [ERROR] Java no esta instalado o no esta en el PATH
    echo.
    echo Por favor instale Java 16 o superior:
    echo https://www.oracle.com/java/technologies/downloads/
    echo.
    pause
    exit /b 1
)

echo [OK] Java detectado
java -version 2>&1 | findstr /i "version"

REM === VALIDACION 2: Verificar estructura de directorios ===
if not exist "bin" (
    echo [ERROR] No se encuentra la carpeta 'bin'
    echo Asegurate de copiar toda la carpeta del proyecto
    pause
    exit /b 1
)

if not exist "accesoEPN\lib" (
    echo [ERROR] No se encuentra 'accesoEPN\lib'
    echo Asegurate de copiar toda la carpeta del proyecto
    pause
    exit /b 1
)

if not exist "accesoEPN\Storage\DataBase\bd_acceso_epn.sqlite" (
    echo [ERROR] No se encuentra la base de datos
    echo Ruta esperada: accesoEPN\Storage\DataBase\bd_acceso_epn.sqlite
    pause
    exit /b 1
)

echo [OK] Estructura de directorios correcta
echo.
echo Iniciando aplicacion...
echo.

REM === EJECUTAR LA APLICACION ===
java --enable-native-access=ALL-UNNAMED -cp "bin;accesoEPN/lib/*" App

echo.
echo ========================================
echo  Aplicacion finalizada
echo ========================================
pause
