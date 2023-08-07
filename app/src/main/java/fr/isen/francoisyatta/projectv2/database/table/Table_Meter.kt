package fr.isen.francoisyatta.projectv2.database.table

import fr.isen.francoisyatta.projectv2.database.PropertyTable.Database
import android.content.ContentValues
import android.provider.BaseColumns
import fr.isen.francoisyatta.projectv2.database.PropertyTable.Column
import fr.isen.francoisyatta.projectv2.database.PropertyTable.Constants
import fr.isen.francoisyatta.projectv2.database.PropertyTable.Table
import fr.isen.francoisyatta.projectv2.Model.*

class Table_Meter(database: Database) :
    Table(database, Constants.METER, true) {
    init {
        add(Column(Constants.NUMBER, Column.Type.TEXT))
        add(Column(Constants.NAME, Column.Type.TEXT))
        add(Column(Constants.UNIT, Column.Type.TEXT))
        add(Column(Constants.PRIOR, Column.Type.INTEGER).apply {
            isForeignKeyConstraint = true
            foreignTable = Constants.METER
            foreignColumn = BaseColumns._ID
        })
    }

    override fun values(obj: Any, forUpdate: Boolean): ContentValues {
        val meter = obj as Meter
        val values = ContentValues()
        if (forUpdate) values.put(BaseColumns._ID, meter.id)
        values.put(Constants.NUMBER, meter.number)
        values.put(Constants.NAME, meter.name)
        values.put(Constants.UNIT, meter.unit.name)
        meter.prior?.let { values.put(Constants.PRIOR, meter.prior!!.id) }
        return values
    }

    override fun whereClause(obj: Any): String = BaseColumns._ID + " = " + (obj as Meter).id

    override fun read(condition: String?): MutableList<Meter> {
        val meters = ArrayList<Meter>()
        val columns = arrayOf(
            BaseColumns._ID,
            Constants.NUMBER,
            Constants.NAME,
            Constants.UNIT,
            Constants.PRIOR
        )
        val cursor = database.query(
            Constants.METER, columns, condition, null,
            null, null, null
        )
        while (cursor.moveToNext()) {
            val meter = Meter()
            var col = 0
            meter.id = cursor.getLong(col++)
            meter.number = cursor.getString(col++)
            meter.name = cursor.getString(col++)
            meter.unit = Meter.Unit.valueOf(cursor.getString(col++))
            meter.priorId = cursor.getLong(col)
            meters.add(meter)
        }
        return meters
    }

    fun import(): MutableList<Meter> {
        val meters: MutableList<Meter> = read(null)
        for (meter in meters) {
            val id = meter.id.toString()
            val tariffs = Database.tableTariff.read(id)
            val readings = Database.tableReading.read(id)
            meter.tariffs = tariffs
            meter.readings = readings
            meter.update()
        }
        return meters
    }
}