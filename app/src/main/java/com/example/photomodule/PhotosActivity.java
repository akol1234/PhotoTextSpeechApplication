package com.example.photomodule;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.speech.tts.TextToSpeech;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.GridView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Locale;

public class PhotosActivity extends AppCompatActivity implements AdapterView.OnItemClickListener {


        ArrayList<Photo> mFlowerDataSet = new ArrayList<>();
        TextView textBox;

        @Override
        protected void onCreate (Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_photos);

        textBox = (TextView)findViewById(R.id.textView);

        //Prepare DataSet
        mFlowerDataSet = prepareDataSet();

        //Initialize Grid View for programming
        GridView gridview = (GridView) findViewById(R.id.gridView);

        //Connect DataSet to Adapter
        PhotosAdapter flowerAdapter = new PhotosAdapter(this, mFlowerDataSet);

        //Now Connect Adapter To GridView
        gridview.setAdapter(flowerAdapter);

        //Add Listener For Grid View Item Click
        gridview.setOnItemClickListener(this);

    }
        @Override
        public void onItemClick(AdapterView<?> adapterView, View view, int position, long l){

        //Show Name Of The Flower
       // Toast.makeText(PhotosActivity.this, mFlowerDataSet.get(position).getFlowerName(),
                //Toast.LENGTH_SHORT).show();
        //Function to speech

           // String str = textBox.getText().toString();
            String text = mFlowerDataSet.get(position).getFlowerName();

          Intent intent = new Intent(PhotosActivity.this,MainActivity.class);
          intent.putExtra("description", text);
          startActivity(intent);



    }




        //Creating Data Set By Adding 6 flower objects
        private ArrayList<Photo> prepareDataSet () {

        ArrayList<Photo> flowerData = new ArrayList<>();

        Photo flower;

        //1st Item
        flower = new Photo();
        flower.setFlowerName("This was me last year, during that day I was not sure about myself due to the stress I had to go through because of my lost family ");
        flower.setPhotoPath(R.drawable.img0);
        flowerData.add(flower);

        //2nd Item
        flower = new Photo();
        flower.setFlowerName("This is my photo of the day");
        flower.setPhotoPath(R.drawable.img1);
        flowerData.add(flower);


        //3rd Item
        flower = new Photo();
        flower.setFlowerName("Hell othere, how is everything my dear friend. Hope you are fine dear.");
        flower.setPhotoPath(R.drawable.img2);
        flowerData.add(flower);


        //4th Item
        flower = new Photo();
        flower.setFlowerName("This was me last year, during that day I was not sure about myself due to the stress I had");
        flower.setPhotoPath(R.drawable.img3);
        flowerData.add(flower);


        //5th Item
        flower = new Photo();
        flower.setFlowerName("This was me last year, during that day I was not sure about myself due to the stress I had");
        flower.setPhotoPath(R.drawable.img4);
        flowerData.add(flower);


        //6th Item
        flower = new Photo();
        flower.setFlowerName("Rose flowers, This was me last year, during that day I was not sure about myself due to the stress I had");
        flower.setPhotoPath(R.drawable.img5);
        flowerData.add(flower);

        //1st Item
        flower = new Photo();
        flower.setFlowerName("This was me last year, during that day I was not sure about myself due to the stress I had");
        flower.setPhotoPath(R.drawable.img6);
        flowerData.add(flower);

        //2nd Item
        flower = new Photo();
        flower.setFlowerName("Daisy is here sitted alone. This was me last year, during that day I was not sure about myself due to the stress I had ");
        flower.setPhotoPath(R.drawable.img7);
        flowerData.add(flower);


        //3rd Item
        flower = new Photo();
        flower.setFlowerName("Jasmine, warm greetings to you baby");
        flower.setPhotoPath(R.drawable.img8);
        flowerData.add(flower);


        //4th Item
        flower = new Photo();
        flower.setFlowerName("Lily good morning there at the capital city waters");
        flower.setPhotoPath(R.drawable.img9);
        flowerData.add(flower);


        //5th Item
        flower = new Photo();
        flower.setFlowerName("This was me last year, during that day I was not sure about myself due to the stress I had");
        flower.setPhotoPath(R.drawable.img10);
        flowerData.add(flower);


        //6th Item
        flower = new Photo();
        flower.setFlowerName("Rose wedded her lover on a sunday like this congratulations more blessings");
        flower.setPhotoPath(R.drawable.img11);
        flowerData.add(flower);

        return flowerData;

    }


}