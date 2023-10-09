package com.tti.wavehealth.network;

import com.tti.wavehealth.models.comment.SubredditCommentResp;
import com.tti.wavehealth.models.listing.RedditRespObject;

import java.util.HashMap;
import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;
import retrofit2.http.QueryMap;

public interface RedditApi {

    @GET("/r/all/hot.json")
    Call<RedditRespObject> getListing(@QueryMap HashMap<String, Object> queryMap);

    @GET("/r/{subreddit}/comments/{id}.json")
    Call<List<SubredditCommentResp>> getComments(@Path("subreddit") String subredditName, @Path("id") String subredditId);
}
