package com.example.crackthepin;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.ClipData;
import android.content.ClipboardManager;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import org.w3c.dom.Text;

public class MainActivity extends AppCompatActivity {

    private SQLiteDatabase mDatabase;
    private PinAdapter mAdapter;
    private Button generateButton, crackButton, saveButton, copyButton;
    private EditText pinInput;
    private TextView pinView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        PinDBHelper pinDBHelper = new PinDBHelper(this);
        mDatabase = pinDBHelper.getWritableDatabase();

        RecyclerView recyclerView = findViewById(R.id.recyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        mAdapter = new PinAdapter(this, getAllItems());
        recyclerView.setAdapter(mAdapter);

        new ItemTouchHelper(new ItemTouchHelper.SimpleCallback(0, ItemTouchHelper.LEFT | ItemTouchHelper.RIGHT) {
            @Override
            public boolean onMove(@NonNull RecyclerView recyclerView, @NonNull RecyclerView.ViewHolder viewHolder, @NonNull RecyclerView.ViewHolder target) {
                return false;
            }

            @Override
            public void onSwiped(@NonNull RecyclerView.ViewHolder viewHolder, int direction) {
                removeItem((long) viewHolder.itemView.getTag());
            }
        }).attachToRecyclerView(recyclerView);

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
        if (pinInput.length() != 0) {
            final String PIN = pinInput.getText().toString();
            pinView.setText(Md5Hash.getMd5(PIN));
        } else {
            Toast.makeText(MainActivity.this, "You must put something in the text field", Toast.LENGTH_SHORT).show();
        }
    }

    public void onCrackButtonClick(View view) {
        if (pinInput.length() != 0) {
            crackThread();
        } else {
            Toast.makeText(MainActivity.this, "You must put something in the text field", Toast.LENGTH_SHORT).show();
        }
    }

    public void onSaveButtonClick(View view) {
        addItem();
    }


    public void onCopyButtonClick(View view) {
        ClipboardManager clipboardManager = (ClipboardManager) getSystemService(Context.CLIPBOARD_SERVICE);
        ClipData clip = ClipData.newPlainText("PIN", pinView.getText().toString());
        clipboardManager.setPrimaryClip(clip);

        Toast.makeText(MainActivity.this, "Copied", Toast.LENGTH_SHORT).show();
    }

    private void addItem() {
        if (pinInput.getText().toString().trim().length() == 0) {
            return;
        }

        String PIN = pinInput.getText().toString();
        String hashedPin = pinView.getText().toString();
        ContentValues contentValues = new ContentValues();

        contentValues.put(PinContract.PinEntry.COLUMN_INPUT, PIN);
        contentValues.put(PinContract.PinEntry.COLUMN_OUTPUT, hashedPin);

        mDatabase.insert(PinContract.PinEntry.TABLE_NAME, null, contentValues);
        mAdapter.swapCursor(getAllItems());

        pinInput.getText().clear();
    }

    private void removeItem(long id) {
        mDatabase.delete(PinContract.PinEntry.TABLE_NAME,
                PinContract.PinEntry._ID + "=" + id, null);
        mAdapter.swapCursor(getAllItems());
    }

    private Cursor getAllItems() {
        return mDatabase.query(
                PinContract.PinEntry.TABLE_NAME,
                null,
                null,
                null,
                null,
                null,
                PinContract.PinEntry.COLUMN_TIMESTAMP + " DESC"
        );
    }

}