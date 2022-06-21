package com.example.reposearch.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.reposearch.databinding.MenuBottomSheetBinding
import com.example.reposearch.ui.RepoViewModel
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import org.koin.android.viewmodel.ext.android.sharedViewModel

class OrderBottomSheet : BottomSheetDialogFragment() {

    private val repoViewModel : RepoViewModel by sharedViewModel()
    private lateinit var binding : MenuBottomSheetBinding

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = MenuBottomSheetBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.btnOrderDefault.setOnClickListener {
            repoViewModel.originalList()
        }
        binding.btnOrderUpvotes.setOnClickListener {
            repoViewModel.sortByUpvotes()
        }
        binding.btnOrderDownvotes.setOnClickListener {
            repoViewModel.sortByDownvotes()
        }

    }
}