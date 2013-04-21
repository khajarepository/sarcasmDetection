package iiit.ir.sarcasmdetection;

import java.io.BufferedReader;

import java.io.DataInputStream;
import java.io.File;

import java.io.FileInputStream;

import java.io.InputStreamReader;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

public class SarcasmDetection {
    public SarcasmDetection() {
        super();
    }
    
    public List<String> sarcasmPatternMatching(String sdPatternString ,List<String> HighFrequencyWordsList ) {

        // Storing the strings

        List<String> patterns = new ArrayList<String>(); ;

        // Splitting the string on space
        String[] sdPatternStringarray=  sdPatternString.split(" ");

        List<Integer> HighFrequencyWordLocations = new ArrayList<Integer>();

        // finding the first occurence of HighFrequency word ;
        for ( int i=0 ; i <sdPatternStringarray.length ; i++) {


            // High Frequency Match Found 
            
           if(Collections.binarySearch(HighFrequencyWordsList, sdPatternStringarray[i]) >= 0 ) {
                            
               HighFrequencyWordLocations.add(i);
               
           }
        }
        
        if(HighFrequencyWordLocations.size()>=2) {
            
           for (int  j =0 ; j< HighFrequencyWordLocations.size() ; j++ ) {
               
               for ( int k = j+1 ; k < HighFrequencyWordLocations.size() ; k++) {
                   
                   int numberOfWordsBetweenHW = HighFrequencyWordLocations.get(k) - HighFrequencyWordLocations.get(j) ;
                                      
                   
                   switch (numberOfWordsBetweenHW) {
                               
                               // This is when the distance between two words is zero
                               case 1:  numberOfWordsBetweenHW = 0;
                                        //HighFrequencyWordLocations.remove(1);
                                        break;
                               
                               // This is when the distance between two HighFrquency Words is one , we need to check the word if it is [CW] or not 
                               case 2:  numberOfWordsBetweenHW = 1;
                                        
                                        if (sdPatternStringarray[(HighFrequencyWordLocations.get(j))+1].equals("CW")) {
                                            
                                                   String tempPatternString ="";
                                            
                                            for (int temp = HighFrequencyWordLocations.get(j) ; temp < HighFrequencyWordLocations.get(k)+1 ; temp++) {
                                                
                                                if( temp == HighFrequencyWordLocations.get(k) )
                                                {
                                                tempPatternString += sdPatternStringarray[temp];
                                                }
                                                else {
                                                    tempPatternString += sdPatternStringarray[temp]+" ";
                                                }
                                                

                                            }
                                            
                                            patterns.add( tempPatternString);
                                               }
                                        
                                        break;
                               case 3:  numberOfWordsBetweenHW = 2;
                               if (sdPatternStringarray[(HighFrequencyWordLocations.get(0))+1].equals("CW") || sdPatternStringarray[(HighFrequencyWordLocations.get(0))+2].equals("CW")) {
                                   
                                          String tempPatternString ="";
                                          
                                          for (int temp = HighFrequencyWordLocations.get(j) ; temp < HighFrequencyWordLocations.get(k)+1 ; temp++) {
                                          
                                          if( temp == HighFrequencyWordLocations.get(k) )
                                          {
                                          tempPatternString += sdPatternStringarray[temp];
                                          }
                                          else {
                                           tempPatternString += sdPatternStringarray[temp]+" ";
                                          }
                                          
                                          
                                          }
                                          
                                          patterns.add( tempPatternString);
                                      }
                                        break;
                               case 4:  numberOfWordsBetweenHW = 3;
                               
                               if (sdPatternStringarray[(HighFrequencyWordLocations.get(0))+1].equals("CW") || sdPatternStringarray[(HighFrequencyWordLocations.get(0))+2].equals("CW")
                               || sdPatternStringarray[(HighFrequencyWordLocations.get(0))+3].equals("CW")) {
                                   
                                          String tempPatternString ="";
                                          
                                          for (int temp = HighFrequencyWordLocations.get(j) ; temp < HighFrequencyWordLocations.get(k)+1 ; temp++) {
                                          
                                          if( temp == HighFrequencyWordLocations.get(k) )
                                          {
                                          tempPatternString += sdPatternStringarray[temp];
                                          }
                                          else {
                                           tempPatternString += sdPatternStringarray[temp]+" ";
                                          }
                                          
                                          
                                          }
                                          
                                          patterns.add( tempPatternString);
                                      }
                               
                                        break;
                               case 5:  numberOfWordsBetweenHW = 4;
                               if (sdPatternStringarray[(HighFrequencyWordLocations.get(0))+1].equals("CW") || sdPatternStringarray[(HighFrequencyWordLocations.get(0))+2].equals("CW")
                               || sdPatternStringarray[(HighFrequencyWordLocations.get(0))+3].equals("CW") || sdPatternStringarray[(HighFrequencyWordLocations.get(0))+4].equals("CW") ) {
                                   
                                          String tempPatternString ="";
                                          
                                          for (int temp = HighFrequencyWordLocations.get(j) ; temp < HighFrequencyWordLocations.get(k)+1 ; temp++) {
                                          
                                          if( temp == HighFrequencyWordLocations.get(k) )
                                          {
                                          tempPatternString += sdPatternStringarray[temp];
                                          }
                                          else {
                                           tempPatternString += sdPatternStringarray[temp]+" ";
                                          }
                                          
                                          
                                          }
                                          
                                          patterns.add( tempPatternString);
                                      }
                                        break;
                               default: 
                                        break;
                           }
                                 
                   
                   
               }
               
               
           }
           
        }

               
        return patterns;
        
    }
    
    
    public HashMap patternProcessing(String dirLocation) {
        
        HashMap wordCountMap = new HashMap();
        HashMap hwAndcwtMap = new HashMap(); 
        HashMap fileAndPatternsMap = new HashMap(); 
        List<SDPattern> listOfSDPattern = new ArrayList<SDPattern>();
        /**
         * Key : CWList, Value : List of CWs
         * Key : HWList, Value : List of HWs
         * Key : Data, Value : HashMap<String, List<SDPattern>>
         * Key : PatternSet, Value : All pattern
         */
        HashMap finalReturnMap = new HashMap();
        
        List<String> listOfPatterns = new ArrayList<String>();
        String stringContent = "";
        List<String> stringContentList = new ArrayList<String>() ;
        
        String directory = dirLocation ;
        
        File[] filename = listOfFiles(directory);
        
        for (int i =0 ; i < filename.length ; i++) {
            
          stringContent= fileToString(filename[i].toString());
            //stringContent= fileToString(directory+"/"+filename[i].toString());  
           stringContent= replaceSpecialCharacters(stringContent);
            
          wordCountMap =  wordCount(stringContent,wordCountMap);
            
          

            stringContentList.add(stringContent);

        }   
        hwAndcwtMap  =hwAndcw(3,2,wordCountMap,hwAndcwtMap);
        
        List<String> cwList = null;
        List<String> hwList = null;
        
        HashMap<String, List<SDPattern>> strXpatternMap = new HashMap<String, List<SDPattern>>();
        List<SDPattern> patternList = null;
        Set<SDPattern> allPatternSet = new HashSet<SDPattern>();
        
        HashMap hwMap = (HashMap)hwAndcwtMap.get("HWmap");
        
        HashMap cwMap = (HashMap)hwAndcwtMap.get("CWmap");
        
        
        cwList = Collections.list(Collections.enumeration(cwMap.keySet()));
        hwList = Collections.list(Collections.enumeration(hwMap.keySet()));
        
        Collections.sort(cwList);
        Collections.sort(hwList);
        
        for (int i =0 ; i < stringContentList.size() ; i++) {
        
            
            String cwReplacedString=replacebyCW(stringContentList.get(i),cwList);
            
            listOfPatterns = sarcasmPatternMatching(cwReplacedString,hwList );
            
            if(listOfPatterns==null || listOfPatterns.size()==0)
                continue;
            
            fileAndPatternsMap.put(cwReplacedString,listOfPatterns );
            
            patternList = new ArrayList<SDPattern>();
            for(String pattern:listOfPatterns)
                patternList.add(new SDPattern(pattern));
            allPatternSet.addAll(patternList);
            strXpatternMap.put(cwReplacedString, patternList);
            
            //TODO Currently we are not removing the patterns which are coming only in 1 string
        }
        
        finalReturnMap.put("Data", strXpatternMap);
        finalReturnMap.put("CWList", cwList);
        finalReturnMap.put("HWList", hwList);
        finalReturnMap.put("PatternSet", allPatternSet);
        
        return finalReturnMap;
            //return fileAndPatternsMap;
        
        
        
       // }
        
    }
    
