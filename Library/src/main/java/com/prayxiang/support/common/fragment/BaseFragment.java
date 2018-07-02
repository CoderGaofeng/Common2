package com.prayxiang.support.common.fragment;

import android.content.Context;
import android.support.v4.app.Fragment;
import android.view.View;
import android.view.inputmethod.InputMethodManager;

import com.prayxiang.support.common.dialog.TipDialog;

public class BaseFragment extends Fragment {


    public void hideInputMethod(View view) {
        InputMethodManager imm = (InputMethodManager) view.getContext().getSystemService(Context.INPUT_METHOD_SERVICE);

        if (imm == null) {
            return;
        }

        imm.hideSoftInputFromWindow(view.getWindowToken(), 0); //强制隐藏键盘

    }

    public void showInputMethod(View view) {
        InputMethodManager imm = (InputMethodManager) view.getContext().getSystemService(Context.INPUT_METHOD_SERVICE);

        if (imm == null) {
            return;
        }
        imm.showSoftInput(view, InputMethodManager.SHOW_FORCED);

    }

    private TipDialog dialog;


    public TipDialog obtainDialog() {
        if (dialog == null) {
            dialog = new TipDialog(getContext());
        }
        return dialog;
    }

    @Override
    public void onDestroyView() {
        if (dialog != null && dialog.isShowing()) {
            dialog.dismiss();
        }
        super.onDestroyView();
    }


}
