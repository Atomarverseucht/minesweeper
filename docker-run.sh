#!/bin/sh

# Allow X11 forwarding
export DOCKER_BUILDKIT=0

IMAGE="minesweeper:local"
# Build image if it doesn't exist
if ! docker images | grep -q minesweeper; then
  echo "Image not found, building $IMAGE..."
  docker build -t $IMAGE target/docker
  echo $?
fi
xhost +local:docker
docker run -it --rm -e DISPLAY=$DISPLAY -v /tmp/.X11-unix:/tmp/.X11-unix $IMAGE