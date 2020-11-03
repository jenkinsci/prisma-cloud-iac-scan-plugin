#!/bin/bash
#######Perform IaC scan###########
#Get workspace path as param $1
cd $1


zip -r  iacscan.zip . -x \*.git\*
#echo "after zip content of repo_path"
#ls -al $repo_path