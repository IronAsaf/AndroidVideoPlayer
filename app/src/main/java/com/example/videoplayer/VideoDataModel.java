package com.example.videoplayer;

public class VideoDataModel
{
    private String textDisplay;
    private int image;


    public VideoDataModel(String buttonDisplay, int image) {
        this.textDisplay = buttonDisplay;
        this.image = image;
    }

    public String getTextDisplay() {
        return textDisplay;
    }

    public int getImage() {
        return image;
    }
}
