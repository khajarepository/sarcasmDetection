package iiit.ir.sarcasmdetection;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.TreeSet;

public class NearestNeighbour {
    public NearestNeighbour() {
        super();
    }
    
    public List<Double> getNearestNeighbour(String testStr, 
                                     Set<SDPattern> testPatternSet, 
                                     HashMap<String, Set<SDPattern>> trainStrXPatternMap,
                                     Map<String, Double> strXLabelMap) {
        
        Map<Integer, List<String>> distanceXmatchingStrMap = new HashMap<Integer, List<String>>();
        List<String> matchingStrList = null;
        
        Set<SDPattern> commonPatternSet = null;
        Integer dist = null;
        for(String trainStr:trainStrXPatternMap.keySet()) {
            if(trainStrXPatternMap.get(trainStr) == null || trainStrXPatternMap.get(trainStr).size()==0)
                continue;
            
            commonPatternSet = new HashSet<SDPattern>();
            commonPatternSet.addAll(testPatternSet);

            commonPatternSet.retainAll(trainStrXPatternMap.get(trainStr));
            
            if(commonPatternSet.size()>0) {
                //Atleast one pattern is common, we can consider this training str as neighbour
                
                dist = testPatternSet.size() + trainStrXPatternMap.get(trainStr).size() - (2 * commonPatternSet.size());
                matchingStrList = distanceXmatchingStrMap.get(dist);
                if(matchingStrList==null)
                    matchingStrList = new ArrayList<String>();
                matchingStrList.add(trainStr);
                distanceXmatchingStrMap.put(dist, matchingStrList);
            }
        }
        
        
        Set<Integer> neighbourSet = new TreeSet<Integer>();
        neighbourSet.addAll(distanceXmatchingStrMap.keySet());
        
        int k=5;
        List<Double> labelList = new ArrayList<Double>();
        
        Iterator<Integer> neighbourIterator = neighbourSet.iterator();
        while(k>0) {
            if(!neighbourIterator.hasNext())
                break;
            
            dist = neighbourIterator.next();
            matchingStrList=distanceXmatchingStrMap.get(dist) ;
            
            for(String matchStr:matchingStrList) {
                labelList.add(strXLabelMap.get(matchStr));
                k--;
                if(k<=0)
                    break;
            }
        }
        
        return labelList;
    }
}
