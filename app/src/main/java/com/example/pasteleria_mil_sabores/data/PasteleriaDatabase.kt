package com.example.pasteleria_mil_sabores.data

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.sqlite.db.SupportSQLiteDatabase
import com.example.pasteleria_mil_sabores.model.CartItem
import com.example.pasteleria_mil_sabores.model.DetalleTransaccion
import com.example.pasteleria_mil_sabores.model.Producto
import com.example.pasteleria_mil_sabores.model.Transaccion
import com.example.pasteleria_mil_sabores.model.Usuario
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

@Database(
    entities = [
        Usuario::class,
        Producto::class,
        Transaccion::class,
        DetalleTransaccion::class,
        CartItem::class
    ],
    version = 6
)
abstract class PasteleriaDatabase : RoomDatabase() {
    abstract fun usuarioDao(): UsuarioDao
    abstract fun productoDao(): ProductoDao
    abstract fun transaccionDao(): TransaccionDao
    abstract fun detalleTransaccionDao(): DetalleTransaccionDao

    abstract fun cartDao(): CartDao

    companion object {
        private var database: PasteleriaDatabase? = null

        fun getDatabase(context: Context): PasteleriaDatabase {
            if (database == null) {
                database = Room.databaseBuilder(
                    context,
                    PasteleriaDatabase::class.java,
                    "pasteleria_mil_sabores.db"
                )
                    .fallbackToDestructiveMigration()
                    .addCallback(object : Callback() {
                    override fun onCreate(db: SupportSQLiteDatabase) {
                        super.onCreate(db)
                        CoroutineScope(Dispatchers.IO).launch {
                            insertarDatosPorDefecto(database!!)
                        }
                    }
                })
                    .setJournalMode(JournalMode.TRUNCATE)
                    .build()
            }
            return database!!
        }

        private suspend fun insertarDatosPorDefecto(db: PasteleriaDatabase) {
            val usuarioDao = db.usuarioDao()

            val usuarios = listOf(
                Usuario(nombre = "Admin", correo = "admin@milsabores.cl", contrasena = "admin123"),
                Usuario(nombre = "Cliente Demo", correo = "cliente@milsabores.cl", contrasena = "1234")
            )
            usuarios.forEach { usuarioDao.insertar(it) }

            val productoDao = db.productoDao()

            val productos = listOf(
                Producto(
                    nombre = "Torta Cuadrada de Chocolate",
                    categoria = "Tortas Cuadradas",
                    precio = 45000,
                    descripcion = "Deliciosa torta de chocolate con capas de ganache y un toque de avellanas. Personalizable con mensajes especiales.",
                    imagen = "tortachocolate.jpg"
                ),
                Producto(
                    nombre = "Torta Cuadrada de Frutas",
                    categoria = "Tortas Cuadradas",
                    precio = 50000,
                    descripcion = "Una mezcla de frutas frescas y crema chantilly sobre un suave bizcocho de vainilla, ideal para celebraciones.",
                    imagen = "tortafrutas.jpg"
                ),
                Producto(
                    nombre = "Torta Circular de Vainilla",
                    categoria = "Tortas Circulares",
                    precio = 40000,
                    descripcion = "Bizcocho de vainilla clásico relleno con crema pastelera y cubierto con un glaseado dulce, perfecto para cualquier ocasión.",
                    imagen = ""
                ),
                Producto(
                    nombre = "Torta Circular de Manjar",
                    categoria = "Tortas Circulares",
                    precio = 42000,
                    descripcion = "Torta tradicional chilena con manjar y nueces, un deleite para los amantes de los sabores dulces y clásicos.",
                    imagen = ""
                )
            )

            productos.forEach { productoDao.insertar(it) }
        }
    }
}