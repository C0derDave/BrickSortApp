package com.codingcrusader.bricksortapp;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;

import com.codingcrusader.bricksort.set.Part;

import java.util.ArrayList;

public class PartAdapter extends BaseAdapter {
    private ArrayList<Part> partList;

    public PartAdapter(ArrayList<Part> data){
        partList = data;
    }

    @Override
    public int getCount() {
        return partList.size();
    }

    @Override
    public Part getItem(int position) {
        return partList.get(position);
    }

    @Override
    public long getItemId(int id) {
        return id;
    }

    @Override
    public View getView(int pos, View convertView, ViewGroup parent) {
        if (convertView == null) {
            convertView = LayoutInflater.from(parent.getContext()).inflate(R.layout.part_item, parent, false);
        }

        Part part = getItem(pos);

        PartListItem partListItem = new PartListItem(convertView, part);

        return convertView;
    }
}
