package com.tti.wavehealth.views.comments;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.tti.wavehealth.R;
import com.tti.wavehealth.models.comment.CommentChildren;
import com.tti.wavehealth.utils.SpacesItemDecoration;

import java.util.ArrayList;
import java.util.List;

public class ListingCommentActivity extends AppCompatActivity {
    CommentViewModel commentViewModel;
    RecyclerViewAdapterComments recyclerViewAdapterComments;

    private RecyclerView recyclerViewComments;
    private Button loadMoreComments;
    private ProgressBar commentsProgressbar;
    private static int limit = 10;

    List<CommentChildren> mCommentChildren;
    private static String TAG = "ListingCommentActivity";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_listing_comment);

        //initialize views
        commentViewModel = new ViewModelProvider(this).get(CommentViewModel.class);
        recyclerViewComments = findViewById(R.id.commentsRecyclerView);
        loadMoreComments = findViewById(R.id.loadCommentsButton);
        commentsProgressbar = findViewById(R.id.commentsProgressBar);
        //initialize views

        commentViewModel.setUpNetworkRequest();
        mCommentChildren = new ArrayList<>();

        //Get parsed extras from Listing Activity
        Intent intent = getIntent();
        String subredditName = intent.getStringExtra("subredditName");
        String id = intent.getStringExtra("id");
        //Get parsed extras from Listing Activity

        commentViewModel.fetchComments(subredditName, id).observe(this, new Observer<List<CommentChildren>>() {
            @Override
            public void onChanged(List<CommentChildren> commentChildren) {
                mCommentChildren = commentChildren;

                if(commentChildren.size() == 0){
                    Log.i("","No comments");
                }

                //display 10 comments first
                if (commentChildren.size() > 20) {
                    setUpRecyclerview(commentChildren.subList(0, limit));
                } else {
                    setUpRecyclerview(commentChildren);
                    hideLoadCommentButton();
                }
            }
        });

        loadMoreComments();

    }


    private void setUpRecyclerview(List<CommentChildren> commentChildren) {
        recyclerViewAdapterComments = new RecyclerViewAdapterComments(commentChildren);

        if(recyclerViewAdapterComments.getItemCount() == 0){
            Toast.makeText(this, "No comments found", Toast.LENGTH_SHORT).show();

        }else if(recyclerViewAdapterComments.getItemCount() > 0){
            hideProgress();
        }else{
            showProgress();
        }

        recyclerViewComments.setAdapter(recyclerViewAdapterComments);
        recyclerViewComments.setLayoutManager(new LinearLayoutManager(this));
        recyclerViewComments.setHasFixedSize(true);
        recyclerViewComments.addItemDecoration(new SpacesItemDecoration(5));


        recyclerViewAdapterComments.notifyDataSetChanged();

    }

    private void loadMoreComments() {
        Log.i(TAG, mCommentChildren.size() + ": total num of comments");

        loadMoreComments.setOnClickListener(v -> {
            limit += 10;

            try {
                setUpRecyclerview(mCommentChildren.subList(0, limit));

                if (limit >= mCommentChildren.size()) {
                    loadMoreComments.setText(R.string.all_comments_loaded);
                }

            } catch (IndexOutOfBoundsException e) {
                Toast.makeText(ListingCommentActivity.this, R.string.all_comments_loaded, Toast.LENGTH_SHORT).show();
                loadMoreComments.setVisibility(View.GONE);
            }


            Log.i("Limit", limit + " " + recyclerViewAdapterComments.getItemCount());
        });
    }

    private void showProgress() {
        commentsProgressbar.setVisibility(View.VISIBLE);
    }

    private void hideProgress() {
        commentsProgressbar.setVisibility(View.INVISIBLE);
    }

    private void hideLoadCommentButton(){
        loadMoreComments.setVisibility(View.INVISIBLE);
    }
}