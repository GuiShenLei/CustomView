package com.example.wbx.palyvideo;

import android.os.Environment;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.VideoView;

import java.io.File;

public class MainActivity extends AppCompatActivity implements View.OnClickListener{
    Button mPlay;
    Button mPause;
    Button mReplay;
    VideoView mViedoView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mViedoView = findViewById(R.id.video_view);
        mPlay = findViewById(R.id.play);
        mPause = findViewById(R.id.pause);
        mReplay = findViewById(R.id.replay);

        mPlay.setOnClickListener(this);
        mPause.setOnClickListener(this);
        mReplay.setOnClickListener(this);

        initVideoPath();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if(mViedoView!=null){
            mViedoView.suspend();
        }
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.play:
                if(!mViedoView.isPlaying()){
                    mViedoView.start();
                }
                break;
            case R.id.pause:
                if(mViedoView.isPlaying()){
                    mViedoView.pause();
                }
                break;
            case R.id.replay:
                if(mViedoView.isPlaying()){
                    mViedoView.resume();
                }
                break;
        }
    }

    private void initVideoPath(){
        File file = new File(Environment.getExternalStorageDirectory(),"test.mp4");
        mViedoView.setVideoPath(file.getPath());
    }
}
