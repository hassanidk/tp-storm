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

import stormTP.core.TortoiseManager;



public class RankEvolutionBolt extends BaseStatefulWindowedBolt<KeyValueState<String, Integer>> {
	private static final long serialVersionUID = 4262379330788107343L;
    private  KeyValueState<String, Integer> state;
    
    private OutputCollector collector;

    @Override
    public void prepare(Map stormConf, TopologyContext context, OutputCollector collector) {
        this.collector = collector;
    }

    @Override
    public void initState(KeyValueState<String, Integer> state) {
        this.state = state;
        state.put("rangMoyenPrecedent", 10);


    }

    @Override
    public void execute(TupleWindow inputWindow) {
    	
    	
    	int taille = inputWindow.get().size() - 1;
		long minTop = inputWindow.get().get(0).getLongByField("top");
		long maxTop = inputWindow.get().get(taille).getLongByField("top");
		String tops = String.valueOf(minTop) + "-" + String.valueOf(maxTop);
		String[] rangs =new String[taille + 1];
		
		int i = 0;
        for (Tuple t : inputWindow.get()) {
        	rangs[i] = t.getStringByField("rank");
        	i++;	
    	}
        int rangMoyen = TortoiseManager.giveAverageRank(rangs);
  
        String evolution = TortoiseManager.giveRankEvolution(rangMoyen, state.get("rangMoyenPrecedent"));
        state.put("rangMoyenPrecedent", rangMoyen);

        collector.emit(inputWindow.get(),new Values(
        		inputWindow.get().get(0).getLongByField("id"),
        		inputWindow.get().get(0).getStringByField("nom"),
        		tops,
        		evolution
        		));
        
    }

    @Override
    public void declareOutputFields(OutputFieldsDeclarer declarer) {
        declarer.declare(new Fields("id", "nom", "tops", "evolution"));
    }
}

