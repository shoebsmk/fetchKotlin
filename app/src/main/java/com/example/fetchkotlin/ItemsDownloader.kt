package com.example.fetchkotlin

import android.net.Uri
import android.util.Log
import com.android.volley.Request
import com.android.volley.Response
import com.android.volley.VolleyError
import com.android.volley.toolbox.JsonArrayRequest
import com.android.volley.toolbox.Volley
import org.json.JSONArray
import org.json.JSONException

object ItemsDownloader {
    private const val ItemsURL = "https://fetch-hiring.s3.amazonaws.com/hiring.json"
    private const val TAG = "ItemsDownloader"
    private val itemArrayList = ArrayList<Item>()
    fun downloadItems(mainActivity: MainActivity) {
        val queue = Volley.newRequestQueue(mainActivity)
        val buildURL = Uri.parse(ItemsURL).buildUpon()
        val urlToUse = buildURL.build().toString()
        val listener = Response.Listener { response: JSONArray ->
            try {
                handleSuccess(response.toString(), mainActivity)
            } catch (e: Exception) {
                e.printStackTrace()
            }
        }
        val errorListener = Response.ErrorListener { obj: VolleyError? -> handleFail(obj) }
        val jsonArrayRequest =
            JsonArrayRequest(Request.Method.GET, urlToUse, null, listener, errorListener)
        queue.add(jsonArrayRequest)
    }

    private fun handleFail(volleyError: VolleyError?) {
        Log.d(TAG, "handleFail: FAILED TO DOWNLOAD")
    }

    @Throws(JSONException::class)
    private fun handleSuccess(responseString: String, mainActivity: MainActivity) {
        val responseArray = JSONArray(responseString)
        Log.d(TAG, "handleSuccess: Response Array: $responseArray")
        for (i in 0 until responseArray.length()) {
            val itemJson = responseArray.getJSONObject(i)
            if (itemJson.getString("name") != "null" && itemJson.getString("name").length > 0) {
                val item = Item(
                    itemJson.getString("id"),
                    itemJson.getString("listId"), itemJson.getString("name")
                )
                itemArrayList.add(item)
            }
        }
        mainActivity.runOnUiThread { mainActivity.acceptItems(itemArrayList) }
    }
}