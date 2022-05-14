package com.example.videoplayer;

import java.io.File;
import java.util.ArrayList;

public class FileUtility
{
    public static ArrayList<String> FileNamesArray(String path, String fileEnd)
    {
        //String path = Environment.getExternalStorageDirectory().toString()+"/Pictures";
        File directory = new File(path);
        File[] files = directory.listFiles();
        ArrayList<String> toReturn = new ArrayList<String>();

        for (int i = 0; i < files.length; i++)
        {
            String fName = files[i].getName();
            if(fName.contains(fileEnd))
            {
                toReturn.add(fName);
            }
        }
        return toReturn;
    }
}
