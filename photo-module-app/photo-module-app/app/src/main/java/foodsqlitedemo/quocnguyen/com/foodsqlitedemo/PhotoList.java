package foodsqlitedemo.quocnguyen.com.foodsqlitedemo;

import android.Manifest;
import android.app.Activity;
import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;

import android.speech.tts.TextToSpeech;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;

import java.io.FileNotFoundException;
import java.io.InputStream;
import java.util.ArrayList;


public class PhotoList extends AppCompatActivity {

    GridView gridView;
    ArrayList<Photo> list;
    PhotoListAdapter adapter = null;

    //for speech
    TextView speakText;
    TextToSpeech textToSpeech;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.photo_list_activity);

        gridView = (GridView) findViewById(R.id.gridView);
        list = new ArrayList<>();
        adapter = new PhotoListAdapter(this, R.layout.photo_items, list);
        gridView.setAdapter(adapter);

        //for speech
        speakText = (TextView)findViewById(R.id.txtDesc);


        // get all data from sqlite
        Cursor cursor = MainActivity.sqLiteHelper.getData("SELECT * FROM PHOTO");
        list.clear();
        while (cursor.moveToNext()) {
            int id = cursor.getInt(0);
            String desc = cursor.getString(1);

            byte[] image = cursor.getBlob(2);

            list.add(new Photo(desc, image, id));


        }
        adapter.notifyDataSetChanged();

        gridView.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> parent, View view, final int position, long id) {

                CharSequence[] items = {"Update", "Delete"};
                AlertDialog.Builder dialog = new AlertDialog.Builder(PhotoList.this);

                dialog.setTitle("Choose an action");
                dialog.setItems(items, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int item) {
                        if (item == 0) {
                            // update
                            Cursor c = MainActivity.sqLiteHelper.getData("SELECT id FROM PHOTO");
                            ArrayList<Integer> arrID = new ArrayList<Integer>();
                            while (c.moveToNext()){
                                arrID.add(c.getInt(0));
                            }
                            // show dialog update at here
                            showDialogUpdate(PhotoList.this, arrID.get(position));

                        } else {
                            // delete
                            Cursor c = MainActivity.sqLiteHelper.getData("SELECT id FROM PHOTO");
                            ArrayList<Integer> arrID = new ArrayList<Integer>();
                            while (c.moveToNext()){
                                arrID.add(c.getInt(0));
                            }
                            showDialogDelete(arrID.get(position));
                        }
                    }
                });
                dialog.show();
                return true;
            }

        });

      //  String[] desc;

        gridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int position, long l) {

                String text = list.get(position).getDesc();


                Intent intent = new Intent(PhotoList.this,SpeechActivity.class);
                intent.putExtra("description", text);
                //intent.putExtra("img", (Parcelable) imageViewFood);
                startActivity(intent);

            }
        });


    }

    ImageView imageViewPhoto;
    private void showDialogUpdate(Activity activity, final int position){

        final Dialog dialog = new Dialog(activity);
        dialog.setContentView(R.layout.update_photo_activity);
        dialog.setTitle("Update");

        imageViewPhoto = (ImageView) dialog.findViewById(R.id.imageViewPhoto);
        final EditText edtDesc = (EditText) dialog.findViewById(R.id.edtDesc);
        Button btnUpdate = (Button) dialog.findViewById(R.id.btnUpdate);

        // set width for dialog
        int width = (int) (activity.getResources().getDisplayMetrics().widthPixels * 0.95);
        // set height for dialog
        int height = (int) (activity.getResources().getDisplayMetrics().heightPixels * 0.7);
        dialog.getWindow().setLayout(width, height);
        dialog.show();

        imageViewPhoto.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // request photo library
                ActivityCompat.requestPermissions(
                        PhotoList.this,
                        new String[]{Manifest.permission.READ_EXTERNAL_STORAGE},
                        888
                );
            }
        });



        btnUpdate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try {
                    MainActivity.sqLiteHelper.updateData(
                            edtDesc.getText().toString().trim(),

                            MainActivity.imageViewToByte(imageViewPhoto),
                            position
                    );
                    dialog.dismiss();
                    Toast.makeText(getApplicationContext(), "Update successfully!!!",Toast.LENGTH_SHORT).show();
                }
                catch (Exception error) {
                    Log.e("Update error", error.getMessage());
                }
                updateFoodList();
            }
        });
    }

    private void showDialogDelete(final int idFood){
        final AlertDialog.Builder dialogDelete = new AlertDialog.Builder(PhotoList.this);

        dialogDelete.setTitle("Warning!!");
        dialogDelete.setMessage("Are you sure you want to delete this?");
        dialogDelete.setPositiveButton("OK", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                try {
                    MainActivity.sqLiteHelper.deleteData(idFood);
                    Toast.makeText(getApplicationContext(), "Deleted successfully!!!",Toast.LENGTH_SHORT).show();
                } catch (Exception e){
                    Log.e("error", e.getMessage());
                }
                updateFoodList();
            }
        });

        dialogDelete.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
            }
        });
        dialogDelete.show();
    }

    private void updateFoodList(){
        // get all data from sqlite
        Cursor cursor = MainActivity.sqLiteHelper.getData("SELECT * FROM PHOTO");
        list.clear();
        while (cursor.moveToNext()) {
            int id = cursor.getInt(0);
            String desc = cursor.getString(1);
            byte[] image = cursor.getBlob(2);

            list.add(new Photo(desc, image, id));
        }
        adapter.notifyDataSetChanged();
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {

        if(requestCode == 888){
            if(grantResults.length >0 && grantResults[0] == PackageManager.PERMISSION_GRANTED){
                Intent intent = new Intent(Intent.ACTION_PICK);
                intent.setType("image/*");
                startActivityForResult(intent, 888);
            }
            else {
                Toast.makeText(getApplicationContext(), "You don't have permission to access file location!", Toast.LENGTH_SHORT).show();
            }
            return;
        }
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {

        if(requestCode == 888 && resultCode == RESULT_OK && data != null){
            Uri uri = data.getData();
            try {
                InputStream inputStream = getContentResolver().openInputStream(uri);
                Bitmap bitmap = BitmapFactory.decodeStream(inputStream);
                imageViewPhoto.setImageBitmap(bitmap);

            } catch (FileNotFoundException e) {
                e.printStackTrace();
            }
        }

        super.onActivityResult(requestCode, resultCode, data);
    }

}