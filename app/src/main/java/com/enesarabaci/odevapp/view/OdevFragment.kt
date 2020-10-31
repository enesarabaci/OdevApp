package com.enesarabaci.odevapp.view

import android.app.AlertDialog
import android.app.Dialog
import android.content.DialogInterface
import android.content.Intent
import android.os.Bundle
import android.text.method.ScrollingMovementMethod
import android.view.*
import androidx.appcompat.app.ActionBarDrawerToggle
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.enesarabaci.odevapp.R
import com.enesarabaci.odevapp.adapter.OdevAdapter
import com.enesarabaci.odevapp.model.Odev
import com.enesarabaci.odevapp.viewModel.OdevViewModel
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.fragment_odev.*
import kotlinx.android.synthetic.main.odev_dialog.*

class OdevFragment : Fragment() {

    var viewModel : OdevViewModel? = null
    var list = ArrayList<Odev>()
    var dayList = ArrayList<Int>()
    var adapter = OdevAdapter(list, dayList, this)

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val view = inflater.inflate(R.layout.fragment_odev, container, false)
        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        progressBar.visibility = View.VISIBLE
        emptyError.visibility = View.GONE
        toggle()
        viewModel = ViewModelProviders.of(this).get(OdevViewModel::class.java)
        viewModel!!.getOdevs(requireContext())
        observeViewModel()

        fab_odev.setOnClickListener {
            val intent = Intent(activity, CreateOdevActivity::class.java)
            startActivity(intent)
            activity?.finish()
        }

        recyclerView_odev.layoutManager = LinearLayoutManager(requireContext())
        recyclerView_odev.adapter = adapter

        val itemTouchHelper = ItemTouchHelper(simpleCallback)
        itemTouchHelper.attachToRecyclerView(recyclerView_odev)

        super.onViewCreated(view, savedInstanceState)
    }

    override fun onResume() {
        activity?.nav_view?.setCheckedItem(R.id.odevler)
        super.onResume()
    }

    private fun toggle() {
        (activity as AppCompatActivity).setSupportActionBar(toolbar_fragment_odev)
        setHasOptionsMenu(true)
        val drawer = activity?.drawer_layout
        val toggle = ActionBarDrawerToggle(activity, drawer, toolbar_fragment_odev,
            R.string.navigation_drawer_open,
            R.string.navigation_drawer_close
        )
        drawer?.addDrawerListener(toggle)
        toggle.syncState()
    }

    private fun observeViewModel() {
        viewModel?.list?.observe(viewLifecycleOwner, Observer {
            list.clear()
            list = it
            adapter.refresh(list, dayList)

        })
        viewModel?.dayList?.observe(viewLifecycleOwner, Observer {
            dayList.clear()
            dayList = it
            adapter.refresh(list, dayList)
        })
        viewModel?.error?.observe(viewLifecycleOwner, Observer {
            if (it) {
                emptyError.visibility = View.VISIBLE
            }else {
                emptyError.visibility = View.GONE
            }
        })
        viewModel?.progressBar?.observe(viewLifecycleOwner, Observer {
            if (it) {
                progressBar.visibility = View.VISIBLE
            }else {
                progressBar.visibility = View.GONE
            }
        })
    }

    fun createDialog(odev : Odev, position : Int) {
        val dialog = Dialog(requireContext())
        dialog.setContentView(R.layout.odev_dialog)
        dialog.dialog_ders.text = odev.ders
        dialog.dialog_day.text = odev.day.toString()
        dialog.dialog_month.text = (odev.month+1).toString()
        dialog.dialog_year.text = odev.year.toString()
        dialog.dialog_konu.text = odev.konu
        dialog.dialog_konu.movementMethod = ScrollingMovementMethod()
        dialog.dialog_duzenle.setOnClickListener {
            val intent = Intent(context, CreateOdevActivity::class.java)
            intent.putExtra("uuid", odev.uuid)
            startActivity(intent)
            activity?.finish()
        }

        dialog.dialog_sil.setOnClickListener {
            val alertDialog = AlertDialog.Builder(context)
            alertDialog.setCancelable(false)
            alertDialog.setMessage("Silmek istediğine emin misin?")
            alertDialog.setPositiveButton("Evet", DialogInterface.OnClickListener { d, which ->
                list.removeAt(position)
                adapter.notifyItemRemoved(position)

                viewModel?.deleteOdev(odev.uuid, requireContext())
                dialog.dismiss()
            })
            alertDialog.setNegativeButton("Hayır", DialogInterface.OnClickListener { dialog, which ->
                dialog.dismiss()
            })
            alertDialog.show()
        }

        dialog.dialog_yapildi.setOnClickListener {
            viewModel?.yapildiOdev(odev.uuid, requireContext())
            dialog.dismiss()
        }

        dialog.show()



    }

    val simpleCallback = object : ItemTouchHelper.SimpleCallback(0, ItemTouchHelper.LEFT or ItemTouchHelper.RIGHT) {
        override fun onMove(
            recyclerView: RecyclerView,
            viewHolder: RecyclerView.ViewHolder,
            target: RecyclerView.ViewHolder
        ): Boolean {
            TODO("Not yet implemented")
        }

        override fun onSwiped(viewHolder: RecyclerView.ViewHolder, direction: Int) {

            val position = viewHolder.adapterPosition
            val odev = list.get(position)
            if (direction == ItemTouchHelper.LEFT) {

                val alertDialog = AlertDialog.Builder(context)
                alertDialog.setCancelable(false)
                alertDialog.setMessage("Silmek istediğine emin misin?")
                alertDialog.setPositiveButton("Evet", DialogInterface.OnClickListener { d, which ->
                    list.removeAt(position)
                    adapter.notifyItemRemoved(position)

                    viewModel?.deleteOdev(odev.uuid, requireContext())
                })
                alertDialog.setNegativeButton("Hayır", DialogInterface.OnClickListener { dialog, which ->
                    progressBar.visibility = View.VISIBLE
                    viewModel?.getOdevs(requireContext())

                    dialog.dismiss()
                })
                alertDialog.show()
            }
            if (direction == ItemTouchHelper.RIGHT) {
                list.removeAt(position)
                adapter.notifyItemRemoved(position)
            }
        }
    }
}