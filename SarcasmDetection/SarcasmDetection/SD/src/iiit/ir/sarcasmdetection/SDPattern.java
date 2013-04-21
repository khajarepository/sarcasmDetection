package iiit.ir.sarcasmdetection;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class SDPattern {
    public SDPattern() {
        super();
    }
    
    SDPattern(String pattern) {
        this.pattern = pattern;
        wordList = new ArrayList<String>();
        wordList.addAll(Arrays.asList(pattern.split(" ")));
    }
    
    private List<String> wordList = null;
    private String pattern = null;


    public void setWordList(List<String> wordList) {
        this.wordList = wordList;
    }

    public List<String> getWordList() {
        return wordList;
    }

    public void setPattern(String pattern) {
        this.pattern = pattern;
    }

    public String getPattern() {
        return pattern;
    }

    

    public boolean equals(Object obj) {
        if(!(obj instanceof SDPattern))
            return false;
        return this.pattern.equals(((SDPattern)obj).pattern);
    }

    public int hashCode() {
        return pattern.hashCode();
    }
}
