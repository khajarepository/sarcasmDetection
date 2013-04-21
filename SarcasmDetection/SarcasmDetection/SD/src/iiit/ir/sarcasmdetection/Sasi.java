package iiit.ir.sarcasmdetection;

import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Hashtable;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

public class Sasi {
    public Sasi() {
        super();
    }
    
    private void code(String dirLocation, List<String> testData) {
        SarcasmDetection sd = new SarcasmDetection();
        Map map = sd.patternProcessing(dirLocation);
        /**
         * Key : CWList, Value : List of CWs
         * Key : HWList, Value : List of HWs
         * Key : Data, Value : HashMap<String, List<SDPattern>>
         * Key : PatternSet, Value : All pattern
         */
        
        Map<String, List<SDPattern>> data = (Map<String, List<SDPattern>>)map.get("Data");
        Set<SDPattern> patternSet = (Set<SDPattern>)map.get("PatternSet");
        List<String> hfwList = (List<String>)map.get("HWList");
        List<String> cwList = (List<String>)map.get("CWList");
        PatternMatching pm = new PatternMatching();
        
        HashMap<String, Set<SDPattern>> stringXPatternMap = new HashMap<String, Set<SDPattern>>();
        HashMap<String, Double> stringXLabelMap = new HashMap<String, Double>();
                                                                       
        for(String str:data.keySet()) {
            pm.patternMatching(str, patternSet, hfwList, stringXPatternMap, stringXLabelMap);
        }
        
        HashMap<Double, Integer> labelXCount = new HashMap<Double, Integer>();
        Integer count = 0;
        Double maxLabel = Double.MIN_VALUE;
        for(String str:stringXLabelMap.keySet()) {
            count = labelXCount.get(stringXLabelMap.get(str));
            if(count==null)
                count=0;
            count++;
            labelXCount.put(stringXLabelMap.get(str), count);
            
            if(stringXLabelMap.get(str)>maxLabel)
                maxLabel = stringXLabelMap.get(str);
        }
        
        HashMap<Double, Integer> labelXCountNew = new HashMap<Double, Integer>();
        for(Double label:labelXCount.keySet()) {
            labelXCountNew.put((label/maxLabel)*5, labelXCount.get(label));
        }
        labelXCount = labelXCountNew;
        
        HashMap<String, Double> stringXLabelMapNew = new HashMap<String, Double>();
        for(String str:stringXLabelMap.keySet()) {
            stringXLabelMapNew.put(str, (stringXLabelMap.get(str)/maxLabel)*5);
        }
        stringXLabelMap = stringXLabelMapNew;
        
        NearestNeighbour nn = new NearestNeighbour();
        String replacedStr = null;
        List<String> patternList = null;
        Set<SDPattern> sdPatternSet = null;
        List<Double> labelList = null;
        
        int denom = 0;
        Double num =0.0; 
        //Testing starts from here
        for(String testStr:testData) {
            replacedStr = sd.replaceSpecialCharacters(testStr);
            replacedStr = sd.replacebyCW(replacedStr, cwList);
            patternList = sd.sarcasmPatternMatching(replacedStr, hfwList);
            
            sdPatternSet = new HashSet<SDPattern>();
            for(String str:patternList)
                sdPatternSet.add(new SDPattern(str));
            
            labelList = nn.getNearestNeighbour(replacedStr, sdPatternSet, stringXPatternMap, stringXLabelMap);
            
            denom = 0;
            num = 0.0;
            int k=5;
            for(Double label:labelList) {
                if(labelXCount.get(label)!=null) {
                    denom += labelXCount.get(label);
                    num += (labelXCount.get(label) * label);
                }
            }
            
            if(labelList.size()==0)
                denom=1;
            System.out.println(testStr+" :"+(num/denom)/k);
            
            
        }
    }
    public static void main(String[] args) {
        Sasi sasi = new Sasi();
        sasi.code("D:\\tk\\iiit\\ir\\sarcasm", Arrays.asList("Chaos, panic, & disorder-my work here is done. Can I trade this job for what's behind door #1?"));
        //System.out.println("a//b".replaceAll("//", " \\; "));
    }
}
