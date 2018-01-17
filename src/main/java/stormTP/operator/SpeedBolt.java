package stormTP.operator;

import java.util.Map;

import javax.json.Json;
import javax.json.JsonObject;
import javax.json.JsonObjectBuilder;

import org.apache.storm.task.OutputCollector;
import org.apache.storm.task.TopologyContext;
import org.apache.storm.topology.OutputFieldsDeclarer;
import org.apache.storm.topology.base.BaseWindowedBolt;
import org.apache.storm.tuple.Fields;
import org.apache.storm.tuple.Tuple;
import org.apache.storm.tuple.Values;
import org.apache.storm.windowing.TupleWindow;

import stormTP.core.TortoiseManager;



public class SpeedBolt extends BaseWindowedBolt {
	private static final long serialVersionUID = 4262387370788107343L;
	private OutputCollector collector;

    @Override
    public void prepare(Map stormConf, TopologyContext context, OutputCollector collector) {
        this.collector = collector;
    }

    @Override
    public void execute(TupleWindow inputWindow) {
    	
    	int taille = inputWindow.get().size();
    	if (taille == 10){
	    	Tuple first = inputWindow.get().get(0);
	    	Tuple last = inputWindow.get().get(taille - 1);
	    	// Tuple last = inputWindow.get().get(inputWindow.get().size());
	    	
	    	double speed = TortoiseManager.computeSpeed(first.getLongByField("top"),
	    			last.getLongByField("top"),
	    			first.getIntegerByField("position"),
	    			last.getIntegerByField("position"));
	    	
	    	String top = String.valueOf(first.getLongByField("top")) + "-" + String.valueOf(last.getLongByField("top"));
	
		    
	        collector.emit(inputWindow.get().get(0),new Values(
	        		first.getLongByField("id"),
	        		first.getStringByField("nom"),
	        		top,
	        		speed));
    	}
        
        
    }

    @Override
    public void declareOutputFields(OutputFieldsDeclarer arg0) {
    	arg0.declare(new Fields("id", "top", "nom", "vitesse"));
    }
}






