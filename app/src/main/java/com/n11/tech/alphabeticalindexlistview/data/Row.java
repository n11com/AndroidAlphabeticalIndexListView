package com.n11.tech.alphabeticalindexlistview.data;

/**
 * Created by onurtaskin on 11/03/15.
 */
public class Row extends Item {

    private String text;

    public Row(String text) {
        this.text = text;
    }

    @Override
    public String getText() {
        return text;
    }

    @Override
    public int Type() {
        return 1;
    }


}
