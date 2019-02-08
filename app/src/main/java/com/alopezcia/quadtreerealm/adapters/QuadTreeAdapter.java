package com.alopezcia.quadtreerealm.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.alopezcia.quadtreerealm.R;
import com.alopezcia.quadtreerealm.models.QuadTree;
import com.alopezcia.quadtreerealm.models.QuadTreeExtent;

import java.util.List;
import java.util.Locale;

public class QuadTreeAdapter extends BaseAdapter {
    private Context context;
    private List<QuadTree> list;
    private int layout;

    public QuadTreeAdapter(Context context, List<QuadTree> quads, int layout)
    {
        this.context = context;
        this.list = quads;
        this.layout = layout;
    }

    @Override
    public int getCount() {
        return list.size();
    }

    @Override
    public QuadTree getItem(int position) {
        return list.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder vh;
        if( convertView == null)
        {
            convertView = LayoutInflater.from(context).inflate(layout, null);
            vh = new ViewHolder();
            vh.layerName = (TextView)convertView.findViewById(R.id.textViewLayerName);
            vh.featNumber = (TextView)convertView.findViewById(R.id.textViewNumberFeat);
            vh.rootExtent= (TextView)convertView.findViewById(R.id.textViewRootExtension);
            convertView.setTag(vh);
        }
        else
        {
            vh = (ViewHolder) convertView.getTag();
        }
        QuadTree qt = list.get(position);
        vh.layerName.setText(qt.getLayerName());
        int numFeat = qt.getFeatCount();
        String textForFeatNumber = (numFeat==1)? numFeat + " feature" : numFeat + " features";
        vh.featNumber.setText(textForFeatNumber);

        QuadTreeExtent qtExtent = qt.getRootNode().getExtent();
        String textForRootExtent = String.format(Locale.ENGLISH,"Extent %f - %f  %f - %f",
                qtExtent.getMinLng(), qtExtent.getMinLat(),
                qtExtent.getMaxLng(), qtExtent.getMaxLat());
        vh.rootExtent.setText(textForRootExtent);

        return convertView;
    }


    public class ViewHolder {
        TextView layerName;
        TextView featNumber;
        TextView rootExtent;

    }
}
