#!/usr/bin/env bash
set -euo pipefail

NAMESPACE="quality"
SONAR_PORT=9000

printf "\e[1;32m=== SonarQube + Kubernetes full restart ===\e[0m\n"

# Ensure Minikube is running
if ! minikube status &>/dev/null; then
  printf "\e[34mStarting minikube...\e[0m\n"
  minikube start --driver=docker
  printf "\e[1;32mdone\e[0m\n"
else
  printf "\e[1;32mMinikube already running.\e[0m\n"
fi

# Clean namespace (if exists)
if kubectl get ns "$NAMESPACE" &>/dev/null; then
  printf "\e[34mDeleting namespace '$NAMESPACE'...\e[0m\n"
  kubectl delete ns "$NAMESPACE" --wait=true
  printf "\e[1;32mdone\e[0m\n"
fi

printf "\e[34mCreating namespace...\e[0m\n"
kubectl apply -f k8s/namespace.yaml
printf "\e[1;32mdone\e[0m\n"

# Deploy PostgreSQL
printf "\e[34mDeploying PostgreSQL...\e[0m\n"
kubectl apply -f k8s/postgres.yaml
kubectl apply -f k8s/postgres-service.yaml
printf "\e[1;32mdone\e[0m\n"

# Deploy SonarQube 9.9 LTS
printf "\e[34mDeploying SonarQube (9.9 LTS)...\e[0m\n"
kubectl apply -f k8s/sonarqube.yaml
kubectl apply -f k8s/sonarqube-service.yaml
printf "\e[1;32mdone\e[0m\n"

# Wait for SonarQube pod
printf "\e[34mWaiting for SonarQube pod to be ready...\e[0m\n"
kubectl wait \
  --for=condition=Ready pod \
  -l app=sonarqube \
  -n "$NAMESPACE" \
  --timeout=5m
printf "\e[1;32mdone\e[0m\n"

# Port-forward (kill existing first)
printf "\e[34mRestarting port-forward on $SONAR_PORT...\e[0m\n"
pkill -f "kubectl port-forward svc/sonarqube" || true

kubectl port-forward \
  svc/sonarqube \
  "$SONAR_PORT:9000" \
  -n "$NAMESPACE" &

sleep 2
printf "\e[1;32mdone\e[0m\n"

printf "\n\e[1;32mSonarQube is starting up\e[0m\n"
printf "\e[1;32mhttp://localhost:$SONAR_PORT\e[0m\n"
