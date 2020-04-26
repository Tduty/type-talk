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
import com.google.android.material.snackbar.Snackbar
import info.tduty.typetalk.App
import info.tduty.typetalk.R
import info.tduty.typetalk.view.ViewNavigation
import info.tduty.typetalk.view.base.BaseFragment
import info.tduty.typetalk.view.login.password.qr.di.AuthQRModule
import kotlinx.android.synthetic.main.fragment_auth_qr.*
import java.io.IOException
import javax.inject.Inject


class AuthQRFragment: BaseFragment(R.layout.fragment_auth_qr),
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

    private var isLoading: Boolean = false

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
        sv_camera_area?.holder?.addCallback(object : SurfaceHolder.Callback {
            override fun surfaceCreated(holder: SurfaceHolder) {
                sv_camera_area.let { cameraSource?.start(sv_camera_area.holder) }
            }

            override fun surfaceChanged(
                holder: SurfaceHolder,
                format: Int,
                width: Int,
                height: Int
            ) {

            }

            override fun surfaceDestroyed(holder: SurfaceHolder) {
                val handler = Handler(Looper.getMainLooper())
                handler.post {
                    cameraSource?.stop()
                }
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

    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray
    ) {
        when (requestCode) {
            REQUEST_CAMERA_PERMISSION -> {
                if (grantResults.isNotEmpty()) {
                    if (grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                        val handler = Handler(Looper.getMainLooper())
                        handler.post {
                            if (sv_camera_area != null) {
                                showScanCamera()
                            }
                        }
                    } else {
                        (activity as? ViewNavigation)?.openLoginAuth()
                    }
                }
                return
            }
        }
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

    override fun isLoading(): Boolean {
        return isLoading
    }

    override fun showScanCamera() {
        try {
            if (ActivityCompat.checkSelfPermission(
                    requireActivity().applicationContext,
                    Manifest.permission.CAMERA
                ) == PackageManager.PERMISSION_GRANTED
            ) {
                sv_camera_area?.visibility = View.VISIBLE
                initialiseDetectorsAndSources()
            } else {
                requestPermissions(
                    arrayOf(Manifest.permission.CAMERA),
                    REQUEST_CAMERA_PERMISSION
                )
            }
        } catch (e: IOException) {
            e.printStackTrace()
        }
    }

    override fun showLoading() {
        isLoading = true
        val handler = Handler(Looper.getMainLooper())
        handler.post {
            cameraSource?.stop()
        }
    }

    override fun openMainScreen() {
        (activity as? ViewNavigation)?.openMain()
    }

    override fun showError() {
        view?.let { Snackbar.make(it, getString(R.string.auth_screen_error_authorization), Snackbar.LENGTH_LONG).show() }
    }
}
