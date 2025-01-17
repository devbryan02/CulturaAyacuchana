package com.amver.cultura_ayacucho.features.login.viewmodel

import android.app.Application
import android.content.Context
import android.util.Log
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import com.amver.cultura_ayacucho.data.api.ApiLogin
import com.amver.cultura_ayacucho.data.api.RetrofitServiceFactoryUserEx
import com.amver.cultura_ayacucho.data.model.login.LoginRequestUser
import com.amver.cultura_ayacucho.data.model.login.LoginResponseUser
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class LoginViewModel(application: Application): AndroidViewModel(application) {
    private val apiLogin: ApiLogin = RetrofitServiceFactoryUserEx.makeRetrofitService()

    //Aqui se crea un MutableStateFlow que se encargara de manejar el estado de la peticion de login
    private val _loginState = MutableStateFlow<Result<LoginResponseUser>?>(null)
    val loginState: StateFlow<Result<LoginResponseUser>?> = _loginState

    //Funcion que se encarga de hacer la peticion de login

    fun loginUser(username:String, password:String){
        //el viewModelScope es un scope que se encarga de manejar las corrutinas en el ciclo de vida de la vista
        viewModelScope.launch {
            try {
                val logiRequest = LoginRequestUser(
                    username = username,
                    password = password
                )
                //Se hace la peticion de login
                val response = apiLogin.loginUserApi(logiRequest)
                //Se asigna el resultado de la peticion al _loginState
                _loginState.value = Result.success(response)
                Log.d("LoginViewModel", "User logged in successfully: $response")

                saveTokenToPreferences(response.token)

                Log.d("LoginViewModel", "Token saved successfully: ${response.token}")

            }catch (e:Exception){
                _loginState.value = Result.failure(e)
                Log.e("LoginViewModel", "Login failed: ${e.message}")
            }
        }
    }

    private fun saveTokenToPreferences(toke:String){
        //Se guarda el token en las preferencias compartidas
        val sharedPreferences = getApplication<Application>().getSharedPreferences("UserPrefs",Context.MODE_PRIVATE)
        //Se obtiene un editor para modificar las preferencias compartidas
        val editor = sharedPreferences.edit()
        editor.putString("token",toke)//Se guarda el token
        editor.apply()//Se aplica el cambio
    }
}