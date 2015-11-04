package com.example.guest.knowyourrep;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.ArrayList;

public class RepAdapter extends BaseAdapter {
    private Context mContext;
    private ArrayList<Representative> mRepList;

    public RepAdapter(Context context, ArrayList<Representative> repList) {
        mContext = context;
        mRepList = repList;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder holder;

        if (convertView == null) {
            convertView = LayoutInflater.from(mContext).inflate(R.layout.rep_list_item, null);
            holder = new ViewHolder();
            holder.nameLabel = (TextView) convertView.findViewById(R.id.nameLabel);
            holder.partyLabel = (TextView) convertView.findViewById(R.id.partyLabel);

            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }
        Representative representative = mRepList.get(position);
        holder.nameLabel.setText("Name: " + representative.getFirstName() + " " + representative.getLastName());
        holder.partyLabel.setText("Political Party: " + representative.getParty());
        return convertView;
    }

    @Override
    public int getCount() {
        return mRepList.size();
    }

    @Override
    public Object getItem(int position) {
        return mRepList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    private static class ViewHolder {
        TextView nameLabel;
        TextView partyLabel;
    }

}

