package com.tti.wavehealth.views.listings;

import android.util.Log;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.tti.wavehealth.models.listing.Children;
import com.tti.wavehealth.models.listing.RedditRespObject;
import com.tti.wavehealth.network.RedditApi;

import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.TimeUnit;

import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class ListingViewModel extends ViewModel {
    RedditApi redditApi;
    MutableLiveData<List<Children>> listing;
    HashMap<String, Object> query;

    public static final String BASE_URL = "https://www.reddit.com";
    private static final String TAG = "ListingViewModel";
    public String afterPage,beforePage ="";
    int responseValue = 0;

    public ListingViewModel() {
        listing = new MutableLiveData<>();
    }


   /**limiting each response to get 10 subreddits, maximum number of items to return in this slice of the listing
    * response contains an "after" passed in the next request to get next page.*/
    public MutableLiveData<List<Children>> fetchListings(String nextPage, String prevPage, int limit) {
        query = new HashMap<>();
        query.put("limit", limit); //listing per page
        query.put("after", nextPage);
        query.put("before", prevPage);

        Call<RedditRespObject> call = redditApi.getListing(query);

        call.enqueue(new Callback<RedditRespObject>() {
            @Override
            public void onResponse(Call<RedditRespObject> call, Response<RedditRespObject> response) {
                if (response.isSuccessful()) {
                    assert response.body() != null;

                    Log.i(TAG, Collections.singletonList(response.body().getData().getChildren()).toString());

                    listing.setValue(response.body().getData().getChildren());

                    afterPage = response.body().getData().getAfter();
                    beforePage = response.body().getData().getBefore();

                    responseValue = 1;
                }
            }

            @Override
            public void onFailure(Call<RedditRespObject> call, Throwable t) {
                Log.i(TAG, t.getLocalizedMessage());
                responseValue = 0;
            }
        });

        return listing;
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

    public String getAfterPage(){
        return  afterPage;
    }

    public String getBeforePage() {
        return beforePage;
    }
}
