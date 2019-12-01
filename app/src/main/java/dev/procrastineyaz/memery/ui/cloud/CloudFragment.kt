package dev.procrastineyaz.memery.ui.cloud

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProviders
import dev.procrastineyaz.memery.R

class CloudFragment : Fragment() {

    companion object {
        fun newInstance() = CloudFragment()
    }

    private lateinit var viewModel: CloudViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.cloud_fragment, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel = ViewModelProviders.of(this).get(CloudViewModel::class.java)
        // TODO: Use the ViewModel
    }

}
