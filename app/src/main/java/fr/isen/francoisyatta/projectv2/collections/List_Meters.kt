@file:Suppress("DEPRECATION")

package fr.isen.francoisyatta.projectv2.collections

import android.app.Activity
import android.content.DialogInterface
import android.content.Intent
import android.os.Bundle
import android.view.ContextMenu
import android.view.ContextMenu.ContextMenuInfo
import android.view.MenuItem
import android.view.View
import android.widget.AdapterView.AdapterContextMenuInfo
import android.widget.ListView
import androidx.fragment.app.ListFragment
import fr.isen.francoisyatta.projectv2.DialogHelper
import fr.isen.francoisyatta.projectv2.DialogHelper.DialogCommand
import fr.isen.francoisyatta.projectv2.DialogHelper.OnListChangedListener
import fr.isen.francoisyatta.projectv2.R
import fr.isen.francoisyatta.projectv2.MeterActivity
import fr.isen.francoisyatta.projectv2.ReadingActivity
import fr.isen.francoisyatta.projectv2.ReadingChart
import fr.isen.francoisyatta.projectv2.Adapter.Adapter_Meters
import fr.isen.francoisyatta.projectv2.database.Constants
import fr.isen.francoisyatta.projectv2.model.Home
class List_Meters : ListFragment(){
    private var deletePosition = 0

    private val activity: Activity by lazy { requireActivity() }
    private val callback by lazy { context as OnListChangedListener }
    private val dialogHelper by lazy { DialogHelper() }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        registerForContextMenu(listView)
        listAdapter = Adapter_Meters(activity, R.id.meter_row, Home.instance.meters, this)
    }

    override fun onResume() {
        super.onResume()
        listView.requestFocus()  // restore focus after swiping
        callback.onListChanged() // update lists
        activity.closeContextMenu()
    }

    override fun onContextItemSelected(item: MenuItem): Boolean {
        if (isResumed) { // instead of userVisibleHint
            val info = item.menuInfo as AdapterContextMenuInfo
            when (item.itemId) {
                R.id.change -> change(info.position)
                R.id.delete -> delete(info.position)
                R.id.tariffs -> showTariffs(info.position)
            }
        }
        return super.onContextItemSelected(item)
    }

    override fun onCreateContextMenu(menu: ContextMenu, v: View, menuInfo: ContextMenuInfo?) {
        super.onCreateContextMenu(menu, v, menuInfo)
        activity.menuInflater.inflate(R.menu.context_meter_list, menu)
    }

    override fun onListItemClick(l: ListView, v: View, position: Int, id: Long) {
        super.onListItemClick(l, v, position, id)
        val intent = Intent(activity, ReadingActivity::class.java)
        intent.putExtra(Constants.METER, position)
        startActivity(intent)
    }

    fun showChart(position: Int) {
        val intent = Intent(activity, ReadingChart::class.java)
        intent.putExtra(Constants.METER, position)
        startActivity(intent)
    }

    fun showReadings(position: Int) {
        val intent = Intent(activity, List_Reading::class.java)
        intent.putExtra(Constants.METER, position)
        startActivity(intent)
    }

    private fun showTariffs(position: Int) {
        val intent = Intent(activity, List_Tariff::class.java)
        intent.putExtra(Constants.METER, position)
        startActivity(intent)
    }

    private fun change(position: Int) {
        val intent = Intent(activity, MeterActivity::class.java)
        intent.putExtra(Constants.METER, position)
        startActivity(intent)
    }

    private fun delete(position: Int) {
        deletePosition = position
        val dialog = dialogHelper.getAlertDialog(activity, R.string.meter, R.string.delete_message)
        dialog.show()
        dialog.getButton(DialogInterface.BUTTON_POSITIVE).tag = DeleteCommand()
    }

    private inner class DeleteCommand : DialogCommand {
        override fun execute() {
            val adapter = listAdapter as Adapter_Meters
            val meter = adapter.getItem(deletePosition)
            Home.instance.remove(meter!!)
            callback.onListChanged()
            activity.setResult(Activity.RESULT_OK)
        }
    }
}