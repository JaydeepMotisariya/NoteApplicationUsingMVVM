package com.riseup.noteapp.repository

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.riseup.noteapp.api.UserAPI
import com.riseup.noteapp.models.UserRequest
import com.riseup.noteapp.models.UserResponse
import com.riseup.noteapp.utils.Constants.TAG
import com.riseup.noteapp.utils.NetworkResult
import org.json.JSONObject
import retrofit2.Response
import javax.inject.Inject

class UserRepository @Inject constructor(private val userAPI: UserAPI) {

    private val _userResponseLiveData = MutableLiveData<NetworkResult<UserResponse>>()
    val userResponseLiveData: LiveData<NetworkResult<UserResponse>>
        get() = _userResponseLiveData

    suspend fun registerUser(userRequest: UserRequest) {
        _userResponseLiveData.postValue(NetworkResult.Loading())
        val response = userAPI.signUp(userRequest)
        //Log.d(TAG, response.body().toString())

        handleResponse(response)
    }

    suspend fun loginUser(userRequest: UserRequest) {
        val response = userAPI.signIn(userRequest)
        // Log.d(TAG, response.body().toString())
        handleResponse(response)
    }

    private fun handleResponse(response: Response<UserResponse>) {
        if (response.isSuccessful && response.body() != null) {
            _userResponseLiveData.postValue(NetworkResult.Success(response.body()!!))
        } else if (response.errorBody() != null) {
            val errorObj = JSONObject(response.errorBody()!!.charStream().readText())
            _userResponseLiveData.postValue(NetworkResult.Error(errorObj.getString("message")))
        } else {
            _userResponseLiveData.postValue(NetworkResult.Error("Something Went Wrong"))

        }
    }


}