package com.example.ejemplo_eventos_nosql

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import com.example.ejemplo_eventos_nosql.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    private lateinit var adapter: TareaAdapter
    private lateinit var viewModel: TareaViewModel

    var tareaEdit = Tarea()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        viewModel = ViewModelProvider(this)[TareaViewModel::class.java]

        viewModel.listaTareas.observe(this){tareas ->
            setupRecyclerView(tareas)
        }

        binding.btnAgregarTarea.setOnClickListener{
            val tarea = Tarea(
                titulo = binding.TTuloTarea.text.toString(),
                descripcion = binding.DescripciNTarea.text.toString()
            )

            viewModel.agregarTarea(tarea)

            binding.TTuloTarea.setText("")
            binding.DescripciNTarea.setText("")
        }

        binding.btnActualizarTarea.setOnClickListener{
            tareaEdit.titulo = ""
            tareaEdit.descripcion = ""

            tareaEdit.titulo = binding.TTuloTarea.text.toString()
            tareaEdit.descripcion = binding.DescripciNTarea.text.toString()

            viewModel.actualizarTarea(tareaEdit)

            adapter.notifyDataSetChanged()

            binding.TTuloTarea.setText("")
            binding.DescripciNTarea.setText("")
        }
    }

    fun setupRecyclerView(listaTarea: List<Tarea>){
        adapter = TareaAdapter(listaTarea, ::borrarTarea, ::actualizarTarea)
        binding.rVTareas.adapter = adapter
    }

    fun borrarTarea(id: String){
        viewModel.borrarTarea(id)
    }

    fun actualizarTarea(tarea: Tarea){
        tareaEdit = tarea

        binding.TTuloTarea.setText(tareaEdit.titulo)
        binding.DescripciNTarea.setText(tareaEdit.descripcion)
    }
}