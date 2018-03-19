package com.example.zern3w.linenotifysample

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import kotlinx.android.synthetic.main.activity_main.*
import com.afollestad.materialdialogs.MaterialDialog
import com.example.zern3w.linenotifysample.model.Response
import javax.security.auth.callback.Callback

class MainActivity : AppCompatActivity() {
    var msg = ""
    var pDialog: MaterialDialog? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        initListener()
    }

    private fun initListener() {
        btnSend.setOnClickListener({_ ->
            msg = etMesssage.text.toString()
            if (msg.trim().isEmpty()){
                Toast.makeText(applicationContext, "Please enter the message!", Toast.LENGTH_LONG).show()
            } else {
                showProgressDialog()
                notifyLine()
            }
        })
    }

    private fun notifyLine() {
        val apiService = ApiService.create()
        val call = apiService.notify(msg, getString(R.string.access_token))
        call.enqueue(object : retrofit2.Callback<Response> {
            override fun onResponse(call: retrofit2.Call<Response>, response: retrofit2.Response<Response>?) {
                if (response != null) {
                    dismissProgressDialog()
                    if (response.body()?.status == 200){
                        Toast.makeText(applicationContext, "Notify successfully :)", Toast.LENGTH_LONG).show()
                    } else {
                        Toast.makeText(applicationContext, "Notify failed :(", Toast.LENGTH_LONG).show()
                    }
                }
            }

            override fun onFailure(call: retrofit2.Call<Response>, t: Throwable) {
                dismissProgressDialog()
            }
        })
    }

    private fun showProgressDialog() {
        pDialog = MaterialDialog.Builder(this)
                .content("Please wait...")
                .progress(true, 0)
                .progressIndeterminateStyle(true)
                .show()
    }

    private fun dismissProgressDialog(){
        if (pDialog != null && pDialog!!.isShowing) {
            pDialog!!.dismiss()
        }
    }

}
