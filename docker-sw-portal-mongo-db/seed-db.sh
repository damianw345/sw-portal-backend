#!/usr/bin/env bash
set -e
mongorestore -v -u root -p password --drop --gzip --archive=/data/mongodump.gz
