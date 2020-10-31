package com.enesarabaci.odevapp.viewModel

import android.app.Application
import android.content.Context
import android.widget.Toast
import androidx.lifecycle.MutableLiveData
import com.enesarabaci.odevapp.model.Odev
import com.enesarabaci.odevapp.service.OdevDatabase
import kotlinx.coroutines.launch
import java.util.*

class CreateOdevViewModel(application : Application) : BaseViewModel(application) {

    val database = OdevDatabase
    var odev = MutableLiveData<Odev>()

    fun saveOdev(ders : String, day : Int, month : Int, year : Int, konu : String, context: Context) {
        val calendar = Calendar.getInstance()
        calendar.set(year, month, day)
        val date = calendar.time.time
        val odev = Odev(ders, day, month, year, konu, date, "wait")
        launch {
            database(context).dao().insert(odev)
            Toast.makeText(context, "işlem tamam", Toast.LENGTH_SHORT).show()
        }
    }
    fun updateOdev(ders : String, day : Int, month: Int, year: Int, konu: String, context: Context, uuid : Int) {
        launch {
            database(context).dao().deleteOdev(uuid)
        }
        saveOdev(ders, day, month, year, konu, context)
    }
    fun getOdev(uuid: Int, context: Context) {
        launch {
            val data = database(context).dao().getOdev(uuid)
            odev.value = data
        }
    }
}