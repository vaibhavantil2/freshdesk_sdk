@ECHO OFF

IF NOT DEFINED FRSH_HOME (
  ECHO Please set the FRSH_HOME environment variable.
)

IF EXIST %FRSH_HOME%\config\first_install.bat (
  CALL %FRSH_HOME%\config\first_install.bat
  REM mklink /D %FRSH_HOME%\sdk\latest %FRSH_HOME%\sdk\frshsdk-%version%
  REM DEL %FRSH_HOME%\config\first_install.bat
)

IF NOT EXIST %FRSH_HOME%\sdk\latest\bin\frsh.bat (
  ECHO Installation not complete. Please run 'frsh-update.bat'.
) else (
  %FRSH_HOME%\sdk\latest\bin\frsh.bat %*
)
