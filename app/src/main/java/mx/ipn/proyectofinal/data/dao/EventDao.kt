package mx.ipn.proyectofinal.data.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update
import kotlinx.coroutines.flow.Flow
import mx.ipn.proyectofinal.data.entity.Event

@Dao
interface EventDao {
    @Insert suspend fun insert(event: Event): Long
    @Update suspend fun update(event: Event)
    @Delete suspend fun delete(event: Event)

    /** Eventos en un rango de fechas. category = null -> todas las categorías. */
    @Query(
        "SELECT * FROM events WHERE dateTime BETWEEN :start AND :end " +
        "AND (:category IS NULL OR category = :category) ORDER BY dateTime"
    )
    fun getBetween(start: Long, end: Long, category: String?): Flow<List<Event>>

    /** Para el respaldo y para el calendario. */
    @Query("SELECT * FROM events ORDER BY dateTime")
    suspend fun getAll(): List<Event>

    @Query("SELECT COUNT(*) FROM events") suspend fun count(): Int
    @Query("DELETE FROM events") suspend fun deleteAll()
    @Insert suspend fun insertAll(events: List<Event>)
}
