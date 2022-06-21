package com.example.reposearch

import android.net.Uri
import android.os.Bundle
import android.widget.SearchView
import android.widget.Toast
import androidx.browser.customtabs.CustomTabsIntent
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.reposearch.adapters.RepoAdapter
import com.example.reposearch.databinding.ActivityMainBinding
import com.example.reposearch.fragments.OrderBottomSheet
import com.example.reposearch.ui.RecyclerViewClickListener
import com.example.reposearch.ui.RepoViewModel
import org.koin.android.viewmodel.ext.android.viewModel


class MainActivity : androidx.appcompat.app.AppCompatActivity(), SearchView.OnQueryTextListener,
    RecyclerViewClickListener {
    private val repoViewModel: RepoViewModel by viewModel()

    private lateinit var binding: ActivityMainBinding

    private var definitionAdapter = RepoAdapter(this)

    private var orderBottomSheet = OrderBottomSheet()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = DataBindingUtil.setContentView(this, R.layout.activity_main)
        binding.lifecycleOwner = this@MainActivity
        binding.repoVM = repoViewModel

        binding.svTerm.isIconified = false
        binding.svTerm.requestFocus()

        repoViewModel.reposListLD.observe(this) { definitionsList ->
            if (definitionsList?.isNotEmpty() == true) {
                definitionAdapter.diff.submitList(definitionsList)
            }
        }

        repoViewModel.error.observe(this) { message ->
            Toast.makeText(this@MainActivity, message, Toast.LENGTH_LONG)
                .show()
        }

        binding.rvRepos.apply {
            layoutManager = LinearLayoutManager(this@MainActivity)
            setHasFixedSize(true)
            adapter = definitionAdapter
        }

        binding.btnOrderBy.setOnClickListener {
            orderBottomSheet.apply {
                show(supportFragmentManager, "")
            }
        }

        binding.svTerm.setOnQueryTextListener(this)
    }

    override fun onQueryTextSubmit(query: String?): Boolean {
        query?.let {
            repoViewModel.searchOrg(it)
        }
        return false
    }

    override fun onQueryTextChange(newText: String?): Boolean {
        return false
    }

    override fun onRecyclerViewItemClick(url: String) {
        val builder = CustomTabsIntent.Builder()
        val customTabsIntent = builder.build()
        customTabsIntent.launchUrl(this, Uri.parse(url))
    }

}
