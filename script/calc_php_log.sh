
for hour in $(seq -f "%02g" 22 23); do
  cd /tmp
  scp -i ~/yangdeliang \
    "yangdeliang@10.0.0.106:/data/logs_bak2/yuechang/202008/25/standard-request-20200825$hour.gz" .
  cd /mnt/spark-3.0.0-preview2-bin-hadoop2.7
  bin/spark-submit --master local[4] /tmp/party-php.jar \
    "/tmp/standard-request-20200825$hour.gz" "/tmp/data/20200825$hour.txt"
  rm -rf "/tmp/standard-request-20200825$hour.gz"
done

for hour in $(seq -f "%02g" 0 06); do
  cd /tmp
  scp -i ~/yangdeliang \
    "yangdeliang@10.0.0.106:/data/logs_bak2/yuechang/202008/26/standard-request-20200826$hour.gz" .
  cd /mnt/spark-3.0.0-preview2-bin-hadoop2.7
  bin/spark-submit --master local[4] /tmp/party-php.jar \
    "/tmp/standard-request-20200826$hour.gz" "/tmp/data/20200826$hour.txt"
  rm -rf "/tmp/standard-request-20200826$hour.gz"
done