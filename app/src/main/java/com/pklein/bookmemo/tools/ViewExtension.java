package com.pklein.bookmemo.tools;

import android.view.View;

import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

public class ViewExtension {

    public static void addSystemWindowInsetToPadding(View view, boolean left, boolean top, boolean right, boolean bottom) {
        int initialLeft = view.getPaddingLeft();
        int initialTop = view.getPaddingTop();
        int initialRight = view.getPaddingRight();
        int initialBottom = view.getPaddingBottom();

        ViewCompat.setOnApplyWindowInsetsListener(view, (v, insets) -> {
            Insets bars = insets.getInsets(
                    WindowInsetsCompat.Type.systemBars() |
                            WindowInsetsCompat.Type.displayCutout() |
                            WindowInsetsCompat.Type.ime()
            );

            v.setPadding(
                    initialLeft + (left ? bars.left : 0),
                    initialTop + (top ? bars.top : 0),
                    initialRight + (right ? bars.right : 0),
                    initialBottom + (bottom ? bars.bottom : 0)
            );

            return WindowInsetsCompat.CONSUMED;
        });
    }
}