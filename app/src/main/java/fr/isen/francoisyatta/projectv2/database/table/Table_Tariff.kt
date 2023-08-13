package fr.isen.francoisyatta.projectv2.database.table

import android.content.ContentValues
import android.provider.BaseColumns
import fr.isen.francoisyatta.projectv2.DateHelper
import fr.isen.francoisyatta.projectv2.database.Column
import fr.isen.francoisyatta.projectv2.database.Constants
import fr.isen.francoisyatta.projectv2.database.Database
import fr.isen.francoisyatta.projectv2.database.Table_
import fr.isen.francoisyatta.projectv2.model.Tariff
class Table_Tariff(database: Database) :
    Table_(database, Constants.TARIFF, true) {
    init {
        add(Column(Constants.METER, Column.Type.INTEGER).apply {
            isForeignKeyConstraint = true
            foreignTable = Constants.METER
            foreignColumn = BaseColumns._ID
        })
        add(Column(Constants.DATE, Column.Type.TEXT))
        add(Column(Constants.FEE, Column.Type.REAL))
        add(Column(Constants.PRICE, Column.Type.REAL))
        add(Column(Constants.PAYMENT, Column.Type.REAL))
    }

    override fun values(obj: Any, forUpdate: Boolean): ContentValues {
        val tariff = obj as Tariff
        val values = ContentValues()
        if (forUpdate) values.put(BaseColumns._ID, tariff.id)
        values.put(Constants.METER, tariff.meter.id)
        values.put(Constants.DATE, tariff.dateFrom.let { DateHelper.format(it) })
        values.put(Constants.FEE, tariff.fee)
        values.put(Constants.PRICE, tariff.price)
        values.put(Constants.PAYMENT, tariff.payment)
        return values
    }

    override fun whereClause(obj: Any): String = BaseColumns._ID + " = " + (obj as Tariff).id

    override fun read(condition: String?): ArrayList<Tariff> {
        val tariffs = ArrayList<Tariff>()
        val columns = arrayOf(
            BaseColumns._ID,
            Constants.DATE,
            Constants.FEE,
            Constants.PRICE,
            Constants.PAYMENT
        )
        val where = Constants.METER + " = " + condition
        val cursor = database.query(
            Constants.TARIFF, columns, where, null,
            null, null, null
        )
        while (cursor.moveToNext()) {
            val tariff = Tariff()
            var col = 0
            tariff.id = cursor.getLong(col++)
            tariff.dateFrom = DateHelper.parse(cursor.getString(col++))
            tariff.fee = cursor.getDouble(col++)
            tariff.price = cursor.getDouble(col++)
            tariff.payment = cursor.getDouble(col)
            tariffs.add(tariff)
        }
        return tariffs
    }
}