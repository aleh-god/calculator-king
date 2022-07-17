package by.godevelopment.kingcalculator.presentation.partypresentation.partycard

import android.app.Dialog
import android.content.DialogInterface
import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AlertDialog
import androidx.core.os.bundleOf
import androidx.fragment.app.DialogFragment
import by.godevelopment.kingcalculator.R
import by.godevelopment.kingcalculator.commons.EMPTY_STRING

class ConfirmDialogFragment() : DialogFragment() {

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {

        val gameTypeName =arguments?.getString(KEY_ARG)


        val listener = DialogInterface.OnClickListener { _, which ->
            parentFragmentManager.setFragmentResult(REQUEST_KEY, bundleOf(KEY_RESPONSE to which))
        }
        return AlertDialog.Builder(requireContext())
            .setCancelable(true)
            .setIcon(R.mipmap.ic_launcher_round)
            .setTitle(R.string.alert_confirm_title)
            .setMessage(getString(R.string.alert_confirm_message, gameTypeName))
            .setPositiveButton(R.string.action_yes, listener)
            .setNegativeButton(R.string.action_dismiss, listener)
            .create()
    }

    companion object {
        @JvmStatic val TAG = ConfirmDialogFragment::class.java.simpleName
        @JvmStatic val REQUEST_KEY = "$TAG:defaultRequestKey"
        @JvmStatic val KEY_RESPONSE = "RESPONSE"
        @JvmStatic val KEY_ARG = "RESPONSE"

        fun newFragmentInstance(gameTypeName: String): ConfirmDialogFragment {
            val dialogFragment = ConfirmDialogFragment()
            dialogFragment.arguments = bundleOf(KEY_ARG to gameTypeName)
            return dialogFragment
        }
    }

}