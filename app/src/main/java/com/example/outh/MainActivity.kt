package com.example.outh

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.appcompat.app.AlertDialog
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import org.json.JSONObject
import java.lang.Exception

class MainActivity : AppCompatActivity() {
        private lateinit var app:MyApp
        private lateinit var bankView:RecyclerView
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        app = applicationContext as MyApp
        bankView = findViewById(R.id.bankView)
        bankView.layoutManager = LinearLayoutManager(this,LinearLayoutManager.VERTICAL, false)
        val bankAdapter = BankAdapter(app.bankList, this)
        bankView.adapter = bankAdapter
        getBanks()

    }
    fun getBanks(){
        HTTP.requestGET("http://192.168.0.3:8080/Bankomats",
            mapOf(
                "token" to app.token
            )
        ){result, error, code ->
            runOnUiThread {
                if (result!=null){
                   try{
                       val json = JSONObject(result)
                       if(!json.has("notice"))
                           throw Exception("Не верный формат ответа, ожидался объект notice")
                       if(json.getJSONObject("notice").has("data")){
                           var data = json.getJSONObject("notice").getJSONArray("data")
                           for(i in 0 until data.length()){
                               val item = data.getJSONObject(i)
                               app.bankList.add(
                                   Bank(
                                       item.getString("address"),
                                       item.getString("work_time")
                                   )
                               )
                           }
                           bankView.adapter?.notifyDataSetChanged()
                       }
                       else{
                           throw Exception("Не верный формат ответа")
                       }


                   }
                   catch (e:Exception){
                       AlertDialog.Builder(this)
                           .setTitle("Ошибка")
                           .setMessage(e.message)
                           .setPositiveButton("OK",null)
                           .create()
                           .show()
                   }
                }
                else{
                    AlertDialog.Builder(this)
                        .setTitle("Ошибка")
                        .setMessage(error)
                        .setPositiveButton("OK",null)
                        .create()
                        .show()
                }
            }
        }
    }
}