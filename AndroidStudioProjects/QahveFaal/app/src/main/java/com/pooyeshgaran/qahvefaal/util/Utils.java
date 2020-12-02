package com.pooyeshgaran.qahvefaal.util;


import android.app.Activity;
import android.content.Context;
import android.graphics.Typeface;
import android.os.Build;
import android.support.design.widget.Snackbar;
import android.util.TypedValue;
import android.view.Gravity;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.TextView;
import com.pooyeshgaran.qahvefaal.R;

public class Utils {
    public static final String TAG = Utils.class.getSimpleName();

    public void setWindowFlag(Activity activity, final int bits, boolean on) {
        Window win = activity.getWindow();
        WindowManager.LayoutParams winParams = win.getAttributes();
        if (on) {
            winParams.flags |= bits;
        } else {
            winParams.flags &= ~bits;
        }
        win.setAttributes(winParams);
    }

//    public void showSnackBar(View view, int string, Context context) {
//        Typeface tf = Typeface.createFromAsset(context.getAssets(), "font/yekan.ttf");
//        Snackbar mSnackBar = Snackbar.make(view, string, Snackbar.LENGTH_SHORT)
//                .setActionTextColor(context.getResources().getColor(R.color.colorWhite));
//        mSnackBar.getView().setBackgroundColor(context.getResources().getColor(R.color.colorBlack));
//        TextView snack_text = (TextView) mSnackBar.getView().findViewById(android.support.design.R.id.snackbar_text);
//        snack_text.setTypeface(tf);
//        snack_text.setTextSize(TypedValue.COMPLEX_UNIT_PX, context.getResources().getDimension(R.dimen.text_size_snack_bar));
//        snack_text.setGravity(Gravity.CENTER_HORIZONTAL);
//        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN_MR1) {
//            snack_text.setTextAlignment(View.TEXT_ALIGNMENT_CENTER);
//        }
//        mSnackBar.show();
//    }


}
