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
        a.add("Armani");
        a.add("Adidas");
        a.add("Abibas");
        List<String> b = new ArrayList<String>();
        b.add("Bandai");
        b.add("Bonibon");
        b.add("Bacardi");

        List<String> c = new ArrayList<String>();
        c.add("C&A");
        c.add("Colins");
        c.add("Ceyo");
        c.add("Cenk");

        List<String> d = new ArrayList<String>();
        d.add("Dockers");
        d.add("Dogan");
        d.add("Dodge");
        d.add("Dolge");

        List<String> e = new ArrayList<String>();
        e.add("Etsy");
        e.add("Elle");
        e.add("Emporio Armani");
        e.add("Else Silver");

        List<String> k = new ArrayList<String>();
        k.add("Koton");
        k.add("Keds");
        k.add("Kenzo");
        k.add("Kawasaki");

        data.put("A", a);
        data.put("B", b);
        data.put("C", c);
        data.put("D", d);
        data.put("E", e);
        data.put("K", k);

        return data;
    }

}
