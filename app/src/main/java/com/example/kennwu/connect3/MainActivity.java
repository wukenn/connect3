package com.example.kennwu.connect3;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.GridLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {

    //0 =  yellow, 1 = red

    int activePlayer = 0;

    boolean gameIsActive = true;

    //2 means unplayed
    //game state keep track of which player own which grid
    int[] gameState = {2,2,2,2,2,2,2,2,2};

    //Possible winning moves
    int[][] winningPositions = {{0,1,2},{3,4,5},{6,7,8},{0,3,6},{1,4,7},{2,5,8},{0,4,8},{2,4,6}};

    /*Unlike IDs, tags are not used to identify views. Tags
    *are essentially an extra piece of information that can be
     *associated with a view. They are most often used as a convenie
    * nce to store data related to views in the views themselves rather
     *than by putting them in a separate structure*/

    public void dropIn(View view){
        ImageView counter = (ImageView) view;

        //print the tag number and use tag to update the gameState
        System.out.println(counter.getTag().toString());

        int tappedCounter = Integer.parseInt(counter.getTag().toString());

        //if counter is untapped, do action and modify the gamestate, otherwise do nothing
        //Cannot carry on playing the game if someone is won
        if(gameState[tappedCounter] == 2 && gameIsActive) {
            //Set image template outside on top of the screen
            counter.setTranslationY(-1000f);

            gameState[tappedCounter] = activePlayer;


            if (activePlayer == 0) {


                //Set counter image resource to be an image
                counter.setImageResource(R.drawable.yellow);

                activePlayer = 1;
            } else {
                counter.setImageResource(R.drawable.red);

                activePlayer = 0;
            }
            counter.animate().translationYBy(1000f).setDuration(300);


            /* Check if the 3 consecutive circles are formed by comparing gamestate with the winning positions
            * Does not count if the gameState is still 2 (which means unplayed)
            * */
            for (int[] winningPosition: winningPositions){
                if(gameState[winningPosition[0]] == gameState[winningPosition[1]]
                        && gameState[winningPosition[0]] == gameState[winningPosition[2]]
                        && gameState[winningPosition[0]] != 2 )
                {
                    gameIsActive= false;

                    String winner = "Red";
                    if(gameState[winningPosition[0]] == 0){
                        winner = "Yellow";
                    }
                    TextView winnerMessage = (TextView) findViewById(R.id.winnerMessage);
                    winnerMessage.setText(winner + " has won");

                    LinearLayout layout = (LinearLayout) findViewById(R.id.playAgainLayout);

                    layout.setVisibility(View.VISIBLE);
                } else {
                    boolean gameIsOver = true;

                    for(int counterState : gameState){
                        if (counterState == 2) gameIsOver = false;
                    }
                    if (gameIsOver){
                        TextView winnerMessage = (TextView) findViewById(R.id.winnerMessage);
                        winnerMessage.setText("its a draw");

                        LinearLayout layout = (LinearLayout) findViewById(R.id.playAgainLayout);

                        layout.setVisibility(View.VISIBLE);
                    }
                }
            }
        }
    }
    /*
    * 1. Make playagain layout invisible.
    * 2. Reset active player and gameState
    * 3. Make children of gridlayout disappear using .getChildAt and .setImageResource
    * */
    public void playAgain(View view){

        LinearLayout layout = (LinearLayout) findViewById(R.id.playAgainLayout);
        layout.setVisibility(View.INVISIBLE);

        gameIsActive = true;


        activePlayer = 0;

        for(int i = 0 ; i < gameState.length;i++){
            gameState[i] = 2;
        }

        GridLayout gridLayout = (GridLayout) findViewById(R.id.gridLayout);

        //get the number of child in gridLayout
        for(int i = 0; i < gridLayout.getChildCount(); i++){

            //setImageResource to 0 means null image
            ((ImageView)gridLayout.getChildAt(i)).setImageResource(0);
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }
}
