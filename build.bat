@echo off
echo Starting the build process...
mvnw clean package -DskipTests
echo Build finished. Press any key to exit.
pause >nul