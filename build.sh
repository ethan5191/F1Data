#!/bin/bash
echo "Starting the build process..."
./mvnw clean package -DskipTests
echo "Build finished."