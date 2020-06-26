package com.example.timer;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;

import android.content.Context;
import android.media.AudioAttributes;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.media.SoundPool;
import android.os.Build;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.text.InputFilter;
import android.text.Layout;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Filter;
import android.widget.TextView;

import java.util.Random;
import java.util.concurrent.Executor;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

public class MainActivity extends AppCompatActivity {

    private Button bt;
    private EditText czas;
    private EditText seria;
    private TextView series;
    private TextView time;
    private TextView inform;
    private ConstraintLayout click;
    private SoundPool soundPool;
    private int sound1,sound2,sound3,sound4,sound5,sound6,def;
    private Random gen= new Random();

    private Integer s;
    private Integer c;
    private Integer x;
    private boolean TimerRunning=false;
    private boolean st=true;
    private CountDownTimer mCDT;
    private MediaPlayer mediaPlayer;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        bt=(Button)findViewById(R.id.button);
        click=(ConstraintLayout)findViewById(R.id.click);
        seria = (EditText)findViewById(R.id.editText);
        czas = (EditText)findViewById(R.id.editText2);
        series=findViewById(R.id.textView2);
        time=findViewById(R.id.textView);
        inform=findViewById(R.id.textView3);






        mediaPlayer=MediaPlayer.create(this, R.raw.sfd);
        mediaPlayer.setLooping(true);


if(Build.VERSION.SDK_INT>=Build.VERSION_CODES.LOLLIPOP){
    AudioAttributes audioAttributes=new AudioAttributes.Builder()
            .setUsage(AudioAttributes.USAGE_ASSISTANCE_SONIFICATION)
            .setContentType(AudioAttributes.CONTENT_TYPE_SONIFICATION)
            .build();
    soundPool=new SoundPool.Builder()
            .setMaxStreams(7)
            .setAudioAttributes(audioAttributes)
            .build();
}
else{
    soundPool=new SoundPool(3, AudioManager.STREAM_MUSIC,0);

}
sound1=soundPool.load(this, R.raw.s1,1 );
sound2=soundPool.load(this, R.raw.s2,1 );
sound3=soundPool.load(this, R.raw.s3,1 );
sound4=soundPool.load(this, R.raw.s4,1 );
sound5=soundPool.load(this, R.raw.s5,1 );
sound6=soundPool.load(this, R.raw.s6,1 );
def=soundPool.load(this, R.raw.bzz,1 );


    }



    public void start(View view){





        if(st){
            mediaPlayer.start();
            bt.setText("Restart");
            seria.setVisibility(View.INVISIBLE);
            czas.setVisibility(View.INVISIBLE);
            inform.setText("CLICK TO START");
            st=false;


            click.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if(s==null&&c==null){
                        String sz=seria.getText().toString();
                        String cz=czas.getText().toString();
                        if(cz.length()==0){cz="30";}
                        if(sz.length()==0){sz="1";}

                        s=Integer.parseInt(sz);
                        c=Integer.parseInt(cz);
                        if(s<1){s=1;}
                        if(c<1){c=1;}
                        x=s*c*1000+10000;
                        s--;

                    }

                    if (TimerRunning) { pause(); inform.setText("CLICK TO START");}
                    else { startTimer();inform.setText("CLICK TO STOP");}
                }
            });

        }
        else{
            click.setOnClickListener(null);
            mediaPlayer.pause();
            if(TimerRunning){pause();}
            seria.setVisibility(View.VISIBLE);
            czas.setVisibility(View.VISIBLE);
            seria.setText("");
            czas.setText("");
            series.setText("");
            time.setText("");
            inform.setText("");
            s=c=null;


            bt.setText("start");
st=true;
        }





    }
        private void startTimer () {

            mCDT = new CountDownTimer(x, 1000) {
                @RequiresApi(api = Build.VERSION_CODES.N)
                @Override
                public void onTick(long millisUntilFinished) {
                    x = Math.toIntExact(millisUntilFinished);

                    updateCountDownText();
                }

                @Override
                public void onFinish() {

                }
            }.start();

            TimerRunning = true;
        }
        private void pause () {
            mCDT.cancel();
            TimerRunning = false;

        }


        public void updateCountDownText () {
            time.setText(c.toString());
            if(s==0){series.setText("");}
            else{series.setText("Zostało "+s.toString()+" serii");}

            if(c==1&&s!=0){
                s--;
                sounds(gen.nextInt(6));

                c=Integer.parseInt(czas.getText().toString());
            }
            else if(c==0&&s==0){
                time.setText("");
                inform.setText("");
                series.setText("KONIEC!");
                click.setOnClickListener(null);

                sounds(def);

            }
            else{c--;}



        }

        public void sounds(Integer r){



switch (r){

    case 0:
        soundPool.play(sound1,1,1,0,0,1);
        break;
    case 1:
        soundPool.play(sound2,1,1,0,0,1);
        break;
    case 2:
        soundPool.play(sound3,1,1,0,0,1);
        break;
    case 3:
        soundPool.play(sound4,1,1,0,0,1);
        break;
    case 4:
        soundPool.play(sound5,1,1,0,0,1);
        break;
    case 5:
        soundPool.play(sound6,1,1,0,0,1);
        break;

    default:
        soundPool.play(def,1,1,0,0,1);
}


        }

}
