screen -dmS zooknimbus sh
screen -S zooknimbus -X stuff "ssh p1302141@linuxetu.univ-lyon1.fr
"
screen -S zooknimbus -X stuff "ssh -i pedabdcloud -X ubuntu@192.168.76.173
"
screen -S zooknimbus -X stuff "cd lib/zookeeper-3.3.6/bin
"
screen -S zooknimbus -X stuff "sudo ./zkServer.sh start
"
screen -S zooknimbus -X stuff "cd /home/ubuntu/lib/apache-storm-1.0.2/bin
"
screen -S zooknimbus -X stuff "sudo ./storm nimbus
"

screen -dmS UI sh
screen -S UI -X stuff "ssh p1302141@linuxetu.univ-lyon1.fr
"
screen -S UI -X stuff "ssh -i pedabdcloud -X ubuntu@192.168.76.173
"
screen -S UI -X stuff "cd /home/ubuntu/lib/apache-storm-1.0.2/bin
"
screen -S UI -X stuff "sudo ./storm ui
"

screen -dmS Supervisor sh
screen -S Supervisor -X stuff "ssh p1302141@linuxetu.univ-lyon1.fr
"
screen -S Supervisor -X stuff "ssh -i pedabdcloud -X ubuntu@192.168.76.173
"
screen -S Supervisor -X stuff "cd /home/ubuntu/lib/apache-storm-1.0.2/bin
"
screen -S Supervisor -X stuff "sudo ./storm supervisor
"

screen -dmS Monitoring sh
screen -S Monitoring -X stuff "ssh p1302141@linuxetu.univ-lyon1.fr
"
screen -S Monitoring -X stuff "ssh -i pedabdcloud -X ubuntu@192.168.76.173
"
screen -S Monitoring -X stuff "cd /home/ubuntu
"

screen -S Monitoring -X stuff "node main.js 225.0.1.5
"
