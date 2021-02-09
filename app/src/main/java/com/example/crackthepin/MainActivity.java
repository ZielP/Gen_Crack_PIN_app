package com.example.crackthepin;

import androidx.appcompat.app.AppCompatActivity;

import android.content.ClipData;
import android.content.ClipboardManager;
import android.content.Context;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

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

    private void crackThread() {
        final String PIN = pinInput.getText().toString();
        //new Thread(() -> pinView.setText(Md5Hash.crack(PIN))).start();
        new Thread(new Runnable() {
            @Override
            public void run() {
                pinView.setText(Md5Hash.crack(PIN));
            }
        }).start();
    }

    public void onGenerateButtonClick(View view) {
        final String PIN = pinInput.getText().toString();
        pinView.setText(Md5Hash.getMd5(PIN));
    }

    public void onCrackButtonClick(View view) {
        crackThread();
    }

    public void onSaveButtonClick(View view) {

    }

    public void onCopyButtonClick(View view) {
        ClipboardManager clipboardManager = (ClipboardManager) getSystemService(Context.CLIPBOARD_SERVICE);
        ClipData clip = ClipData.newPlainText("PIN", pinView.getText().toString());
        clipboardManager.setPrimaryClip(clip);

        Toast.makeText(MainActivity.this, "Copied", Toast.LENGTH_SHORT).show();
    }
}