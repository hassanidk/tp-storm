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

public class Exit2Bolt implements IRichBolt {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	String ipM = "";
	int port = -1;
	StreamEmiter semit = null;
	private OutputCollector collector;
	
	
	
	
	public Exit2Bolt(int port, String ip) {
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
		int position = t.getIntegerByField("position");
		int nbDevant = t.getIntegerByField("nbDevant");
		int nbDerriere = t.getIntegerByField("nbDerriere");
		int total = t.getIntegerByField("total");
		
		Runner r = new Runner(id, nom, nbDevant,nbDerriere,total, position, top );
		String n = r.getJSON_V1();
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