package com.ralphevmanzano.apps.home.presentation

import android.widget.Toast
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import com.ralphevmanzano.apps.common.extensions.hide
import com.ralphevmanzano.apps.common.extensions.show
import com.ralphevmanzano.apps.core.base.BaseFragment
import com.ralphevmanzano.apps.home.databinding.FragmentHomeBinding
import com.ralphevmanzano.apps.home.navigation.HomeNavigation
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class HomeFragment : BaseFragment<FragmentHomeBinding>() {

    private val viewModel by viewModels<HomeViewModel>()

    override fun getViewBinding() = FragmentHomeBinding.inflate(layoutInflater)

    @Inject
    lateinit var homeNavigation: HomeNavigation

    private lateinit var adapter: MentorsAdapter

    override fun setupViews() = with(binding) {
        adapter = MentorsAdapter()
        adapter.onItemClick = { homeNavigation.navigateToDetails(it.login) }

        rvMentors.layoutManager = LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false)
        rvMentors.adapter = adapter
    }

    override fun observeData() = with(binding) {
        observeResourceData(
            viewModel.mentorsResult,
            onLoading = {
                pbLoading.show()
                rvMentors.hide()
            }, onSuccess = {
                pbLoading.hide()
                rvMentors.show()
                if (!it.isNullOrEmpty()) adapter.submitList(it)
            }, onError = {
                pbLoading.hide()
                rvMentors.hide()
                Toast.makeText(context, it.first, Toast.LENGTH_LONG).show()
            }
        )
    }
}