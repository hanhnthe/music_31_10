package com.example.hanh_music_31_10.ui.holder;

import static com.example.hanh_music_31_10.model.Constants.FIREBASE_REALTIME_SONG_PATH;

import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;

import com.bumptech.glide.GenericTransitionOptions;
import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.example.hanh_music_31_10.R;
import com.example.hanh_music_31_10.model.Song;
import com.example.hanh_music_31_10.service.MediaPlaybackService;
import com.example.hanh_music_31_10.ui.recycler.BaseRecyclerViewHolder;
import com.example.hanh_music_31_10.ui.recycler.RecyclerActionListener;
import com.example.hanh_music_31_10.ui.recycler.RecyclerData;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class HomeSongItemHolder extends BaseRecyclerViewHolder {
    private ImageView mImageView;
    private TextView mTextSongName;
    private TextView mTextArtistsName;
    private DatabaseReference mDatabaseReference;

    public HomeSongItemHolder(@NonNull View itemView) {
        super(itemView);
        mImageView = itemView.findViewById(R.id.image_song_home);
        mTextSongName = itemView.findViewById(R.id.name_song);
        mTextArtistsName = itemView.findViewById(R.id.artists);
    }

    @Override
    public void bindViewHolder(RecyclerData data) {
        if (data instanceof Song) {
            Song song = (Song) data;
            if (TextUtils.isEmpty(song.getNameSong())) {
                if (mDatabaseReference == null)
                    mDatabaseReference = FirebaseDatabase.getInstance().getReference();
                mDatabaseReference.child(FIREBASE_REALTIME_SONG_PATH).child(String.valueOf(((Song) data).getId())).get()
                        .addOnCompleteListener(task -> {
                            if (!task.isSuccessful()) {
                                Log.e("firebase", "Error getting data", task.getException());
                            } else {
                                Song value = task.getResult().getValue(Song.class);
                                if (value == null) return;
                                updateData(value);
                            }
                        });
            } else {
                updateData(song);
            }
        }
    }

    private void updateData(Song song) {
        //Glide.... de load anh :|
        Glide.with(mImageView)
                .load(song.getImageUrl())
                .transition(GenericTransitionOptions.with(android.R.anim.fade_in))
                .apply(RequestOptions.
                        placeholderOf(R.drawable.placeholder_music))
                .into(mImageView);

//            mImageView.setImageResource(R.drawable.home_test);
        mTextSongName.setText(song.getNameSong());
        mTextArtistsName.setText(song.getSinger());
    }

    @Override
    public void setupClickableViews(RecyclerActionListener actionListener) {
        itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                actionListener.onViewClick(getAdapterPosition(), v, HomeSongItemHolder.this);
            }
        });
    }

    @Override
    public void setService(MediaPlaybackService service) {

    }

}
