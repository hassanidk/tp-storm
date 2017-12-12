screen -dmS StartStream sh
screen -S StartStream -X stuff "ssh p1302141@linuxetu.univ-lyon1.fr
"
screen -S StartStream -X stuff "ssh -i pedabdcloud -X ubuntu@192.168.239.146
"
screen -S StartStream -X stuff "./startStream.sh \"OTHOMENE-HASSANI\" \"A5\" \"224.0.0.146\"
"
screen -dmS ZookeeperNimbus sh
screen -S ZookeeperNimbus -X stuff "ssh p1302141@linuxetu.univ-lyon1.fr
"
screen -S ZookeeperNimbus -X stuff "ssh -i pedabdcloud -X ubuntu@192.168.239.146
"
screen -S ZookeeperNimbus -X stuff "cd lib/zookeeper-3.3.6/bin
"
screen -S ZookeeperNimbus -X stuff "sudo ./zkServer.sh start
"
screen -S ZookeeperNimbus -X stuff "cd /home/ubuntu/lib/apache-storm-1.0.2/bin
"
screen -S ZookeeperNimbus -X stuff "sudo ./storm nimbus
"

screen -dmS UI sh
screen -S UI -X stuff "ssh p1302141@linuxetu.univ-lyon1.fr
"
screen -S UI -X stuff "ssh -i pedabdcloud -X ubuntu@192.168.239.146
"
screen -S UI -X stuff "cd /home/ubuntu/lib/apache-storm-1.0.2/bin
"
screen -S UI -X stuff "sudo ./storm ui
"

screen -dmS Supervisor sh
screen -S Supervisor -X stuff "ssh p1302141@linuxetu.univ-lyon1.fr
"
screen -S Supervisor -X stuff "ssh -i pedabdcloud -X ubuntu@192.168.239.146
"
screen -S Supervisor -X stuff "cd /home/ubuntu/lib/apache-storm-1.0.2/bin
"
screen -S Supervisor -X stuff "sudo ./storm supervisor
"

screen -dmS Monitoring sh
screen -S Monitoring -X stuff "ssh p1302141@linuxetu.univ-lyon1.fr
"
screen -S Monitoring -X stuff "ssh -i pedabdcloud -X ubuntu@192.168.239.146
"
screen -S Monitoring -X stuff "cd /home/ubuntu
"

screen -S Monitoring -X stuff "node main.js 225.0.0.146
"
