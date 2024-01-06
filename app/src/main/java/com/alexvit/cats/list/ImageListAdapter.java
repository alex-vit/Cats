package com.alexvit.cats.list;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.DiffUtil;
import androidx.recyclerview.widget.ListAdapter;
import androidx.recyclerview.widget.RecyclerView;

import com.alexvit.cats.GlideApp;
import com.alexvit.cats.R;
import com.alexvit.cats.data.Image;
import com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions;

import java.util.Objects;

/**
 * Created by Aleksandrs Vitjukovs on 11/4/2017.
 */

class ImageListAdapter extends ListAdapter<Image, ImageListAdapter.ViewHolder> {

    private final OnImageClickListener listener;

    ImageListAdapter(OnImageClickListener listener) {
        super(new ImageDiff());
        this.listener = listener;
    }

    @Override
    @NonNull
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_thumbnail, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Image image = getItem(position);
        GlideApp.with(holder.itemView.getContext())
                .load(image.url())
                .placeholder(R.drawable.ic_image_24dp)
                .error(R.drawable.ic_image_24dp)
                .transition(DrawableTransitionOptions.withCrossFade())
                .into(holder.ivThumbnail);
        holder.ivThumbnail.setOnClickListener(__ -> listener.onImageClick(getItem(position),
                holder.ivThumbnail));
    }

    @FunctionalInterface
    interface OnImageClickListener {
        void onImageClick(Image image, ImageView shared);
    }

    static class ViewHolder extends RecyclerView.ViewHolder {
        private final ImageView ivThumbnail;

        ViewHolder(View itemView) {
            super(itemView);
            ivThumbnail = itemView.findViewById(R.id.iv_thumbnail);
        }
    }

    private static class ImageDiff extends DiffUtil.ItemCallback<Image> {
        @Override
        public boolean areItemsTheSame(@NonNull Image oldItem, @NonNull Image newItem) {
            return Objects.equals(oldItem.id(), newItem.id());
        }

        @Override
        public boolean areContentsTheSame(@NonNull Image oldItem, @NonNull Image newItem) {
            return Objects.equals(oldItem, newItem);
        }
    }
}
