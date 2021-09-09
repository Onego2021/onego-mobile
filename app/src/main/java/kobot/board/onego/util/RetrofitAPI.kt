package kobot.board.onego.util

import okhttp3.MultipartBody
import okhttp3.RequestBody
import okhttp3.ResponseBody
import retrofit2.Call
import retrofit2.Response
import retrofit2.http.*
import java.util.*
import kotlin.collections.HashMap

interface RetrofitAPI {

    @Multipart
    @POST("/aws/upload_img/")
    fun post_Manuscript_Request(
        @Field("") uid: RequestBody,
        @Part imageFile: MultipartBody.Part?): Call<HashMap<String, Object>>

    @Streaming
    @GET("/download_txt")
    suspend fun downloadFile(@Query("url") fileUrl:String): Call<String>
}
//data class ResponseDC(var result:Stirng? = null)
//interface RestrofitApi{
//    @GET("/")
//    fun getRequest(@Query("name")name:string):Call<ResponseDC>
//
//    @FormUrlEncoded
//    @POST("/")
//    fun postRequest(@Field("id")id:String,
//                    @Field("password")password:String):Call<ResponseDC>
//}
