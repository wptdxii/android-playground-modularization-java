package com.wptdxii.playground.gank.api;

import com.wptdxii.playground.gank.model.BaseGankResponse;
import com.wptdxii.playground.gank.model.GankModel;

import java.util.List;

import io.reactivex.Observable;
import retrofit2.http.GET;
import retrofit2.http.Path;

/**
 * Created by wptdxii on 2016/8/1 0001.
 * 当需要通过RetrofitClient.getInstance().createMultiApi()
 * 设置不同的baseUrl时，一定要定义"BASE_URL"字段
 */
public interface GankApi {

    @GET("api/data/福利/{count}/{page}")
    Observable<BaseGankResponse<List<GankModel>>> getGankList(@Path("count") int count,
                                                              @Path("page") int page);
}