package com.enesarabaci.odevapp.view

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.ActionBarDrawerToggle
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.GridLayoutManager
import com.enesarabaci.odevapp.R
import com.enesarabaci.odevapp.adapter.DerslerAdapter
import com.enesarabaci.odevapp.model.Ders
import com.enesarabaci.odevapp.viewModel.DerslerViewModel
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.fragment_dersler.*

class DerslerFragment : Fragment() {

    var viewModel : DerslerViewModel ?= null
    var list = ArrayList<Ders>()
    val adapter = DerslerAdapter(list)

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val view = inflater.inflate(R.layout.fragment_dersler, container, false)
        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        viewModel = ViewModelProviders.of(this).get(DerslerViewModel::class.java)
        viewModel?.getDers(requireContext())
        observeViewModel()

        dersler_rv.layoutManager = GridLayoutManager(requireContext(), 2)
        dersler_rv.adapter = adapter

        (activity as AppCompatActivity).setSupportActionBar(toolbar_fragment_dersler)
        val drawer = activity?.drawer_layout
        val toggle = ActionBarDrawerToggle(activity, drawer, toolbar_fragment_dersler,
            R.string.navigation_drawer_open,
            R.string.navigation_drawer_close
        )
        drawer?.addDrawerListener(toggle)
        toggle.syncState()

        super.onViewCreated(view, savedInstanceState)
    }

    override fun onResume() {
        activity?.nav_view?.setCheckedItem(R.id.dersler)
        super.onResume()
    }

    private fun observeViewModel() {
        viewModel?.list?.observe(viewLifecycleOwner, Observer{
            for (ders in it) {
                println("${ders.ders} ${ders.odevSayi} ${ders.ok} ${ders.wait} ${ders.close}")
                list = it
                adapter.updateList(list)
            }
        })
    }
}