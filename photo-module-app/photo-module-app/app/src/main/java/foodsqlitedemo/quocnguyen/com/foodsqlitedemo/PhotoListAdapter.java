package foodsqlitedemo.quocnguyen.com.foodsqlitedemo;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;


public class PhotoListAdapter extends BaseAdapter {

    private Context context;
    private  int layout;
    private ArrayList<Photo> photosList;

    public PhotoListAdapter(Context context, int layout, ArrayList<Photo> photosList) {
        this.context = context;
        this.layout = layout;
        this.photosList = photosList;
    }

    @Override
    public int getCount() {
        return photosList.size();
    }

    @Override
    public Object getItem(int position) {
        return photosList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    private class ViewHolder{
        ImageView imageView;
        TextView txtDesc;
    }

    @Override
    public View getView(int position, View view, ViewGroup viewGroup) {

        View row = view;
        ViewHolder holder = new ViewHolder();

        if(row == null){
            LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            row = inflater.inflate(layout, null);

            holder.txtDesc = (TextView) row.findViewById(R.id.txtSpeak);
            holder.imageView = (ImageView) row.findViewById(R.id.imgPhoto);
            row.setTag(holder);
        }
        else {
            holder = (ViewHolder) row.getTag();
        }

        Photo photo = photosList.get(position);

        holder.txtDesc.setText(photo.getDesc());


        byte[] photoImage = photo.getImage();
        Bitmap bitmap = BitmapFactory.decodeByteArray(photoImage, 0, photoImage.length);
        holder.imageView.setImageBitmap(bitmap);

        return row;
    }
}
