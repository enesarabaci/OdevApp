package com.enesarabaci.odevapp.view

import android.app.DatePickerDialog
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.widget.DatePicker
import androidx.lifecycle.ViewModelProviders
import androidx.navigation.NavController
import com.enesarabaci.odevapp.R
import com.enesarabaci.odevapp.viewModel.CreateOdevViewModel
import com.enesarabaci.odevapp.viewModel.OdevViewModel
import kotlinx.android.synthetic.main.activity_create_odev.*
import kotlinx.android.synthetic.main.fragment_create_odev.datePick
import kotlinx.android.synthetic.main.fragment_create_odev.dayText
import kotlinx.android.synthetic.main.fragment_create_odev.ders
import kotlinx.android.synthetic.main.fragment_create_odev.konu
import kotlinx.android.synthetic.main.fragment_create_odev.monthText
import kotlinx.android.synthetic.main.fragment_create_odev.yearText
import java.util.*

class CreateOdevActivity : AppCompatActivity(), DatePickerDialog.OnDateSetListener {

    var viewModel : CreateOdevViewModel? = null
    var day : Int? = null
    var month : Int? = null
    var year : Int? = null
    val calendar = java.util.Calendar.getInstance()
    var uuid = -1

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_create_odev)

        viewModel = ViewModelProviders.of(this).get(CreateOdevViewModel::class.java)

        val intent = intent
        uuid = intent.getIntExtra("uuid", -1)

        setSupportActionBar(toolbar_activity_create_odev)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)

        makeCalendar(uuid)

        datePick.setOnClickListener {
            val calendar = java.util.Calendar.getInstance()
            val datePicker = DatePickerDialog(this, this, calendar.get(Calendar.YEAR), calendar.get(
                Calendar.MONTH), calendar.get(Calendar.DAY_OF_MONTH))
            datePicker.show()
        }
    }

    override fun onDateSet(view: DatePicker?, year: Int, month: Int, dayOfMonth: Int) {
        yearText.text = (year % 100).toString()
        monthText.text = (month+1).toString()
        dayText.text = dayOfMonth.toString()
        this.day = dayOfMonth
        this.month = month
        this.year = year
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.add_odev_menu, menu)
        return super.onCreateOptionsMenu(menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if (item.itemId == R.id.save) {
            if (uuid == -1) {
                viewModel?.saveOdev(ders.text.toString(), day!!, month!!, year!!, konu.text.toString(), this)
            }
            else {
                viewModel?.updateOdev(ders.text.toString(), day!!, month!!, year!!, konu.text.toString(), this, uuid)
            }
            goToMainActivity()
        }
        if (item.itemId == android.R.id.home) {
            goToMainActivity()
        }

        return super.onOptionsItemSelected(item)
    }

    private fun goToMainActivity() {
        val intent = Intent(this, MainActivity::class.java)
        startActivity(intent)
        finish()
    }

    private fun makeCalendar(uuid : Int) {
        if (uuid == -1) {
            day = calendar.get(java.util.Calendar.DAY_OF_MONTH)
            month = calendar.get(java.util.Calendar.MONTH)
            year = calendar.get(java.util.Calendar.YEAR)
            dayText.text = day.toString()
            monthText.text = (month!!+1).toString()
            yearText.text = (year!! % 100).toString()
        }else {
            viewModel?.getOdev(uuid, this)
            observeData()
        }
    }

    private fun observeData() {
        viewModel?.odev?.observe(this, androidx.lifecycle.Observer {
            day = it.day
            month = it.month
            year = it.year
            dayText.text = day.toString()
            monthText.text = (month!!+1).toString()
            yearText.text = (year!! % 100).toString()
            ders.setText(it.ders)
            konu.setText(it.konu)
        })

    }

}