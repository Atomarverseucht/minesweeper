#!/bin/bash

# Allow X11 forwarding
xhost +local:docker

# Build image if it doesn't exist
if ! docker images | grep -q winesmeeper:latest; then
  echo "Image not found, building winesmeeper:latest..."
  docker build -t winesmeeper:latest .
fi

docker run -e DISPLAY=$DISPLAY -v /tmp/.X11-unix:/tmp/.X11-unix -ti winesmeeper:latest
