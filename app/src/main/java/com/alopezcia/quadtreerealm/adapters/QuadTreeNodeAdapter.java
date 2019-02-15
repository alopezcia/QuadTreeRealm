package com.alopezcia.quadtreerealm.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.alopezcia.quadtreerealm.R;
import com.alopezcia.quadtreerealm.models.QuadTreeExtent;
import com.alopezcia.quadtreerealm.models.QuadTreeNode;

import java.util.List;
import java.util.Locale;

public class QuadTreeNodeAdapter extends BaseAdapter {
    private Context context;
    private List<QuadTreeNode> list;
    private int layout;

    public QuadTreeNodeAdapter(Context context, List<QuadTreeNode> nodes, int layout)
    {
        this.context=context;
        this.list = nodes;
        this.layout = layout;
    }

    @Override
    public int getCount() {
        return list.size();
    }

    @Override
    public QuadTreeNode getItem(int position) {
        return list.get(position);
    }

    @Override
    public long getItemId(int position) {
        return list.get(position).getId();
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        QuadTreeNodeAdapter.ViewHolder vh;
        if( convertView == null)
        {
            convertView = LayoutInflater.from(context).inflate(layout, null);
            vh = new QuadTreeNodeAdapter.ViewHolder();
            vh.area = (TextView)convertView.findViewById(R.id.textViewNodeArea);
            vh.featNumber = (TextView)convertView.findViewById(R.id.textViewNodeNumFeat);
            vh.extent= (TextView)convertView.findViewById(R.id.textViewNodeExtent);
            convertView.setTag(vh);
        }
        else
        {
            vh = (QuadTreeNodeAdapter.ViewHolder) convertView.getTag();
        }

        QuadTreeNode qtn = list.get(position);
        QuadTreeExtent qtExtent = qtn.getExtent();

        long areaL = qtExtent.getAreaKm2();
        String area =  Long.toString(areaL) + " Km2";
        vh.area.setText(area);

        int numFeat = qtn.getEntitites().size();
        // TODO Esto devuelve siempre 0, hay que hacer una consulta Realm.where ????
        String textForFeatNumber = (numFeat==1)? numFeat + " feature" : numFeat + " features";
        vh.featNumber.setText(textForFeatNumber);

        String textForRootExtent = String.format(Locale.ENGLISH,"Extent %f - %f  %f - %f",
                qtExtent.getMinLng(), qtExtent.getMinLat(),
                qtExtent.getMaxLng(), qtExtent.getMaxLat());
        vh.extent.setText(textForRootExtent);

        return convertView;
    }

    public class ViewHolder {
        TextView area;
        TextView featNumber;
        TextView extent;
    }

}
