package com.enesarabaci.odevapp.viewModel

import android.app.Application
import android.content.Context
import androidx.lifecycle.MutableLiveData
import com.enesarabaci.odevapp.model.Odev
import com.enesarabaci.odevapp.service.OdevDatabase
import kotlinx.coroutines.launch
import java.sql.Date
import java.util.*
import kotlin.collections.ArrayList

class OdevViewModel(application: Application) : BaseViewModel(application) {

    var list = MutableLiveData<ArrayList<Odev>>()
    var dayList = MutableLiveData<ArrayList<Int>>()
    var error = MutableLiveData<Boolean>()
    var progressBar = MutableLiveData<Boolean>()
    var database = OdevDatabase

    fun getOdevs(context : Context) {

        launch {
            val listt = database(context).dao().selectAllOdev()
            listt.sortedBy { Date(it.date) }.also { list.value = ArrayList(it) }

            findCountDown(context)

            if (list.value!!.size > 0) {
                error.value = false
                progressBar.value = false
            }else {
                error.value = true
                progressBar.value = false
            }
        }
    }

    private fun findCountDown(context: Context) {
        val liste = ArrayList<Int>()
        for (i in list.value!!) {
            val c = Calendar.getInstance()
            c.time = Date(i.date)


            val year = c.get(Calendar.YEAR) - Calendar.getInstance().get(Calendar.YEAR)
            if (year > 0) {
                liste.add(366)
            }else {
                val day = c.get(Calendar.DAY_OF_YEAR) - Calendar.getInstance().get(Calendar.DAY_OF_YEAR)
                liste.add(day)
                if (day < 0) {
                    if (i.status != "ok" || i.status != "close") {
                        i.status = "close"
                        closeOdev(i.uuid, context)
                    }
                }
            }
        }
        //getOdevs(context)
        dayList.value = liste
    }

    fun deleteOdev(uuid : Int, context : Context) {
        launch {
            database(context).dao().deleteOdev(uuid)
        }
    }

    fun yapildiOdev(uuid : Int, context : Context) {
        launch {
            database(context).dao().updateOdev(uuid, "ok")
            getOdevs(context)
        }
    }

    private fun closeOdev(uuid: Int, context: Context) {
        launch {
            database(context).dao().updateOdev(uuid, "close")
        }
    }

}