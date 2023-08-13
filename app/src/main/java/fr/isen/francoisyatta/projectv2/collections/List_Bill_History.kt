@file:Suppress("DEPRECATION")

package fr.isen.francoisyatta.projectv2.collections

import android.app.ListActivity
import android.os.Bundle
import fr.isen.francoisyatta.projectv2.R
import fr.isen.francoisyatta.projectv2.Adapter.Adapter_Bill_History
import fr.isen.francoisyatta.projectv2.database.Constants
import fr.isen.francoisyatta.projectv2.model.Bill
import fr.isen.francoisyatta.projectv2.model.Home
class List_Bill_History: ListActivity()  {
    private lateinit var bill: Bill
    private lateinit var adapterBillHistory: Adapter_Bill_History

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.bill_history_list)
        registerForContextMenu(listView)

        val bundle = this.intent.extras
        val position = bundle!!.getInt(Constants.BILL)
        bill = Home.instance.bill(position)
        title = getString(R.string.history) + ": " + bill.name

        adapterBillHistory = Adapter_Bill_History(this, R.id.bill_row_history, bill, bill.years())
        listAdapter = adapterBillHistory
    }
}