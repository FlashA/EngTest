package ru.comrades.engtest;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.BaseAdapter;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;

/**
 * Created by Anton on 30.08.2015.
 */
public class ListAdapter extends BaseAdapter  {

    private Context mContext;
    private ArrayList<String> list;
    private LayoutInflater lInflater;

    public ListAdapter(Context context, ArrayList<String> list) {
        super();
        this.list = list;
        this.mContext = context;
        lInflater = (LayoutInflater) context
                .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }

    @Override
    public int getCount() {
        return list.size();
    }

    @Override
    public Object getItem(int position) {
        return list.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View view = convertView;
        if (view == null) {
            view = lInflater.inflate(R.layout.list_item, parent, false);
        }

        ((TextView) view.findViewById(R.id.textView)).setText(list.get(position).toString());


        //((ImageView) view.findViewById(R.id.imageView)).setImageResource(p.image);


        return view;
    }

}