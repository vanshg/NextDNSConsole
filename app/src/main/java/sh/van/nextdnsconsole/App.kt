package sh.van.nextdnsconsole

import android.app.Application
import androidx.security.crypto.EncryptedSharedPreferences
import androidx.security.crypto.MasterKeys
import com.franmontiel.persistentcookiejar.PersistentCookieJar
import com.franmontiel.persistentcookiejar.cache.SetCookieCache
import com.franmontiel.persistentcookiejar.persistence.SharedPrefsCookiePersistor
import com.readystatesoftware.chuck.ChuckInterceptor
import okhttp3.CookieJar
import okhttp3.OkHttpClient
import sh.van.nextdns.api.NextDNSService
import timber.log.Timber

class App : Application() {
    lateinit var cookieJar: CookieJar
    lateinit var service: NextDNSService

    override fun onCreate() {
        super.onCreate()
        if (BuildConfig.DEBUG) Timber.plant(Timber.DebugTree())
        instance = this
        cookieJar = PersistentCookieJar(
            SetCookieCache(),
            SharedPrefsCookiePersistor(EncryptedSharedPreferences.create(
                "secret_shared_prefs",
                MasterKeys.getOrCreate(MasterKeys.AES256_GCM_SPEC),
                this,
                EncryptedSharedPreferences.PrefKeyEncryptionScheme.AES256_SIV,
                EncryptedSharedPreferences.PrefValueEncryptionScheme.AES256_GCM
            )))
        service = NextDNSService.get(cookieJar,
            OkHttpClient.Builder().addInterceptor(ChuckInterceptor(this)))
    }

    companion object {
        lateinit var instance: App
    }
}
