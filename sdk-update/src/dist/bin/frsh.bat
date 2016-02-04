@ECHO OFF

IF NOT DEFINED FRSH_HOME (
  ECHO Please set the FRSH_HOME environment variable.
  EXIT /B
)

IF EXIST "%FRSH_HOME%\config\exec_version.bat" (
  CALL "%FRSH_HOME%\config\exec_version.bat"
  IF DEFINED version (
    "%FRSH_HOME%\sdk\frsh-%version%\bin\frsh.bat" %*
  ) ELSE (
    ECHO "Installation corrupted. Please reinstall."
  )
)
