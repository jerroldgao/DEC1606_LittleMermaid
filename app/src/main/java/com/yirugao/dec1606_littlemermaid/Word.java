package com.yirugao.dec1606_littlemermaid;

/**
 * Created by Yirugao on 11/3/16.
 */

public class Word {
    private int index;
    private double categoryValue;
    private double ngramValue;
    private String lemma;
    public Word(int index,double categoryValue,double ngramValue,String lemma){
        this.index = index;
        this.categoryValue = categoryValue;
        this.ngramValue = ngramValue;
        this.lemma = lemma;
    }

    public double getCategoryValue() {
        return categoryValue;
    }



    public String getLemma() {
        return lemma;
    }


    public int getIndex() {
        return index;
    }

    public void setIndex(int index) {
        this.index = index;
    }

    public double getNgramValue() {
        return ngramValue;
    }

    public void setNgramValue(double ngramValue) {
        this.ngramValue = ngramValue;
    }




}
