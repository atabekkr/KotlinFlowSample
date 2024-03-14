package com.example.kotlinflowsample.presentation

import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.isVisible
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import com.example.kotlinflowsample.data.network.Status
import com.example.kotlinflowsample.databinding.ActivityMainBinding
import com.example.kotlinflowsample.presentation.viewmodel.CommentViewModel
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach

class MainActivity : AppCompatActivity() {

    private val binding by lazy {
        ActivityMainBinding.inflate(layoutInflater)
    }

    private lateinit var viewModel: CommentViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

        setupData()
        setupListeners()
        setupObservers()

    }

    private fun setupData() {
        viewModel = ViewModelProvider(this)[CommentViewModel::class.java]
    }

    private fun setupListeners() {
        binding.button.setOnClickListener {

            val textId = binding.searchEditText.text
            if (textId.isNullOrEmpty())
                Toast.makeText(this, "Query cannot by empty", Toast.LENGTH_SHORT).show()
            else
                viewModel.getNewComment(textId.toString().toInt())
        }
    }

    private fun setupObservers() {


        viewModel.commentState.onEach {

            when (it.status) {

                Status.LOADING -> {
                    binding.progressBar.isVisible = true
                }

                Status.SUCCESS -> {
                    binding.progressBar.isVisible = false
                    it.data?.let { comment ->
                        binding.commentIdTextview.text = comment.id.toString()
                        binding.nameTextview.text = comment.name
                        binding.emailTextview.text = comment.email
                        binding.commentTextview.text = comment.comment
                    }
                }

                else -> {
                    binding.progressBar.isVisible = false
                    Toast.makeText(this, it.message, Toast.LENGTH_SHORT).show()
                }
            }

        }.launchIn(lifecycleScope)

    }
}