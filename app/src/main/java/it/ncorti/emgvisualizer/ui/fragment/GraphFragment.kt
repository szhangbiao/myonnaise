package it.ncorti.emgvisualizer.ui.fragment

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.ncorti.myonnaise.MYO_CHANNELS
import com.ncorti.myonnaise.MYO_MAX_VALUE
import com.ncorti.myonnaise.MYO_MIN_VALUE
import dagger.hilt.android.AndroidEntryPoint
import it.ncorti.emgvisualizer.ui.base.BaseFragment
import it.ncorti.emgvisualizer.databinding.LayoutGraphBinding
import it.ncorti.emgvisualizer.ui.contract.GraphContract
import javax.inject.Inject

@AndroidEntryPoint
class GraphFragment : BaseFragment<GraphContract.Presenter>(), GraphContract.View {

    companion object {
        fun newInstance() = GraphFragment()
    }

    private lateinit var binding: LayoutGraphBinding

    @Inject
    lateinit var graphPresenter: GraphContract.Presenter

    override fun onAttach(context: Context) {
        super.onAttach(context)
        attachPresenter(graphPresenter)
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        binding = LayoutGraphBinding.inflate(inflater, container, false)
        setHasOptionsMenu(true)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.sensorGraphView.channels = MYO_CHANNELS
        binding.sensorGraphView.maxValue = MYO_MAX_VALUE
        binding.sensorGraphView.minValue = MYO_MIN_VALUE
    }

    override fun showData(data: FloatArray) {
        binding.sensorGraphView.addPoint(data)
    }

    override fun startGraph(running: Boolean) {
        binding.sensorGraphView.apply {
            this.running = running
        }
    }

    override fun showNoStreamingMessage() {
        binding.textEmptyGraph.visibility = View.VISIBLE
    }

    override fun hideNoStreamingMessage() {
        binding.textEmptyGraph.visibility = View.INVISIBLE
    }
}
