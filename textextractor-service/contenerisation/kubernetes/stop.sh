#!/usr/bin/env bash

kubectl delete all --all

kubectl config set-context --current --namespace=default

kubectl delete namespace kamilmucik-lkequiz-maestro
