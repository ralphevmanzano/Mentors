package com.ralphevmanzano.apps.core.base

import android.content.DialogInterface
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AlertDialog
import androidx.fragment.app.Fragment
import androidx.lifecycle.LiveData
import androidx.viewbinding.ViewBinding
import com.ralphevmanzano.apps.domain.model.Resource
import com.ralphevmanzano.apps.domain.model.Status

abstract class BaseFragment<VB : ViewBinding> : Fragment() {

    protected lateinit var binding: VB
    protected abstract fun getViewBinding(): VB

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = getViewBinding()
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupViews()
        observeData()
    }

    fun <T> observeLiveData(liveData: LiveData<T>, doNext: (T) -> Unit) {
        liveData.observe(viewLifecycleOwner) {
            doNext(it)
        }
    }

    open fun setupViews() {}
    open fun observeData() {}

    fun <T> observeResourceData(
        liveData: LiveData<Resource<T>>,
        onSuccess: (T) -> Unit,
        onError: (Pair<String, String>) -> Unit,
        onLoading: () -> Unit
    ) {
        liveData.observe(viewLifecycleOwner) { res ->
            when (res.status) {
                Status.LOADING -> onLoading()
                Status.SUCCESS -> res.data?.let { onSuccess(it) }
                Status.ERROR -> onError(Pair(res.message.orEmpty(), res.code.orEmpty()))
            }
        }
    }

    fun showPositiveDialog(title: String, message: String, onOk: (DialogInterface, Int) -> Unit) {
        val builder = AlertDialog.Builder(requireContext())
        with(builder) {
            setTitle(title)
            setMessage(message)
            setPositiveButton(android.R.string.ok, DialogInterface.OnClickListener(function = onOk))
            show()
        }
    }
}