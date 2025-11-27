package com.example.todoapp

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import androidx.room.Room
import androidx.room.RoomDatabase
import com.example.todoapp.databinding.FragmentInsertTaskBinding
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch

private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

class InsertTaskFragment : Fragment() {
    lateinit var binding: FragmentInsertTaskBinding

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
        binding = FragmentInsertTaskBinding.inflate(inflater,container,false)

        return binding.root
        // Inflate the layout for this fragment
//        return inflater.inflate(R.layout.fragment_insert_task, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        //Create object of RoomDB
        database = RoomDB.getDatabase(requireContext())
        dao = database.dao()

        binding.btnSaveTask.setOnClickListener {
            var title = binding.etTask.text.toString()
            if (title.isNotBlank()){
                lifecycleScope.launch {
                    dao.insertTask(Entity(title = title))
//                    Toast.makeText(requireContext(), "Data inserted Successfully : $id", Toast.LENGTH_SHORT).show()
                }
            }
//            findNavController().popBackStack()
            findNavController().navigate(R.id.action_insertTaskFragment_to_displayTaskFragment)
        }

    }

    companion object {
        @JvmStatic
        fun newInstance(param1: String, param2: String) =
            InsertTaskFragment().apply {
                arguments = Bundle().apply {
                    putString(ARG_PARAM1, param1)
                    putString(ARG_PARAM2, param2)
                }
            }
    }
}