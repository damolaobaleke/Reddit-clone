package com.tti.wavehealth.views.comments;

import static com.tti.wavehealth.views.listings.ListingViewModel.BASE_URL;

import android.util.Log;

import androidx.annotation.NonNull;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.tti.wavehealth.models.comment.CommentChildren;
import com.tti.wavehealth.models.comment.SubredditCommentResp;
import com.tti.wavehealth.network.RedditApi;

import java.util.Arrays;
import java.util.List;
import java.util.concurrent.TimeUnit;

import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class CommentViewModel extends ViewModel {
    RedditApi redditApi;
    MutableLiveData<List<CommentChildren>> comments;

    private final static String TAG = "CommentViewModel";

    public CommentViewModel() {
        comments = new MutableLiveData<>();
    }


    public MutableLiveData<List<CommentChildren>> fetchComments(String subredditName, String id) {
        Call<List<SubredditCommentResp>> call = redditApi.getComments(subredditName, id);

        call.enqueue(new Callback<List<SubredditCommentResp>>() {
            @Override
            public void onResponse(@NonNull Call<List<SubredditCommentResp>> call, @NonNull Response<List<SubredditCommentResp>> response) {
                if(response.isSuccessful()){

                    assert response.body() != null;
                    Log.i(TAG, response.body().get(1).getData().getChildren().toString());

                    //Comments resides in position 1 from response
                    comments.setValue(response.body().get(1).getData().getChildren());
                }
            }

            @Override
            public void onFailure(@NonNull Call<List<SubredditCommentResp>> call, @NonNull Throwable t) {
                Log.i(TAG, t.getLocalizedMessage());
            }
        });
        return comments;

    }

    public void setUpNetworkRequest() {
        Gson gson = new GsonBuilder().serializeNulls().create();//to be able to see null value fields

        HttpLoggingInterceptor httpLoggingInterceptor = new HttpLoggingInterceptor();
        httpLoggingInterceptor.setLevel(HttpLoggingInterceptor.Level.BODY);

        OkHttpClient okHttpClient = new OkHttpClient().newBuilder()
                .addInterceptor(httpLoggingInterceptor)
                .connectTimeout(10000, TimeUnit.MILLISECONDS).build();

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(BASE_URL)
                .addConverterFactory(GsonConverterFactory.create(gson))
                .client(okHttpClient)
                .build();

        redditApi = retrofit.create(RedditApi.class);
    }
}
