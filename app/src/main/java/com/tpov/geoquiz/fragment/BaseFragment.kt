package com.tpov.geoquiz.fragment

import androidx.fragment.app.Fragment

open class BaseFragment: Fragment() {
    open fun onClickNew(name: String, stars: Int) {}
}