dftp() {
	URL=$(echo $1 | sed 's/ftp:\/\//ftp:\/\/michong:xtkj2016@/g')
	wget -c "$URL"
}

for hour in $(seq -f "%02g" 0 23); do
  cd /tmp
  dftp "ftp://10.0.0.172//yuechang/202002/14/request-20200214$hour.gz"
  cd /mnt/spark-3.0.0-preview2-bin-hadoop2.7
  bin/spark-submit --master local[4] /mnt/party-stat/out/artifacts/party_old_log_jar/party-old-log.jar \
    "/tmp/request-20200214$hour.gz" "/tmp/data2/20200214$hour.txt"
  rm -rf "/tmp/request-20200214$hour.gz"
done

for hour in $(seq -f "%02g" 0 05); do
  cd /tmp
  dftp "ftp://10.0.0.172//yuechang/202002/15/request-20200215$hour.gz"
  cd /mnt/spark-3.0.0-preview2-bin-hadoop2.7
  bin/spark-submit --master local[4] /mnt/party-stat/out/artifacts/party_old_log_jar/party-old-log.jar \
    "/tmp/request-20200215$hour.gz" "/tmp/data2/20200215$hour.txt"
  rm -rf "/tmp/request-20200215$hour.gz"
done