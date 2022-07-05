package foodsqlitedemo.quocnguyen.com.foodsqlitedemo;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.speech.tts.TextToSpeech;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.Locale;

public class SpeechActivity extends AppCompatActivity implements TextToSpeech.OnInitListener {
    Button chooseBtn, editBtn, enterBtn;
    TextView speakText;
    TextToSpeech textToSpeech;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_speech);
        speakText = (TextView) findViewById(R.id.txtSpeak);
        chooseBtn = (Button)findViewById(R.id.btnChoose);
        editBtn = (Button)findViewById(R.id.btnEdit);
        enterBtn = (Button)findViewById(R.id.btnInsert);
        textToSpeech = new TextToSpeech(this, this);
        chooseBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(SpeechActivity.this, PhotoList.class);
                startActivity(intent);
            }
        });


        enterBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(SpeechActivity.this,MainActivity.class);
                startActivity(intent);
            }
        });




    }

    @Override
    public void onInit(int status) {
        if (status == TextToSpeech.SUCCESS) {
            int result = textToSpeech.setLanguage(Locale.US);
            if (result == TextToSpeech.LANG_MISSING_DATA || result == TextToSpeech.LANG_NOT_SUPPORTED) {
                Log.e("error", "This Language is not supported");
            } else {
                texttoSpeak();
            }
        } else {
            Log.e("error", "Failed to Initialize");
        }
    }
    @Override
    public void onDestroy() {
        if (textToSpeech != null) {
            textToSpeech.stop();
            textToSpeech.shutdown();
        }
        super.onDestroy();

    }
    private void texttoSpeak() {
        // String text = speakText.getText().toString();
        Intent intent = getIntent();
        String text = intent.getStringExtra("description");
        speakText.setText(text);

        if ("".equals(text)) {
            text = "Please enter some text to speak.";
        }
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            textToSpeech.speak(text, TextToSpeech.QUEUE_FLUSH, null, null);
        }
        else
            {
            textToSpeech.speak(text, TextToSpeech.QUEUE_FLUSH, null);
        }
    }

}