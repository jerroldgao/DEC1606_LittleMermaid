package com.yirugao.dec1606_littlemermaid;

import java.util.HashMap;
import java.util.NoSuchElementException;

/**
 * Created by Yirugao on 11/4/16.
 */

public class SplitingNgram {
    private HashMap<String,HashMap<String,Integer>> result;
    public HashMap<String,HashMap<String,Integer>> splitNgram(String input){
        result = new HashMap<String, HashMap<String, Integer>>();
        if (input.isEmpty()){
            throw new NoSuchElementException();
        }
        input = input.replaceAll("\\{|\\}","");
        String[] grams = input.split(";",-1);
        for (String gram:grams){
            String[] set = gram.split(":",-1);
            String[] word = set[0].split("\\s+");
            result.put(word[0],new HashMap<String, Integer>());
            result.get(word[0]).put(word[1],Integer.parseInt(set[1]));
        }
        return null;
    }
}
