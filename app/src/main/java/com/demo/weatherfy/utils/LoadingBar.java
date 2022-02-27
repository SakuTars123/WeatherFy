package com.demo.weatherfy.utils;

import android.app.Dialog;
import android.content.Context;
import android.content.res.Resources;
import android.view.ViewGroup;

import com.demo.weatherfy.R;

public class LoadingBar {

    public static Dialog loadingDialog;

    public static void LoadingTime(Context context, Resources resources, boolean load){
        if(load) {
            loadingDialog = new Dialog(context);
            loadingDialog.setContentView(R.layout.loading_progress_dialog);
            loadingDialog.setCancelable(false);
            loadingDialog.getWindow().setBackgroundDrawable(resources.getDrawable(R.drawable.slider_background));
            loadingDialog.getWindow().setLayout(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
            loadingDialog.show();
        }else loadingDialog.dismiss();
    }

}
