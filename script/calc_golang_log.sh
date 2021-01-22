dftp() {
	URL=$(echo $1 | sed 's/ftp:\/\//ftp:\/\/michong:xtkj2016@/g')
	wget -c "$URL"
}

for hour in $(seq -f "%02g" 0 23); do
  cd /tmp
  dftp "ftp://10.0.0.172/k8s-logs-1-12/202012/25/http.request.v1-20201225$hour.gz" .
  cd /mnt/spark-3.0.0-preview2-bin-hadoop2.7
  bin/spark-submit --master local[6] /tmp/party-php.jar \
    "/tmp/http.request.v1-20201225$hour.gz" "/tmp/data/app-20201225$hour.txt"
  rm -rf "/tmp/http.request.v1-20201225$hour.gz"
done

for hour in $(seq -f "%02g" 0 06); do
  cd /tmp
  dftp "ftp://10.0.0.172/k8s-logs-1-12/202012/26/http.request.v1-20201226$hour.gz" .
  cd /mnt/spark-3.0.0-preview2-bin-hadoop2.7
  bin/spark-submit --master local[6] /tmp/party-php.jar \
    "/tmp/http.request.v1-20201226$hour.gz" "/tmp/data/app-20201226$hour.txt"
  rm -rf "/tmp/http.request.v1-20201226$hour.gz"
done