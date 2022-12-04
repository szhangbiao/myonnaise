package it.ncorti.emgvisualizer.ui.fragment

import android.animation.Animator
import android.animation.AnimatorListenerAdapter
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import android.widget.Toast
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.DefaultItemAnimator
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.button.MaterialButton
import dagger.hilt.android.AndroidEntryPoint
import it.ncorti.emgvisualizer.ui.base.BaseFragment
import it.ncorti.emgvisualizer.R
import it.ncorti.emgvisualizer.databinding.LayoutScanDeviceBinding
import it.ncorti.emgvisualizer.ui.contract.ScanDeviceContract
import it.ncorti.emgvisualizer.ui.MainActivity
import it.ncorti.emgvisualizer.model.Device
import it.ncorti.emgvisualizer.ui.ConvertActivity
import javax.inject.Inject

const val ADD_ITEM_FADE_MS: Long = 1000

@AndroidEntryPoint
class ScanDeviceFragment : BaseFragment<ScanDeviceContract.Presenter>(), ScanDeviceContract.View {

    companion object {
        fun newInstance() = ScanDeviceFragment()
    }

    @Inject
    lateinit var scanDevicePresenter: ScanDeviceContract.Presenter

    private var listDeviceAdapter: DeviceAdapter? = null

    private lateinit var binding: LayoutScanDeviceBinding

    override fun onAttach(context: Context) {
        super.onAttach(context)
        attachPresenter(scanDevicePresenter)
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        binding = LayoutScanDeviceBinding.inflate(inflater, container, false)
        setHasOptionsMenu(true)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.fabScan.setOnClickListener { scanDevicePresenter.onScanToggleClicked() }
        binding.fabConvert.setOnClickListener {
            startActivity(Intent(requireContext(), ConvertActivity::class.java))
        }
        listDeviceAdapter = DeviceAdapter(object : DeviceSelectedListener {
            override fun onDeviceSelected(v: View, position: Int) {
                scanDevicePresenter.onDeviceSelected(position)
            }
        })
        binding.listDeviceFound.layoutManager = LinearLayoutManager(this.context)
        binding.listDeviceFound.itemAnimator = FadeInAnimator()
        binding.listDeviceFound.adapter = listDeviceAdapter
    }

    override fun showStartMessage() {
        binding.textEmptyList.text = getString(R.string.first_do_a_scan)
        binding.textEmptyList.visibility = View.VISIBLE
    }

    override fun showEmptyListMessage() {
        binding.textEmptyList.text = getString(R.string.no_myo_found)
        binding.textEmptyList.visibility = View.VISIBLE
    }

    override fun hideEmptyListMessage() {
        binding.textEmptyList.visibility = View.INVISIBLE
    }

    override fun populateDeviceList(list: List<Device>) {
        listDeviceAdapter?.deviceList = list.toMutableList()
        listDeviceAdapter?.notifyDataSetChanged()
    }

    override fun addDeviceToList(device: Device) {
        listDeviceAdapter?.deviceList?.add(device)
        listDeviceAdapter?.notifyItemInserted(listDeviceAdapter?.itemCount ?: 0)
    }

    override fun wipeDeviceList() {
        listDeviceAdapter?.deviceList = mutableListOf()
        listDeviceAdapter?.notifyItemRangeRemoved(0, listDeviceAdapter?.itemCount ?: 0)
    }

    override fun showScanLoading() {
        binding.progressBarSearch.animate().alpha(1.0f)
        binding.fabScan.setImageDrawable(ContextCompat.getDrawable(requireContext(), R.drawable.ic_stop))
    }

    override fun hideScanLoading() {
        binding.progressBarSearch.animate()?.alpha(0.0f)
        binding.fabScan.setImageDrawable(ContextCompat.getDrawable(requireContext(), R.drawable.ic_magnify))
    }

    override fun showScanError() {
        Toast.makeText(this.context, getString(R.string.scan_failed), Toast.LENGTH_SHORT).show()
    }

    override fun showScanCompleted() {
        Toast.makeText(this.context, getString(R.string.scan_completed), Toast.LENGTH_SHORT).show()
    }

    override fun navigateToControlDevice() {
        (activity as MainActivity).navigateToPage(1)
    }

    class DeviceAdapter(
        private val deviceSelectedListener: DeviceSelectedListener
    ) : RecyclerView.Adapter<DeviceAdapter.DeviceViewHolder>() {

        var deviceList = mutableListOf<Device>()

        override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) = DeviceViewHolder(
            LayoutInflater.from(parent.context)
                .inflate(R.layout.item_device, parent, false),
            deviceSelectedListener
        )

        override fun getItemCount() = deviceList.size

        override fun onBindViewHolder(holder: DeviceViewHolder, position: Int) {
            holder.bind(deviceList[position])
        }

        class DeviceViewHolder(val item: View, private val listener: DeviceSelectedListener) : RecyclerView.ViewHolder(item) {
            fun bind(device: Device) {
                item.findViewById<TextView>(R.id.text_name).text = device.name ?: item.context.getString(R.string.unknown_device)
                item.findViewById<TextView>(R.id.text_address).text = device.address
                item.findViewById<MaterialButton>(R.id.button_select).setOnClickListener {
                    listener.onDeviceSelected(it, adapterPosition)
                }
            }
        }
    }

    interface DeviceSelectedListener {
        fun onDeviceSelected(v: View, position: Int)
    }

    inner class FadeInAnimator : DefaultItemAnimator() {

        override fun animateAdd(viewHolder: RecyclerView.ViewHolder): Boolean {
            if (viewHolder is DeviceAdapter.DeviceViewHolder) {
                viewHolder.item.alpha = 0.0f
                viewHolder.itemView.animate()
                    .alpha(1.0f)
                    .setDuration(ADD_ITEM_FADE_MS)
                    .setListener(object : AnimatorListenerAdapter() {
                        override fun onAnimationEnd(animation: Animator) {
                            super.onAnimationEnd(animation)
                            dispatchAddFinished(viewHolder)
                        }
                    })
                    .start()
            }
            return false
        }
    }
}
