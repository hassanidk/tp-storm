package stormTP.operator;

import java.util.Map;

import org.apache.storm.task.OutputCollector;
import org.apache.storm.task.TopologyContext;
import org.apache.storm.topology.IRichBolt;
import org.apache.storm.topology.OutputFieldsDeclarer;
import org.apache.storm.tuple.Fields;
import org.apache.storm.tuple.Tuple;
import org.json.JSONException;
import org.json.JSONObject;

import stormTP.core.Runner;
import stormTP.stream.StreamEmiter;

public class Exit3Bolt implements IRichBolt {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	String ipM = "";
	int port = -1;
	StreamEmiter semit = null;
	private OutputCollector collector;
	
	
	
	public Exit3Bolt(int port, String ip) {
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
			
		String nom = t.getStringByField("nom");
		long id = t.getLongByField("id");
		long top =  t.getLongByField("top");
		String rank = t.getStringByField("rank");
		int total = t.getIntegerByField("total");
		
		Runner r = new Runner(id, nom, 0,0,total, 0, top );
		r.setRang(rank);
		String n = r.getJSON_V2();
		this.semit.send(n);
		collector.ack(t);
		
		return;



		
		
	}

	@Override
	public void prepare(@SuppressWarnings("rawtypes") Map arg0, TopologyContext arg1, OutputCollector arg2) {
		this.collector = arg2;
		
	}

	@Override
	public void declareOutputFields(OutputFieldsDeclarer arg0) {
		arg0.declare(new Fields("json"));
	}

	@Override
	public Map<String, Object> getComponentConfiguration() {
		// TODO Auto-generated method stub
		return null;
	}

}