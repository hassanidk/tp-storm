package stormTP.topology;

import org.apache.storm.Config;
import org.apache.storm.StormSubmitter;
import org.apache.storm.topology.TopologyBuilder;

import stormTP.operator.Exit2Bolt;
import stormTP.operator.MyTortoiseBolt;
import stormTP.operator.MasterInputStreamSpout;

public class TopologyT2 {
	
	public static void main(String[] args) throws Exception {
		
		int nbExecutors = 1;
		int portINPUT = 9001;
		int portOUTPUT = 9002;
		String ipmINPUT = "224.0.0." + args[0];
		String ipmOUTPUT = "225.0.0." + args[0];
    	
		// Création du spout & topologie puis affectation
		MasterInputStreamSpout spout = new MasterInputStreamSpout(portINPUT, ipmINPUT);
    	TopologyBuilder builder = new TopologyBuilder();
        builder.setSpout("localStream", spout);
        
        // Affectation à la topologie du bolt qui affiche le binôme
        builder.setBolt("myTortoise",  new MyTortoiseBolt(portOUTPUT,ipmOUTPUT), nbExecutors).shuffleGrouping("localStream");
     
        // Affectation à la topologie du bolt qui émet le flux de sortie
        builder.setBolt("exit", new Exit2Bolt(portOUTPUT, ipmOUTPUT), nbExecutors).shuffleGrouping("myTortoise");
       
        // Création d'une configuration à laquelle on affecte un worker
        Config config = new Config();
        config.setNumWorkers(1);
        
        // Topologie est soumise à STORM
        StormSubmitter.submitTopology("topoT2", config, builder.createTopology());
        

        
        
}
		
	
}