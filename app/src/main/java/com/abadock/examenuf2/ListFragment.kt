package com.abadock.examenuf2

import androidx.fragment.app.viewModels
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.abadock.examenuf2.databinding.FragmentListBinding
import com.abadock.examenuf2.databinding.FragmentLoginBinding

class ListFragment : Fragment() {

    companion object {
        fun newInstance() = ListFragment()
    }

    private val viewModel: ListViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        // TODO: Use the ViewModel
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        val binding = FragmentListBinding.inflate(inflater)

        binding.button.setOnClickListener{
            viewModel.newTeacher("Jaime", "Pedret", "12312412X", "Nateja", "jpedret@gmail.com")
        }

        return binding.root
    }
}