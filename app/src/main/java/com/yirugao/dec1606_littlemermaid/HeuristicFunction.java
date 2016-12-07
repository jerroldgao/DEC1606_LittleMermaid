package com.yirugao.dec1606_littlemermaid;

import com.firebase.client.snapshot.Index;
import com.firebase.client.utilities.Pair;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.HashMap;

/**
 * Created by Yirugao on 11/3/16.
 */

public class HeuristicFunction implements Comparator<String>{
    int flag = 0;
    private HashMap<String,ArrayList<Pair<String,Integer>>> nGramValues;
    private HashMap<String,Integer>  cateValue;
    private String prevWord;
    private String prevPrevWord;
    private double leftGramValue;
    private double rightGramValue;
    private int index;
    public HeuristicFunction(int index, String prevWord, String prevPrevWord, HashMap<String,ArrayList<Pair<String,Integer>>> nGramValue,HashMap<String,Integer> cateSumMap){
        if (index ==0||prevWord==null||prevPrevWord==null){
            flag = 0;
        }else if (index==1||prevWord==null){
            flag = 1;
            this.nGramValues =nGramValue;
            this.prevWord = prevWord;
        }else {
            flag = 2;
            this.nGramValues = nGramValue;
            this.prevWord = prevWord;
            this.prevPrevWord = prevPrevWord;
        }
        cateValue = cateSumMap;
        leftGramValue = 0.0;
        rightGramValue = 0.0;
        this.index = index;

    }

    @Override
    public int compare(String lhs, String rhs) {
        if (flag==1) {
            if (nGramValues.containsKey(prevWord)){
                for (Pair<String, Integer> pair : nGramValues.get(prevWord)) {
                    String grams = pair.getFirst();
                    String[] gram = grams.split(" ");
                    if (gram[0].equals(lhs)) {
                        leftGramValue += pair.getSecond();
                    }
                    if (gram[0].equals(rhs)) {
                        rightGramValue += pair.getSecond();
                    }
                }
            }
        }else if (flag == 2){
            if (nGramValues.containsKey(prevWord)) {
                for (Pair<String, Integer> pair : nGramValues.get(prevWord)) {
                    String grams = pair.getFirst();
                    String[] gram = grams.split(" ");
                    if (gram[0].equals(lhs)) {
                        leftGramValue += pair.getSecond();
                    }
                    if (gram[0].equals(rhs)) {
                        rightGramValue += pair.getSecond();
                    }
                }
            }
            if (nGramValues.containsKey(prevPrevWord)) {
                for (Pair<String, Integer> pair : nGramValues.get(prevPrevWord)) {
                    String grams = pair.getFirst();
                    String[] gram = grams.split(" ");
                    if (gram[0].equals(lhs)) {
                        leftGramValue += pair.getSecond();
                    }
                    if (gram[0].equals(rhs)) {
                        rightGramValue += pair.getSecond();
                    }
                }
            }
        }
        double func = Math.exp((-0.25)*((double)(index+1)));
        double leftCate = func*cateValue.get(lhs);
        double rightCate = func*cateValue.get(rhs);
        double leftNgram = (1-func)*leftGramValue;
        double rightNgram = (1-func)*rightGramValue;

        return  Double.compare(leftCate+leftNgram,rightCate+rightNgram);
    }
}
