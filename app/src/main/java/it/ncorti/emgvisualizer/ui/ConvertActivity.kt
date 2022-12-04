package it.ncorti.emgvisualizer.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.google.android.exoplayer2.ExoPlayer
import com.google.android.exoplayer2.MediaItem
import com.google.android.exoplayer2.Player.REPEAT_MODE_ALL
import com.google.android.exoplayer2.SimpleExoPlayer
import dagger.hilt.android.AndroidEntryPoint
import it.ncorti.emgvisualizer.databinding.ActivityConvertBinding
import it.ncorti.emgvisualizer.ui.contract.ConvertTextContract
import it.ncorti.emgvisualizer.ui.dialog.LoadingDialog
import javax.inject.Inject


@AndroidEntryPoint
class ConvertActivity : AppCompatActivity(), ConvertTextContract.View {

    @Inject
    lateinit var presenter: ConvertTextContract.Presenter

    lateinit var binding: ActivityConvertBinding

    private val loadingDialog by lazy { LoadingDialog(this) }

    private val player: ExoPlayer by lazy {
        SimpleExoPlayer.Builder(this).build()
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityConvertBinding.inflate(LayoutInflater.from(this))
        initListeners()
        initExoplayer()
        binding.editQuery.setText("SUED WARM MEIN NICHT")
        setContentView(binding.root)
    }

    private fun initListeners() {
        binding.btnTranslate.setOnClickListener {
            val text = binding.editQuery.text.toString().trim()
            presenter.onTextConvert(text)
        }
    }

    private fun initExoplayer() {
        binding.playerView.hideController()
        binding.playerView.player = player
    }

    override fun showToast(message: String) {
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show()
    }

    override fun showLoading() {
        loadingDialog.showDialog(this, "loading", false, null)
    }

    override fun hideLoading() {
        loadingDialog.dismissDialog()
    }

    override fun convertSuccess(videoUrl: String) {
        val mediaItem: MediaItem = MediaItem.fromUri(videoUrl)
        player.setMediaItem(mediaItem)
        player.repeatMode = REPEAT_MODE_ALL
        player.prepare()
        player.play()
    }
}