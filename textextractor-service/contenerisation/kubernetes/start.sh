#!/usr/bin/env bash

kubectl create namespace kamilmucik-zwroty-maestro
kubectl config set-context --current --namespace=kamilmucik-zwroty-maestro


kubectl apply -f textextractor-svc.yaml
sleep 15


kubectl apply -f ingress.yaml

kubectl get all
