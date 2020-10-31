package com.enesarabaci.odevapp.service

import androidx.room.*
import com.enesarabaci.odevapp.model.Odev

@Dao
interface OdevDAO {

    @Insert
    suspend fun insert(odev : Odev) : Long

    @Query("SELECT * FROM odev WHERE uuid = :id")
    suspend fun getOdev(id : Int) : Odev

    @Query("SELECT * FROM odev WHERE ders = :ders")
    suspend fun getOdevDers(ders : String) : List<Odev>

    @Query("DELETE FROM odev WHERE uuid = :id")
    suspend fun deleteOdev(id : Int)

    @Query("SELECT * FROM odev")
    suspend fun selectAllOdev() : List<Odev>

    @Query("DELETE FROM odev")
    suspend fun deleteAllOdev()

    @Query("UPDATE odev SET status = :status WHERE uuid = :id ")
    suspend fun updateOdev(id : Int, status : String)



}