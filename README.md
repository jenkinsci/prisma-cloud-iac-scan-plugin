# Prisma Cloud Jenkins Plugin (IaC)

[![License](https://img.shields.io/github/license/jenkinsci/github-plugin.svg)](LICENSE)

## Description

This extension enables Prisma Cloud Infrastructure-as-Code (IaC) scan from Palo Alto Networks Inc. in Jenkins. Prisma Cloud IaC Scan identifies insecure configurations in common Infrastructure as Code (IaC) templates - for example, AWS Cloud Formation Templates, HashiCorp Terraform templates, Kubernetes App Deployment YAML files


This plugin has two features:

- Scan Infrastructure-as-Code (IaC) templates (AWS CFT, Terraform templates, Kubernetes app deployment YAML files)
- Display IaC template security issues with severity in Jenkins UI after the scan
- Define severity based failure criteria for passing / failing the pipelines
- Setup service connection to Prisma Cloud

## Prerequisite

 Authentication credentials from Prisma Cloud. Get Prisma Cloud here - https://marketplace.paloaltonetworks.com/s/product-rdl
