# (Deprecated) Prisma Cloud Jenkins Plugin (IaC)

[![License](https://img.shields.io/github/license/jenkinsci/github-plugin.svg)](LICENSE)

***This plugin has been deprecated and will be removed at the end of February 2023. Please transition to Prisma Cloud Code Security.***

## Description

This plugin enables Prisma Cloud Infrastructure-as-Code (IaC) scan from Palo Alto Networks Inc. in Jenkins. Prisma Cloud IaC Scan identifies insecure configurations in common Infrastructure as Code (IaC) templates such as AWS Cloud Formation Templates, HashiCorp Terraform templates and Kubernetes App Deployment YAML files


Use this plugin to detect and remove as many issues as possible before deploying to production.

It enables you to:
- Scan Infrastructure-as-Code (IaC) templates (AWS CFT, Terraform templates, Kubernetes app deployment YAML files)
- Display IaC template security issues with severity in Jenkins UI after the scan
- Define severity based failure criteria for passing / failing the builds

## Prerequisite

Authentication credentials from Prisma Cloud. Get Prisma Cloud here - https://marketplace.paloaltonetworks.com/s/product-rdl

[Configuration Documentation](https://docs.paloaltonetworks.com/prisma/prisma-cloud/prisma-cloud-admin/prisma-cloud-devops-security/use-the-prisma-cloud-plugin-for-jenkins.html#id443f2a86-174c-4b12-8d77-15dd990e7908)
