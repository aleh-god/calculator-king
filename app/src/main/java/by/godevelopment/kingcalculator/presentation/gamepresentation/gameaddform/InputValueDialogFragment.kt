package by.godevelopment.kingcalculator.presentation.gamepresentation.gameaddform

import android.app.Dialog
import android.content.DialogInterface
import android.os.Bundle
import android.text.InputType
import android.widget.EditText
import androidx.appcompat.app.AlertDialog
import androidx.core.os.bundleOf
import androidx.fragment.app.DialogFragment
import by.godevelopment.kingcalculator.R

class InputValueDialogFragment : DialogFragment() {

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        val editText = EditText(requireContext()).apply {
            inputType = InputType.TYPE_CLASS_NUMBER
        }

        val listener = DialogInterface.OnClickListener { _, _ ->
            editText.text?.toString()?.toIntOrNull()?.let {
                parentFragmentManager.setFragmentResult(REQUEST_KEY, bundleOf(KEY_RESPONSE to it))
            }
        }
        return AlertDialog.Builder(requireContext())
            .setCancelable(true)
            .setIcon(R.mipmap.ic_launcher_round)
            .setTitle(R.string.input_tricks_title)
            .setMessage(getString(R.string.input_tricks_message))
            .setPositiveButton(R.string.action_yes, listener)
            .setNegativeButton(R.string.action_dismiss, null)
            .setView(editText)
            .create()
    }

    companion object {
        @JvmStatic
        val TAG = InputValueDialogFragment::class.java.simpleName
        @JvmStatic
        val REQUEST_KEY = "$TAG:defaultRequestKey"
        @JvmStatic
        val KEY_RESPONSE = "NEW VALUE"

        fun newFragmentInstance(): InputValueDialogFragment = InputValueDialogFragment()
    }
}
