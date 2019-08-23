package com.sipapah.simpel.network;

import com.sipapah.simpel.models.ResponseBerita;

import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.Multipart;
import retrofit2.http.POST;
import retrofit2.http.Part;


public interface ApiServices {

    @GET("get.php")
    Call<ResponseBerita>requestBerita();

    /*@FormUrlEncoded
    @POST("login.php")
    Call<ResponseLogin> requestLogin(@Field("username") String username,
                                     @Field("password") String password);*/

    /*@FormUrlEncoded
    @POST("register.php")
    Call<ResponseLogin> requestRegister(@Field("fullname") String fullname,
                                        @Field("username") String username,
                                        @Field("password") String password);*/


    @Multipart
    @POST("insert.php")
    Call<ResponseBerita> postBerita(@Part("judul") String title,
                                    @Part("isi") String content,
                                    @Part MultipartBody.Part file,
                                    @Part("file") RequestBody name,
                                    @Part("id_user") int id_user);

    @FormUrlEncoded
    @POST("delete.php")
    Call<ResponseBerita> requestDelete(@Field("id_info") int id_berita);

    @Multipart
    @POST("update.php")
    Call<ResponseBerita> requestUpdateFoto(@Part("id_info") int id_berita,
                                           @Part("judul") String title,
                                           @Part("isi") String content,
                                           @Part MultipartBody.Part file,
                                           @Part("file") RequestBody name);

    @FormUrlEncoded
    @POST("update.php")
    Call<ResponseBerita> requestUpdate(@Field("id_info") String id_berita,
                                       @Field("judul") String title,
                                       @Field("isi") String content,
                                       @Field("flag") String flag);

//    @FormUrlEncoded
//    @POST("updateUser.php")
//    Call<ResponseBerita> requestChange(@Field("username") String username,
//                                       @Field("password") String password);

}
