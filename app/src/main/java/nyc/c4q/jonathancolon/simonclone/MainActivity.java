package nyc.c4q.jonathancolon.simonclone;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.preference.PreferenceManager;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Random;
import java.util.TimerTask;

public class MainActivity extends AppCompatActivity {

    Handler timer;
    private int round = 0;
    Button startButton;
    ImageView btnGreen;
    ImageView btnBlue;
    ImageView btnRed;
    ImageView btnYellow;
    TextView roundCounter;
    private ArrayList<Integer> simonPatternArray = new ArrayList<>();
    private int index = 0;
    private static final String TAG = "MyActivity";
    private Handler time;
    private int oneSec = 1000;
    private int halfsec = 500;
    private int quartsec = 250;
    boolean normalMode = true;
    boolean hardMode = false;
    private SharedPreferences prefs;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        prefs = PreferenceManager.getDefaultSharedPreferences(this);


        btnGreen = (ImageView) findViewById(R.id.greenDim);
        btnBlue = (ImageView) findViewById(R.id.blueDim);
        btnRed = (ImageView) findViewById(R.id.redDim);
        btnYellow = (ImageView) findViewById(R.id.yellowDim);
        roundCounter = (TextView) findViewById(R.id.roundcounter);
        startButton = (Button) findViewById(R.id.startButton);
        startButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startGame(true);

            }
        });




    }

    public void startGame(boolean startGame) {

        if (startGame == true) {
            simonPatternArray.clear();
            round = 0;
            round++;
            roundCounter.setText(String.valueOf(round));


            for (int i = 0; i < round; i++) {
                Random simonPatternNum = new Random();
                int simonButtonPressed = simonPatternNum.nextInt(4);

                simonPatternArray.add(simonButtonPressed);
                Log.e(TAG, "Current Simon Array: " + simonPatternArray);
            }
        } else {


            Random simonPatternNum = new Random();
            int simonButtonPressed = simonPatternNum.nextInt(4);

            simonPatternArray.add(simonButtonPressed);
            Log.e(TAG, "Current Simon Array: " + simonPatternArray);
        }
//        }

        activateButtons();

        btnGreen.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                if (event.getAction() == MotionEvent.ACTION_DOWN) {
                    btnGreen.setImageResource(R.drawable.green_lit);
                    compareAnswers(0);
                }
                if (event.getAction() == MotionEvent.ACTION_UP)
                    btnGreen.setImageResource(R.drawable.green_dim);
                return false;
            }
        });

        btnRed.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                if (event.getAction() == MotionEvent.ACTION_DOWN) {
                    btnRed.setImageResource(R.drawable.red_lit);
                    compareAnswers(1);
                }
                if (event.getAction() == MotionEvent.ACTION_UP)
                    btnRed.setImageResource(R.drawable.red_dim);
                return false;
            }
        });


        btnBlue.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                if (event.getAction() == MotionEvent.ACTION_DOWN) {
                    btnBlue.setImageResource(R.drawable.blue_lit);
                    compareAnswers(2);
                }
                if (event.getAction() == MotionEvent.ACTION_UP)
                    btnBlue.setImageResource(R.drawable.blue_dim);
                return false;
            }
        });

        btnYellow.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                if (event.getAction() == MotionEvent.ACTION_DOWN) {
                    btnYellow.setImageResource(R.drawable.yellow_lit);
                    compareAnswers(3);
                }
                if (event.getAction() == MotionEvent.ACTION_UP)
                    btnYellow.setImageResource(R.drawable.yellow_dim);
                return false;
            }
        });
        Handler initialTimer = new Handler();
        initialTimer.postDelayed(new TimerTask() {
            @Override
            public void run() {
                animateSimonPattern();
            }
        }, 2000);
    }

    public void restartSimon() {
        Toast.makeText(this, "Simon Restarted", Toast.LENGTH_LONG).show();
        startGame(false);
        startGame(true);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.main_menu, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        switch (item.getItemId()) {
            case R.id.mode_normal:
                restartSimon();
                break;
            case R.id.mode_hard:
                restartSimon();
                hardMode = true;
                break;
        }
        return super.onOptionsItemSelected(item);
    }

    public void animateSimonPattern() {
        for (int i = 0; i < simonPatternArray.size(); i++) {
            int indexTime = i;
            flashButton(simonPatternArray.get(i), indexTime);
        }
    }

    public void flashButton(int buttonPressed, int indexTime) {
        Handler timer = new Handler();


        if (normalMode == true) {
            switch (buttonPressed) {
                case 0:
                    timer.postDelayed(new TimerTask() {
                        @Override
                        public void run() {
                            btnGreen.setImageResource(R.drawable.green_lit);
                        }
                    }, oneSec * indexTime);
                    timer.postDelayed(new TimerTask() {
                        @Override
                        public void run() {
                            btnGreen.setImageResource(R.drawable.green_dim);
                        }
                    }, oneSec * indexTime + halfsec);
                    break;
                case 1:
                    timer.postDelayed(new TimerTask() {
                        @Override
                        public void run() {
                            btnRed.setImageResource(R.drawable.red_lit);
                        }
                    }, oneSec * indexTime);
                    timer.postDelayed(new TimerTask() {
                        @Override
                        public void run() {
                            btnRed.setImageResource(R.drawable.red_dim);
                        }
                    }, oneSec * indexTime + halfsec);
                    break;
                case 2:
                    timer.postDelayed(new TimerTask() {
                        @Override
                        public void run() {
                            btnBlue.setImageResource(R.drawable.blue_lit);
                        }
                    }, oneSec * indexTime);
                    timer.postDelayed(new TimerTask() {
                        @Override
                        public void run() {
                            btnBlue.setImageResource(R.drawable.blue_dim);
                        }
                    }, oneSec * indexTime + halfsec);
                    break;
                case 3:
                    timer.postDelayed(new TimerTask() {
                        @Override
                        public void run() {
                            btnYellow.setImageResource(R.drawable.yellow_lit);
                        }
                    }, oneSec * indexTime);
                    timer.postDelayed(new TimerTask() {
                        @Override
                        public void run() {
                            btnYellow.setImageResource(R.drawable.yellow_dim);
                        }
                    }, oneSec * indexTime + halfsec);
                    break;
                default:
                    break;
            }
        }

        if (hardMode == true) {
            normalMode = false;
            oneSec = 500;
            switch (buttonPressed) {
                case 0:
                    timer.postDelayed(new TimerTask() {
                        @Override
                        public void run() {
                            btnGreen.setImageResource(R.drawable.green_lit);
                        }
                    }, halfsec * indexTime);
                    timer.postDelayed(new TimerTask() {
                        @Override
                        public void run() {
                            btnGreen.setImageResource(R.drawable.green_dim);
                        }
                    }, halfsec * indexTime + quartsec);
                    break;
                case 1:
                    timer.postDelayed(new TimerTask() {
                        @Override
                        public void run() {
                            btnRed.setImageResource(R.drawable.red_lit);
                        }
                    }, halfsec * indexTime);
                    timer.postDelayed(new TimerTask() {
                        @Override
                        public void run() {
                            btnRed.setImageResource(R.drawable.red_dim);
                        }
                    }, halfsec * indexTime + quartsec);
                    break;
                case 2:
                    timer.postDelayed(new TimerTask() {
                        @Override
                        public void run() {
                            btnBlue.setImageResource(R.drawable.blue_lit);
                        }
                    }, halfsec * indexTime);
                    timer.postDelayed(new TimerTask() {
                        @Override
                        public void run() {
                            btnBlue.setImageResource(R.drawable.blue_dim);
                        }
                    }, halfsec * indexTime + quartsec);
                    break;
                case 3:
                    timer.postDelayed(new TimerTask() {
                        @Override
                        public void run() {
                            btnYellow.setImageResource(R.drawable.yellow_lit);
                        }
                    }, halfsec * indexTime);
                    timer.postDelayed(new TimerTask() {
                        @Override
                        public void run() {
                            btnYellow.setImageResource(R.drawable.yellow_dim);
                        }
                    }, halfsec * indexTime + quartsec);
                    break;
                default:
                    break;
            }
        }
    }

    public void compareAnswers(int buttonPressed) {

        if (simonPatternArray.get(index) == buttonPressed) {
            index++;
        } else {
            Toast.makeText(this, "INCORRECT", Toast.LENGTH_SHORT).show();
            recreate();
        }

        if (index == simonPatternArray.size()) {
            round++;
            roundCounter.setText(String.valueOf(round));
            index = 0;
            startGame(false);
            Toast.makeText(this, "ROUND SUCCESS", Toast.LENGTH_SHORT).show();
        }
    }

    public void deactivateButtons() {
        btnGreen.setEnabled(false);
        btnGreen.setImageResource(R.drawable.green_dim);
        btnRed.setEnabled(false);
        btnRed.setImageResource(R.drawable.red_dim);
        btnBlue.setEnabled(false);
        btnBlue.setImageResource(R.drawable.blue_dim);
        btnYellow.setEnabled(false);
        btnYellow.setImageResource(R.drawable.yellow_dim);
    }

    public void activateButtons() {
        btnGreen.setEnabled(true);
        btnRed.setEnabled(true);
        btnBlue.setEnabled(true);
        btnYellow.setEnabled(true);
    }


}


