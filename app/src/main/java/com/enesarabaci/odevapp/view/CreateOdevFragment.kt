package com.enesarabaci.odevapp.view

import android.app.DatePickerDialog
import android.os.Bundle
import android.view.*
import android.widget.DatePicker
import androidx.appcompat.app.ActionBarDrawerToggle
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.navigation.NavController
import androidx.navigation.Navigation
import com.enesarabaci.odevapp.R
import com.enesarabaci.odevapp.viewModel.CreateOdevViewModel
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.fragment_create_odev.*

class CreateOdevFragment : Fragment(), DatePickerDialog.OnDateSetListener {

    var viewModel : CreateOdevViewModel? = null
    var day : Int? = null
    var month : Int? = null
    var year : Int? = null
    val calendar = java.util.Calendar.getInstance()
    var navController : NavController? = null
    var uuid = -1

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val view = inflater.inflate(R.layout.fragment_create_odev, container, false)
        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        viewModel = ViewModelProviders.of(this).get(CreateOdevViewModel::class.java)
        navController = Navigation.findNavController(view)
        arguments?.let {
            uuid = CreateOdevFragmentArgs.fromBundle(it).uuid
        }
        toggle()

        makeCalendar(uuid)

        datePick.setOnClickListener {
            val calendar = java.util.Calendar.getInstance()
            val datePicker = DatePickerDialog(requireContext(), this, calendar.get(java.util.Calendar.YEAR), calendar.get(java.util.Calendar.MONTH), calendar.get(java.util.Calendar.DAY_OF_MONTH))
            datePicker.show()
        }

        super.onViewCreated(view, savedInstanceState)
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        inflater.inflate(R.menu.add_odev_menu, menu)

        super.onCreateOptionsMenu(menu, inflater)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if (item.itemId == R.id.save) {
            if (uuid == -1) {
                viewModel?.saveOdev(ders.text.toString(), day!!, month!!, year!!, konu.text.toString(), requireContext())
            }
            else {
                viewModel?.updateOdev(ders.text.toString(), day!!, month!!, year!!, konu.text.toString(), requireContext(), uuid)
            }
            val action = CreateOdevFragmentDirections.actionAddOdevFragmentToOdevFragment()
            navController?.navigate(action)
        }

        return super.onOptionsItemSelected(item)
    }

    override fun onResume() {
        activity?.nav_view?.setCheckedItem(R.id.odevler)
        super.onResume()
    }

    override fun onDateSet(view: DatePicker?, year: Int, month: Int, dayOfMonth: Int) {
        yearText.text = (year % 100).toString()
        monthText.text = (month+1).toString()
        dayText.text = dayOfMonth.toString()
        this.day = dayOfMonth
        this.month = month
        this.year = year
    }

    private fun toggle() {
        (activity as AppCompatActivity).setSupportActionBar(toolbar_fragment_add_odev)
        setHasOptionsMenu(true)
        val drawer = activity?.drawer_layout
        val toggle = ActionBarDrawerToggle(activity, drawer, toolbar_fragment_add_odev,
            R.string.navigation_drawer_open,
            R.string.navigation_drawer_close
        )
        drawer?.addDrawerListener(toggle)
        toggle.syncState()
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
            viewModel?.getOdev(uuid, requireContext())
            observeData()
        }
    }

    private fun observeData() {
        viewModel?.odev?.observe(viewLifecycleOwner, Observer {
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