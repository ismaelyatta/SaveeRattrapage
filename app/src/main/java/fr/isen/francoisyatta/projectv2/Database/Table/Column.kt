package fr.isen.francoisyatta.projectv2.Database.Table


class Column(val name: String, val type: Type) {
        var isAutoIncrement = false
        var isForeignKeyConstraint = false
        var isNullable = true
        var isPrimaryKey = false
        val isUnique = false
        var foreignTable: String? = null
        var foreignColumn: String? = null

        enum class Type {
            INTEGER, REAL, TEXT
        }
    }