    public File[] listOfFiles(String directoryName){
        
        
        
        
        File folder = new File(directoryName);
        File[] listOfFiles = folder.listFiles();

            for (int i = 0; i < listOfFiles.length; i++) {
              if (listOfFiles[i].isFile()) {
                System.out.println("File " + listOfFiles[i].getName());
              } else if (listOfFiles[i].isDirectory()) {
                System.out.println("Directory " + listOfFiles[i].getName());
              }
            }
        
        
        return listOfFiles;
        
    }
    
    
    
    public String replaceSpecialCharacters(String fileContent ) {
        
        String[] rep = new String[]{"\\;","\\.","\\,","\\:","//","\\~"};
        
        for(String r:rep)
            fileContent = fileContent.replaceAll(r, " "+r+" ");
        
        fileContent = fileContent.replaceAll("  ", " ");
        return fileContent;
        /*char[] fileContentArray= fileContent.toCharArray();
        
         for ( int i =0 ; i<=fileContentArray.length ; i++) {
             
             
             if( fileContentArray[i] == ';') {
                 
                 fileContent = fileContent.substring(0, i-1)+" ; "+ fileContent.substring(i+1,fileContentArray.length);
             }
             else if( fileContentArray[i] == '.') {
                 
                 fileContent = fileContent.substring(0, i)+" . "+ fileContent.substring(i+1,fileContentArray.length);
             }
             else if( fileContentArray[i] == ',') {
                 
                 fileContent = fileContent.substring(0, i)+" , "+ fileContent.substring(i+1,fileContentArray.length);
             }

             else if( fileContentArray[i] == ':') {
                 
                 fileContent = fileContent.substring(0, i)+" : "+ fileContent.substring(i+1,fileContentArray.length);
             }

             else if( fileContentArray[i] == '/') {
                 
                 fileContent = fileContent.substring(0, i)+" / "+ fileContent.substring(i+1,fileContentArray.length);
             }

             else if( fileContentArray[i] == '~') {
                 
                 fileContent = fileContent.substring(0, i)+" ~ "+ fileContent.substring(i+1,fileContentArray.length);
             }
             
             fileContentArray= fileContent.toCharArray();
         }
        
        return fileContent;
        */
    }
    
