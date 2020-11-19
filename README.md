# Prisma Cloud Jenkins Plugin (IaC)

[![License](https://img.shields.io/github/license/jenkinsci/github-plugin.svg)](LICENSE)

## Description

This plugin enables Prisma Cloud Infrastructure-as-Code (IaC) scan from Palo Alto Networks Inc. in Jenkins. Prisma Cloud IaC Scan identifies insecure configurations in common Infrastructure as Code (IaC) templates such as AWS Cloud Formation Templates, HashiCorp Terraform templates and Kubernetes App Deployment YAML files


Use this plugin to detect and remove as many issues as possible before deploying to production.

It enables you to:
- Scan Infrastructure-as-Code (IaC) templates (AWS CFT, Terraform templates, Kubernetes app deployment YAML files)
- Display IaC template security issues with severity in Jenkins UI after the scan
- Define severity based failure criteria for passing / failing the builds

## Prerequisite

 Authentication credentials from Prisma Cloud. Get Prisma Cloud here - https://marketplace.paloaltonetworks.com/s/product-rdl
