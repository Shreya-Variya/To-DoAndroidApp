package com.example.todoapp

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import com.example.todoapp.databinding.FragmentInsertTaskBinding
import com.example.todoapp.databinding.FragmentUpdateTaskBinding
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

class UpdateTaskFragment : Fragment() {

    lateinit var binding: FragmentUpdateTaskBinding

    private lateinit var database: RoomDB
    private lateinit var dao: DAO
    private var param1: String? = null
    private var param2: String? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            param1 = it.getString(ARG_PARAM1)
            param2 = it.getString(ARG_PARAM2)
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentUpdateTaskBinding.inflate(inflater, container, false)

        return binding.root
        // Inflate the layout for this fragment
//        return inflater.inflate(R.layout.fragment_update_task, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        //Create object of RoomDB
        database = RoomDB.getDatabase(requireContext())
        dao = database.dao()

        var taskId = arguments?.getInt("taskId") ?: -1
//        Toast.makeText(requireContext(), "TaskId : $taskId", Toast.LENGTH_SHORT).show()

        if (taskId != -1){
        dao.getTitleById(taskId).observe(viewLifecycleOwner){
            title ->
//            Toast.makeText(requireContext(), "Title : $title", Toast.LENGTH_SHORT).show()
            binding.etUpdateTask.setText(title)
        }

        binding.btnUpdateTask.setOnClickListener {
            lifecycleScope.launch {
                dao.updateTask(Entity(taskId, binding.etUpdateTask.text.toString()))
            }
            findNavController().navigate(R.id.action_updateTaskFragment_to_displayTaskFragment)
        }
        }

        binding.btnDeleteTask.setOnClickListener {
            lifecycleScope.launch {
                dao.deleteOne(Entity(taskId, binding.etUpdateTask.text.toString()))
            }
            findNavController().navigate(R.id.action_updateTaskFragment_to_displayTaskFragment)
        }
    }

    companion object {
        @JvmStatic
        fun newInstance(param1: String, param2: String) =
            UpdateTaskFragment().apply {
                arguments = Bundle().apply {
                    putString(ARG_PARAM1, param1)
                    putString(ARG_PARAM2, param2)
                }
            }
    }
}