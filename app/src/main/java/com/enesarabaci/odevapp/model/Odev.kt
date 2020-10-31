package com.enesarabaci.odevapp.model

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import java.sql.Date
import java.util.*

@Entity(tableName = "odev")
class Odev(
    @ColumnInfo(name = "ders")
    var ders : String,
    @ColumnInfo(name = "day")
    var day : Int,
    @ColumnInfo(name = "month")
    var month : Int,
    @ColumnInfo(name = "year")
    var year : Int,
    @ColumnInfo(name = "konu")
    var konu : String,
    @ColumnInfo(name = "date")
    var date : Long,
    @ColumnInfo(name = "status")
    var status : String
) {
    @PrimaryKey(autoGenerate = true)
    var uuid = 0
}