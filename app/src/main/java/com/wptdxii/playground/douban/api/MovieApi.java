package com.wptdxii.playground.douban.api;

import com.wptdxii.playground.douban.model.BaseMovieResponse;
import com.wptdxii.playground.douban.model.MovieModel;

import java.util.List;

import io.reactivex.Observable;
import retrofit2.http.GET;
import retrofit2.http.Query;

/**
 * Created by wptdxii on 2016/8/23 0023.
 * 当需要通过RetrofitClient.getInstance().createMultiApi()
 * 设置不同的baseUrl时，一定要定义"BASE_URL"字段
 */
public interface MovieApi {

    @GET("top250")
    Observable<BaseMovieResponse<List<MovieModel>>> getMove(@Query("start") int start,
                                                            @Query("count") int count);
}
