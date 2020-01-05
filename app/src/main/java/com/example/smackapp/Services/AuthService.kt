package com.example.smackapp.Services

import android.content.Context
import android.util.Log
import com.android.volley.Request
import com.android.volley.Response
import com.android.volley.VolleyError
import com.android.volley.toolbox.JsonObjectRequest
import com.android.volley.toolbox.StringRequest
import com.android.volley.toolbox.Volley
import com.example.smackapp.Utilities.URL_AUTH_LOGIN
import com.example.smackapp.Utilities.URL_REGISTER
import org.json.JSONException
import org.json.JSONObject

object AuthService {

    var isLoggedIn = false
    var userEmail = ""
    var authToken = ""



    fun registerUser(context: Context, email: String, password: String, complete:(Boolean) -> Unit ){



        val jsonBody = JSONObject()
        jsonBody.put("email", email)
        jsonBody.put("password",password)
        val requestBody = jsonBody.toString()


        val registerRequest = object : StringRequest(Method.POST, URL_REGISTER, Response.Listener {response ->

            println(response)
            complete(true)

        }, Response.ErrorListener {error: VolleyError? ->

            Log.d("error", "Unable to register user: $error")
            complete(false)

        }) {

            override fun getBodyContentType(): String {
                return "application/json; charset=utf-8"
            }

            override fun getBody(): ByteArray {
                return requestBody.toByteArray()
            }

        }



        Volley.newRequestQueue(context).add(registerRequest)



    }


    fun loginUser(context: Context, email: String, password: String, complete: (Boolean) -> Unit){


        val jsonBody = JSONObject()
        jsonBody.put("email", email)
        jsonBody.put("password",password)
        val requestBody = jsonBody.toString()


        val loginRequest = object: JsonObjectRequest(Method.POST, URL_AUTH_LOGIN,null,Response.Listener {response ->

               //parse JSON object

            println(response)

            try {
                authToken = response.getString("token")
                userEmail = response.getString("user")
                isLoggedIn = true
                complete(true)

            }catch (e: JSONException){

                Log.d("errorJSON:", "EXE:" + e.localizedMessage)
                complete(false)
            }




        }, Response.ErrorListener {error: VolleyError? ->

            Log.d("error", "Unable to register user: $error")
            complete(false)


        }){

            override fun getBodyContentType(): String {
                return "application/json; charset=utf-8"
            }

            override fun getBody(): ByteArray {
                return requestBody.toByteArray()
            }


        }


       Volley.newRequestQueue(context).add(loginRequest)




    }


}