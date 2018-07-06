package com.codepath.apps.restclienttemplate;

import android.content.Context;
import android.support.v7.app.AppCompatActivity;
import android.text.Editable;
import android.text.TextUtils;
import android.text.method.MovementMethod;
import android.util.AttributeSet;
import android.view.KeyEvent;
import android.widget.EditText;
import android.widget.TextView;

public class ComposeText extends EditText {
    public ComposeText(Context context) {
        super(context);
    }

    public ComposeText(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public ComposeText(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    public ComposeText(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
    }


    @Override
    public boolean onKeyPreIme(int keyCode, KeyEvent event) {
        if (event.getKeyCode() == KeyEvent.KEYCODE_BACK) {
            dispatchKeyEvent(event);
            ((AppCompatActivity) getContext()).finish();
            return false;
        }
        return super.onKeyPreIme(keyCode, event);
    }
}
