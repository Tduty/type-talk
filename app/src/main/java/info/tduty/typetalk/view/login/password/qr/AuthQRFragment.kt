package info.tduty.typetalk.view.login.password.qr

import android.Manifest
import android.content.pm.PackageManager
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.view.SurfaceHolder
import android.view.View
import androidx.core.app.ActivityCompat
import androidx.fragment.app.Fragment
import com.google.android.gms.vision.CameraSource
import com.google.android.gms.vision.Detector
import com.google.android.gms.vision.barcode.Barcode
import com.google.android.gms.vision.barcode.BarcodeDetector
import info.tduty.typetalk.App
import info.tduty.typetalk.R
import info.tduty.typetalk.view.ViewNavigation
import info.tduty.typetalk.view.login.password.qr.di.AuthQRModule
import kotlinx.android.synthetic.main.fragment_auth_qr.*
import java.io.IOException
import javax.inject.Inject

class AuthQRFragment: Fragment(R.layout.fragment_auth_qr),
    AuthQRView {

    companion object {
        private const val REQUEST_CAMERA_PERMISSION = 201

        @JvmStatic
        fun newInstance() : AuthQRFragment {
            return AuthQRFragment()
        }
    }

    @Inject
    lateinit var presenter: AuthQRPresenter

    private var barcodeDetector: BarcodeDetector? = null
    private var cameraSource: CameraSource? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setupFragmentComponent()
    }

    private fun setupFragmentComponent() {
        App.get(this.requireContext())
            .appComponent
            .plus(AuthQRModule(this))
            .inject(this)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        presenter.onCreate()
    }

    private fun initialiseDetectorsAndSources() {
        barcodeDetector = BarcodeDetector.Builder(context)
            .setBarcodeFormats(Barcode.ALL_FORMATS)
            .build()
        // TODO dynamic value
        val width = 1920
        val height = 1080
        cameraSource = CameraSource.Builder( activity?.applicationContext, barcodeDetector)
            .setRequestedPreviewSize(width, height)
            .setAutoFocusEnabled(true)
            .build()
        sv_camera_area!!.holder.addCallback(object : SurfaceHolder.Callback {
            override fun surfaceCreated(holder: SurfaceHolder) {
                try {
                    if (ActivityCompat.checkSelfPermission(
                            activity!!.applicationContext,
                            Manifest.permission.CAMERA
                        ) == PackageManager.PERMISSION_GRANTED
                    ) {
                        cameraSource?.start(sv_camera_area!!.holder)
                    } else {
                        ActivityCompat.requestPermissions(
                            activity!!,
                            arrayOf(Manifest.permission.CAMERA),
                            REQUEST_CAMERA_PERMISSION
                        )
                    }
                } catch (e: IOException) {
                    e.printStackTrace()
                }
            }

            override fun surfaceChanged(
                holder: SurfaceHolder,
                format: Int,
                width: Int,
                height: Int
            ) {
            }

            override fun surfaceDestroyed(holder: SurfaceHolder) {
                cameraSource?.stop()
            }
        })
        barcodeDetector?.setProcessor(object : Detector.Processor<Barcode> {
            override fun release() {}

            override fun receiveDetections(detections: Detector.Detections<Barcode>) {
                if (detections.detectedItems.size() != 0) {
                    presenter.onNext(detections.detectedItems.valueAt(0).displayValue)
                }
            }
        })
    }

    override fun onPause() {
        super.onPause()
        val handler = Handler(Looper.getMainLooper())
        handler.post {
            cameraSource?.release()
        }
    }

    override fun onResume() {
        super.onResume()
        presenter.onResume()
    }

    override fun showScanCamera() {
        sv_camera_area?.visibility = View.VISIBLE
        initialiseDetectorsAndSources()
    }

    override fun showLoading() {
        val handler = Handler(Looper.getMainLooper())
        handler.post {
            cameraSource!!.stop()
            sv_camera_area?.visibility = View.GONE
        }
    }

    override fun openMainScreen() {
        (activity as? ViewNavigation)?.openMain()
    }

    override fun showError() {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }
}
