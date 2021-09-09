package kobot.board.onego.util

import kobot.board.onego.R
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit

class NetworkClient {
    private lateinit var retrofit : Retrofit
    private val MAIN_BASE_URL = R.string.main_server_url.toString()
    private val MODEL_BASE_URL = R.string.model_server_url.toString()

    public fun getRetrofit(url : String) : Retrofit{
        val interceptor = HttpLoggingInterceptor()
        interceptor.setLevel(HttpLoggingInterceptor.Level.BODY)
        val CONNECT_TIMEOUT_SEC= 20000L
        var okHttpClient = OkHttpClient.Builder().addInterceptor(interceptor) .connectTimeout(CONNECT_TIMEOUT_SEC, TimeUnit.SECONDS) .build()
        if(retrofit == null){
            if(url == "MAIN_SERVER_URL"){
                retrofit = Retrofit.Builder().baseUrl(MAIN_BASE_URL).addConverterFactory(GsonConverterFactory.create()).client(okHttpClient).build()
            }
            else if(url == "MODEL_SERVER_URL"){
                retrofit = Retrofit.Builder().baseUrl(MODEL_BASE_URL).addConverterFactory(GsonConverterFactory.create()).client(okHttpClient).build()
            }
        }
        return retrofit
    }
}