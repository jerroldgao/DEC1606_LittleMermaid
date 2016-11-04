package com.yirugao.dec1606_littlemermaid;

/**
 * Created by Yirugao on 11/3/16.
 */

public class Word {
    private int index;
    private double categoryValue;
    private double ngramValue;
    public Word(int index,double categoryValue,double ngramValue){
        this.index = index;
        this.categoryValue = categoryValue;
        this.ngramValue = ngramValue;
    }

    public double getCategoryValue() {
        return categoryValue;
    }

    public double getNgramValue() {
        return ngramValue;
    }

    public int getIndex() {
        return index;
    }
}
