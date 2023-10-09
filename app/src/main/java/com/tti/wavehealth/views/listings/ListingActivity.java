package com.tti.wavehealth.views.listings;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.Toast;

import com.tti.wavehealth.R;
import com.tti.wavehealth.models.listing.Children;
import com.tti.wavehealth.utils.*;
import com.tti.wavehealth.views.comments.ListingCommentActivity;

import java.util.List;

public class ListingActivity extends AppCompatActivity {
    private ListingViewModel listingViewModel;
    private RecyclerView listingRecyclerView;
    RecyclerViewAdapterListing recyclerViewAdapterListing;
    private TextView emptyListings;
    private ScrollView listingScrollView;
    private ProgressBar progressBar;

    private static final String TAG = "ListingActivity";
    StringBuilder builder;
    int mScrollY;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_listing);

        //initialize view model
        listingViewModel = new ViewModelProvider(this).get(ListingViewModel.class);
        builder = new StringBuilder();

        //initialize views
        listingRecyclerView = findViewById(R.id.listingRecyclerView);
        emptyListings = findViewById(R.id.emptyListingsText);
        listingScrollView = findViewById(R.id.listingsScrollView);
        progressBar = findViewById(R.id.progressBar);
        //initialize views

        listingViewModel.setUpNetworkRequest();
        getListings("","");

        //on Scroll change when end of scroll is reached, pass the after value in the query, to get next page
        listingScrollView.setOnScrollChangeListener(new View.OnScrollChangeListener() {
            @Override
            public void onScrollChange(View v, int scrollX, int scrollY, int oldScrollX, int oldScrollY) {
                mScrollY = scrollY;

                //reached end of scroll view, last item position
                if (isScrollEnd()) {
                    //show progress bar
                    showProgress();
                    //make request with "after" for next page
                    getListings(listingViewModel.getAfterPage(), "");
                }

                //reached top of scroll go to previous page, <==before is always null as seen in request==>
                if (scrollY == 0 && listingViewModel.getBeforePage() != null) {
                    getListings("", listingViewModel.getBeforePage());
                }

                //Note: response/internet speed is so fast, that progress bar never shows.
                //on Successful response
                if (listingViewModel.responseValue == 1) {
                    hideProgress();
                } else {
                    showProgress();
                }
            }
        });

    }

    private void getListings(String nextPage, String prevPage) {
        listingViewModel.fetchListings(nextPage, prevPage, 10).observe(this, new Observer<List<Children>>() {
            @Override
            public void onChanged(List<Children> children) {
                setUpRecyclerView(children);
            }
        });

    }

    private void setUpRecyclerView(List<Children> children) {
        recyclerViewAdapterListing = new RecyclerViewAdapterListing(children, this);

        if (recyclerViewAdapterListing.getItemCount() > 0) {
            emptyListings.setVisibility(View.INVISIBLE);
        } else {
            emptyListings.setVisibility(View.VISIBLE);
        }

        recyclerViewAdapterListing.setOnItemClickListener(new RecyclerViewAdapterListing.OnItemClickListener() {
            @Override
            public void onItemClick(int position) {
                //Go to comment activity==> GET Request to comments with subreddit name and id
                Children listing = children.get(position);

                Log.i(TAG, listing.getData().get("title").toString());

                startCommentActivity(listing.getData().get("subreddit").toString(), listing.getData().get("id").toString());
            }
        });

        listingRecyclerView.setHasFixedSize(true);
        listingRecyclerView.setAdapter(recyclerViewAdapterListing);
        listingRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        //listingRecyclerView.addItemDecoration(new SpacesItemDecoration(8));

        recyclerViewAdapterListing.notifyDataSetChanged();

    }

    private void startCommentActivity(String subredditName, String subredditId) {
        Intent intent = new Intent(this, ListingCommentActivity.class);
        intent.putExtra("subredditName", subredditName);
        intent.putExtra("id", subredditId);
        startActivity(intent);
    }

    private void showProgress() {
        progressBar.setVisibility(View.VISIBLE);
    }

    private void hideProgress() {
        progressBar.setVisibility(View.INVISIBLE);
    }

    private boolean isScrollEnd() {
        builder.append("ScrollY::")
                .append(mScrollY).append(" ")
                .append(listingScrollView.getChildAt(0).getMeasuredHeight()).append(" ")
                .append(listingScrollView.getMeasuredHeight());

        //Log.i(TAG, builder.toString());

       if(mScrollY == listingScrollView.getChildAt(0).getMeasuredHeight() - listingScrollView.getMeasuredHeight()){
           return true;
       }
       return false;
    }

    private void notification(String message) {
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        Intent intent = new Intent(Intent.ACTION_MAIN);
        intent.addCategory(Intent.CATEGORY_HOME);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        startActivity(intent);
    }
}