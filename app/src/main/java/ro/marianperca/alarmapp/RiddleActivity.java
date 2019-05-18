package ro.marianperca.alarmapp;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.TextView;

import java.util.Random;

public class RiddleActivity extends AppCompatActivity {

    TextView riddleText;
    private Riddle[] riddles = new Riddle[]{
            new Riddle("When asked how old she was \n" +
                    "Suzie replied: In two years I will be twice as old as I was five years ago.\n" +
                    "How old is she?", 12),
            new Riddle("I am a three digit number. \n" +
                    "My tens digit is five more than my ones digit. \n" +
                    "My hundreds digit is eight less than my tens digit. \n" +
                    "What number am I?", 194),
            new Riddle("A nonstop train leaves Moscow for Leningrad at 60 kph.\n" +
                    "Another nonstop train leaves leningrad for Moscow at 40 kph.\n" +
                    "How far apartare the trains 1 hour before they pass eachother?", 100)
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_riddle);

        riddleText = findViewById(R.id.riddle_text);

        Random rand = new Random();
        displayRiddle(rand.nextInt(riddles.length));
    }

    private void displayRiddle(int index) {
        Riddle riddle = riddles[index];

        riddleText.setText(riddle.text);
    }
}
