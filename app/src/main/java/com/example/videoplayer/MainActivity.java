package com.example.videoplayer;

import android.content.Context;
import android.content.pm.PackageManager;
import android.media.AudioManager;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.SearchView;
import android.widget.SeekBar;
import android.widget.VideoView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity
{
    //XML Attributes
    private LinearLayout linearLayoutForVideosList;
    private VideoView videoPlayer;
    private ProgressBar videoProgressBar;
    private SeekBar soundSeekBar;
    private Button pauseBtn;
    private Button playBtn;
    private Button replayBtn;
    private Button stopBtn;
    private SearchView searchView;

    //Runtime Attributes
    private String currentVideoName = "sample.mp4";
    private Button[] playableVideosButtons;
    AudioManager audioManager;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //Ask for perms:
        ActivityCompat.requestPermissions(this, VideoUtility.permissions, PackageManager.PERMISSION_GRANTED);

        CacheAttributes();
        SetButtonListeners();
        String name = ListAllVideos();
        SetVideo(name);
        SetSearchView();
        SetVideoAudioListeners();
    }

    private void SetSearchView()
    {
        searchView.setOnCloseListener(new SearchView.OnCloseListener() {
            @Override
            public boolean onClose() {
                String str = searchView.getQuery().toString();
                OnSearchCloseAffectButtons(str);
                return false;
            }
        });
    }


    private void CacheAttributes()
    {
        linearLayoutForVideosList = findViewById(R.id.linearLayoutForVideoButtons);
        videoPlayer = findViewById(R.id.videoView);
        videoProgressBar = findViewById(R.id.videoProgressBar);
        soundSeekBar = findViewById(R.id.soundSeekbar);
        playBtn = findViewById(R.id.playBtn);
        pauseBtn = findViewById(R.id.pauseBtn);
        replayBtn = findViewById(R.id.replayBtn);
        stopBtn = findViewById(R.id.stopBtn);
        searchView = findViewById(R.id.searchView);

        audioManager = (AudioManager) getSystemService(Context.AUDIO_SERVICE);
    }

    private void SetButtonListeners()
    {
        playBtn.setOnClickListener(view -> PlayVideo());
        pauseBtn.setOnClickListener(view -> PauseVideo());
        replayBtn.setOnClickListener(view -> RestartVideo());
        stopBtn.setOnClickListener(view -> StopVideo());
    }

    private void SetVideoAudioListeners()
    {
        SetVideo(currentVideoName);
        videoPlayer.setOnPreparedListener(mediaPlayer -> videoProgressBar.setMax(videoPlayer.getDuration()));

        //General Audio Control
        int maxVol = audioManager.getStreamMaxVolume(AudioManager.STREAM_MUSIC);
        soundSeekBar.setMax(maxVol);
        soundSeekBar.setMin(0);
        soundSeekBar.setProgress(audioManager.getStreamVolume(AudioManager.STREAM_MUSIC));
        soundSeekBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int i, boolean b) {
                audioManager.setStreamVolume(AudioManager.STREAM_MUSIC,i,0);
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {

            }
        });
    }

    private void SetVideo(String videoName) throws NullPointerException
    {
        if(videoPlayer == null) throw new NullPointerException("videoPlayer is empty, please cache videoPlayer!");
        String path = VideoUtility.MEDIA_PATH + videoName;
        videoPlayer.setVideoPath(path);
    }

    private void PauseVideo()
    {
        videoPlayer.pause();
    }

    private void PlayVideo()
    {
        if(videoPlayer.isPlaying())
            videoPlayer.resume();
        else
            videoPlayer.start();

        new CountDownTimer(videoPlayer.getDuration(), 1){

            @Override
            public void onTick(long l) {
                videoProgressBar.setProgress(videoPlayer.getCurrentPosition(),true);
            }

            @Override
            public void onFinish() {
            }
        }.start();
    }

    private void RestartVideo()
    {
        videoPlayer.stopPlayback();
        SetVideo(currentVideoName);
        videoPlayer.start();
    }

    private void StopVideo()
    {
        videoPlayer.stopPlayback();
        SetVideo(currentVideoName);
    }

    private void OnListedVideoClick(String nameOfVideo)
    {
        System.out.println("vid " + nameOfVideo);
        StopVideo();
        SetVideo(nameOfVideo);
        PlayVideo();
    }

    private String ListAllVideos()
    {
        ArrayList<String> strArray = FileUtility.FileNamesArray(VideoUtility.MEDIA_PATH,".mp4");
        LinearLayout.LayoutParams lp = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT,
                LinearLayout.LayoutParams.WRAP_CONTENT);
        playableVideosButtons = new Button[strArray.size()];
        for(int i =0; i < strArray.size(); i++)
        {
            Button btn = new Button(this);
            String btnName = strArray.get(i);
            btn.setText(btnName);
            linearLayoutForVideosList.addView(btn,lp);
            btn.setOnClickListener(v -> {
               OnListedVideoClick(btnName);
            });
            playableVideosButtons[i] = btn;
        }
        if(strArray.size() <=0) return "";
        return strArray.get(0);
    }

    private void OnSearchCloseAffectButtons(String searchQuery)
    {
        for(int i = 0; i < playableVideosButtons.length; i++)
        {
            if(playableVideosButtons[i].getText().toString().contains(searchQuery)) continue;
            playableVideosButtons[i].setEnabled(false);
            playableVideosButtons[i].setAlpha(0f);
        }
    }
}