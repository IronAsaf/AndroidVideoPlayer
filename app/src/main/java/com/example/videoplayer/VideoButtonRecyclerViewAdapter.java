package com.example.videoplayer;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class VideoButtonRecyclerViewAdapter extends RecyclerView.Adapter<VideoButtonRecyclerViewAdapter.MyViewHolder>
{
    private Context context;
    private ArrayList<VideoDataModel> videoButtonModels;
    private final RecycleViewAdapterInterface recycleViewAdapterInterface;

    public VideoButtonRecyclerViewAdapter(Context context, ArrayList<VideoDataModel> videoButtonModels,
                                          RecycleViewAdapterInterface recycleViewAdapterInterface)
    {
        this.context = context;
        this.videoButtonModels = videoButtonModels;
        this.recycleViewAdapterInterface = recycleViewAdapterInterface;
    }

    @NonNull
    @Override
    public VideoButtonRecyclerViewAdapter.MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(context);
        View v = inflater.inflate(R.layout.recycler_view_row, parent, false);
        return new VideoButtonRecyclerViewAdapter.MyViewHolder(v, recycleViewAdapterInterface);
    }

    @Override
    public void onBindViewHolder(@NonNull VideoButtonRecyclerViewAdapter.MyViewHolder holder, int position) {
        holder.textView.setText(videoButtonModels.get(position).getTextDisplay());
        holder.imageView.setImageResource(videoButtonModels.get(position).getImage());
    }

    @Override
    public int getItemCount() {
        return videoButtonModels.size();
    }

    public long getItemIdByIndex(int pos)
    {
        return getItemId(pos);
    }

    public static class MyViewHolder extends RecyclerView.ViewHolder
    {
        private TextView textView;
        private ImageView imageView;

        public MyViewHolder(@NonNull View itemView, RecycleViewAdapterInterface recycleViewAdapterInterface) {
            super(itemView);

            imageView = itemView.findViewById(R.id.recyclerImageView);
            textView = itemView.findViewById(R.id.recycleTextView);
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if(recycleViewAdapterInterface == null) return;
                    int pos = getAdapterPosition();
                    if(pos == RecyclerView.NO_POSITION) return;
                    recycleViewAdapterInterface.onItemClick(pos);
                }
            });

        }
    }
}
