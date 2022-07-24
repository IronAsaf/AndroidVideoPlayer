package com.example.videoplayer;

import android.Manifest;
import android.os.Environment;

import java.io.File;
import java.util.ArrayList;

public class VideoUtility {
    public static String[] permissions = new String[]{Manifest.permission.READ_EXTERNAL_STORAGE};
    public static String MEDIA_PATH = Environment.getExternalStorageDirectory().getPath() + "/Download/";

    public static File[] GetFiles(String DirectoryPath) {
        File f = new File(DirectoryPath);
        f.mkdirs();
        File[] file = f.listFiles();
        return file;
    }

    public static ArrayList<String> getFileNames(File[] file){
        ArrayList<String> arrayFiles = new ArrayList<String>();
        if (file.length == 0)
            return null;
        else {
            for (int i=0; i<file.length; i++)
                arrayFiles.add(file[i].getName());
        }

        return arrayFiles;
    }

    public static ArrayList<VideoDataModel> cloneVideoDataModel(ArrayList<VideoDataModel> clone)
    {
        ArrayList<VideoDataModel> toReturn = new ArrayList<>();
        for(int i = 0; i < clone.size(); i++)
        {
            toReturn.add(new VideoDataModel(clone.get(i)));
        }
        return toReturn;
    }

}
