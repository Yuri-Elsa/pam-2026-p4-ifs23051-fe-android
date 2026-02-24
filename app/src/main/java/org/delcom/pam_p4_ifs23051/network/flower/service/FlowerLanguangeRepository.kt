package org.delcom.pam_p4_ifs23051.network.flower.service

import okhttp3.MultipartBody
import okhttp3.RequestBody
import org.delcom.pam_p4_ifs23051.helper.SuspendHelper
import org.delcom.pam_p4_ifs23051.network.data.ResponseMessage
import org.delcom.pam_p4_ifs23051.network.flower.data.ResponseFlowerLanguage
import org.delcom.pam_p4_ifs23051.network.flower.data.ResponseFlowerLanguageAdd
import org.delcom.pam_p4_ifs23051.network.flower.data.ResponseFlowerLanguages

class FlowerLanguageRepository(
    private val api: FlowerLanguageApiService,
) : IFlowerLanguageRepository {

    override suspend fun getAllFlowers(search: String?): ResponseMessage<ResponseFlowerLanguages?> =
        SuspendHelper.safeApiCall { api.getAllFlowers(search) }

    override suspend fun postFlower(
        namaUmum: RequestBody, namaLatin: RequestBody,
        makna: RequestBody, asalBudaya: RequestBody,
        deskripsi: RequestBody, file: MultipartBody.Part,
    ): ResponseMessage<ResponseFlowerLanguageAdd?> =
        SuspendHelper.safeApiCall {
            api.postFlower(namaUmum, namaLatin, makna, asalBudaya, deskripsi, file)
        }

    override suspend fun getFlowerById(flowerId: String): ResponseMessage<ResponseFlowerLanguage?> =
        SuspendHelper.safeApiCall { api.getFlowerById(flowerId) }

    override suspend fun putFlower(
        flowerId: String, namaUmum: RequestBody, namaLatin: RequestBody,
        makna: RequestBody, asalBudaya: RequestBody,
        deskripsi: RequestBody, file: MultipartBody.Part?,
    ): ResponseMessage<String?> =
        SuspendHelper.safeApiCall {
            api.putFlower(flowerId, namaUmum, namaLatin, makna, asalBudaya, deskripsi, file)
        }

    override suspend fun deleteFlower(flowerId: String): ResponseMessage<String?> =
        SuspendHelper.safeApiCall { api.deleteFlower(flowerId) }
}