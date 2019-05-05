package com.savedroid.wish

import android.annotation.SuppressLint
import android.app.Activity
import android.app.AlertDialog
import android.content.Context
import android.net.ConnectivityManager
import android.view.View
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.savedroid.wish.database.entities.Wish


val Context.networkStatus
    @SuppressLint("MissingPermission")
    get() :Boolean {
        val connectivityManager = getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
        val networkInfo = connectivityManager.activeNetworkInfo

        return networkInfo != null && networkInfo.isConnected
    }

fun View.gone() {
    visibility = View.GONE
}

fun View.visible() {
    visibility = View.VISIBLE
}

fun <T : Any?> MutableLiveData<T>.default(initialValue: T) = apply { setValue(initialValue) }

private fun noInternetDialog(activity: Activity) {
    try {
        val builder = AlertDialog.Builder(activity)
        builder.setTitle("No Internet Connection")
        builder.setMessage("You need to have Mobile Data or wifi to access this. Press ok to Exit")
        builder.setPositiveButton("Ok") { dialog, _ -> dialog.dismiss() }
        builder.show()
    } catch (e: Exception) {
        e.printStackTrace()
    }


}

interface DataCallBack {
    fun onSuccess(wishes: List<Wish>)
}




