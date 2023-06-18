#!/usr/bin/env bash

SCRIPT_DIR=$( cd -- "$( dirname -- "${BASH_SOURCE[0]}" )" &> /dev/null && pwd )
DEPLOY_VER="2.2.1-SNAPSHOT"
RELEASE_VER="2.2.0"

echo $SCRIPT_DIR

find . -name "*.tag" -type f
find . -name "*.releaseBackup" -type f
find . -name "release.properties" -type f
find . -name "*.tag" -type f -delete
find . -name "*.releaseBackup" -type f -delete
find . -name "release.properties" -type f -delete

git checkout .

mvn clean

#mvn -B -DdevelopmentVersion=${DEPLOY_VER} -DrleaseVersion=${RELEASE_VER} -Dresume=false release:prepare release:perform -DdryRun=true
#mvn -B -DdevelopmentVersion="2.2.1-SNAPSHOT" -DrleaseVersion="2.2.0" -Dresume=false release:prepare release:perform -DdryRun=true -Pmegapack
