package com.example.wbx.dialogtest;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.WindowManager;

/**
 * desc
 * 创建人：wbx
 * 创建时间：2018-08-08
 */

public class Dialog extends android.app.Dialog {
    private Context mContext;

    public Dialog(Context context) {
        super(context,R.style.Dialog);
        mContext = context;
    }
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        LayoutInflater inflater = LayoutInflater.from(mContext);
        View contentView = inflater.inflate(R.layout.order_secondary_confirm_dialog_layout, null);
        setContentView(contentView);

        WindowManager.LayoutParams layoutParams = getWindow().getAttributes();
        layoutParams.width = mContext.getResources().getDimensionPixelSize(R.dimen.size_292dp);
        layoutParams.height = WindowManager.LayoutParams.WRAP_CONTENT;
        layoutParams.dimAmount = 0.25f;
        getWindow().setAttributes(layoutParams);
    }

}
