@echo off
TITLE Find strings

set searchStr={!}
set filePath=%USERPROFILE%\Documents\GitHub\SEPR3Project\Game\src
set fileExt=*.java
:start

echo Files containing
echo %searchStr%
echo.
FINDSTR /L /N /S "%searchStr%" "%filePath%\%fileExt%"
echo.
Pause
echo __________
echo.

GOTO start
