package stormTP.operator;

import java.util.Map;
import javax.json.Json;
import javax.json.JsonObject;
import javax.json.JsonObjectBuilder;

import org.apache.storm.state.KeyValueState;
import org.apache.storm.task.OutputCollector;
import org.apache.storm.task.TopologyContext;
import org.apache.storm.topology.OutputFieldsDeclarer;
import org.apache.storm.topology.base.BaseStatefulWindowedBolt;
import org.apache.storm.tuple.Fields;
import org.apache.storm.tuple.Tuple;
import org.apache.storm.tuple.Values;
import org.apache.storm.windowing.TupleWindow;



public class RankEvolutionBolt extends BaseStatefulWindowedBolt<KeyValueState<String, Integer>> {
	private static final long serialVersionUID = 4262379330788107343L;
    private  KeyValueState<String, Integer> state;
    private  int sum;

    private OutputCollector collector;

    @Override
    public void prepare(Map stormConf, TopologyContext context, OutputCollector collector) {
        this.collector = collector;
    }

    @Override
    public void initState(KeyValueState<String, Integer> state) {
        this.state = state;
        sum = state.get("sum", 0);
        System.out.println("initState with state [" + state + "] current sum [" + sum + "]");
    }

    @Override
    public void execute(TupleWindow inputWindow) {
    	arg0.declare(new Fields("id", "nom", "top", "rank", "total"));
    	
    	int taille = inputWindow.get().size() - 1;
		long minTop = inputWindow.get().get(0).getLongByField("top");
		long maxTop = inputWindow.get().get(taille).getLongByField("top");
		
        for (Tuple t : inputWindow.get()) {
        	
    		
    	}
        state.put("sum", cpt);
        
        JsonObjectBuilder r = Json.createObjectBuilder();
        r.add("test", "statelessWithWindow");
        r.add("nbNewTuples", cpt);
		r.add("totalNumberOfTuples", cpt);
        JsonObject row = r.build();
	    
        collector.emit(inputWindow.get(),new Values(row.toString()));
        
    }

    @Override
    public void declareOutputFields(OutputFieldsDeclarer declarer) {
        declarer.declare(new Fields("json"));
    }
}

