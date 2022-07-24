package com.example.videoplayer;

import android.content.Context;
import android.content.pm.PackageManager;
import android.media.AudioManager;
import android.os.Bundle;
import android.widget.Button;
import android.widget.SearchView;
import android.widget.SeekBar;
import android.widget.Toast;
import android.widget.VideoView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity implements RecycleViewAdapterInterface
{
    //XML Attributes
    //private LinearLayout linearLayoutForVideosList;
    private VideoView videoPlayer;
    private SeekBar soundSeekBar;
    private Button pauseBtn;
    private Button playBtn;
    private Button replayBtn;
    private Button stopBtn;
    private SearchView searchView;
    private RecyclerView recyclerView;

    //Runtime Attributes
    private final String currentVideoName = "sample.mp4";
    //private Button[] playableVideosButtons;
    private AudioManager audioManager;

    private ArrayList<VideoDataModel> videoButtonModels;

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
        SetUpRecyclerView();
        SetVideo(name);
        SetSearchView();
        SetVideoAudioListeners();
    }
    private void SetUpRecyclerView()
    {
        if(videoButtonModels.size() <=0) return;
        VideoButtonRecyclerViewAdapter adapter = new VideoButtonRecyclerViewAdapter(this, videoButtonModels, this);

        recyclerView.setAdapter(adapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        for(int i = 0; i < recyclerView.getChildCount(); i++)
        {
            Button btn = findViewById((int) adapter.getItemIdByIndex(i));
            btn.setText(Integer.toString(i));
        }
    }

    private void SetSearchView()
    {
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String s) {
                OnSearchCloseAffectButtons(s);
                return false;
            }

            @Override
            public boolean onQueryTextChange(String s) {
                OnSearchCloseAffectButtons(s);
                return false;
            }
        });
    }


    private void CacheAttributes()
    {
        //linearLayoutForVideosList = findViewById(R.id.lin);
        videoPlayer = findViewById(R.id.videoView);
        soundSeekBar = findViewById(R.id.soundSeekbar);
        playBtn = findViewById(R.id.playBtn);
        pauseBtn = findViewById(R.id.pauseBtn);
        replayBtn = findViewById(R.id.replayBtn);
        stopBtn = findViewById(R.id.stopBtn);
        searchView = findViewById(R.id.searchVideoView);
        recyclerView = findViewById(R.id.recycler);
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
        //videoProgressBar.StopProgress();
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
        videoButtonModels = new ArrayList<>();
        for(int i = 0; i < strArray.size(); i++)
        {
            videoButtonModels.add(new VideoDataModel(strArray.get(i), R.drawable.ic_baseline_video_library_24));
        }

        /*LinearLayout.LayoutParams lp = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT,
                LinearLayout.LayoutParams.WRAP_CONTENT);
        playableVideosButtons = new Button[strArray.size()];
        for(int i =0; i < strArray.size(); i++)
        {
            Button btn = new Button(this);
            btn.setBackgroundResource(R.drawable.custom_button);
            String btnName = strArray.get(i);
            btn.setText(btnName);
            linearLayoutForVideosList.addView(btn,lp);
            btn.setOnClickListener(v -> {
               OnListedVideoClick(btnName);
            });
            playableVideosButtons[i] = btn;
        }*/
        if(strArray.size() <=0) return "";
        return strArray.get(0);
    }

    private void OnSearchCloseAffectButtons(String searchQuery)
    {
        /*if(searchQuery.isEmpty())
        {
            for(int i =0; i < playableVideosButtons.length; i++)
            {
                playableVideosButtons[i].setVisibility(View.VISIBLE);
            }
            return;
        }
        for(int i = 0; i < playableVideosButtons.length; i++)
        {
            boolean queryFindings = playableVideosButtons[i].getText().toString().contains(searchQuery);
            int visibility = queryFindings ? View.VISIBLE : View.GONE;
            playableVideosButtons[i].setVisibility(visibility);
        }*/
    }

    @Override
    public void onItemClick(int position)
    {
        VideoDataModel model = videoButtonModels.get(position);
        OnListedVideoClick(model.getTextDisplay());
        Toast.makeText(this, "Clicked " + model.getTextDisplay(), Toast.LENGTH_SHORT).show();
    }
}