#!/bin/bash
if ! java --version &>/dev/null
then
    echo "Java not found in PATH. Please install at least Java 17 and try again."
    read -p "Press any key to exit..."
    exit 1
else
    echo "Java found:"
    java --version
    echo "Starting the build process..."
    ./mvnw clean package -DskipTests
    echo "Build finished."
    read -p "Press any key to exit..."
fi
