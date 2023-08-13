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
import fr.isen.francoisyatta.projectv2.model.Tariff

class Adapter_Tariff(
    private var ctx: Context, textView: Int,
    private var tariffs: List<Tariff>
) : ArrayAdapter<Tariff>(ctx, textView, tariffs) {
    private val number = MainActivity.numberFormat
    private val currency: String = MainActivity.currencyFormat
    private val currencySymbol: String = MainActivity.currencySymbol

    override fun getView(position: Int, convertView: View?, parent: ViewGroup): View {
        var row = convertView
        if (row == null) {
            val inflater = ctx.getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater
            row = inflater.inflate(R.layout.tariff_row, parent, false)

            val holder = ViewHolder().apply {
                begin = row.findViewById(R.id.valid_from)
                fee = row.findViewById(R.id.monthly_fee)
                price = row.findViewById(R.id.unit_price)
                payment = row.findViewById(R.id.payment)
            }
            row.tag = holder
        }

        val tariff = tariffs[position]
        val strPayment =
            String.format(currency, if (tariff.payment > 0) tariff.payment else tariff.lastPayment)
        val strFee = String.format(currency, if (tariff.fee > 0) tariff.fee else tariff.lastFee)
        var strPrice = number.format(if (tariff.price > 0) tariff.price else tariff.lastPrice)
        strPrice += " $currencySymbol"

        val holder = row!!.tag as ViewHolder
        holder.apply {
            begin.text = DateHelper.formatMedium(tariff.dateFrom)

            fee.text = strFee
            price.text = strPrice
            payment.text = strPayment

            fee.alpha = if (tariff.hasFee()) 1.0f else 0.5f
            price.alpha = if (tariff.hasPrice()) 1.0f else 0.5f
            payment.alpha = if (tariff.hasPayment()) 1.0f else 0.5f
        }
        return row
    }

    internal class ViewHolder {
        lateinit var begin: TextView
        lateinit var fee: TextView
        lateinit var price: TextView
        lateinit var payment: TextView
    }
}