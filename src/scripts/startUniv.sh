screen -dmS zook sh
screen -S zook -X stuff "ssh -i pedabdcloud -X ubuntu@192.168.76.173
"
screen -S zook -X stuff "cd lib/zookeeper-3.3.6/bin
"
screen -S zook -X stuff "sudo ./zkServer.sh start
"

screen -dmS nimbus sh
screen -S zook -X stuff "ssh -i pedabdcloud -X ubuntu@192.168.76.173
"
screen -S zook -X stuff "cd /home/ubuntu/lib/apache-storm-1.0.2/bin
"
screen -S zook -X stuff "sudo ./storm nimbus
"

screen -dmS ui sh
screen -S ui -X stuff "ssh -i pedabdcloud -X ubuntu@192.168.76.173
"
screen -S ui -X stuff "cd /home/ubuntu/lib/apache-storm-1.0.2/bin
"
screen -S ui -X stuff "sudo ./storm ui
"

screen -dmS super sh
screen -S super -X stuff "ssh -i pedabdcloud -X ubuntu@192.168.76.173
"
screen -S super -X stuff "cd /home/ubuntu/lib/apache-storm-1.0.2/bin
"
screen -S super -X stuff "sudo ./storm supervisor
"

screen -dmS Monitoring sh
screen -S Monitoring -X stuff "ssh -i pedabdcloud -X ubuntu@192.168.76.173
"
screen -S Monitoring -X stuff "cd /home/ubuntu
"
screen -S Monitoring -X stuff "node main.js 225.0.4.5
"

