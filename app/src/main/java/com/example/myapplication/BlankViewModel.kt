package com.example.myapplication

import android.content.Context
import android.content.Intent
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.isActive
import kotlinx.coroutines.launch

class BlankViewModel: ViewModel() {

    val list = mutableListOf<MutableLiveData<String>>()
    val GET_DATA = "get data"

    fun start(applicationContext: Context) {
        val intent = Intent()
        val job = CoroutineScope(Dispatchers.IO).launch(Dispatchers.IO) {
        var i = 0
            while (isActive) {
                //list[0].postValue(i.toString())
                intent.action = GET_DATA
                intent.putExtra("test", i)
                intent.putExtra("device", 0)
                applicationContext.sendBroadcast(intent)
                i++
                delay(500L)
            }
        }
        job.start()
    }
    fun start1(applicationContext: Context) {
        val intent = Intent()
        val job = viewModelScope.launch(Dispatchers.Default) {
            var i = 1000000
            while (isActive) {
                //list[1].postValue(i.toString())
                intent.action = GET_DATA
                intent.putExtra("test", i)
                intent.putExtra("device", 1)
                applicationContext.sendBroadcast(intent)
                i++
                delay(500L)
            }
        }
        job.start()
        /*viewModelScope.launch(Dispatchers.IO) {
            var i = 100000
            asd(i)
        }*/
    }
    fun qwe() {
        for (i in 0 until 2) {
            val liveDataDroneStatus: MutableLiveData<String> = MutableLiveData()
            list.add(liveDataDroneStatus)
        }
    }

    fun asd(i: Int,device:Int) {
        viewModelScope.launch(Dispatchers.Default) {
            zxc(i,device)
        }
    }

    fun zxc(i: Int, device: Int){
        list[device].postValue(i.toString())
    }
}