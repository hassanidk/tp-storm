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

import stormTP.stream.StreamEmiter;

public class MyTortoiseBolt implements IRichBolt {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private OutputCollector collector;
	
	String ipM = "";
	int port = -1;
	StreamEmiter semit = null;

	
	public MyTortoiseBolt (int port, String ip) {
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
		
		String json = t.getValueByField("json").toString();
		try {
			JSONObject jobj = new JSONObject(json);
			long id = jobj.getLong("id");
	        int top = jobj.getInt("top");
	        int position = jobj.getInt("position");
	        int nbDevant = jobj.getInt("nbDevant");
	        int nbDerriere = jobj.getInt("nbDerriere");
	        int total = jobj.getInt("total");
		
			JSONObject r = new JSONObject();

		    r.put("id", id);
		    r.put("top", top);
		    r.put("nom", "okokokokoko");
		    r.put("position", position);
		    r.put("nbDevant", nbDevant);
		    r.put("nbDerriere", nbDerriere);
		    r.put("total", total);

			
			collector.emit(t,new Values(r.toString()));

		
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	
		
	}

	@Override
	public void prepare(Map arg0, TopologyContext arg1, OutputCollector arg2) {
		// TODO Auto-generated method stub
		
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