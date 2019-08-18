package com.devper.template.widget

import android.os.Bundle
import android.view.Gravity
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.annotation.StringRes
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.DialogFragment
import com.devper.template.R
import com.devper.template.databinding.DialogConfirmBinding

class ConfirmDialog : DialogFragment() {

    private var title: String = ""
    private var message: String = ""
    private var positive: Int = 0
    private var negative: Int = 0
    private lateinit var binding: DialogConfirmBinding

    private val onDialogListener: OnDialogListener?
        get() {
            val fragment = parentFragment
            try {
                return if (fragment != null) {
                    fragment as OnDialogListener?
                } else {
                    activity as OnDialogListener?
                }
            } catch (ignored: Exception) {
            }
            return null
        }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        if (savedInstanceState == null) {
            restoreArguments(arguments!!)
        } else {
            restoreInstanceState(savedInstanceState)
        }
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        binding = DataBindingUtil.inflate(inflater, R.layout.dialog_confirm, container, false)
        binding.lifecycleOwner = activity
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupView()
    }

    private fun setupView() {
        binding.tvTitle.text = title
        binding.tvMessage.text = message
        binding.btnConfirm.setText(positive)

        if (negative == 0) {
            binding.lyCancel.gravity = Gravity.END
            binding.lyCancel.visibility = View.GONE
        } else {
            binding.btnCancel.setText(negative)
        }

        if (positive != 0) {
            binding.btnConfirm.setText(positive)
        }

        binding.btnConfirm.setOnClickListener {
            val listener = onDialogListener
            listener?.onPositiveClick(tag ?: "dialog")
            dismiss()
        }

        binding.btnCancel.setOnClickListener {
            val listener = onDialogListener
            listener?.onNegativeClick()
            dismiss()
        }
    }

    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
        outState.putString(KEY_TITLE, title)
        outState.putString(KEY_MESSAGE, message)
        outState.putInt(KEY_POSITIVE, positive)
        outState.putInt(KEY_NEGATIVE, negative)
    }

    private fun restoreInstanceState(bundle: Bundle) {
        title = bundle.getString(KEY_TITLE, "")
        message = bundle.getString(KEY_MESSAGE, "")
        positive = bundle.getInt(KEY_POSITIVE)
        negative = bundle.getInt(KEY_NEGATIVE)
    }

    private fun restoreArguments(bundle: Bundle) {
        title = bundle.getString(KEY_TITLE, "")
        message = bundle.getString(KEY_MESSAGE, "")
        positive = bundle.getInt(KEY_POSITIVE)
        negative = bundle.getInt(KEY_NEGATIVE)
    }

    interface OnDialogListener {
        fun onPositiveClick(tag: String)

        fun onNegativeClick()
    }

    class Builder {
        private var title: String = ""
        private var message: String = ""
        private var positive: Int = 0
        private var negative: Int = 0

        fun setTitle(title: String): Builder {
            this.title = title
            return this
        }

        fun setMessage(message: String): Builder {
            this.message = message
            return this
        }

        fun setPositive(@StringRes positive: Int): Builder {
            this.positive = positive
            return this
        }

        fun setNegative(@StringRes negative: Int): Builder {
            this.negative = negative
            return this
        }

        fun build(): DialogFragment {
            return newInstance(title, message, positive, negative)
        }
    }

    companion object {
        private const val KEY_TITLE = "key_title"
        private const val KEY_MESSAGE = "key_message"
        private const val KEY_POSITIVE = "key_positive"
        private const val KEY_NEGATIVE = "key_negative"

        fun newInstance(title: String, message: String, @StringRes positive: Int, @StringRes negative: Int): DialogFragment {
            val fragment = ConfirmDialog()
            val bundle = Bundle()
            bundle.putString(KEY_TITLE, title)
            bundle.putString(KEY_MESSAGE, message)
            bundle.putInt(KEY_POSITIVE, positive)
            bundle.putInt(KEY_NEGATIVE, negative)
            fragment.arguments = bundle
            return fragment
        }
    }
}
