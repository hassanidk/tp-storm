package stormTP.operator;



import java.util.Map;


import javax.json.Json;
import javax.json.JsonObject;
import javax.json.JsonObjectBuilder;

import org.apache.storm.state.KeyValueState;
import org.apache.storm.task.OutputCollector;
import org.apache.storm.task.TopologyContext;
import org.apache.storm.topology.OutputFieldsDeclarer;
import org.apache.storm.topology.base.BaseStatefulBolt;
import org.apache.storm.tuple.Fields;
import org.apache.storm.tuple.Tuple;
import org.apache.storm.tuple.Values;

import stormTP.core.Runner;
import stormTP.core.TortoiseManager;
import stormTP.stream.StreamEmiter;


public class ComputeBonusBolt extends BaseStatefulBolt<KeyValueState<String, Integer>> {
	private static final long serialVersionUID = 4262379330722107343L;
    KeyValueState<String, Integer> kvState;
    int bonus;
    private OutputCollector collector;
	String ipM = "";
	int port = -1;
	StreamEmiter semit = null;


	public ComputeBonusBolt (int port, String ip) {
		this.port = port;
		this.ipM = ip; 
		this.semit = new StreamEmiter(this.port,this.ipM);
		
	}

    @Override
    public void execute(Tuple t) {
    	

		if (t.getLongByField("top") % 15 == 0) {
	
			int points = TortoiseManager.computePoints(t.getStringByField("rang"), t.getIntegerByField("total"));
			kvState.put("pointBonus", bonus + points);
			collector.emit(t, new Values(t.getLongByField("id"),
					t.getLongByField("top"), 
					t.getStringByField("nom"),
					bonus + points
					));
		
		}    

    }

    @Override
    public void initState(KeyValueState<String, Integer> state) {
        kvState = state;
        bonus = kvState.get("pointBonus", 0);

        
    }

    @Override
    public void prepare(Map stormConf, TopologyContext context, OutputCollector collector) {
        this.collector = collector;
    }
    
    @Override
    public void declareOutputFields(OutputFieldsDeclarer declarer) {
        declarer.declare(new Fields("json"));
    }


}