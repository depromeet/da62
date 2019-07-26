package com.da62.usecase

import com.da62.datasource.local.PreferenceStorage
import com.da62.model.Plant
import com.da62.model.PlantImageRequest
import com.da62.model.Response
import com.da62.repository.GalleryRepository
import com.da62.util.ImageUtil
import com.da62.util.ResourceProvider
import com.da62.util.toDateString
import io.reactivex.Single
import io.reactivex.schedulers.Schedulers
import okhttp3.MediaType
import okhttp3.MultipartBody
import okhttp3.RequestBody
import java.io.File

interface GalleryUseCase {
    fun uploadImage(
        request: PlantImageRequest
    ): Single<Response<Plant>>
}

class GalleryUseCaseImpl(
    private val repository: GalleryRepository,
    private val preferenceStorage: PreferenceStorage,
    private val resourceProvider: ResourceProvider
) : GalleryUseCase {

    override fun uploadImage(
        request: PlantImageRequest
    ): Single<Response<Plant>> {
        return repository.uploadImage(
            multiPartImageMapper(request.image),
            requestBodyMapper(request, preferenceStorage.userId)
        ).subscribeOn(Schedulers.io())
    }

    private fun multiPartImageMapper(imagePath: String): MultipartBody.Part {
        val file = File(imagePath)
        val requestBody = RequestBody.create(
            MediaType.parse("multipart/form-data"),
            ImageUtil(resourceProvider).bitmapTransform(file)
        )

        return MultipartBody.Part.createFormData("image", file.name, requestBody)
    }

    private fun requestBodyMapper(
        imageRequest: PlantImageRequest,
        userId: Int
    ): Map<String, RequestBody> {
        return mapOf(
            "date" to toRequestBody(imageRequest.date.toDateString() ?: ""),
            "tag" to toRequestBody(imageRequest.tag),
            "plantId" to toRequestBody(imageRequest.plantId.toString()),
            "userId" to toRequestBody(userId.toString())
        )
    }

    private fun toRequestBody(value: String): RequestBody {
        return RequestBody.create(MediaType.parse("text/plain"), value)
    }


}