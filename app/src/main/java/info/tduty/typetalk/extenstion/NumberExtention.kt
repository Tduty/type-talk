package info.tduty.typetalk.extenstion

import android.content.res.Resources
import android.util.DisplayMetrics
import androidx.annotation.Dimension

/**
 * Created by Evgeniy Mezentsev on 27.04.2020.
 */

@JvmOverloads
@Dimension(unit = Dimension.PX)
fun Number.dpToPx(metrics: DisplayMetrics = Resources.getSystem().displayMetrics): Float = toFloat() * metrics.density
