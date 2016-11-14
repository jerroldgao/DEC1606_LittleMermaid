package com.yirugao.dec1606_littlemermaid;

import java.util.HashMap;

/**
 * Created by Yirugao on 11/7/16.
 */

public class NGramEvaluation {
    //This method use tri-gram for deciding the words suggestion
    public double Evaluation(String word2Prev,String wordPrev, HashMap<String,HashMap<String,Integer>> dict){
        if (wordPrev.isEmpty()||wordPrev==null){
            return 0;
        }else if (word2Prev.isEmpty()||word2Prev==null){
            return 0;
        }
        return 0;
    }
}
