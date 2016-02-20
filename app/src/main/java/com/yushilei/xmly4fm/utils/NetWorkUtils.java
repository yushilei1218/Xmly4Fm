package com.yushilei.xmly4fm.utils;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.TypeAdapter;
import com.google.gson.stream.JsonReader;
import com.google.gson.stream.JsonWriter;
import com.yushilei.xmly4fm.entities.AlbumDetailEntity;
import com.yushilei.xmly4fm.entities.Anchor.AnchorHomeEntity;
import com.yushilei.xmly4fm.entities.AutoSuggestEntity;
import com.yushilei.xmly4fm.entities.BulletCommentsEntity;
import com.yushilei.xmly4fm.entities.CategoryListEntity;
import com.yushilei.xmly4fm.entities.HomeEntity;
import com.yushilei.xmly4fm.entities.HotKeyWordEntity;
import com.yushilei.xmly4fm.entities.SuggestAllEntity;
import com.yushilei.xmly4fm.entities.SuggestRelationAlbumEntity;
import com.yushilei.xmly4fm.entities.SuggestRelationTrackEntity;
import com.yushilei.xmly4fm.entities.SuggestRelationUserEntity;
import com.yushilei.xmly4fm.entities.TrackEntity;
import com.yushilei.xmly4fm.entities.TrackPageEntity;
import com.yushilei.xmly4fm.entities.category.CategoryTagListEntity;
import com.yushilei.xmly4fm.entities.radio.RadioHomeEntity;
import com.yushilei.xmly4fm.entities.rank.RankHomeEntity;

import java.io.IOException;
import java.util.Date;

import retrofit.Call;
import retrofit.GsonConverterFactory;
import retrofit.Retrofit;
import retrofit.http.GET;
import retrofit.http.Path;
import retrofit.http.Query;

/**
 * Created by yushilei on 2016/1/22.
 */
public class NetWorkUtils {

    private static SuggestService suggestService;
    private static Service service;
    private static RadioService radioService;

    public interface Service {
        @GET("/mobile/discovery/v1/recommends?channel=and-d10&device=android&includeActivity=true&includeSpecial=true&scale=2&version=4.3.44.2")
        Call<HomeEntity> getHomeEntity();

        @GET("/mobile/others/ca/album/track/{albumId}/true/{page}/20?device=android")
        Call<AlbumDetailEntity> getAlbumDetailEntity(@Path("albumId") long albumId, @Path("page") int page);

        @GET("/v1/track/baseInfo?device=android")
        Call<TrackEntity> getTrackEntity(@Query("trackId") long trackId);

        @GET("/s/hot_search_key?device=android&size=9")
        Call<HotKeyWordEntity> getHotKeyWordEntity();

        @GET("/v1/track/ca/playpage/{trackId}?device=android")
        Call<TrackPageEntity> getTrackPageEntity(@Path("trackId") long trackId, @Query("albumId") long albumId, @Query("trackUid") long trackUid);

        @GET("/comment-mobile/bullet?device=android")
        Call<BulletCommentsEntity> getBulletCommentsEntity(@Query("second") int second, @Query("trackId") long trackId);

        @GET("/mobile/discovery/v1/categories?device=android&picVersion=11&scale=2")
        Call<CategoryListEntity> getCategoryListEntity();

        @GET("/mobile/discovery/v1/category/tagsWithoutCover")
        Call<CategoryTagListEntity> getCategoryTagListEntity(@Query("categoryId") int categoryId, @Query("contentType") String contentType, @Query("device") String device);

        @GET("/m/explore_user_index?device=android")
        Call<AnchorHomeEntity> getAnchorHomeEntity(@Query("page") int page);

        @GET("/mobile/discovery/v2/rankingList/group?channel=and-d10&device=android&includeActivity=true&includeSpecial=true&scale=2&version=4.3.44.2")
        Call<RankHomeEntity> getRankHomeEntity();
    }

    public interface SuggestService {
        @GET("/suggest?device=android")
        Call<AutoSuggestEntity> getAutoSuggestEntity(@Query("kw") String kw);

        @GET("/front/v1?device=android&core=all&page=1&rows=3&spellchecker=true")
        Call<SuggestAllEntity> getSuggestAllEntity(@Query("kw") String kw);

        //分类查找
        @GET("/front/v1?device=android&condition=relation&core=track&rows=20&spellchecker=true")
        Call<SuggestRelationTrackEntity> getSuggestRelationTrackEntity(@Query("kw") String kw, @Query("page") int page);

        @GET("/front/v1?device=android&condition=relation&core=user&rows=20&spellchecker=true")
        Call<SuggestRelationUserEntity> getSuggestRelationUserntity(@Query("kw") String kw, @Query("page") int page);

        @GET("/front/v1?device=android&condition=relation&core=album&rows=20&spellchecker=true")
        Call<SuggestRelationAlbumEntity> getSuggestRelationAlbumEntity(@Query("kw") String kw, @Query("page") int page);

    }

    public interface RadioService {
        @GET("/live-web/v1/getHomePageRadiosList?device=android")
        Call<RadioHomeEntity> getRadioHomeEntity();
    }


    static {
        Gson gson = new GsonBuilder().registerTypeAdapter(Date.class, new TypeAdapter<Date>() {
            @Override
            public void write(JsonWriter out, Date value) throws IOException {
                out.value(value.getTime());
            }

            @Override
            public Date read(JsonReader in) throws IOException {
                return new Date(in.nextLong());
            }
        }).create();
        service = new Retrofit.Builder().baseUrl("http://mobile.ximalaya.com")
                .addConverterFactory(GsonConverterFactory.create(gson)).build().create(Service.class);

        suggestService = new Retrofit.Builder().baseUrl("http://search.ximalaya.com")
                .addConverterFactory(GsonConverterFactory.create()).build().create(SuggestService.class);

        radioService = new Retrofit.Builder().baseUrl("http://live.ximalaya.com")
                .addConverterFactory(GsonConverterFactory.create()).build().create(RadioService.class);
    }

    public static Service getService() {
        return service;
    }

    public static SuggestService getSuggestService() {
        return suggestService;
    }

    public static RadioService getRadioService() {
        return radioService;
    }
}
