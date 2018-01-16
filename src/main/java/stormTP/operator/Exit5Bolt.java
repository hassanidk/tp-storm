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

import stormTP.stream.StreamEmiter;

public class Exit5Bolt implements IRichBolt {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	String ipM = "";
	int port = -1;
	StreamEmiter semit = null;
	private OutputCollector collector;
	
	
	
	public Exit5Bolt(int port, String ip) {
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
		System.out.println("EXIT4BOLTDEBUT");
		String nom = t.getStringByField("nom");
		long id = t.getLongByField("id");
		long top =  t.getLongByField("top");
		double speed = t.getDoubleByField("speed");
		
		JSONObject jobj = new JSONObject();
		try {
			jobj.put("id", id);
			jobj.put("top", top);
			jobj.put("nom", nom);
			jobj.put("vitesse", speed);
			
		} catch (JSONException e) {
			e.printStackTrace();
		}
		
		String n = jobj.toString();
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