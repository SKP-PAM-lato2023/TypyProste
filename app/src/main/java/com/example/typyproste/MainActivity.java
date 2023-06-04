package com.example.typyproste;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.content.SharedPreferences;
import android.widget.CheckBox;
import android.widget.EditText;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Button button = findViewById(R.id.button);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                SharedPreferences sharedPreferences = getPreferences(MODE_PRIVATE);
                SharedPreferences.Editor prefsEditor = sharedPreferences.edit();

                EditText editText = findViewById(R.id.editText);
                CheckBox checkBox = findViewById(R.id.checkbox);

                // zapis stanu
                prefsEditor.putString("editText", editText.getText().toString());
                prefsEditor.putBoolean("checkBox", checkBox.isChecked());
                prefsEditor.commit();
            }
        });

        SharedPreferences sharedPreferences = getPreferences(MODE_PRIVATE);
        String tekst = sharedPreferences.getString("editText", "");
        boolean w = sharedPreferences.getBoolean("checkBox", false);
        EditText editText = findViewById(R.id.editText);
        CheckBox checkBox = findViewById(R.id.checkbox);
        editText.setText(tekst);
        checkBox.setChecked(w);

        // zapis do pliku tekstowego
        Button buttonZapisz = findViewById(R.id.buttonZapisz);
        EditText editText2 = findViewById(R.id.editText2);
        buttonZapisz.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                try {
                    FileOutputStream fileOutputStream = openFileOutput("text.txt", MODE_PRIVATE);
                    OutputStreamWriter osw = new OutputStreamWriter(fileOutputStream);
                    osw.write(editText2.getText().toString());
                    osw.close();
                }
                catch (IOException e) {
                    e.printStackTrace();
                }
            }
        });

        Button buttonWczytaj = findViewById(R.id.buttonWczytaj);
        buttonWczytaj.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                try {
                    FileInputStream fileInputStream = openFileInput("text.txt");
                    InputStreamReader isr = new InputStreamReader(fileInputStream);
                    BufferedReader reader = new BufferedReader(isr);

                    StringBuilder stringBuilder = new StringBuilder();
                    String linia;
                    while ((linia = reader.readLine()) != null) {
                        stringBuilder.append(linia).append("\n");
                    }
                    // usuwam ostatni enter
                    stringBuilder.deleteCharAt(stringBuilder.length()-1);
                    reader.close();
                    editText2.setText(stringBuilder.toString());
                }catch (IOException e){
                    e.printStackTrace();
                }
            }
        });

    }
}