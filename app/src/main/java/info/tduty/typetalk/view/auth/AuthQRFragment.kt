package info.tduty.typetalk.view.auth

import android.app.Activity.RESULT_CANCELED
import android.app.Activity.RESULT_OK
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import androidx.fragment.app.Fragment
import info.tduty.typetalk.R
import kotlinx.android.synthetic.main.fragment_auth_qr.*
import javax.inject.Inject


class AuthQRFragment: Fragment(R.layout.fragment_auth_qr), AuthQRView {

    companion object {

        @JvmStatic
        fun newInstance() : AuthQRFragment {
            return AuthQRFragment()
        }
    }

    @Inject
    lateinit var presenter: AuthQRPresenter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        qr_button_open.setOnClickListener { openQRScanningCamera() }
    }

    fun openQRScanning() {
        try {
            val intent = Intent("com.google.zxing.client.android.SCAN")
            intent.putExtra("SCAN_MODE", "QR_CODE_MODE") // "PRODUCT_MODE for bar codes
            startActivityForResult(intent, 0)
        } catch (e: Exception) {
            val marketUri: Uri = Uri.parse("market://details?id=com.google.zxing.client.android")
            val marketIntent = Intent(Intent.ACTION_VIEW, marketUri)
            startActivity(marketIntent)
        }
    }

    fun openQRScanningCamera() {
        openQRScanning()
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == 0) {
            if (resultCode == RESULT_OK) {
                val contents = data?.getStringExtra("SCAN_RESULT")
            }
            if (resultCode == RESULT_CANCELED) { //handle cancel

            }
        }
    }
}
