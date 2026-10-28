package mx.ipn.proyectofinal.ui.calendar

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import mx.ipn.proyectofinal.databinding.FragmentCalendarBinding

/**
 * Pantalla MOSTRAR CALENDARIO.
 *
 * Por ahora es solo un molde vacío con ViewBinding.
 * La lógica de la pantalla va en onViewCreated().
 */
class CalendarFragment : Fragment() {

    // ViewBinding: _binding solo existe entre onCreateView y onDestroyView
    private var _binding: FragmentCalendarBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View {
        _binding = FragmentCalendarBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        // TODO: configurar la pantalla (listas, botones, observar datos...)
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null // evita fugas de memoria
    }
}
