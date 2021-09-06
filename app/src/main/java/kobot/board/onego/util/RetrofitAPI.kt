package kobot.board.onego.util

import kobot.board.onego.R
import okhttp3.MultipartBody
import okhttp3.ResponseBody
import retrofit2.Call
import retrofit2.Response
import retrofit2.http.*

interface RetrofitAPI {

    @Multipart
    @POST((R.string.main_server_url).toString()+"/upload")
    fun post_Manuscript_Request(
        @Part("UID")UID: String,
        @Part imageFile : MultipartBody.Part) : Call<String>

    @Streaming
    @GET((R.string.main_server_url).toString()+"/download")
    suspend fun downloadFile(@Url fileUrl:String): Response<ResponseBody>
}
