package mx.ipn.proyectofinal.data.entity

import androidx.room.Entity
import androidx.room.Index
import androidx.room.PrimaryKey

enum class EventStatus { PENDIENTE, REALIZADO, APLAZADO }

enum class ReminderOption { AL_MOMENTO, DIEZ_MIN_ANTES, UN_DIA_ANTES, SIN_RECORDATORIO }

/**
 * Un evento del organizador.
 * - dateTime: fecha y hora juntas, en milisegundos (como Calendar.timeInMillis)
 * - category: se guarda el NOMBRE de la categoría, así si se elimina en Configuración
 *   los eventos viejos no se rompen
 */
@Entity(tableName = "events", indices = [Index("dateTime")])
data class Event(
    @PrimaryKey(autoGenerate = true) val id: Long = 0,
    val category: String,
    val dateTime: Long,
    val description: String,
    val status: EventStatus = EventStatus.PENDIENTE,
    val locationAddress: String = "",
    val latitude: Double? = null,
    val longitude: Double? = null,
    val contactName: String = "",
    val contactUri: String? = null,   // URI del contacto en el teléfono
    val reminder: ReminderOption = ReminderOption.SIN_RECORDATORIO
)
