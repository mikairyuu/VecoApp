package com.veco.vecoapp.data.repository

import com.veco.vecoapp.di.di
import com.veco.vecoapp.domain.entity.Material
import com.veco.vecoapp.domain.entity.enums.ResponseResult
import com.veco.vecoapp.domain.entity.response.MaterialPageResponse
import com.veco.vecoapp.domain.entity.response.Response
import com.veco.vecoapp.domain.repository.IMaterialRepository
import com.veco.vecoapp.utils.safeRequest
import io.ktor.client.HttpClient
import io.ktor.http.HttpMethod
import io.ktor.http.appendPathSegments
import io.realm.kotlin.Realm
import io.realm.kotlin.UpdatePolicy
import io.realm.kotlin.ext.query
import org.kodein.di.instance

class MaterialRepository : IMaterialRepository {
    private val httpClient: HttpClient by di.instance()
    private val materialRealm: Realm by di.instance(tag = "matRealm")

    override suspend fun getMaterialPage(
        page: Int,
        pageSize: Int,
        fromCache: Boolean
    ): Response<MaterialPageResponse> {
        val response: Response<MaterialPageResponse>
        if (fromCache) {
            val query = materialRealm.query<Material>().find()
            response = Response(
                ResponseResult.Success,
                MaterialPageResponse(
                    query.subList(pageSize * page, pageSize * (page + 1)),
                    query.size > pageSize * (page + 1)
                )
            )
        } else {
            response = httpClient.safeRequest("materials/pages") {
                url {
                    appendPathSegments("$page", "$pageSize")
                }
                method = HttpMethod.Get
            }
            if (response.resultCode == ResponseResult.Success) {
                materialRealm.write {
                    response.data?.data?.forEach {
                        copyToRealm(it, UpdatePolicy.ALL)
                    }
                }
            }
        }
        return response
    }

    override suspend fun getMaterial(id: Int, fromCache: Boolean): Response<Material> {
        val response: Response<Material>
        if (fromCache) {
            response = Response(
                ResponseResult.Success,
                materialRealm.query<Material>("id == $0", id).first().find()
            )
        } else {
            response = httpClient.safeRequest("materials") {
                url {
                    appendPathSegments("$id")
                }
                method = HttpMethod.Get
            }
        }
        return response
    }
}
