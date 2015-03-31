package com.n11.tech.alphabeticalindexlistview.util;

import android.content.Context;
import android.content.res.Resources;
import android.util.TypedValue;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by onurtaskin on 27/03/15.
 */
public class Utils {

    public static int convertPxFromSp(Context ctx, float sp) {
        Resources r = ctx.getResources();
        return (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_SP, sp, r.getDisplayMetrics());
    }

    public static Map<String, List<String>> testData() {
        Map<String, List<String>> data = new HashMap();
        List<String> a = new ArrayList<String>();
        a.add("Acura");
        a.add("Audi");
        a.add("Alfa Romeo");
        List<String> b = new ArrayList<String>();
        b.add("Buick");
        b.add("BMW");
        b.add("Bentley");

        List<String> c = new ArrayList<String>();
        c.add("Cadillac");
        c.add("Carver");
        c.add("Chevrolet");
        c.add("Chrysler");
        c.add("Corvette");

        List<String> d = new ArrayList<String>();
        d.add("Dodge");
        d.add("Donkervoort");
        d.add("Daewoo");
        d.add("Dacia");

        List<String> f = new ArrayList<String>();
        f.add("Ferrari");
        f.add("Fiat");
        f.add("Ford");
        f.add("Fisker");

        List<String> l = new ArrayList<String>();
        l.add("Lamborghini");
        l.add("Lancia");
        l.add("Land Rover");
        l.add("Lexus");
        l.add("Lincoln");
        l.add("Lotus");

        data.put("A", a);
        data.put("B", b);
        data.put("C", c);
        data.put("D", d);
        data.put("F", f);
        data.put("L", l);

        return data;
    }

}
