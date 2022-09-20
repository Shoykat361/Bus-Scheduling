package com.example.busscheduling.customdaila

import android.app.Dialog
import android.content.DialogInterface
import android.os.Bundle
import androidx.appcompat.app.AlertDialog
import androidx.fragment.app.DialogFragment
import com.example.busscheduling.R

class CustomAleartDialog
    (val title:String,
     val icon:Int= R.drawable.ic_baseline_info_24,
     val body:String,
     val possitiveButtomText:String,
     val negativeButtomText:String,
     val possitivebuttonclickcallback: () ->Unit
):DialogFragment() {
    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        val builder = AlertDialog.Builder(requireActivity()).apply {
            setTitle(title)
            setMessage(body)
            setIcon(icon)
            setPositiveButton(possitiveButtomText) { dialogInterface, i ->
                possitivebuttonclickcallback()

            }
            setNegativeButton(negativeButtomText,null)
        }
       return builder.create()

    }
}