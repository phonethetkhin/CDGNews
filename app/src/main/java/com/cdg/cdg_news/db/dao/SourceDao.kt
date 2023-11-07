package com.cdg.cdg_news.db.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.cdg.cdg_news.db.entity.SourceEntity

@Dao
interface SourceDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAllSources(sources: List<SourceEntity>)

    @Query("SELECT * FROM tbl_source")
    suspend fun getAllSources(): List<SourceEntity>

}