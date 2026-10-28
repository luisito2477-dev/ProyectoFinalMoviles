package mx.ipn.proyectofinal.data.db

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import mx.ipn.proyectofinal.data.dao.CategoryDao
import mx.ipn.proyectofinal.data.dao.EventDao
import mx.ipn.proyectofinal.data.entity.Category
import mx.ipn.proyectofinal.data.entity.Event

/**
 * Base de datos SQLite (Room). Singleton: usar AppDatabase.get(context).
 * Si cambian las columnas de una entidad: subir "version" y desinstalar la app
 * del celular para que se recree la BD.
 */
@Database(entities = [Event::class, Category::class], version = 1, exportSchema = false)
abstract class AppDatabase : RoomDatabase() {

    abstract fun eventDao(): EventDao
    abstract fun categoryDao(): CategoryDao

    companion object {
        @Volatile private var INSTANCE: AppDatabase? = null

        fun get(context: Context): AppDatabase =
            INSTANCE ?: synchronized(this) {
                Room.databaseBuilder(
                    context.applicationContext, AppDatabase::class.java, "organizador.db"
                ).build().also { INSTANCE = it }
            }
    }
}
