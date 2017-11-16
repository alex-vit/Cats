package com.alexvit.cats.ui.list;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.alexvit.cats.R;
import com.alexvit.cats.data.model.Image;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Aleksandrs Vitjukovs on 11/4/2017.
 */

class ListAdapter extends RecyclerView.Adapter<ListAdapter.ViewHolder> {

    private List<Image> images = new ArrayList<>();
    private final OnItemClickListener listener;

    ListAdapter(OnItemClickListener listener) {
        this.listener = listener;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_thumbnail, parent, false);

        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        Image image = getItem(position);

        Picasso.with(holder.itemView.getContext())
                .load(image.url)
                .placeholder(R.drawable.ic_image_24dp)
                .error(R.drawable.ic_image_24dp)
                .into(holder.ivThumbnail);

        holder.ivThumbnail.setOnClickListener(__ -> listener.onItemClicked(getItem(position),
                holder.ivThumbnail));
    }

    @Override
    public int getItemCount() {
        return (images == null) ? 0 : images.size();
    }

    void setImages(List<Image> images) {
        this.images.clear();
        this.images.addAll(images);
        notifyDataSetChanged();
    }

    private Image getItem(int position) {
        return images.get(position);
    }

    static class ViewHolder extends RecyclerView.ViewHolder {

        private ImageView ivThumbnail;

        ViewHolder(View itemView) {
            super(itemView);

            ivThumbnail = itemView.findViewById(R.id.iv_thumbnail);
        }

    }

    interface OnItemClickListener {
        void onItemClicked(Image image, ImageView shared);
    }
}
