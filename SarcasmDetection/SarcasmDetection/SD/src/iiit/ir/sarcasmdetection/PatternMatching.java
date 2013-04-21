package iiit.ir.sarcasmdetection;

import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.TreeSet;

public class PatternMatching {
    public PatternMatching() {
        super();
    }
    
    public static void main(String[] args) {
        PatternMatching pm = new PatternMatching();
        Set<SDPattern> patternSet = new HashSet<SDPattern>();
        
        SDPattern pattern1 = new SDPattern();
        pattern1.setPattern("Garmin CW does not");
        pattern1.setWordList(Arrays.asList("Garmin","CW","does","not"));
        patternSet.add(pattern1);
        
        pattern1 = new SDPattern();
        pattern1.setPattern("Garmin CW not");
        pattern1.setWordList(Arrays.asList("Garmin","CW","not"));
        patternSet.add(pattern1);
        
        pattern1 = new SDPattern();
        pattern1.setPattern("Garmin CW CW does not");
        pattern1.setWordList(Arrays.asList("Garmin","CW","CW","does","not"));
        patternSet.add(pattern1);
                
                
//        pattern1 = new SDPattern();
//        pattern1.setPattern("Garmin CW does not");
//        pattern1.setWordList(Arrays.asList("Garwin","CW","does","not"));
//        patternSet.add(pattern1);
                
          //double label =      pm.patternMatching("Garmin CW does not care much about product quality or customer support", patternSet);
       // System.out.println(label);
        
    }
    
    /*
     * Assumptions - str already contains "." separated by spaces, and CWs are replaced woth CW
     * I/P - str, patternSet, hfwSet
     * O/P - stringXPatternMap, stringXLabelMap
     */
    public double patternMatching(String str, Set<SDPattern> patternSet, List<String> hfwList, Map<String, Set<SDPattern>> stringXPatternMap, Map<String, Double> stringXLabelMap) {
        
        if(str==null || str.trim().length()==0)
            return 0.0;
        if(patternSet==null || patternSet.size()==0)
            return 0.0;
        
        double label = 0.0; 
        String firstWord = null;
        String lastWord = null;
        int firstWordIndex = -1;
        int lastWordIndex = -1;
        
        Set<SDPattern> strPatternSet = stringXPatternMap.get(str);
        if(strPatternSet==null)
            strPatternSet = new HashSet<SDPattern>();
            
        String subString = null;
        Double addToLabel = null;
        
        //TODO Unhandled case of multiple HFWs
        for(SDPattern pattern:patternSet) {
        
            addToLabel = null;
            //4Types of match
            if(str.contains(pattern.getPattern())) {
                //Case 1. Complete match
                addToLabel = 1.0;
            }
            else {
                firstWord = pattern.getWordList().get(0);
                lastWord = pattern.getWordList().get(pattern.getWordList().size()-1);
                
                firstWordIndex = str.indexOf(firstWord);
                lastWordIndex = str.lastIndexOf(lastWord);
                
         
                
               
                if(firstWordIndex==-1 && lastWordIndex==-1) {
                    //Case of inbetween HFWs
                    
                    subString = str;
                    
                    Set<String> patternHFWSet = new HashSet<String>();
                    patternHFWSet.addAll(pattern.getWordList());
                    patternHFWSet.retainAll(hfwList);
                    
                    boolean containsAtleastOneHFW = false;
                    for(String patternString:patternHFWSet) {
                        if(subString.contains(patternString)) {
                            containsAtleastOneHFW = true;
                            break;
                        }
                    }
                    
                    if(containsAtleastOneHFW==false) 
                        continue;
                }
                else if(firstWordIndex==-1)
                    subString = str.substring(0, lastWordIndex+lastWord.length());
                else if(lastWordIndex==-1)
                    subString = str.substring(firstWordIndex);
                else if(lastWordIndex>=firstWordIndex)
                    subString = str.substring(firstWordIndex, lastWordIndex+lastWord.length());
                else
                    continue;        //Case 4. No Match
                
                int matchingWords = 0;
                for(String patternWord:pattern.getWordList()) {
                    if(subString.contains(patternWord)) {
                        matchingWords++;
                        subString = subString.replace(patternWord, "");
                    }
                }
                
                addToLabel = 0.1 *((double)matchingWords/pattern.getWordList().size());
                
            }
            
            if(addToLabel!=null) {
                label += addToLabel;
                
                strPatternSet.add(pattern);
            }
            
        }
        
        stringXPatternMap.put(str, strPatternSet);
        stringXLabelMap.put(str, label);
        return label;
    }
    
    private int findOccurrence(String str, String word) {
        String[] strArray = str.split(" ");
        int count=0;
        for(String s1:strArray)
            if(s1.equals(word))
                count++;
        return count;
    }
}
