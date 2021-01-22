dftp() {
	URL=$(echo $1 | sed 's/ftp:\/\//ftp:\/\/michong:xtkj2016@/g')
	wget -c "$URL"
}

# 2020-12-25 16:12

for hour in $(seq -f "%02g" 5 5); do
  cd /tmp
  dftp "ftp://10.0.0.172/yuechang/202012/25/standard-request-20201225$hour.gz" .
  cd /mnt/spark-3.0.0-preview2-bin-hadoop2.7
  bin/spark-submit --master local[6] /tmp/party-php.jar \
    "/tmp/standard-request-20201225$hour.gz" "/tmp/data/20201225$hour.txt"
  rm -rf "/tmp/standard-request-20201225$hour.gz"
done

#for hour in $(seq -f "%02g" 3 05); do
#  cd /tmp
#  dftp "ftp://10.0.0.172/yuechang/202012/26/standard-request-20201226$hour.gz" .
#  cd /mnt/spark-3.0.0-preview2-bin-hadoop2.7
#  bin/spark-submit --master local[6] /tmp/party-php.jar \
#    "/tmp/standard-request-20201226$hour.gz" "/tmp/data/20201226$hour.txt"
#  rm -rf "/tmp/standard-request-20201226$hour.gz"
#done