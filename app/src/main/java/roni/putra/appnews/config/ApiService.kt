package roni.putra.appnews.config

import okhttp3.MultipartBody
import retrofit2.Call
import retrofit2.http.Multipart
import retrofit2.http.POST
import retrofit2.http.Part
import roni.putra.appnews.model.UploadModel

interface ApiService {

    @Multipart
    @POST("api/upload.php")
    fun uploadFile(@Part file: MultipartBody.Part): Call<UploadModel>
}