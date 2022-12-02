package it.ncorti.emgvisualizer.ui.fragment

import android.Manifest.permission.WRITE_EXTERNAL_STORAGE
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.content.pm.PackageManager.PERMISSION_GRANTED
import android.os.Bundle
import android.os.Environment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.content.ContextCompat
import dagger.hilt.android.AndroidEntryPoint
import it.ncorti.emgvisualizer.ui.base.BaseFragment
import it.ncorti.emgvisualizer.R
import it.ncorti.emgvisualizer.databinding.LayoutExportBinding
import it.ncorti.emgvisualizer.ui.contract.ExportContract
import it.ncorti.emgvisualizer.ui.dialog.LoadingDialog
import java.io.File
import java.io.FileOutputStream
import javax.inject.Inject

private const val REQUEST_WRITE_EXTERNAL_CODE = 2

@AndroidEntryPoint
class ExportFragment : BaseFragment<ExportContract.Presenter>(), ExportContract.View {

    companion object {
        fun newInstance() = ExportFragment()
    }

    @Inject
    lateinit var exportPresenter: ExportContract.Presenter

    private lateinit var binding: LayoutExportBinding

    private val loadingDialog by lazy { LoadingDialog(requireActivity()) }

    private var fileContentToSave: String? = null

    override fun onAttach(context: Context) {
        super.onAttach(context)
        attachPresenter(exportPresenter)
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        binding = LayoutExportBinding.inflate(inflater, container, false)
        setHasOptionsMenu(true)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.buttonStartCollecting.setOnClickListener { exportPresenter.onCollectionTogglePressed() }
        binding.buttonResetCollecting.setOnClickListener { exportPresenter.onResetPressed() }
        binding.buttonShare.setOnClickListener { exportPresenter.onSharePressed() }
        binding.buttonSave.setOnClickListener { exportPresenter.onSavePressed() }
        binding.buttonUpload.setOnClickListener { exportPresenter.onUploadPressed() }
        showSaveArea()
    }

    override fun enableStartCollectingButton() {
        binding.buttonStartCollecting.isEnabled = true
    }

    override fun disableStartCollectingButton() {
        binding.buttonStartCollecting.isEnabled = false
    }

    override fun showNotStreamingErrorMessage() {
        Toast.makeText(activity, "You can't collect points if Myo is not streaming!", Toast.LENGTH_SHORT).show()
    }

    override fun showCollectionStarted() {
        binding.buttonStartCollecting?.text = getString(R.string.stop_collecting)
    }

    override fun showCollectionStopped() {
        binding.buttonStartCollecting?.text = getString(R.string.start_collecting)
    }

    override fun showCollectedPoints(totalPoints: Int) {
        binding.pointsCount.text = totalPoints.toString()
    }

    override fun enableResetButton() {
        binding.buttonResetCollecting.isEnabled = true
    }

    override fun disableResetButton() {
        binding.buttonResetCollecting.isEnabled = false
    }

    override fun hideSaveArea() {
        binding.buttonSave.visibility = View.INVISIBLE
        binding.buttonUpload.visibility = View.INVISIBLE
        binding.buttonShare.visibility = View.INVISIBLE
        binding.saveExportTitle.visibility = View.INVISIBLE
        binding.saveExportSubtitle.visibility = View.INVISIBLE
    }

    override fun showSaveArea() {
        binding.buttonSave.visibility = View.VISIBLE
        binding.buttonUpload.visibility = View.VISIBLE
        binding.buttonShare.visibility = View.VISIBLE
        binding.saveExportTitle.visibility = View.VISIBLE
        binding.saveExportSubtitle.visibility = View.VISIBLE
    }

    override fun sharePlainText(content: String) {
        val sendIntent = Intent()
        sendIntent.action = Intent.ACTION_SEND
        sendIntent.putExtra(Intent.EXTRA_TEXT, content)
        sendIntent.type = "text/plain"
        startActivity(sendIntent)
    }

    override fun showToast(message: String) {
        Toast.makeText(context, message, Toast.LENGTH_SHORT).show()
    }

    override fun showLoading() {
        loadingDialog.showDialog(requireContext(), "loading", false, null)
    }

    override fun hideLoading() {
        loadingDialog.dismissDialog()
    }

    override fun saveCsvFile(content: String) {
        context?.apply {
            val hasPermission = (ContextCompat.checkSelfPermission(this, WRITE_EXTERNAL_STORAGE) == PERMISSION_GRANTED)
            if (hasPermission) {
                writeToFile(content)
            } else {
                fileContentToSave = content
                requestPermissions(arrayOf(WRITE_EXTERNAL_STORAGE), REQUEST_WRITE_EXTERNAL_CODE)
            }
        }
    }

    private fun writeToFile(content: String) {
        val storageDir = File("${Environment.getExternalStorageDirectory().absolutePath}/myo_emg")
        storageDir.mkdir()
        val outfile = File(storageDir, "myo_emg_export_${System.currentTimeMillis()}.csv")
        val fileOutputStream = FileOutputStream(outfile)
        fileOutputStream.write(content.toByteArray())
        fileOutputStream.close()
        Toast.makeText(activity, "Saved to: ${outfile.path}", Toast.LENGTH_LONG).show()
    }

    override fun onRequestPermissionsResult(requestCode: Int, permissions: Array<out String>, grantResults: IntArray) {
        when (requestCode) {
            REQUEST_WRITE_EXTERNAL_CODE -> {
                if (grantResults.isNotEmpty() && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    fileContentToSave?.apply { writeToFile(this) }
                } else {
                    Toast.makeText(activity, getString(R.string.write_permission_denied_message), Toast.LENGTH_SHORT).show()
                }
            }
        }
    }
}
