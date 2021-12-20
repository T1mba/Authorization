package com.example.outh

import android.app.Activity
import android.content.Context
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import java.lang.Exception
import java.util.ArrayList

/*
Класс адаптера наследуется от RecyclerView.Adapter с указанием класса,
который будет хранить ссылки на виджеты элемента списка, т.е. класса, реализующего ViewHolder.
В нашем случае класс объявлен внутри класса адаптера.
В параметры основного конструктора передаем список c данными о погоде и указатель на активити главного окна
дело в том, что runOnUiThread работает только в контексте активити
Использование:
в КЛАССЕ активности объявляем переменные
private lateinit var someRecyclerView: RecyclerView
private val someClassList = ArrayList<SomeClass>()
в КОНСТРУКТОРЕ инициализируем:
someRecyclerView = findViewById(R.id.someRecyclerView)
// назначаем менеджер разметки
someRecyclerView.layoutManager = LinearLayoutManager(this, RecyclerView.HORIZONTAL, false)
// создаем адаптер
val someClassAdapter = WeatherAdapter(someClassList, this)
// при клике на элемент списка показать подробную информацию (сделайте сами)
someClassAdapter.setItemClickListener { weather ->
    Log.d("KEILOG", "Click on Weather item")
}
someRecyclerView.adapter = weatherAdapter
разбор JSONObject
// перед заполнением очищаем список
someClassList.clear()
val json = JSONObject(result)
val list = json.getJSONArray("list")
// перебираем json массив
for(i in 0 until list.length()){
    val item = list.getJSONObject(i)
    ...
*/
class BankAdapter(
    private val values: ArrayList<Bank>,
    private val activity: Activity
): RecyclerView.Adapter<BankAdapter.ViewHolder>(){

    // обработчик клика по элементу списка (лямбда выражение), может быть не задан
    private var itemClickListener: ((Bank) -> Unit)? = null

    fun setItemClickListener(itemClickListener: (Bank) -> Unit) {
        this.itemClickListener = itemClickListener
    }

    // Метод onCreateViewHolder вызывается при создании визуального элемента
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        // грузим layout, который содержит вёрстку элемента списка (нарисуйте сами)
        val itemView = LayoutInflater
            .from(parent.context)
            .inflate(R.layout.bank_item,
                parent,
                false)

        // создаем на его основе ViewHolder
        return ViewHolder(itemView)
    }

    override fun getItemCount(): Int = values.size

    // заполняет визуальный элемент данными
    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
            holder.address.text = values[position].address
            holder.time.text = values[position].timeWork
    }

    //Реализация класса ViewHolder, хранящего ссылки на виджеты.
    class ViewHolder(itemView: View): RecyclerView.ViewHolder(itemView){
        var address:TextView = itemView.findViewById(R.id.addressText)
        var time:TextView = itemView.findViewById(R.id.timeView)
    }
}