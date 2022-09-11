package by.godevelopment.kingcalculator.presentation.settingsscreen

import android.app.Dialog
import android.content.DialogInterface
import android.os.Bundle
import androidx.appcompat.app.AlertDialog
import androidx.core.os.bundleOf
import androidx.fragment.app.DialogFragment
import by.godevelopment.kingcalculator.R

class DeleteConfirmDialogFragment() : DialogFragment() {

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        val listener = DialogInterface.OnClickListener { _, which ->
            parentFragmentManager.setFragmentResult(REQUEST_KEY, bundleOf(KEY_RESPONSE to which))
        }
        return AlertDialog.Builder(requireContext())
            .setCancelable(true)
            .setIcon(R.mipmap.ic_launcher_round)
            .setTitle(R.string.alert_confirm_delete_title)
            .setMessage(getString(R.string.alert_confirm_delete_message))
            .setPositiveButton(R.string.action_yes, listener)
            .setNegativeButton(R.string.action_dismiss, listener)
            .create()
    }

    companion object {
        @JvmStatic
        val TAG = DeleteConfirmDialogFragment::class.java.simpleName
        @JvmStatic
        val REQUEST_KEY = "$TAG:defaultRequestKey"
        @JvmStatic
        val KEY_RESPONSE = "RESPONSE"
    }
}
