package fr.isen.francoisyatta.projectv2

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.recyclerview.widget.LinearLayoutManager
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val arrayList = ArrayList<Model>()

        arrayList.add(Model("Ma consommation", "Afficher votre consommation électrique", R.drawable.maconso))
        arrayList.add(Model("Bilan Carbone", "Afficher votre bilan carbone", R.drawable.co2))
        arrayList.add(Model("Détection d'anomalies", "Afficher vos anomalies de consommation d'eau", R.drawable.warning))
        arrayList.add(Model("Maintenance prédictive", "Afficher les améliorations possibles", R.drawable.maintenance))
        arrayList.add(Model("Aide", "Un problème ? Contactez nous", R.drawable.aide))
        arrayList.add(Model("BLE", "Connexion BLE", R.drawable.bluetooth))

        val myAdapter = MyAdapter(arrayList, this)

        recyclerView.layoutManager = LinearLayoutManager (this)
        recyclerView.adapter = myAdapter

    }
}