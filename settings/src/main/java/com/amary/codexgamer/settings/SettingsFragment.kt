package com.amary.codexgamer.settings

import android.app.AlertDialog
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.CompoundButton
import android.widget.RadioButton
import android.widget.RadioGroup
import androidx.appcompat.app.AppCompatDelegate
import androidx.fragment.app.Fragment
import com.amary.codexgamer.utils.Preference
import kotlinx.android.synthetic.main.fragment_settings.*
import org.koin.android.ext.android.inject
import org.koin.android.viewmodel.ext.android.viewModel
import org.koin.core.context.loadKoinModules

class SettingsFragment : Fragment(), CompoundButton.OnCheckedChangeListener,
    RadioGroup.OnCheckedChangeListener {

    private val settingsViewModel: SettingsViewModel by viewModel()
    private val preference: Preference by inject()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_settings, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        loadKoinModules(settingsModule)
        getSettingLanguage()
        rb_set_language.setOnCheckedChangeListener(this)

        getSettingDarkMode()
        sw_dark_mode.setOnCheckedChangeListener(this)
    }

    private fun getSettingDarkMode() {
        val darkPreference = preference.getDataDarkMode()
        sw_dark_mode.isChecked = darkPreference == 2
    }

    private fun getSettingLanguage() {
        val localLanguage = getString(R.string.localization)
        if (localLanguage == "en-US") {
            rb_en.isChecked = true
            rb_id.isChecked = false
        } else {
            rb_en.isChecked = false
            rb_id.isChecked = true
        }
    }

    private fun alertChangeLanguage() {
        val builder = AlertDialog.Builder(context)
        builder.setTitle(getString(R.string.txt_setting_language))
        builder.setMessage(getString(R.string.alert_language_change))
        builder.setCancelable(false)
        builder.setPositiveButton(getString(R.string.restart_now)) { _, _ ->
            activity?.finish()
        }

        builder.setNegativeButton(getString(R.string.restart_later)) { _, _ -> }
        builder.create().show()
    }

    override fun onCheckedChanged(cButton: CompoundButton, isChecked: Boolean) {
        if(cButton.id == R.id.sw_dark_mode){
            val nightMode: Int = if (isChecked){
                AppCompatDelegate.MODE_NIGHT_YES
            } else {
                AppCompatDelegate.MODE_NIGHT_NO
            }

            AppCompatDelegate.setDefaultNightMode(nightMode)
            preference.setDataDarkMode(nightMode)
        }
    }

    override fun onCheckedChanged(group: RadioGroup, id: Int) {
        val rb = group.findViewById<RadioButton>(id)
        if (rb != null) {
            when (id) {
                R.id.rb_en -> {
                    rb.isChecked = true
                    preference.setDataLanguage("en")
                    alertChangeLanguage()
                }
                R.id.rb_id -> {
                    rb.isChecked = true
                    preference.setDataLanguage("in")
                    alertChangeLanguage()
                }
            }
        }
    }
}