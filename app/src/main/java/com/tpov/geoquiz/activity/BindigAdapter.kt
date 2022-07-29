package com.tpov.geoquiz.activity

import androidx.databinding.BindingAdapter
import com.tpov.geoquiz.entities.Crime
import kotlinx.coroutines.InternalCoroutinesApi

@InternalCoroutinesApi
@BindingAdapter("getUpdateCrime")
fun bindGetUpdateCrime(it: List<Crime>, insertCrime: Boolean): MainActivity {
    if (insertCrime) {
        !insertCrime
        it.forEach { item ->
            (item)
        }
    }
}
