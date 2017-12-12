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

public class Exit2Bolt   extends ExitBolt implements IRichBolt {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	String ipM = "";
	int port = -1;
	StreamEmiter semit = null;
	private OutputCollector collector;
	
	
	
	public Exit2Bolt(int port, String ip) {
		super(port, ip);
		// TODO Auto-generated constructor stub
	}

	
	@Override
	public void cleanup() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void execute(Tuple t) {
		// TODO Auto-generated method stub
			
		String json = t.getValueByField("json").toString();
		try {
			JSONObject jobj = new JSONObject(json);
			long id = jobj.getLong("id");
			if (id == 0) {
				String n = t.getValueByField("json").toString();
				this.semit.send(n);
				collector.ack(t);
				
				return;
			}


		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		
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