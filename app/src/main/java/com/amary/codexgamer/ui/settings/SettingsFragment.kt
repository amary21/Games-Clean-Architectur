package com.amary.codexgamer.ui.settings

import android.annotation.SuppressLint
import android.app.AlertDialog
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.RadioButton
import androidx.fragment.app.Fragment
import com.amary.codexgamer.R
import com.amary.codexgamer.utils.Preference
import kotlinx.android.synthetic.main.fragment_settings.*
import org.koin.android.ext.android.inject

class SettingsFragment : Fragment() {

//    private val settingsViewModel: SettingsViewModel by viewModel()
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
        getSettingLanguage()
        setSettingLanguage()
    }

    @SuppressLint("UseRequireInsteadOfGet")
    private fun setSettingLanguage() {
        rb_set_language.setOnCheckedChangeListener { group, checkedId ->
            val rb = group.findViewById<RadioButton>(checkedId)
            if (rb != null) {
                when (checkedId) {
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
}