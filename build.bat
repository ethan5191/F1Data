@echo off
where java >nul 2>nul
if %errorlevel%==1 (
    @echo Java not found in path, please install at least Java 17 and try again.
    pause
) else (
    echo java found:
    java --version
    echo Starting the build process...
    call mvnw clean package -DskipTests
    echo Build finished. Press any key to exit.
    pause >nul
)