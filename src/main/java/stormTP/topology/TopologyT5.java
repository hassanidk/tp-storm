package stormTP.topology;

import org.apache.storm.Config;

import org.apache.storm.StormSubmitter;
import org.apache.storm.topology.TopologyBuilder;
import org.apache.storm.topology.base.BaseWindowedBolt.Count;
import org.apache.storm.topology.base.BaseWindowedBolt.Duration;
import java.util.concurrent.TimeUnit;

import stormTP.operator.ComputeBonusBolt;
import stormTP.operator.Exit5Bolt;
import stormTP.operator.GiveRankBolt;
import stormTP.operator.MasterInputStreamSpout;
import stormTP.operator.MyTortoiseBolt;
import stormTP.operator.SpeedBolt;


public class TopologyT5 {
	
public static void main(String[] args) throws Exception {
	//@TODO
	
	
	
	
        
    }

}
