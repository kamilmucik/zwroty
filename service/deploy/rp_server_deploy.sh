#!/bin/sh

FILENAME=$1
MODULE=$2
VERSION=$3

echo "\n1. Docker Deploy Front "${FILENAME}" "${MODULE}" "${VERSION}
#docker images
#
#rm -rf /tmp/d3s/
mkdir -p /tmp/rp/
cd /tmp/rp/

docker stop ${MODULE}
docker rm ${MODULE}
docker rmi registry.hub.docker.com/kamilmucik/${MODULE}:${VERSION}

docker load -i ${FILENAME}

cd /home/ubuntu/return-parcel

docker-compose up  -d --build ${MODULE}

echo "\n3. Docker images"
docker images
sleep 60

docker ps

