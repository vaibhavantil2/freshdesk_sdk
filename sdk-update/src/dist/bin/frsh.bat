@ECHO OFF

IF NOT DEFINED FRSH_HOME (
  ECHO Please set the FRSH_HOME environment variable.
)

IF EXIST %FRSH_HOME%\config\first_install.bat (
  call %FRSH_HOME%\config\first_install.bat
  REM mklink /D %FRSH_HOME%\sdk\latest %FRSH_HOME%\sdk\frshsdk-%version%
  DEL %FRSH_HOME%\config\first_install.bat
)

IF NOT EXIST %FRSH_HOME%\sdk\latest\bin\frsh.bat (
  echo Installation not complete. Please run 'frsh-update.bat'.
)

%FRSH_HOME%\sdk\latest\bin\frsh.bat %*
