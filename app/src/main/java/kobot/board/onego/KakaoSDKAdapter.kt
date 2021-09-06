package kobot.board.onego

import com.kakao.auth.ApprovalType
import com.kakao.auth.AuthType
import com.kakao.auth.IApplicationConfig
import com.kakao.auth.ISessionConfig
import com.kakao.auth.KakaoAdapter

class KakaoSDKAdapter : KakaoAdapter() {
    override fun getSessionConfig(): ISessionConfig {
        return object: ISessionConfig {
            override fun isSaveFormData(): Boolean {
                return true
            }

            override fun getAuthTypes(): Array<AuthType> {
                return arrayOf(AuthType.KAKAO_TALK_ONLY)
            }

            override fun isSecureMode(): Boolean {
                return false
            }

            override fun getApprovalType(): ApprovalType? {
                return ApprovalType.INDIVIDUAL
            }

            override fun isUsingWebviewTimer(): Boolean {
                return false
            }

        }
    }

    //앱이 가진 정보를 얻기 위한 인터페이스
    override fun getApplicationConfig(): IApplicationConfig {
        return IApplicationConfig {
            GlobalApplication.instance?.getGlobalApplicationContext()
        }
    }
}
