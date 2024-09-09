#!/bin/bash

# List of directories to iterate through
directories=(
  "food-donation-app-match"
  "food-donation-app-request"
  "food-donation-app-donate"
  # "food-donation-app-userservice"
)

# Iterate through each directory and run `mvn clean install`
for dir in "${directories[@]}"; do
  if [ -d "$dir" ]; then  # Check if the directory exists
    echo "Building in directory: $dir"
    cd "$dir" || exit 1   # Change to the directory or exit if it fails
    mvn clean install -Dmaven.test.skip=true   # Run Maven clean install
    if [ $? -ne 0 ]; then # Check if the build was successful
      echo "Build failed in directory: $dir"
      exit 1
    fi
    cd ..                 # Go back to the parent directory
  else
    echo "Directory not found: $dir"
  fi
done

echo "All builds completed successfully."

