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

        override fun convert(value: ResponseBody): Any? {
            // Response content will be "OK" on login success
            // We don't want to consume the response, so check contentLength instead of body
            // We also need to verify contentType because empty JSON list/object is also 2 chars
            return if (value.contentLength() == 2L && value.contentType()?.type() == "text") {
                LoginResponse(true)
            } else {
                nextResponseBodyConverter.convert(value)
            }
        }
    }
}
