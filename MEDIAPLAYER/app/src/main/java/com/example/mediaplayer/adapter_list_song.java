package com.example.mediaplayer;

import android.app.Activity;
import android.content.Context;
import android.media.MediaMetadataRetriever;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

public class adapter_list_song extends ArrayAdapter<String> {
    Context context;
    String[] path;;

    public adapter_list_song(Context context, int layoutTobeInflated, String[] path) {
        super(context, R.layout.adapter_item_song, path);
        this.context = context;
        this.path = path;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        LayoutInflater inflater = ((Activity) context).getLayoutInflater();
        View row = inflater.inflate(R.layout.adapter_item_song, null);
        TextView txttitle = (TextView) row.findViewById(R.id.txt_title);
        TextView txtarti = (TextView) row.findViewById(R.id.txt_artist);

        MediaMetadataRetriever mmr = new MediaMetadataRetriever();
        mmr.setDataSource(path[position]);
        String title = mmr.extractMetadata(MediaMetadataRetriever.METADATA_KEY_TITLE);
        String artist = mmr.extractMetadata(MediaMetadataRetriever.METADATA_KEY_ARTIST);
        txttitle.setText(title);
        txtarti.setText(artist);
        return row;
    }

}