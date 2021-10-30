package com.example.myapplication2;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.os.Environment;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.example.myapplication2.Utils.DatabaseHandler;
import com.example.myapplication2.adapter.ContactAdapter;
import com.example.myapplication2.model.Contact;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileOutputStream;
import java.io.OutputStreamWriter;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {
    List<Contact> contacts;

    EditText editText;

    Button btnDelete, btnExport;

    DatabaseHandler db;

    ContactAdapter adapter;

    RecyclerView rvContacts;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        init();
        db.openDatabase();
        addData();

        resetAdapter(contacts);

    }

    private void init() {
        db = new DatabaseHandler(this.getBaseContext());
        editText = findViewById(R.id.edtContact);
        btnDelete = findViewById(R.id.btn_delete);
        rvContacts = findViewById(R.id.rvContact);
        btnExport = findViewById(R.id.btn_export);

    }

    private void addData() {

        contacts = db.getAllContacts();


    }

    public void clickBtnDelete(View view) {

        String name = editText.getText().toString().trim();

        if (name != null && name.length() != 0) {
            db.insertContact(new Contact(0, name));

            contacts.clear();
            contacts = db.getAllContacts();
            resetAdapter(contacts);

            editText.setText("");
        }
    }


    public void resetAdapter(List<Contact> contacts) {

        adapter = new ContactAdapter(contacts);
        rvContacts.setAdapter(adapter);
        rvContacts.setLayoutManager(new LinearLayoutManager(this));

    }

    public void clickBtnExport(View view) {
        StringBuilder temp = new StringBuilder();
        for (Contact c : contacts) {
            temp.append("\"" + c.getId() + "\"" + ",\"" + c.getContactName() + "\"");
            temp.append("\n");
        }

        writeFile(temp.toString());

    }

    private void writeFile(String text) {
        File path = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS);


        File file = new File(path, "file-list-contact.csv");

        Log.e("PHUC", "writeFile: " + file.getAbsolutePath());


        try (FileOutputStream stream = new FileOutputStream(file)) {
            OutputStreamWriter osw = new OutputStreamWriter(stream, StandardCharsets.UTF_8);
            BufferedWriter writer = new BufferedWriter(osw);
            writer.append(text);
            writer.flush();
            writer.close();

        } catch (Exception e) {
            Log.e("FILE_ERROR", "writeFile: " + e.getMessage());
        }
    }

}