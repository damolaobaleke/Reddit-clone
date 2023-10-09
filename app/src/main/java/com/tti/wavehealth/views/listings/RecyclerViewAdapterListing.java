package com.tti.wavehealth.views.listings;

import android.content.Context;
import android.net.Uri;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.RecyclerView;

import com.squareup.picasso.Picasso;
import com.tti.wavehealth.R;
import com.tti.wavehealth.models.listing.Children;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class RecyclerViewAdapterListing extends RecyclerView.Adapter<RecyclerViewAdapterListing.ListingViewHolder> {
    List<Children> listings; //children in api
    OnItemClickListener mListener;
    Context context;


    public RecyclerViewAdapterListing(List<Children> listings, Context context) {
        this.listings = listings;
        this.context = context;
    }

    @NonNull
    @Override
    public ListingViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.custom_listing_card, parent, false);
        return new ListingViewHolder(view, mListener);
    }

    @Override
    public void onBindViewHolder(@NonNull ListingViewHolder holder, int position) {
        Children listing = listings.get(position);

        //Log.i("Listings", listing.getData().toString());
        Log.i("Listing Titles", listing.getData().get("title").toString()+"\n");

        if(listing.getData() != null) {
            holder.title.setText(String.valueOf(listing.getData().get("title")));
            holder.namePrefix.setText(String.valueOf(listing.getData().get("subreddit_name_prefixed")));

            if(!listing.getData().containsKey("thumbnail") || listing.getData().get("thumbnail") == ""){
                holder.thumbNail.setImageDrawable(ContextCompat.getDrawable(context, R.drawable.reddit_logo));
            }else {
                Uri thumbnail = Uri.parse(String.valueOf(listing.getData().get("thumbnail")));
                Picasso.get().load(thumbnail).into(holder.thumbNail);
            }
        }
    }

    @Override
    public int getItemCount() {
        return listings.size();
    }

    /**
     * Click events and Listeners
     */
    interface OnItemClickListener {
        void onItemClick(int position);
    }

    public void setOnItemClickListener(OnItemClickListener listener) {
        mListener = listener;
    }

    /**
     * Click events and Listeners
     */

    static class ListingViewHolder extends RecyclerView.ViewHolder {
        ImageView thumbNail;
        TextView title;
        TextView namePrefix;


        public ListingViewHolder(@NonNull View itemView, OnItemClickListener listener) {
            super(itemView);

            thumbNail = itemView.findViewById(R.id.imageThumbNail);
            title = itemView.findViewById(R.id.title);
            namePrefix = itemView.findViewById(R.id.subredditNamePrefix);

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (listener != null) {
                        if (getAdapterPosition() != RecyclerView.NO_POSITION) {
                            listener.onItemClick(getAdapterPosition());
                        }
                    }
                }
            });
        }
    }
}
