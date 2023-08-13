package fr.isen.francoisyatta.projectv2.Adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.ImageButton
import android.widget.TextView
import fr.isen.francoisyatta.projectv2.DateHelper
import fr.isen.francoisyatta.projectv2.DialogHelper
import fr.isen.francoisyatta.projectv2.R
import fr.isen.francoisyatta.projectv2.MainActivity
import fr.isen.francoisyatta.projectv2.collections.List_Meters
import fr.isen.francoisyatta.projectv2.database.Constants
import fr.isen.francoisyatta.projectv2.model.Meter
import java.text.DecimalFormat
internal class Adapter_Meters(
    private var ctx: Context, textView: Int,
    private var meters: List<Meter>,
    private var listMeter: List_Meters
) : ArrayAdapter<Meter>(ctx, textView, meters), DialogHelper.OnListChangedListener {
    private var decimalFormat: DecimalFormat = MainActivity.decimalFormat
    private var values: MutableList<HashMap<String, String>> = ArrayList()

    init {
        fillValues()
    }

    override fun onListChanged() {
        fillValues()
    }

    private fun fillValues() {
        values.clear()
        meters.forEach {
            val last = it.lastReading()
            val entry: HashMap<String, String> = HashMap()
            entry[Constants.NUMBER] = it.number
            entry[Constants.NAME] = it.name
            entry[Constants.DATE] = if (last == null) "" else DateHelper.formatLong(last.date)
            entry[Constants.COUNT] = if (last == null) "" else
                decimalFormat.format(last.count) + " " + it.unit
            values.add(entry)
        }
    }

    override fun getView(position: Int, convertView: View?, parent: ViewGroup): View {
        var row = convertView
        if (row == null) {
            val inflater = ctx.getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater
            row = inflater.inflate(R.layout.meter_row, parent, false)

            val holder = ViewHolder().apply {
                name = row.findViewById(R.id.meter_name)
                number = row.findViewById(R.id.meter_number)
                date = row.findViewById(R.id.meter_date)
                count = row.findViewById(R.id.meter_count)

                chart = row.findViewById(R.id.chart_button)
                chart.setOnClickListener { v: View ->
                    listMeter.showChart(v.tag as Int)
                }

                more = row.findViewById(R.id.more_button)
                more.setOnClickListener { v: View ->
                    listMeter.showReadings(v.tag as Int)
                }
            }
            row.tag = holder
        }
        val holder = row!!.tag as ViewHolder
        holder.apply {
            number.text = values[position][Constants.NUMBER]
            name.text = values[position][Constants.NAME]
            date.text = values[position][Constants.DATE]
            count.text = values[position][Constants.COUNT]
            chart.tag = position
            more.tag = position
        }
        return row
    }

    internal class ViewHolder {
        lateinit var name: TextView
        lateinit var number: TextView
        lateinit var count: TextView
        lateinit var date: TextView
        lateinit var chart: ImageButton
        lateinit var more: ImageButton
    }
}