#!/usr/bin/env bash

echo "Test STARTING"
BASE_DIR=${SCRIPT_DIR:-`PWD`}
BASE_PROJECT_DIR=${BASE_DIR}/..
echo "SCRIPT_DIR: $BASE_DIR"

#watchman watch-del-all

# rm -rf node_modules && npm install

npm test  -- --coverage --watchAll=false
#npm t --silent -- --watchAll=false --json --passWithNoTests

echo "Test STOPPED"