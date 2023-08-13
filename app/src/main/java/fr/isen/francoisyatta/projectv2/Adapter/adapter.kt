@file:Suppress("DEPRECATION")

package fr.isen.francoisyatta.projectv2.Adapter


import android.content.Context
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentPagerAdapter
import fr.isen.francoisyatta.projectv2.fragment.Jour
import fr.isen.francoisyatta.projectv2.fragment.Mois
import fr.isen.francoisyatta.projectv2.fragment.Semaine


internal class adapter (var context: Context, fm: FragmentManager, var totalTabs: Int): FragmentPagerAdapter(fm) {

    override fun getItem(position: Int): Fragment {
        return when(position){
            0 -> {
                Jour()
            }

            1 -> {
                Semaine()
            }

            2 -> {
                Mois()
            }

            else -> getItem(position)
        }
    }

    override fun getCount(): Int {
        return totalTabs
    }
}