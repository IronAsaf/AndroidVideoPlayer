package com.example.videoplayer;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class VideoButtonRecyclerViewAdapter extends RecyclerView.Adapter<VideoButtonRecyclerViewAdapter.MyViewHolder>
{
    private Context context;
    private ArrayList<VideoButtonModel> videoButtonModels;

    public VideoButtonRecyclerViewAdapter(Context context, ArrayList<VideoButtonModel> videoButtonModels)
    {
        this.context = context;
        this.videoButtonModels = videoButtonModels;
    }

    @NonNull
    @Override
    public VideoButtonRecyclerViewAdapter.MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(context);
        View v = inflater.inflate(R.layout.recycler_view_row, parent, false);
        return new VideoButtonRecyclerViewAdapter.MyViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull VideoButtonRecyclerViewAdapter.MyViewHolder holder, int position) {
        holder.button.setText(videoButtonModels.get(position).getButtonDisplay());
        holder.imageView.setImageResource(videoButtonModels.get(position).getImage());
    }

    @Override
    public int getItemCount() {
        return videoButtonModels.size();
    }

    public static class MyViewHolder extends RecyclerView.ViewHolder
    {
        private Button button;
        private ImageView imageView;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);

            imageView = itemView.findViewById(R.id.recyclerImageView);
            button = itemView.findViewById(R.id.recyclerButton);
        }
    }
}
