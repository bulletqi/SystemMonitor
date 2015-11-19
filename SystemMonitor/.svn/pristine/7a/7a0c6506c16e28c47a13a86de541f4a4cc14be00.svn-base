@echo off
setlocal
cd ../
mkdir logs
cd bin
set _WRAPPER_BASE=wrapper
set _WRAPPER_DIR=
set _WRAPPER_CONF_DEFAULT="../config/wrapper.conf"
if "%OS%"=="Windows_NT" goto nt
echo This script only works with NT-based versions of Windows.
goto :eof

:nt
IF not DEFINED _WRAPPER_DIR goto dir_undefined
set _WRAPPER_DIR_QUOTED="%_WRAPPER_DIR:"=%"
if not "%_WRAPPER_DIR:~-2,1%" == "\" set _WRAPPER_DIR_QUOTED="%_WRAPPER_DIR_QUOTED:"=%\"
if "%_WRAPPER_DIR_QUOTED:~2,1%" == ":" goto absolute_path
if "%_WRAPPER_DIR_QUOTED:~1,1%" == "\" goto absolute_path
set _REALPATH="%~dp0%_WRAPPER_DIR_QUOTED:"=%"
goto pathfound

:dir_undefined
set _REALPATH="%~dp0"
goto pathfound
:absolute_path
set _REALPATH="%_WRAPPER_DIR_QUOTED:"=%"

:pathfound
if "%PROCESSOR_ARCHITEW6432%"=="AMD64" goto amd64
if "%PROCESSOR_ARCHITECTURE%"=="AMD64" goto amd64
if "%PROCESSOR_ARCHITECTURE%"=="IA64" goto ia64
set _WRAPPER_L_EXE="%_REALPATH:"=%%_WRAPPER_BASE%-windows-x86-32.exe"
goto search
:amd64
set _WRAPPER_L_EXE="%_REALPATH:"=%%_WRAPPER_BASE%-windows-x86-64.exe"
goto search
:ia64
set _WRAPPER_L_EXE="%_REALPATH:"=%%_WRAPPER_BASE%-windows-ia-64.exe"
goto search
:search
set _WRAPPER_EXE="%_WRAPPER_L_EXE:"=%"
if exist %_WRAPPER_EXE% goto conf
set _WRAPPER_EXE="%_REALPATH:"=%%_WRAPPER_BASE%.exe"
if exist %_WRAPPER_EXE% goto conf
echo Unable to locate a Wrapper executable using any of the following names:
echo %_WRAPPER_L_EXE%
echo %_WRAPPER_EXE%
pause
goto :eof

:conf
if [%_WRAPPER_CONF_OVERRIDE%]==[true] (
    set _WRAPPER_CONF="%~f1"
    if not [%_WRAPPER_CONF%]==[""] (
        shift
        goto :startup
    )
)
set _WRAPPER_CONF="%_WRAPPER_CONF_DEFAULT:"=%"

:startup
if not [%_PASS_THROUGH%]==[true] (
	if not [%1]==[] (
		echo WARNING: Extra arguments will be ignored. Please check usage in the batch file.
	)
)

:parameters
set _PARAMETERS=%_PARAMETERS% %1
shift
if not [%1]==[] goto :parameters

if not [%_PASS_THROUGH%]==[true] (
    %_WRAPPER_EXE% -i %_WRAPPER_CONF%
) else (
    %_WRAPPER_EXE% -i %_WRAPPER_CONF% -- %_PARAMETERS%
)
if not errorlevel 1 goto :eof
pause


