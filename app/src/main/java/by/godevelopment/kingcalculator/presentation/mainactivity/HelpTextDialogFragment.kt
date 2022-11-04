package by.godevelopment.kingcalculator.presentation.mainactivity

import android.app.Dialog
import android.os.Bundle
import androidx.annotation.StringRes
import androidx.appcompat.app.AlertDialog
import androidx.core.os.bundleOf
import androidx.fragment.app.DialogFragment
import by.godevelopment.kingcalculator.R

class HelpTextDialogFragment() : DialogFragment() {

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
            val helpTextRes = arguments?.getInt(KEY_ARG) ?: R.string.message_error_data_null

            return AlertDialog.Builder(requireContext())
                .setCancelable(true)
                .setIcon(R.mipmap.ic_launcher_round)
                .setTitle(R.string.alert_help_text_title)
                .setMessage(helpTextRes)
                .setNeutralButton(R.string.dialog_neutral_btn_text, null)
                .create()
        }

    companion object {
        @JvmStatic
        val KEY_ARG = "HelpText"

        fun newFragmentInstance(@StringRes helpTextRes: Int): HelpTextDialogFragment {
            val dialogFragment = HelpTextDialogFragment()
            dialogFragment.arguments = bundleOf(KEY_ARG to helpTextRes)
            return dialogFragment
        }
    }
}
