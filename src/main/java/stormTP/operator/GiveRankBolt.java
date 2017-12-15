package stormTP.operator;

import java.util.Map;

import org.apache.storm.task.OutputCollector;
import org.apache.storm.task.TopologyContext;
import org.apache.storm.topology.IRichBolt;
import org.apache.storm.topology.OutputFieldsDeclarer;
import org.apache.storm.tuple.Fields;
import org.apache.storm.tuple.Tuple;
import org.apache.storm.tuple.Values;
import org.json.JSONException;
import org.json.JSONObject;

import stormTP.core.Runner;
import stormTP.core.TortoiseManager;
import stormTP.stream.StreamEmiter;

public class GiveRankBolt implements IRichBolt {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private OutputCollector collector;
	
	String ipM = "";
	int port = -1;
	StreamEmiter semit = null;

	
	public GiveRankBolt (int port, String ip) {
		this.port = port;
		this.ipM = ip; 
		this.semit = new StreamEmiter(this.port,this.ipM);
		
	}
	

	@Override
	public void cleanup() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void execute(Tuple t) {
		// TODO Auto-generated method stub
		
		TortoiseManager tortoiseManager = new TortoiseManager(t.getLongByField("id"), t.getStringByField("nom"));
		Runner tortoise = tortoiseManager.computeRank(t.getLongByField("id"), 
				t.getLongByField("top"), 
				t.getStringByField("nom"), 
				t.getIntegerByField("nbDevant"),
				t.getIntegerByField("nbDerriere"),
				t.getIntegerByField("total")
				);
		if (tortoise != null) {
			//collector.emit(t,new Values(tortoise.getJSON_V1()));

			collector.emit(t, new Values(tortoise.getId(),
					tortoise.getNom(),
					tortoise.getTop(), 
					tortoise.getRang(),  
					tortoise.getTotal()));
			collector.ack(t);
		}
	
		
	}

	@Override
	public void prepare(Map arg0, TopologyContext arg1, OutputCollector arg2) {
		this.collector = arg2;
		
	}

	@Override
	public void declareOutputFields(OutputFieldsDeclarer arg0) {
		arg0.declare(new Fields("id", "nom", "top", "rank", "total"));
		
	}

	@Override
	public Map<String, Object> getComponentConfiguration() {
		// TODO Auto-generated method stub
		return null;
	}

}