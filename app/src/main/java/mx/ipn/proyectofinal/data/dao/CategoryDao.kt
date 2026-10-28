package mx.ipn.proyectofinal.data.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import kotlinx.coroutines.flow.Flow
import mx.ipn.proyectofinal.data.entity.Category

@Dao
interface CategoryDao {
    @Query("SELECT * FROM categories ORDER BY id") fun getAll(): Flow<List<Category>>
    @Query("SELECT COUNT(*) FROM categories") suspend fun count(): Int
    @Insert(onConflict = OnConflictStrategy.IGNORE) suspend fun insert(category: Category): Long
    @Delete suspend fun delete(category: Category)
}
