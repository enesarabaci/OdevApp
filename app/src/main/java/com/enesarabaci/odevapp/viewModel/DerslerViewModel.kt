package com.enesarabaci.odevapp.viewModel

import android.app.Application
import android.content.Context
import androidx.lifecycle.MutableLiveData
import com.enesarabaci.odevapp.model.Ders
import com.enesarabaci.odevapp.service.OdevDatabase
import kotlinx.coroutines.launch

class DerslerViewModel(application: Application) : BaseViewModel(application) {

    val database = OdevDatabase
    val list = MutableLiveData<ArrayList<Ders>>()
    val progressBar = MutableLiveData<Boolean>()

    fun getDers(context: Context) {
        launch {
            val dersList = ArrayList<String>()
            val dersler = ArrayList<Ders>()
            val data = database(context).dao().selectAllOdev()
            for (odev in data) {
                if (dersList.contains(odev.ders)) {
                    var position = 0
                    for (dersName in dersList) {
                        if (dersName == odev.ders) {
                            dersler.get(position).odevSayi = dersler.get(position).odevSayi + 1
                            if (odev.status == "ok") {
                                dersler.get(position).ok = dersler.get(position).ok + 1
                            }else if (odev.status == "wait") {
                                dersler.get(position).wait = dersler.get(position).wait + 1
                            }else {
                                dersler.get(position).close = dersler.get(position).close + 1
                            }
                            break
                        }
                        position++
                    }
                }else {
                    dersList.add(odev.ders)
                    var ders : Ders
                    if (odev.status == "ok") {
                        ders = Ders(odev.ders, 1, 1, 0, 0)
                    }else if (odev.status == "wait") {
                        ders = Ders(odev.ders, 1, 0, 1, 0)
                    }else {
                        ders = Ders(odev.ders, 1, 0, 0, 1)
                    }
                    dersler.add(ders)
                }
            }
            list.value = dersler
            progressBar.value = false
        }
    }

}