package fr.isen.francoisyatta.projectv2

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.appcompat.app.ActionBar
import androidx.viewpager.widget.ViewPager
import com.github.mikephil.charting.animation.Easing
import com.github.mikephil.charting.charts.LineChart
import com.github.mikephil.charting.components.XAxis
import com.github.mikephil.charting.components.YAxis
import com.github.mikephil.charting.data.Entry
import com.github.mikephil.charting.data.LineData
import com.github.mikephil.charting.data.LineDataSet
import com.google.android.material.tabs.TabLayout
import fr.isen.francoisyatta.projectv2.Adapter.adapter
import fr.isen.francoisyatta.projectv2.databinding.ActivityMaconsoBinding

class maconso : AppCompatActivity() {

    private lateinit var binding: ActivityMaconsoBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_maconso)


        binding = ActivityMaconsoBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val actionBar : ActionBar? = supportActionBar
        actionBar!!.setDisplayHomeAsUpEnabled(true)
        actionBar!!.setDisplayShowHomeEnabled(true)

        // get data from putExtra intent
        val intent = intent
        val aTitle = intent.getStringExtra("iTitle")

        //set title in another activity
        actionBar.setTitle(aTitle)
        //a_title.text = aTitle
        initializeScreen()
    }
    private fun initializeScreen() {
        val consommation = setLineChartData(evolution_consommation(), R.color.bleusavee)
        val graphLignes = listOf<LineDataSet>(consommation)
        drawChart(graphLignes, binding.consoGraph)

    }

    private fun setLineChartData(lineValues: ArrayList<Entry>, color: Int): LineDataSet {
        val lineDataset = LineDataSet(lineValues, "Consommation")
        //We add features to our chart
        lineDataset.color = resources.getColor(color)
        lineDataset.lineWidth = 3f
        lineDataset.circleRadius = 2f
        lineDataset.setCircleColors(color)
        lineDataset.valueTextSize = 0f
        lineDataset.mode = LineDataSet.Mode.CUBIC_BEZIER

        return lineDataset
    }

    private fun drawChart(linesDataSet: List<LineDataSet>, chart: LineChart) {
        val data = LineData(linesDataSet)
        chart.data = data
        chart.animateXY(2000, 2000, Easing.EaseInCubic)

        chart.description.isEnabled = false
        chart.legend.isEnabled = false

        chart.setTouchEnabled(false)
        chart.setPinchZoom(false)
        chart.setDrawGridBackground(false)

        val x: XAxis = chart.xAxis
        x.position = XAxis.XAxisPosition.BOTTOM
        x.setDrawAxisLine(true)
        x.setDrawGridLines(false)
        x.textColor = getColor(R.color.black)
        x.axisLineColor = getColor(R.color.black)

        val y: YAxis = chart.axisLeft
        y.setDrawZeroLine(true)
        y.setDrawGridLines(false)
        y.textColor = getColor(R.color.black)
        y.axisLineColor = getColor(R.color.black)

        chart.axisRight.isEnabled = false
    }

    private fun evolution_consommation(): ArrayList<Entry> {
        val lineValues = ArrayList<Entry>()
        lineValues.add(Entry(0f, 5.1F))
        lineValues.add(Entry(5f, 5.3F))
        lineValues.add(Entry(10f, 5.2F))
        lineValues.add(Entry(15f, 5.5F))
        lineValues.add(Entry(20f, 5.3F))
        lineValues.add(Entry(25f, 5.7F))
        lineValues.add(Entry(30f, 7.3F))
        lineValues.add(Entry(35f, 9.1F))
        lineValues.add(Entry(40f, 15.2F))
        lineValues.add(Entry(45f, 17.8F))
        lineValues.add(Entry(50f, 19.5F))
        lineValues.add(Entry(55f, 18.6F))
        lineValues.add(Entry(60f, 17.1F))
        lineValues.add(Entry(65f, 10.0F))
        lineValues.add(Entry(70f, 7.7F))
        lineValues.add(Entry(75f, 6.5F))
        lineValues.add(Entry(80f, 5.6F))
        lineValues.add(Entry(85f, 5.2F))
        lineValues.add(Entry(90f, 5.0F))
        lineValues.add(Entry(95f, 4.8F))
        lineValues.add(Entry(100f, 5.0F))
        return lineValues
    }
}