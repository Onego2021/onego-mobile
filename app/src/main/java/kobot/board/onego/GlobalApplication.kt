package kobot.board.onego
import android.app.Application
import com.kakao.auth.KakaoSDK
import com.kakao.sdk.common.KakaoSdk

class GlobalApplication : Application() {
    override fun onCreate() {
        super.onCreate()
        KakaoSdk.init(this, "e1b0edbf0130606d5308e1c69bb0d871")
        instance = this
    }

    override fun onTerminate() {
        super.onTerminate()
        instance = null
    }

    fun getGlobalApplicationContext() : GlobalApplication {
        checkNotNull(instance)
        return instance!!
    }

    companion object{
        var instance: GlobalApplication? = null
    }
}