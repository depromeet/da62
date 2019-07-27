package com.da62.datasource.api

import com.da62.model.*
import io.reactivex.Observable
import com.da62.model.KakaoProfile
import com.da62.model.KakaoTalkProfile
import com.da62.model.Plant
import com.da62.model.User
import io.reactivex.Single
import okhttp3.MultipartBody
import okhttp3.RequestBody
import retrofit2.http.*

interface ApiService {

    @POST("/api/login")
    fun postLogin(@Body kakaoToken: String): Single<User>

    @POST("/api/plants/save")
    fun savePlant(
        @Header("Authorization") token: String,
        @Header("Content-Type") contentType: String = "application/json",
        @Body plantDto: SavePlant,
        @Query("userId") userId: Int
    ): Single<String>

    @POST("/api/getPlantNames")
    fun getPlantNames(
        @Header("Authorization") token: String,
        @Query("word") word: String
    ): Observable<List<String>>

    @FormUrlEncoded
    @POST("/api/plants")
    fun getPlants(
        @Header("Authorization") accessToken: String,
        @Field("userId") userId: Int,
        @Field("page") page: Int = 0
    ): Single<List<Plant>>

    @POST("/api/plants/{id}/detail")
    fun getDetail(
        @Header("Authorization") accessToken: String,
        @Path("id") id: Int
    ): Single<Plant>

    @Multipart
    @POST("/api/uploadImage")
    fun uploadImage(
        @Header("Authorization") accessToken: String,
        @Part image: MultipartBody.Part,
        @PartMap params: Map<String, @JvmSuppressWildcards RequestBody>
    ): Single<Message>

    @POST("/api/plants/{id}/love")
    fun postLove(
        @Header("Authorization") accessToken: String,
        @Path("id") id: Int
    ): Single<Plant>

    @GET("/api/user/{id}/resisterToken")
    fun postDeviceToken(
        @Header("Authorization") accessToken: String,
        @Path("id") id: Int,
        @Query("deviceToken") deviceToken: String
    ): Single<Response<Any>>

    @POST("api/plants/{id}/delete")
    fun deletePlant(
        @Header("Authorization") accessToken: String,
        @Path("id") id: Int,
        @Query("userId") userId: Int
    ): Single<String>

    @GET("api/getImage")
    fun getPlantImageList(
        @Header("Authorization") accessToken: String,
        @Query("plantId") plantId: Int,
        @Query("userId") userId: Int
    ): Single<List<PlantImage>>
}

interface KakaoApiService {

    @GET("/v1/api/talk/profile")
    fun getKakaoTalkProfile(
        @Header("Authorization") accessToken: String
    ): Single<KakaoTalkProfile>

    @POST("/v2/user/me")
    fun postKakaoProfile(
        @Header("Authorization") accessToken: String
    ): Single<KakaoProfile>
}