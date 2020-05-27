package com.example.hangmanapplication;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

public class HangmanGameActivity extends AppCompatActivity {

    private static String puzzleString;
    int uniqueLetters = 0;
    int correctGuesses = 0;
    int incorrectGuesses = 0;
    private static final int maxIncorrectGuesses = 7;
    private List<Character> guessesSoFar;

    private String generatePromptString() {

        StringBuilder patternBuilder = new StringBuilder();
        patternBuilder.append("[");
        for (int i = 0; i < guessesSoFar.size(); i++) {
            patternBuilder.append(guessesSoFar.get(i));
        }
        patternBuilder.append("]");

        return puzzleString.toUpperCase().replaceAll("\\s", "   ").replaceAll(patternBuilder.toString(), " _ ");
    }


    /* This only works for English... */
    private void populateGuessesList() {

        if (guessesSoFar == null) {
            guessesSoFar = new ArrayList<Character>();
        }

        for (int i = 0; i < 26; i++) {
            guessesSoFar.add((char) ('A' + i));
        }
    }

    private void updatePuzzlePrompt() {

        TextView t = findViewById(R.id.textView3);

        t.setText(generatePromptString());

    }

    private void provideFeedbackToUser(String s) {
        TextView t = findViewById(R.id.textView5);
        t.setText(s);
    }

    private void endGame(){
        Button b = findViewById(R.id.button2);
        b.setEnabled(false);
    }

    private class HangmanListener implements View.OnClickListener {

        @Override
        public void onClick(View v) {
            EditText e = findViewById(R.id.editText2);
            String userInput = e.getText().toString().toUpperCase();
            char guess;

            provideFeedbackToUser("");

            if (userInput.length() > 0) {
                guess = userInput.charAt(0);

                if (guessesSoFar.contains(guess)) {

                    guessesSoFar.remove((Character.valueOf(guess)));

                    if (puzzleString.contains("" + guess)) {
                        correctGuesses++;

                        if(correctGuesses == uniqueLetters){
                            provideFeedbackToUser("You Win!");
                            endGame();
                        }

                    } else {
                        incorrectGuesses++;

                        if(incorrectGuesses >= maxIncorrectGuesses){
                            provideFeedbackToUser("You Lose!");
                            endGame();
                        }

                        String response = guess + " is not correct";
                        provideFeedbackToUser(response);

                    }

                } else {
                    provideFeedbackToUser("You already guessed " + guess);
                }

                updatePuzzlePrompt();

            }
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_hangman_game);

        populateGuessesList();

        puzzleString = getIntent().getStringExtra("PUZZLESTRING").toUpperCase();

        List<Character> uniqueChars = new ArrayList<Character>();
        char ptr;

        for(int i = 0; i < puzzleString.length(); i++){
            ptr = puzzleString.charAt(i);

            if(!uniqueChars.contains(ptr)){
                uniqueChars.add(ptr);
            }
        }

        uniqueLetters = uniqueChars.size();

        Button b = findViewById(R.id.button2);

        TextView t = findViewById(R.id.textView5);
        t.setText("");

        b.setOnClickListener(new HangmanListener());

    }

    @Override
    protected void onStart() {
        super.onStart();

        updatePuzzlePrompt();

    }
}
