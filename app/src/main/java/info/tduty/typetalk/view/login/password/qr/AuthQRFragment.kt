package info.tduty.typetalk.view.login.password.qr

import android.Manifest
import android.content.pm.PackageManager
import android.graphics.Point
import android.os.Bundle
import android.view.Display
import android.view.SurfaceHolder
import android.view.View
import android.widget.Toast
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
    var intentData = ""

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
        initialiseDetectorsAndSources()
    }

    private fun initialiseDetectorsAndSources() {
        Toast.makeText(activity?.applicationContext, "Barcode scanner started", Toast.LENGTH_SHORT)
            .show()
        barcodeDetector = BarcodeDetector.Builder(context)
            .setBarcodeFormats(Barcode.ALL_FORMATS)
            .build()
        val display: Display? = activity?.windowManager?.defaultDisplay
        val size = Point()
        display!!.getSize(size)
        val width: Int = size.x
        val height: Int = size.y
        cameraSource = CameraSource.Builder( activity?.applicationContext, barcodeDetector)
            .setRequestedPreviewSize(width, height)
            .setAutoFocusEnabled(true) //you should add this feature
            .build()
        surfaceView!!.holder.addCallback(object : SurfaceHolder.Callback {
            override fun surfaceCreated(holder: SurfaceHolder) {
                try {
                    if (ActivityCompat.checkSelfPermission(
                            activity!!.applicationContext,
                            Manifest.permission.CAMERA
                        ) == PackageManager.PERMISSION_GRANTED
                    ) {
                        cameraSource?.start(surfaceView!!.holder)
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
            override fun release() {
                Toast.makeText(
                    context,
                    "To prevent memory leaks barcode scanner has been stopped",
                    Toast.LENGTH_SHORT
                ).show()
            }

            override fun receiveDetections(detections: Detector.Detections<Barcode>) {
                val barcodes = detections.detectedItems
                if (barcodes.size() != 0) {
                    txtBarcodeValue!!.post {
                        if (barcodes.valueAt(0).email != null) {
                            txtBarcodeValue!!.removeCallbacks(null)
                            intentData = barcodes.valueAt(0).email.address
                            txtBarcodeValue!!.text = intentData
                        } else {
                            intentData = barcodes.valueAt(0).displayValue
                            txtBarcodeValue!!.text = intentData
                        }
                    }
                }
            }
        })
    }

    override fun onPause() {
        super.onPause()
        cameraSource?.release()
    }

    override fun onResume() {
        super.onResume()
        presenter.onResume()
    }

    override fun showScanCamera() {
        initialiseDetectorsAndSources()
    }

    override fun showLoading() {
        surfaceView?.visibility = View.GONE
        txtBarcodeValue?.visibility = View.GONE
    }

    override fun openMainScreen() {
        (activity as? ViewNavigation)?.openMain()
    }

    override fun showError() {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }
}
