package mx.ipn.proyectofinal.data.db

import mx.ipn.proyectofinal.data.entity.Category
import mx.ipn.proyectofinal.data.entity.Event
import mx.ipn.proyectofinal.data.entity.EventStatus
import mx.ipn.proyectofinal.data.entity.ReminderOption
import java.util.Calendar

/** Datos de ejemplo para la primera revisión. Solo se insertan si las tablas están vacías. */
object SeedData {

    /** Milisegundos de "hoy + N días" a cierta hora. */
    private fun inDays(days: Int, hour: Int, minute: Int): Long =
        Calendar.getInstance().apply {
            add(Calendar.DAY_OF_YEAR, days)
            set(Calendar.HOUR_OF_DAY, hour)
            set(Calendar.MINUTE, minute)
            set(Calendar.SECOND, 0)
            set(Calendar.MILLISECOND, 0)
        }.timeInMillis

    suspend fun insertIfEmpty(db: AppDatabase) {
        if (db.categoryDao().count() == 0) {
            listOf("Reunión", "Entrega de proyecto", "Práctica", "Examen", "Otro")
                .forEach { db.categoryDao().insert(Category(name = it, isDefault = true)) }
        }
        if (db.eventDao().count() == 0) {
            db.eventDao().insertAll(
                listOf(
                    Event(
                        category = "Reunión", dateTime = inDays(0, 10, 0),
                        description = "Revisión del primer avance", contactName = "Alejandro",
                        locationAddress = "Auditorio Principal",
                        reminder = ReminderOption.DIEZ_MIN_ANTES
                    ),
                    Event(
                        category = "Examen", dateTime = inDays(1, 8, 30),
                        description = "Examen de minería de datos",
                        locationAddress = "Salón 3101"
                    ),
                    Event(
                        category = "Entrega de proyecto", dateTime = inDays(2, 23, 59),
                        description = "Subir proyecto a Teams", status = EventStatus.APLAZADO
                    ),
                    Event(
                        category = "Práctica", dateTime = inDays(3, 12, 0),
                        description = "Práctica de laboratorio", contactName = "Profe",
                        status = EventStatus.REALIZADO
                    ),
                    Event(
                        category = "Otro", dateTime = inDays(4, 17, 0),
                        description = "Cita con el dentista", locationAddress = "Centro de salud"
                    ),
                    Event(
                        category = "Reunión", dateTime = inDays(-2, 16, 0),
                        description = "Junta de equipo", status = EventStatus.REALIZADO
                    )
                )
            )
        }
    }
}
