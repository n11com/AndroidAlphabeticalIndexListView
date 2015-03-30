package com.n11.tech.alphabeticalindexlistview;

import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.GestureDetector;
import android.view.Gravity;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.n11.tech.alphabeticalindexlistview.adapter.IndexAdapter;
import com.n11.tech.alphabeticalindexlistview.data.Item;
import com.n11.tech.alphabeticalindexlistview.data.Row;
import com.n11.tech.alphabeticalindexlistview.data.Section;
import com.n11.tech.alphabeticalindexlistview.util.Utils;

import java.text.Collator;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.Set;


public class MainActivity extends ActionBarActivity implements AdapterView.OnItemClickListener {

    private ListView listView;
    private LinearLayout indexLL;
    private EditText searchEditText;
    private GestureDetector mGestureDetector;
    private List<Object[]> letterIndexList = new ArrayList<>();
    private HashMap<String, Integer> sections = new HashMap<>();
    private int sideIndexHeight;
    private static float sideIndexX;
    private static float sideIndexY;
    private int indexListSize;
    private List<Item> rowList = new ArrayList<>();
    private IndexAdapter adapter;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        prepareViews();

        Map<String, List<String>> listMap = Utils.testData();
        parseData(listMap);

        adapter = new IndexAdapter(this, rowList);
        listView.setAdapter(adapter);
        mGestureDetector = new GestureDetector(this, new SideIndexGestureListener());
        searchEditText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if (adapter != null) {
                    adapter.getFilter().filter(s);
                }
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });

        updateList();
    }

    private void prepareViews() {
        listView = (ListView) findViewById(R.id.listView);
        listView.setOnItemClickListener(this);
        indexLL = (LinearLayout) findViewById(R.id.sideIndex);
        searchEditText = (EditText) findViewById(R.id.searchEditTextView);
    }

    private void parseData(Map<String, List<String>> data) {

        Set<String> keySet = data.keySet();
        List<String> alphabets = Arrays.asList(keySet.toArray(new String[keySet.size()]));
        Collections.sort(alphabets, new Comparator<String>() {
            @Override
            public int compare(String lhs, String rhs) {
                Collator collator = Collator.getInstance(new Locale("tr", "TR"));
                return collator.compare(lhs, rhs);
            }
        });

        int start = 0;
        for (String alpha : alphabets) {
            List<String> brand = data.get(alpha);
            int end = start + brand.size();
            Object[] tmpIndexItem = new Object[3];
            tmpIndexItem[0] = alpha;
            tmpIndexItem[1] = start;
            tmpIndexItem[2] = end;

            letterIndexList.add(tmpIndexItem);
            sections.put(alpha, start);

            rowList.add(new Section(alpha));
            for (String b : brand) {
                rowList.add(new Row(b));
            }
            start = end + 1;
        }
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        Item item = adapter.getItem(position);

        if (item instanceof Row) {
            Toast.makeText(this, item.getText(), Toast.LENGTH_LONG).show();
        }
    }

    private static final int INDEX_FONT_SIZE = 14;

    public void updateList() {
        indexLL.removeAllViews();
        indexListSize = letterIndexList.size();
        if (indexListSize < 1) {
            return;
        }
        int rowHeight = Utils.convertPxFromSp(this, INDEX_FONT_SIZE);
        int indexMaxSize = (int) Math.floor(indexLL.getHeight() / rowHeight);
        int tmpIndexListSize = indexListSize;
        while (tmpIndexListSize > indexMaxSize) {
            tmpIndexListSize = tmpIndexListSize / 2;
        }
        double delta;
        if (tmpIndexListSize > 0) {
            delta = indexListSize / tmpIndexListSize;
        } else {
            delta = 1;
        }

        for (double i = 1; i <= indexListSize; i = i + delta) {
            Object[] tmpIndexItem = letterIndexList.get((int) i - 1);
            String tmpLetter = tmpIndexItem[0].toString();
            TextView tmpTV = new TextView(this);
            tmpTV.setText(tmpLetter);
            tmpTV.setGravity(Gravity.CENTER);
            tmpTV.setTextSize(INDEX_FONT_SIZE);
            tmpTV.setTextColor(getResources().getColor(R.color.blue_title));
            tmpTV.setIncludeFontPadding(false);
            LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT, 1);
            tmpTV.setLayoutParams(params);
            indexLL.addView(tmpTV);
        }
        sideIndexHeight = indexLL.getHeight();
        indexLL.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                if (mGestureDetector.onTouchEvent(event)) {
                    return true;
                }
                sideIndexX = event.getX();
                sideIndexY = event.getY();
                displayListItem();
                return false;
            }
        });
    }

    public void displayListItem() {
        sideIndexHeight = indexLL.getHeight();
        double pixelPerIndexItem = (double) sideIndexHeight / indexListSize;
        int itemPosition = (int) (sideIndexY / pixelPerIndexItem);
        if (itemPosition >= 0 && itemPosition < letterIndexList.size()) {
            Object[] indexItem = letterIndexList.get(itemPosition);
            int subItemPosition = sections.get(indexItem[0]);
            listView.setSelection(subItemPosition);
        }
    }

    class SideIndexGestureListener extends GestureDetector.SimpleOnGestureListener {
        @Override
        public boolean onScroll(MotionEvent e1, MotionEvent e2, float distanceX, float distanceY) {
            sideIndexX = sideIndexX - distanceX;
            sideIndexY = sideIndexY - distanceY;
            if (sideIndexX >= 0 && sideIndexY >= 0) {
                displayListItem();
            }
            return super.onScroll(e1, e2, distanceX, distanceY);
        }
    }
}
