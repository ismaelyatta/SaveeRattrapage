package fr.isen.francoisyatta.projectv2

import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.content.ContextCompat.startActivity

import androidx.recyclerview.widget.RecyclerView
import fr.isen.francoisyatta.projectv2.ble.BleActivity
import kotlinx.android.synthetic.main.row.view.*
import java.util.logging.Logger.global


class MyAdapter (val arrayList: ArrayList<Model>, val context: Context) :
    RecyclerView.Adapter<MyAdapter.ViewHolder>() {

    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

        fun bindItems(model: Model) {

            itemView.titleTv.text = model.title
            itemView.descriptionTv.text = model.des
            itemView.imageIv.setImageResource(model.image)

        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {

        val v = LayoutInflater.from(parent.context).inflate(R.layout.row, parent, false)

        return ViewHolder(v)
    }

    override fun getItemCount(): Int {
        return arrayList.size
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bindItems(arrayList[position])
        //avoir position de l'item selectionné
        val model = arrayList.get(position)

        //avoir le titre de l'item selectionné
        var gTitle : String = model.title
        holder.itemView.setOnClickListener {

            if (position == 0) {
                Toast.makeText(
                    context,
                    "Click sur Ma consommation",
                    Toast.LENGTH_LONG
                ).show()
                val intentStat = Intent(context, maconso::class.java)
                context.startActivity(intentStat)
                intentStat.putExtra("iTitle" , gTitle)
            }

            if (position == 1) {
                Toast.makeText(
                    context,
                    "Click sur Bilan carbone",
                    Toast.LENGTH_LONG
                ).show()
                val intentBilanCarbonne = Intent(context, bilancarbone::class.java)
                context.startActivity(intentBilanCarbonne)
            }

            if (position == 2) {
                Toast.makeText(
                    context,
                    "Click sur Détection d'anomalies",
                    Toast.LENGTH_LONG
                ).show()
                val intentAnomalies = Intent(context, anomalies::class.java)
                context.startActivity(intentAnomalies)
            }

            if (position == 3) {
                Toast.makeText(
                    context,
                    "Click sur Maintenance prédictive",
                    Toast.LENGTH_LONG
                ).show()
                val intentMaintenance = Intent(context, maintenance::class.java)
                context.startActivity(intentMaintenance)
            }

            if (position == 4) {
                Toast.makeText(
                    context,
                    "Click sur Aide",
                    Toast.LENGTH_LONG
                ).show()
                val intentAide = Intent(context, aide::class.java)
                context.startActivity(intentAide)
            }

            if (position == 5) {
                Toast.makeText(
                    context,
                    "Click sur BLE",
                    Toast.LENGTH_LONG
                ).show()
                val intentBle = Intent(context, BleActivity::class.java)
                context.startActivity(intentBle)
            }


            //avoir position de l'item selectionné
            //val model = arrayList.get(position)

            //avoir le titre de l'item selectionné
            //var gTitle : String = model.title

            //prendre tous ces élements avec putExtra intent
            //intentStat.putExtra("iTitle" , gTitle)
            //intent2.putExtra("iTitle" , gTitle)
            //intent3.putExtra("iTitle" , gTitle)
            //intent4.putExtra("iTitle" , gTitle)
            //intent5.putExtra("iTitle" , gTitle)

        }
    }
}