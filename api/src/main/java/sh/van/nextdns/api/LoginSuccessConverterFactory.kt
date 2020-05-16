package sh.van.nextdns.api

import okhttp3.ResponseBody
import retrofit2.Converter
import retrofit2.Retrofit
import sh.van.nextdns.model.LoginResponse
import java.lang.reflect.Type


internal class LoginSuccessConverterFactory : Converter.Factory() {
    fun converterFactory() = this
    override fun responseBodyConverter(
        type: Type,
        annotations: Array<out Annotation>,
        retrofit: Retrofit
    ) = object : Converter<ResponseBody, Any?> {
        val nextResponseBodyConverter =
            retrofit.nextResponseBodyConverter<Any?>(converterFactory(), type, annotations)

        override fun convert(value: ResponseBody) =
            if (value.contentLength() <= 2L) LoginResponse(true) else nextResponseBodyConverter.convert(value)
    }
}
