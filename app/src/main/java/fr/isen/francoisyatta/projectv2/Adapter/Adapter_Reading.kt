package fr.isen.francoisyatta.projectv2.Adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.TextView
import fr.isen.francoisyatta.projectv2.DateHelper
import fr.isen.francoisyatta.projectv2.R
import fr.isen.francoisyatta.projectv2.MainActivity
import fr.isen.francoisyatta.projectv2.model.Reading
internal class Adapter_Reading(
    private var ctx: Context, textView: Int,
    private var readings: List<Reading>
) : ArrayAdapter<Reading>(ctx, textView, readings) {
    private val currencyFormat: String = MainActivity.currencyFormat
    private val unitFormat: String = MainActivity.UNIT_FORMAT
    private val unit by lazy { readings.first().meter.unit.toString() }

    override fun getView(position: Int, convertView: View?, parent: ViewGroup): View {
        var row = convertView
        if (row == null) {
            val inflater = ctx.getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater
            row = inflater.inflate(R.layout.reading_row, parent, false)

            val holder = ViewHolder().apply {
                date = row.findViewById(R.id.reading_date)
                count = row.findViewById(R.id.reading_count)
                costs = row.findViewById(R.id.reading_costs)
                consumption = row.findViewById(R.id.delta)
            }
            row.tag = holder
        }

        val reading = readings[position]
        val holder = row!!.tag as ViewHolder
        holder.apply {
            date.text = DateHelper.formatMedium(reading.date)
            count.text = String.format(unitFormat, reading.count, unit)
            consumption.text = String.format(unitFormat, reading.usage(), unit)
            costs.text = String.format(currencyFormat, reading.costs())
        }

        row.alpha = if (reading.isCalculated) 0.5f else 1.0f
        return row
    }

    internal class ViewHolder {
        lateinit var consumption: TextView
        lateinit var costs: TextView
        lateinit var count: TextView
        lateinit var date: TextView
    }
}