    public String replacebyCW(String fileContent, List<String> cwList) {
        
        if(cwList==null)
            return fileContent;
        
//        for(String cw:cwList)
//            fileContent = fileContent.replaceAll(cw, "CW");
//        return fileContent;
        
        String stringAfterReplacing = "";
        
        String[] fileContentSplitArray=  fileContent.split(" ");
        
        for ( int i=0 ; i <fileContentSplitArray.length ; i++) {
            
        if(Collections.binarySearch(cwList, fileContentSplitArray[i]) >= 0 ) {
                         
            fileContentSplitArray[i]= "CW";
            
        }
        
            stringAfterReplacing += fileContentSplitArray[i]+" " ;
        
        }
        
        
        return stringAfterReplacing;
        
    }
    
    
    
    
    public HashMap wordCount(String fileContent, HashMap countMap) {
        
       // Map countMap = new HashMap();
        
        String[] fileContentSplitArray=  fileContent.split(" ");
        
        for ( int i=0 ; i <fileContentSplitArray.length ; i++) {
            
            if(fileContentSplitArray[i].trim().length()==0)
                continue;
            
        if(countMap.get(fileContentSplitArray[i].trim())== null) {
            countMap.put(fileContentSplitArray[i].trim(),1);
        }else{
            
            int count= Integer.parseInt(countMap.get(fileContentSplitArray[i].trim()).toString())+1 ;
            
            countMap.put(fileContentSplitArray[i].trim(),count);
        }
                        
        
       
    }
    
    return countMap;
    
    } 
    
    
    public HashMap hwAndcw(int HWlimit , int CWlimit , HashMap fileContentCount,HashMap  returnMap) {
        
        
         Map HWmap = new HashMap();
         Map CWmap = new HashMap();
        
        if(fileContentCount == null ) {
            return null ;
        }
        
        Iterator it = fileContentCount.entrySet().iterator();
            while (it.hasNext()) {
                
                
                Map.Entry pairs = (Map.Entry)it.next();
                System.out.println(pairs.getKey() + " = " + pairs.getValue());
                
                if(Integer.parseInt(pairs.getValue().toString()) >= HWlimit) {
     
                    HWmap.put(pairs.getKey(), pairs.getValue());
                }
                
            if(Integer.parseInt(pairs.getValue().toString()) <= CWlimit) {
        
                CWmap.put(pairs.getKey(), pairs.getValue());
              }
        
    }
    
    
    returnMap = new HashMap();
    

        returnMap.put("HWmap",HWmap);
        returnMap.put("CWmap",CWmap);
        
        
        return returnMap;
    
    }
    
    public String fileToString(String fileName ) {
        
       String fileToStringConversion = "" ;
       
          try{
          // Open the file that is the first 
          // command line parameter
          FileInputStream fstream = new FileInputStream(fileName);
          // Get the object of DataInputStream
          DataInputStream in = new DataInputStream(fstream);
          BufferedReader br = new BufferedReader(new InputStreamReader(in));
          String strLine;
          //Read File Line By Line
          while ((strLine = br.readLine()) != null)   {
          // Print the content on the console
         fileToStringConversion += strLine ;
       
          }
          //Close the input stream
          in.close();
       
            }catch (Exception e){//Catch exception if any
       
          System.err.println("Error: " + e.getMessage());
       
          }
          
        
        return fileToStringConversion ; 
    }
    
    

}
