package com.example.photomodule;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;

public class PhotosAdapter extends BaseAdapter {

    private ArrayList<Photo> mFlowerData = new ArrayList<>();
    private LayoutInflater mInflaterCatalogListItems;

    public PhotosAdapter(Context context, ArrayList<Photo> flowerData) {
        mFlowerData = flowerData;
        mInflaterCatalogListItems = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }

    //This function will determine how many items to be displayed
    @Override
    public int getCount() {
        return mFlowerData.size();
    }

    @Override
    public Object getItem(int position) {
        return mFlowerData.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    //This function will iterate through each object in the Data Set. This function will form each item in a Grid View
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        ViewHolder holder;

        if (convertView == null) {

            holder = new ViewHolder();
            convertView = mInflaterCatalogListItems.inflate(R.layout.adapter_photo,
                    null);
            holder.sFlowerName = (TextView) convertView.findViewById(R.id.textView);
            holder.sFlowerPhotoPath = (ImageView) convertView.findViewById(R.id.photoView);
            convertView.setTag(holder);

        } else {
            holder = (ViewHolder) convertView.getTag();
        }


        //Change the content here
        if (mFlowerData.get(position) != null) {
            holder.sFlowerName.setText(mFlowerData.get(position).getFlowerName());
            holder.sFlowerPhotoPath.setImageResource(mFlowerData.get(position).getPhotoPath());
        }

        return convertView;
    }

    //View Holder class used for reusing the same inflated view. It will decrease the inflation overhead @getView
    private static class ViewHolder {
        TextView sFlowerName;
        ImageView sFlowerPhotoPath;

    }
}
