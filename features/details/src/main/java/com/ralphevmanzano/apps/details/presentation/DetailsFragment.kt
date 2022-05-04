package com.ralphevmanzano.apps.details.presentation

import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.navArgs
import androidx.recyclerview.widget.GridLayoutManager
import com.bumptech.glide.Glide
import com.ralphevmanzano.apps.common.extensions.formatDateTime
import com.ralphevmanzano.apps.common.extensions.hide
import com.ralphevmanzano.apps.common.extensions.show
import com.ralphevmanzano.apps.common.extensions.showOrHide
import com.ralphevmanzano.apps.core.base.BaseFragment
import com.ralphevmanzano.apps.details.R
import com.ralphevmanzano.apps.details.databinding.FragmentDetailsBinding
import com.ralphevmanzano.apps.details.navigation.DetailsNavigation
import com.ralphevmanzano.apps.domain.model.Details
import dagger.hilt.android.AndroidEntryPoint
import java.util.*
import javax.inject.Inject

@AndroidEntryPoint
class DetailsFragment : BaseFragment<FragmentDetailsBinding>() {

    private val viewModel by viewModels<DetailsViewModel>()
    private val args by navArgs<DetailsFragmentArgs>()

    override fun getViewBinding() = FragmentDetailsBinding.inflate(layoutInflater)

    @Inject
    lateinit var detailsNavigation: DetailsNavigation

    private lateinit var adapter: TimeSlotsAdapter

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel.getMentorDetails(args.login)
    }

    override fun setupViews() = with(binding) {
        toolbar.setNavigationIcon(R.drawable.ic_back)
        toolbar.setNavigationOnClickListener {
            detailsNavigation.backToHome()
        }

        adapter = TimeSlotsAdapter()
        rvTimeSlots.layoutManager = GridLayoutManager(context, 3, GridLayoutManager.VERTICAL, false)
        rvTimeSlots.adapter = adapter

        adapter.onItemClick = { handleOnTimeSlotClick(it) }

        btnPrev.setOnClickListener { handleOnPrevClick() }
        btnNext.setOnClickListener { viewModel.nextDate() }
        btnBook.setOnClickListener { handleOnBookClick() }
    }

    private fun handleOnTimeSlotClick(it: Pair<Int, Int>) {
        val list = adapter.currentList.map {
            val copy = it.copy()
            copy.isAvailable = it.isAvailable
            copy
        }
        if (it.first >= 0) list[it.first].isSelected = false
        list[it.second].isSelected = true
        adapter.prevSelected = it.second
        adapter.submitList(list)
        viewModel.setTimeSlot(list[it.second])
    }

    private fun handleOnPrevClick() {
        if (viewModel.selectedDate.get(Calendar.DAY_OF_YEAR) == viewModel.currentDate.get(Calendar.DAY_OF_YEAR)) return
        viewModel.prevDate()
    }

    private fun handleOnBookClick() = with(viewModel) {
        selectedTimeSlot?.let {
            selectedDate.set(Calendar.HOUR_OF_DAY, it.hour)
            selectedDate.set(Calendar.MINUTE, it.mins ?: 0)
            showPositiveDialog(
                getString(R.string.successfully_booked),
                getString(R.string.successfully_booked_message, selectedDate.time.formatDateTime()),
            ) { _, _ ->
                detailsNavigation.backToHome()
            }
        }
    }

    override fun observeData() = with(binding) {
        observeResourceData(
            viewModel.detailsResult,
            onLoading = {
                pbLoading.show()
                nsvContent.hide()
            },
            onSuccess = {
                pbLoading.hide()
                nsvContent.show()
                showContent(it)
            },
            onError = {
                pbLoading.hide()
                nsvContent.hide()
                Toast.makeText(context, it.first, Toast.LENGTH_LONG).show()
            }
        )

        observeLiveData(viewModel.dateStr) { tvDate.text = it }
        observeLiveData(viewModel.timeSlots) { adapter.submitList(it) }
        observeLiveData(viewModel.canBook) { btnBook.isEnabled = it }
    }

    private fun showContent(details: Details) = with(binding) {
        Glide.with(this@DetailsFragment).load(details.avatarUrl).circleCrop().into(ivMentor)
        tvName.text = details.name.orEmpty()
        tvLogin.text = getString(R.string.login_label, details.login)
        tvFollowers.text = details.followers.toString()
        tvFollowing.text = details.following.toString()
        tvCompany.showOrHide(extractCompany(details.company))
        tvLocation.showOrHide(details.location)
    }

    private fun extractCompany(company: String?): String? {
        val companies = company?.split(',')
        if (companies?.size ?: 0 > 0) {
            return companies?.first()
        }
        return company
    }
}