package ru.comrades.engtest;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;

/**
 * Created by Anton on 30.08.2015.
 */
public class ListAdapter extends BaseAdapter  {

    private Context mContext;
    private ArrayList<ListItem> list;
    private LayoutInflater lInflater;

    private DBDataHelper DBHelper;

    public ListAdapter(Context context, ArrayList<ListItem> list) {
        super();
        this.list = list;
        this.mContext = context;
        lInflater = (LayoutInflater) context
                .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        DBHelper = new DBDataHelper(mContext);
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


        ((TextView) view.findViewById(R.id.textView)).setText("№ вопроса: " + list.get(position).getQuestion() + " Ответ: " + list.get(position).getAnswer());


        if (!list.get(position).isRightAnswer()) {
            ((ImageView) view.findViewById(R.id.imageView)).setImageResource(R.drawable.error);
        } else {
            ((ImageView) view.findViewById(R.id.imageView)).setImageResource(R.drawable.complete);
        }



        return view;
    }

}