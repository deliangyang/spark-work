dftp() {
	URL=$(echo $1 | sed 's/ftp:\/\//ftp:\/\/michong:xtkj2016@/g')
	wget -c "$URL"
}

for hour in $(seq -f "%02g" 20 22); do
  cd /tmp
  dftp "ftp://10.0.0.172/yuechang/202012/08/standard-request-20201208$hour.gz"
  cd /mnt/spark-3.0.0-preview2-bin-hadoop2.7
  bin/spark-submit --master local[8] /mnt/party-stat/target/log-size-jar-with-dependencies.jar \
    "/tmp/standard-request-20201208$hour.gz" "/tmp/data4/20201208$hour.txt" "/tmp/data4/stat-20201208$hour.txt"
  rm -rf "/tmp/standard-request-20201208$hour.gz"
done

# ftp://10.0.0.172/k8s-logs-1-12/202012/08/http.request.v1-2020120814.gz
for hour in $(seq -f "%02g" 20 22); do
  cd /tmp
  dftp "ftp://10.0.0.172/k8s-logs-1-12/202012/08/http.request.v1-20201208$hour.gz"
  cd /mnt/spark-3.0.0-preview2-bin-hadoop2.7
  bin/spark-submit --master local[8] /mnt/party-stat/target/log-size-jar-with-dependencies.jar \
    "/tmp/http.request.v1-20201208$hour.gz" "/tmp/data4/go-20201208$hour.txt" "/tmp/data4/go-stat-20201208$hour.txt"
  rm -rf "/tmp/http.request.v1-20201208$hour.gz"
done