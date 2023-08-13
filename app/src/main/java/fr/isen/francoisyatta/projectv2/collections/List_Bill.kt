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
import androidx.activity.result.contract.ActivityResultContracts.StartActivityForResult
import androidx.fragment.app.ListFragment
import fr.isen.francoisyatta.projectv2.DialogHelper
import fr.isen.francoisyatta.projectv2.DialogHelper.DialogCommand
import fr.isen.francoisyatta.projectv2.R
import fr.isen.francoisyatta.projectv2.ActivityBill
import fr.isen.francoisyatta.projectv2.Adapter.Adapter_Bill
import fr.isen.francoisyatta.projectv2.database.Constants
import fr.isen.francoisyatta.projectv2.model.Home
class List_Bill: ListFragment()  {
    private var deletePosition = 0

    private val activity: Activity by lazy { requireActivity() }
    private val callback by lazy { context as DialogHelper.OnListChangedListener }
    private val dialogHelper by lazy { DialogHelper() }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        registerForContextMenu(listView)
        listAdapter = Adapter_Bill(activity, R.id.bill_row, Home.instance.bills)
    }

    override fun onResume() {
        super.onResume()
        listView.requestFocus() // restore focus after swiping
        callback.onListChanged() // update lists
        activity.closeContextMenu()
    }

    override fun onContextItemSelected(item: MenuItem): Boolean {
        if (isResumed) { // instead of userVisibleHint
            val info = item.menuInfo as AdapterContextMenuInfo
            when (item.itemId) {
                R.id.change -> change(info.position)
                R.id.delete -> delete(info.position)
            }
        }
        return super.onContextItemSelected(item)
    }

    override fun onCreateContextMenu(menu: ContextMenu, v: View, menuInfo: ContextMenuInfo?) {
        super.onCreateContextMenu(menu, v, menuInfo)
        val inflater = activity.menuInflater
        inflater.inflate(R.menu.context_bill_list, menu)
    }

    override fun onListItemClick(l: ListView, v: View, position: Int, id: Long) {
        super.onListItemClick(l, v, position, id)
        val intent = Intent(activity, List_Bill_History::class.java)
        intent.putExtra(Constants.BILL, position)
        startActivity(intent)
    }

    private val billChange =
        registerForActivityResult(StartActivityForResult()) {
            if (it.resultCode == Activity.RESULT_OK)
                callback.onListChanged()
        }

    private fun change(position: Int) {
        val intent = Intent(activity, ActivityBill::class.java)
        intent.putExtra(Constants.BILL, position)
        billChange.launch(intent)
    }

    private fun delete(position: Int) {
        deletePosition = position
        val dialog = dialogHelper.getAlertDialog(activity, R.string.bill, R.string.delete_message)
        dialog.show()
        dialog.getButton(DialogInterface.BUTTON_POSITIVE).tag = DeleteCommand()
    }

    private inner class DeleteCommand : DialogCommand {
        override fun execute() {
            val adapter = listAdapter as Adapter_Bill
            val bill = adapter.getItem(deletePosition)
            Home.instance.remove(bill!!)
            adapter.notifyDataSetChanged()
            activity.setResult(Activity.RESULT_OK)
        }
    }
}