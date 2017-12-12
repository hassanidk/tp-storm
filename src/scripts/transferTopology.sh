screen -dmS TransferToServer sh
screen -S TransferToServer -X stuff "cd Storm
"
screen -S TransferToServer -X stuff "scp -i pedabdcloud target/stormTP-0.1-jar-with-dependencies.jar ubuntu@192.168.239.146:/home/ubuntu/
"

screen -dmS Transfert sh
screen -S Transfert -X "cd Storm
"
screen -S Transfert -X "scp -i pedabdcloud target/stormTP-0.1-jar-with-dependencies.jar ubuntu@192.168.239.146:/home/ubuntu
"

screen -dmS Topology sh
screen -S Topology -X stuff "ssh -i pedabdcloud -X ubuntu@192.168.239.146
"
screen -S Topology -X stuff "cd /home/ubuntu/lib/apache-storm-1.0.2/bin
"
screen -S Topology -X stuff " ./storm jar /home/ubuntu/stormTP-0.1-jar-with-dependencies.jar stormTP.topology.TopologyT2 146
"



