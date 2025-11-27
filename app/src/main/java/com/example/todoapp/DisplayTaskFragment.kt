package com.example.todoapp

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.todoapp.databinding.FragmentDisplayTaskBinding
import com.example.todoapp.databinding.FragmentInsertTaskBinding
import kotlinx.coroutines.launch

private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

class DisplayTaskFragment : Fragment() {
    lateinit var binding: FragmentDisplayTaskBinding

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
        binding = FragmentDisplayTaskBinding.inflate(inflater, container, false)

        return binding.root
        // Inflate the layout for this fragment
//        return inflater.inflate(R.layout.fragment_display_task, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        //Create object of RoomDB
        database = RoomDB.getDatabase(requireContext())
        dao = database.dao()

        binding.btnAdd.setOnClickListener {
            findNavController().navigate(R.id.action_displayTaskFragment_to_insertTaskFragment)
        }

        binding.btnDeleteAll.setOnClickListener {
            lifecycleScope.launch {
                dao.deleteAll()
//                Toast.makeText(requireContext(), "All Data deleted.", Toast.LENGTH_SHORT).show()
            }
        }

        setRecyclerView()
    }

    /**
     *
     */
    fun setRecyclerView(){
        val adapter = Adapter(emptyList())
        binding.rcView.adapter = adapter
        binding.rcView.layoutManager = LinearLayoutManager(requireContext())

        dao.getTask().observe(viewLifecycleOwner){
            list -> adapter.updateData(list)
        }
    }

    companion object {
        @JvmStatic
        fun newInstance(param1: String, param2: String) =
            DisplayTaskFragment().apply {
                arguments = Bundle().apply {
                    putString(ARG_PARAM1, param1)
                    putString(ARG_PARAM2, param2)
                }
            }
    }
}