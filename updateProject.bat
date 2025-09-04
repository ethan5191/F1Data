@echo off
REM This script automates a 'git pull' to update the project.
REM This command is critical: it changes the current directory to where the script is located.
REM This ensures the git command works regardless of where the user double-clicks the file from.
cd /d "%~dp0"
echo Pulling latest changes from the remote repository...
REM This is the safest and most common option for a single user.
git pull origin main
echo.
echo Update complete.
pause
