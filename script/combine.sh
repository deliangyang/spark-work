#!/usr/bin/env bash

rm -rf /tmp/log-size
mkdir -p /tmp/log-size

for hour in $(seq -f "%02g" 20 22); do
  FILENAME="/tmp/data4/20201208$hour.txt"
  GO_FILENAME="/tmp/data4/go-20201208$hour.txt"
  STAT_FILENAME="/tmp/data4/stat-20201208$hour.txt"
  GO_STAT_FILENAME="/tmp/data4/go-stat-20201208$hour.txt"

  cat "$FILENAME"/* | sed 's/LogData(//g; s/)$//g' > "/tmp/log-size/20201208$hour.txt"
  cat "$GO_FILENAME"/* | sed 's/LogData(//g; s/)$//g' > "/tmp/log-size/go-20201208$hour.txt"
  cat "$STAT_FILENAME"/* > "/tmp/log-size/stat-20201208$hour.txt"
  cat "$GO_STAT_FILENAME"/* > "/tmp/log-size/go-stat-20201208$hour.txt"
done


cd /tmp/log-size/
tar -zvcf log-size.tar.gz *.txt