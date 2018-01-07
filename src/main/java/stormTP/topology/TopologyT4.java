package stormTP.topology;


import org.apache.storm.Config;

import org.apache.storm.StormSubmitter;
import org.apache.storm.topology.TopologyBuilder;
import org.apache.storm.topology.base.BaseWindowedBolt.Count;

import stormTP.operator.ComputeBonusBolt;
import stormTP.operator.Exit3Bolt;
import stormTP.operator.GiveRankBolt;
import stormTP.operator.MasterInputStreamSpout;
import stormTP.operator.MyTortoiseBolt;

public class TopologyT4 {
	
	public static void main(String[] args) throws Exception {
		
		int nbExecutors = 1;
		int portINPUT = 9001;
		int portOUTPUT = 9002;
		String ipmINPUT = "224.0.0." + args[0];
		String ipmOUTPUT = "225.0." + args[0] + "." + args[1];
    	
		/*Création du spout*/
		MasterInputStreamSpout spout = new MasterInputStreamSpout(portINPUT, ipmINPUT);
    	/*Création de la topologie*/
    	TopologyBuilder builder = new TopologyBuilder();
        /*Affectation à la topologie du spout*/
    	
        builder.setSpout("localStream", spout);
      
        builder.setBolt("myTortoise",  new MyTortoiseBolt(portOUTPUT,ipmOUTPUT), nbExecutors).shuffleGrouping("localStream");
 
        builder.setBolt("rank",  new GiveRankBolt(portOUTPUT,ipmOUTPUT), nbExecutors).shuffleGrouping("myTortoise");
        
        builder.setBolt("compute", new ComputeBonusBolt(portOUTPUT,ipmOUTPUT), nbExecutors).shuffleGrouping("rank");
        
        builder.setBolt("exit", new Exit3Bolt(portOUTPUT, ipmOUTPUT), nbExecutors).shuffleGrouping("compute");
       
        /*Création d'une configuration*/
        Config config = new Config();
        /*Affectation de workers pour la topologie */
        config.setNumWorkers(1);
        /*La topologie est soumise à STORM*/
        
        StormSubmitter.submitTopology("topoT3", config, builder.createTopology());
        
        
}
}
