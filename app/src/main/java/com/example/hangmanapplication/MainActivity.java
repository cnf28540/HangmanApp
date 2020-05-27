package com.example.hangmanapplication;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Button b = findViewById(R.id.button);

        b.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                EditText e = findViewById(R.id.editText);

                String userInput = e.getText().toString();

                Intent gameScreenIntent = new Intent(getBaseContext(), HangmanGameActivity.class);
                gameScreenIntent.putExtra("PUZZLESTRING", userInput);
                startActivity(gameScreenIntent);
            }
        });

    }
}
