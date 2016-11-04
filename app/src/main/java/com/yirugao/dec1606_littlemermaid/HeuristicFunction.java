package com.yirugao.dec1606_littlemermaid;

import java.util.Comparator;

/**
 * Created by Yirugao on 11/3/16.
 */

public class HeuristicFunction implements Comparator<Word>{

    @Override
    public int compare(Word lhs, Word rhs) {
        double leftCate = 1/((double)lhs.getIndex()+1)*lhs.getCategoryValue();
        double rightCate = 1/((double)rhs.getIndex()+1)*rhs.getCategoryValue();
        double leftNgram = ((double)lhs.getIndex())/((double)lhs.getIndex()+1)*lhs.getNgramValue();
        double rightNgram = ((double)rhs.getIndex())/((double)rhs.getIndex()+1)*rhs.getNgramValue();
        return (int)(leftCate+leftNgram-rightCate-rightNgram);
    }
}
