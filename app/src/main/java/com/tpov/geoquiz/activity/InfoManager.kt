package com.tpov.geoquiz.activity

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.preference.Preference
import androidx.preference.PreferenceFragmentCompat
import com.tpov.geoquiz.R
import com.tpov.geoquiz.settings.BillingManager

class InfoManager : PreferenceFragmentCompat() {

    override fun onCreatePreferences(savedInstanceState: Bundle?, rootKey: String?) {
        setPreferencesFromResource(R.xml.info_preference, rootKey)
        init()
    }

    private fun init() {

    }

    override fun onDestroy() {
        super.onDestroy()
    }
}
