package mx.ipn.proyectofinal

import android.os.Bundle
import androidx.activity.OnBackPressedCallback
import androidx.appcompat.app.ActionBarDrawerToggle
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.GravityCompat
import androidx.core.view.ViewCompat
import androidx.core.view.WindowCompat
import androidx.core.view.WindowInsetsCompat
import androidx.navigation.NavController
import androidx.navigation.NavOptions
import androidx.navigation.fragment.NavHostFragment
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import mx.ipn.proyectofinal.data.db.AppDatabase
import mx.ipn.proyectofinal.data.db.SeedData
import mx.ipn.proyectofinal.databinding.ActivityMainBinding

/**
 * Activity única de la app. Contiene:
 *  - Toolbar + menú hamburguesa (8 opciones)
 *  - Bottom Navigation (Inicio, Consultar, Salir)
 *  - NavHost donde se cargan los fragmentos (las pantallas)
 *
 * Si necesitas tocar este archivo, avisa al equipo.
 */
class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    private lateinit var navController: NavController

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        // Con targetSdk 35+ la app se dibuja debajo de las barras del sistema.
        // Aquí se deja espacio arriba/lados para que la Toolbar no quede tapada.
        WindowCompat.setDecorFitsSystemWindows(window, false)

        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        setSupportActionBar(binding.toolbar)

        ViewCompat.setOnApplyWindowInsetsListener(binding.contentRoot) { view, insets ->
            val bars = insets.getInsets(
                WindowInsetsCompat.Type.systemBars() or WindowInsetsCompat.Type.displayCutout()
            )
            view.setPadding(bars.left, bars.top, bars.right, 0)
            insets
        }

        // Datos de ejemplo (solo se insertan si las tablas están vacías)
        CoroutineScope(Dispatchers.IO).launch {
            SeedData.insertIfEmpty(AppDatabase.get(applicationContext))
        }

        val navHost = supportFragmentManager
            .findFragmentById(R.id.nav_host_fragment) as NavHostFragment
        navController = navHost.navController

        // Ícono de hamburguesa que abre/cierra el drawer
        val toggle = ActionBarDrawerToggle(
            this, binding.drawerLayout, binding.toolbar,
            R.string.open_drawer, R.string.close_drawer
        )
        binding.drawerLayout.addDrawerListener(toggle)
        toggle.syncState()

        // Menú hamburguesa
        binding.navView.setNavigationItemSelectedListener { item ->
            binding.drawerLayout.closeDrawers()
            onMenuItemSelected(item.itemId)
        }

        // Bottom navigation
        binding.bottomNav.setOnItemSelectedListener { item ->
            onMenuItemSelected(item.itemId)
        }

        // Al cambiar de pantalla: actualiza el título y la opción marcada
        navController.addOnDestinationChangedListener { _, destination, _ ->
            supportActionBar?.title = destination.label
            binding.navView.menu.findItem(destination.id)?.isChecked = true
            binding.bottomNav.menu.findItem(destination.id)?.isChecked = true
        }

        // Botón atrás: primero cierra el drawer si está abierto
        onBackPressedDispatcher.addCallback(this, object : OnBackPressedCallback(true) {
            override fun handleOnBackPressed() {
                if (binding.drawerLayout.isDrawerOpen(GravityCompat.START)) {
                    binding.drawerLayout.closeDrawers()
                } else {
                    isEnabled = false
                    onBackPressedDispatcher.onBackPressed()
                }
            }
        })
    }

    /** Los ids de los menús son iguales a los ids de destino en nav_graph.xml. */
    private fun onMenuItemSelected(itemId: Int): Boolean {
        if (itemId == R.id.nav_exit) {
            showExitDialog()
            return false
        }
        if (navController.currentDestination?.id == itemId) return true

        val options = NavOptions.Builder()
            .setLaunchSingleTop(true)
            .setPopUpTo(R.id.nav_home, false) // no acumula pantallas en la pila
            .build()
        navController.navigate(itemId, null, options)
        return true
    }

    private fun showExitDialog() {
        MaterialAlertDialogBuilder(this)
            .setTitle(R.string.exit_dialog_title)
            .setMessage(R.string.exit_dialog_message)
            .setPositiveButton(R.string.yes) { _, _ -> finishAffinity() }
            .setNegativeButton(R.string.no, null)
            .show()
    }
}
