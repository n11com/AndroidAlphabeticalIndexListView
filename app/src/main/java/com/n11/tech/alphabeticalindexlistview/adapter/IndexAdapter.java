package com.n11.tech.alphabeticalindexlistview.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Filter;
import android.widget.TextView;

import com.n11.tech.alphabeticalindexlistview.R;
import com.n11.tech.alphabeticalindexlistview.data.Item;
import com.n11.tech.alphabeticalindexlistview.data.Row;
import com.n11.tech.alphabeticalindexlistview.data.Section;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by onurtaskin on 11/03/15.
 */
public class IndexAdapter extends ArrayAdapter<Item> {

    private List<Item> rows;
    private List<Item> searchRows;

    public IndexAdapter(Context context, List<Item> rows) {
        super(context, R.layout.row_view, rows);
        searchRows = new ArrayList<>();
        setRows(rows);
    }

    private void setRows(List<Item> rows) {
        this.searchRows = rows;
        this.rows = new ArrayList<>(rows);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public int getViewTypeCount() {
        return 2;
    }

    @Override
    public int getItemViewType(int position) {
        return getItem(position).Type();
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        View view = convertView;
        if (getItemViewType(position) == 1) { // Row
            if (view == null) {
                LayoutInflater inflater = (LayoutInflater) parent.getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
                view = inflater.inflate(R.layout.row_view, parent, false);
            }
            Row row = (Row) getItem(position);
            TextView textView = (TextView) view.findViewById(R.id.textView);
            View divider = view.findViewById(R.id.divider);
            textView.setText(row.getText());
            if (!isDividerVisible(position)) {
                divider.setVisibility(View.GONE);
            } else {
                divider.setVisibility(View.VISIBLE);
            }
        } else { // Section
            if (view == null) {
                LayoutInflater inflater = (LayoutInflater) parent.getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
                view = inflater.inflate(R.layout.section_view, parent, false);
            }
            Section section = (Section) getItem(position);
            TextView textView = (TextView) view.findViewById(R.id.indexTextView);
            textView.setText(section.getText());
        }
        return view;
    }

    private boolean isDividerVisible(int position) {
        if (position + 1 >= getCount()) {
            return true;
        } else {
            Item item = getItem(position + 1);
            if (item instanceof Section) {
                return false;
            } else {
                return true;
            }
        }
    }

    @Override
    public Filter getFilter() {
        return new Filter() {
            @Override
            protected FilterResults performFiltering(CharSequence constraint) {
                String filterString = constraint.toString().toLowerCase();
                FilterResults results = new FilterResults();

                if (filterString == null || filterString.length() == 0) {
                    results.values = rows;
                    results.count = rows.size();
                } else {
                    final List<Item> list = rows;
                    int count = list.size();
                    List<Item> nlist = new ArrayList<>(count);

                    String filterableString;
                    for (int i = 0; i < count; i++) {
                        filterableString = list.get(i).getText();
                        if (filterableString.toLowerCase().startsWith(filterString) && list.get(i) instanceof Row) {
                            nlist.add(list.get(i));
                        }
                    }
                    results.values = nlist;
                    results.count = nlist.size();
                }
                return results;
            }

            @Override
            protected void publishResults(CharSequence constraint, FilterResults results) {
                searchRows.clear();
                searchRows.addAll((ArrayList<Item>) results.values);
                notifyDataSetChanged();
            }
        };
    }

}
