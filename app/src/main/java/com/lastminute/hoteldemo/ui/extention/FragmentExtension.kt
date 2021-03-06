package com.lastminute.hoteldemo.ui.extention

import android.os.Bundle
import androidx.fragment.app.Fragment

inline fun <T : Fragment> T.build(args: Bundle.() -> Unit): T =
    apply { arguments = Bundle().apply(args) }
