package com.n11.tech.alphabeticalindexlistview.data;

/**
 * Created by onurtaskin on 11/03/15.
 */
public class Section extends Item {
    public final String text;

    public Section(String text) {
        this.text = text;
    }

    @Override
    public String getText() {
        return text;
    }

    @Override
    public int Type() {
        return 0;
    }
}
