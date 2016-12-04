package com.yirugao.dec1606_littlemermaid;

import java.util.Comparator;

/**
 * Created by Yirugao on 11/3/16.
 */

public class HeuristicFunction implements Comparator<Word>{

    @Override
    public int compare(Word lhs, Word rhs) {
        double leftFunc = Math.exp((-0.25)*((double)(lhs.getIndex()+1)));
        double rightFunc = Math.exp((-0.25)*((double)(lhs.getIndex()+1)));
        double leftCate = leftFunc*lhs.getCategoryValue();
        double rightCate = rightFunc*rhs.getCategoryValue();
        double leftNgram = (1-leftFunc)*lhs.getNgramValue();
        double rightNgram = (1-rightFunc)*rhs.getNgramValue();
        double result = leftCate+leftNgram-rightCate-rightNgram;
        if (result >= 0){
            return 1;
        }else {
            return -1;
        }
    }
}
