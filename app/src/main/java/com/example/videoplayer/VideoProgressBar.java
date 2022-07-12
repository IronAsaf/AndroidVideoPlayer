package com.example.videoplayer;

import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.VideoView;

public class VideoProgressBar
{
    private final ProgressBar progressBar;
    private final VideoView videoPlayer;
    private TextView textView;

    //Runtime
    Thread th;

    public VideoProgressBar(ProgressBar progress, VideoView video, TextView tv)
    {
        progressBar = progress;
        videoPlayer = video;
        textView = tv;
    }

    public void StartProgress()
    {
        textView.setText(0);
        int duration = videoPlayer.getDuration();
        progressBar.setMax(duration);
        int buff = videoPlayer.getBufferPercentage();
        try
        {
            th = new Thread() {
                @Override
                public void run() {
                    int current = 0;
                    while(current < duration)
                    {
                        current = videoPlayer.getCurrentPosition();


                        try {
                            sleep(buff);
                            progressBar.setProgress(current,false);
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                        textView.setText(String.valueOf(progressBar.getProgress()));
                    }
                }
            };

            th.start();
        }
        catch(Exception e)
        {

        }

    }

    public void StopProgress()
    {
        //th.stop();
        th = null;
    }
}
