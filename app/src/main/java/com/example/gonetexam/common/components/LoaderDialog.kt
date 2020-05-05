package com.example.gonetexam.common.components

import android.app.Dialog
import android.content.Context
import android.graphics.drawable.ColorDrawable
import android.os.Build
import android.view.LayoutInflater
import android.widget.RelativeLayout
import androidx.annotation.RequiresApi
import com.example.gonetexam.R


class LoaderDialog(context: Context) : Dialog(context) {
    @RequiresApi(Build.VERSION_CODES.M)
    override fun onStart() {
        super.onStart()
        val window = this.window
        this.setCancelable(false)
        val inflate = LayoutInflater.from(context).inflate(R.layout.app_progress, null)
        this.setContentView(inflate)
        window?.setBackgroundDrawable(ColorDrawable(context.getColor(R.color.withe_transparency)))
        window?.setLayout(
            RelativeLayout.LayoutParams.MATCH_PARENT, RelativeLayout.LayoutParams.MATCH_PARENT
        )
    }
}