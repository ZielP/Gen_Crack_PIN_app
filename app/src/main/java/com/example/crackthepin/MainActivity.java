package com.example.crackthepin;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.view.View;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {

    private Button generateButton, crackButton, saveButton, copyButton;
    private EditText pinInput;
    private TextView pinView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        pinView = (TextView) findViewById(R.id.pinTextView);

        generateButton = (Button) findViewById(R.id.generate_button);
        crackButton = (Button) findViewById(R.id.crack_button);
        saveButton = (Button) findViewById(R.id.save_button);
        copyButton = (Button) findViewById(R.id.copy_button);

        pinInput = (EditText) findViewById(R.id.editTextGivenPin);
    }

   /* private void runThread() {

        new Thread() {
            public void run() {
                final String PIN = pinInput.getText().toString();
                    try {
                        runOnUiThread(new Runnable() {

                            @Override
                            public void run() {
                                pinView.setText(Md5Hash.crack(PIN));
                            }
                        });
                        Thread.sleep(300);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }

        }.start();
    }*/

    public void onGenerateButtonClick(View view) {
        final String PIN = pinInput.getText().toString();
        pinView.setText(Md5Hash.getMd5(PIN));
    }

    public void onCrackButtonClick(View view) {
        final String PIN = pinInput.getText().toString();
        //pinView.setText(Md5Hash.crack(PIN));

        /*runOnUiThread(new Runnable() {
            @Override
            public void run() {
                pinView.setText(Md5Hash.crack(PIN));
            }

        });*/

        //pinView.setText("Working on that");

        runThread();
    }

    public void onSaveButtonClick(View view) {

    }

    public void onCopyButtonClick(View view) {

    }
}