#
#for hour in $(seq -f "%02g" 0 23); do
#  cd /tmp
#  scp -i ~/yangdeliang \
#    "yangdeliang@10.0.0.106:/data/logs_bak2/k8s-logs-1-12/202008/25/http.request.v1-20200825$hour.gz" .
#  cd /mnt/spark-3.0.0-preview2-bin-hadoop2.7
#  bin/spark-submit --master local[4] /tmp/party-php.jar \
#    "/tmp/http.request.v1-20200825$hour.gz" "/tmp/data/app-20200825$hour.txt"
#  rm -rf "/tmp/http.request.v1-20200825$hour.gz"
#done

for hour in $(seq -f "%02g" 0 06); do
  cd /tmp
  scp -i ~/yangdeliang \
    "yangdeliang@10.0.0.106:/data/logs_bak2/k8s-logs-1-12/202008/26/http.request.v1-20200826$hour.gz" .
  cd /mnt/spark-3.0.0-preview2-bin-hadoop2.7
  bin/spark-submit --master local[4] /tmp/party-php.jar \
    "/tmp/http.request.v1-20200826$hour.gz" "/tmp/data/app-20200826$hour.txt"
  rm -rf "/tmp/http.request.v1-20200826$hour.gz"
done