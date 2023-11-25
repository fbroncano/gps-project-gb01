package es.unex.gps.weathevent

import android.os.Bundle
import androidx.preference.PreferenceFragmentCompat
import es.unex.gps.weathevent.R

class SettingsFragment : PreferenceFragmentCompat() {

    override fun onCreatePreferences(savedInstanceState: Bundle?, rootKey: String?) {
        setPreferencesFromResource(R.xml.preferences, rootKey)
    }
}