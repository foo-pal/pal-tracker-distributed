#!/usr/bin/env bash
set -e -o pipefail

cd $(dirname $0)

fly -t pal set-pipeline -p pal-tracker -c pipeline.yml -l variables.yml
