package com.ocr.realestatektv2.util

import android.content.Context
import android.view.LayoutInflater
import androidx.appcompat.app.AlertDialog
import androidx.core.view.isNotEmpty
import com.ocr.realestatektv2.R
import kotlinx.android.synthetic.main.name_picture_pop_up.view.*

object DialogUtils {

    fun getDefaultPopUp(context: Context, savedPicture: (text: String) -> Unit): AlertDialog{
        val builder = AlertDialog.Builder(context ,R.style.CustomAlertDialog)
        val dialogView = LayoutInflater.from(context).inflate(R.layout.name_picture_pop_up, null)
        builder.setView(dialogView)

        val dialog = builder.create()
        dialog.setCanceledOnTouchOutside(false)
        dialog.setCancelable(false)

        dialogView.cancel.setOnClickListener { dialog.dismiss() }

        dialogView.confirm.setOnClickListener {
            if (dialogView.inputNamePicture.isNotEmpty())
            savedPicture(dialogView.inputNamePicture.text.toString())
            dialog.dismiss()
        }

        return dialog
    }
}