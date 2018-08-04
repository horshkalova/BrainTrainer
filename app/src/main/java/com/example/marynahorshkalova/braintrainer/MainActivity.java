package com.example.marynahorshkalova.braintrainer;

import android.os.CountDownTimer;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.GridLayout;
import android.widget.TextView;

import java.util.Random;


public class MainActivity extends AppCompatActivity {

    Button startButton;
    Button playAgainbutton;
    TextView greetingTextView;
    TextView timerTextView;
    TextView checkerTextView;
    TextView expressionTextView;
    TextView rightScoreTextView;
    TextView wrongScoreTextView;
    TextView dilimiterScoreTextView;

    Button button0;
    Button button1;
    Button button2;
    Button button3;

    Boolean gameIsActive;
    GridLayout gridLayout;

    String secondsLeftString;
    int secondsToSolve = 30;
    int firstInt;
    int secondInt;
    int sum;
    String sumString;
    int rightScore = 1;
    int wrongScore = 1;

    public void visibilityChange() {

        if (gameIsActive == false) {

            startButton.setVisibility(startButton.VISIBLE);
            greetingTextView.setVisibility(greetingTextView.VISIBLE);

            gridLayout.setVisibility(View.INVISIBLE);
            timerTextView.setVisibility(View.INVISIBLE);
            expressionTextView.setVisibility(View.INVISIBLE);
            checkerTextView.setVisibility(View.INVISIBLE);
            rightScoreTextView.setVisibility(View.INVISIBLE);
            wrongScoreTextView.setVisibility(View.INVISIBLE);
            dilimiterScoreTextView.setVisibility(View.INVISIBLE);
            playAgainbutton.setVisibility(View.INVISIBLE);

        } else {

            startButton.setVisibility(startButton.INVISIBLE);
            greetingTextView.setVisibility(greetingTextView.INVISIBLE);

            gridLayout.setVisibility(View.VISIBLE);
            timerTextView.setVisibility(View.VISIBLE);
            expressionTextView.setVisibility(View.VISIBLE);
            checkerTextView.setVisibility(View.VISIBLE);
            rightScoreTextView.setVisibility(View.VISIBLE);
            wrongScoreTextView.setVisibility(View.VISIBLE);
            dilimiterScoreTextView.setVisibility(View.VISIBLE);

        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        startButton = findViewById(R.id.startButton);
        greetingTextView = findViewById(R.id.greetingTextView);
        timerTextView = findViewById(R.id.timerTextView);
        checkerTextView = findViewById(R.id.checkerTextView);
        gridLayout = findViewById(R.id.gridLayout);
        expressionTextView = findViewById(R.id.expressionTextView);
        wrongScoreTextView = findViewById(R.id.wrongScoreTextView);
        rightScoreTextView = findViewById(R.id.rightScoreTextView);
        dilimiterScoreTextView = findViewById(R.id.dilimiterScoreTextView);
        playAgainbutton = findViewById(R.id.playAgainbutton);

        button0 = findViewById(R.id.button0);
        button1 = findViewById(R.id.button1);
        button2 = findViewById(R.id.button2);
        button3 = findViewById(R.id.button3);

        gameIsActive = false;
        visibilityChange();
    }

    public void startButtonPressed(View view) {

        gameIsActive = true;

        visibilityChange();

        setTimer();

        generateExpression();

        setNumbersToButtons();

    }

    private void setTimer() {

        if (gameIsActive == true) {

            CountDownTimer countDownTimer = new CountDownTimer
                    (secondsToSolve * 1000 + 200, 1000) {
                @Override
                public void onTick(long l) {

                    int secondsLeft = (int) l / 1000;
                    secondsLeftString = Integer.toString(secondsLeft);


                    if (secondsLeft < 10) {

                        secondsLeftString = "0" + secondsLeft;
                    }

                    timerTextView.setText("00:" + secondsLeftString);

                }

                @Override
                public void onFinish() {

                    timerTextView.setText("00:00");

                    Log.i("Timer", "finished");

                    gameIsActive = false;

                    checkerTextView.setText("Your score is: " + Integer.toString(rightScore - 1)
                            + " / " + Integer.toString(wrongScore - 1));

                    playAgainbutton.setVisibility(View.VISIBLE);


                }
            }.start();
        }
    }

    private void generateExpression() {

        if (gameIsActive == true) {

            firstInt = randInt(0, 20);
            secondInt = randInt(0, 20);
            sum = firstInt + secondInt;

            expressionTextView.setText(firstInt + " + " + secondInt + " =");
        }
    }

    public void setNumbersToButtons() {

        if (gameIsActive == true) {

            Button[] buttons = new Button[4];
            buttons[0] = button0;
            buttons[1] = button1;
            buttons[2] = button2;
            buttons[3] = button3;

            int error1 = randInt(sum - sum / 3, sum + sum / 3);
            int error2 = randInt(sum - sum / 5, sum + sum / 5);
            int error3 = randInt(sum - sum / 2, sum + sum / 2);
            int error4 = randInt(sum - sum / 4, sum + sum / 4);

            int randNum = randInt(sum - error1, sum + error1);
            int randNum1 = randInt(sum - error2, sum + error2);
            int randNum2 = randInt(sum - error3, sum + error3);
            int randNum3 = randInt(sum - error4, sum + error4);


            button0.setText(Integer.toString(randNum));
            button1.setText(Integer.toString(randNum1));
            button2.setText(Integer.toString(randNum2));
            button3.setText(Integer.toString(randNum3));

            int sumTag = randInt(0, 3);
            buttons[sumTag].setText(Integer.toString(sum));

        }
    }


    public static int randInt(int min, int max) {

        Random rand = new Random();

        // nextInt is normally exclusive of the top value,
        // so add 1 to make it inclusive
        int randomNum = rand.nextInt((max - min) + 1) + min;

        return randomNum;
    }

    public void buttonClicked(View view) {

        if (gameIsActive == true) {

            // which number was pressed
            Button button = (Button) findViewById(view.getId());
            String text = button.getText().toString();

            sumString = Integer.toString(sum);

            if (text == sumString) {

                checkerTextView.setText("Right!");
                rightScoreTextView.setText(Integer.toString(rightScore));
                rightScore++;


            } else {

                checkerTextView.setText("Wrong!");
                wrongScoreTextView.setText(Integer.toString(wrongScore));
                wrongScore++;

            }

            Log.i("Button's text", text);
            Log.i("Sum", sumString);

            generateExpression();
            setNumbersToButtons();
        } else {
            Log.i("Game", "over");
        }

    }

    public void playAgain(View view) {

        visibilityChange();
        rightScore = 1;
        wrongScore = 1;
        checkerTextView.setText("");
        rightScoreTextView.setText("0");
        wrongScoreTextView.setText("0");
    }
}
