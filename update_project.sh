#!/bin/bash
# This script automates a 'git pull' to update the project.
# This command is critical: it changes the current directory to where the script is located.
# This ensures the git command works regardless of where the user runs it from.
cd "$(dirname "$0")"
echo "Pulling latest changes from the remote repository..."
git pull origin main
echo
echo "Update complete."