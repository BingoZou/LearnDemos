package cn.zhoudl.attrdemo;

import android.content.Context;
import android.content.res.TypedArray;
import android.util.AttributeSet;
import android.util.Log;
import android.widget.TextView;

/**
 * Created by dave on 2016/8/24 0024 17:28
 */
public class ZTextView extends TextView {

    public ZTextView(Context context) {
        super(context);
    }

    public ZTextView(Context context, AttributeSet attrs) {
        this(context, attrs, R.attr.ZTV_def_style);
    }

    public ZTextView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        parse(context, attrs, defStyleAttr);
    }

    private void parse(Context context, AttributeSet attrs, int defStyleAttr) {
        TypedArray typedArray = context.obtainStyledAttributes(attrs, R.styleable.ZTextViewStyle, defStyleAttr, R.style.ZTV_default_style);
        String one = typedArray.getString(R.styleable.ZTextViewStyle_attr_1);
        String two = typedArray.getString(R.styleable.ZTextViewStyle_attr_2);
        String three = typedArray.getString(R.styleable.ZTextViewStyle_attr_3);
        String four = typedArray.getString(R.styleable.ZTextViewStyle_attr_4);

        log("one = " + one);
        log("two = " + two);
        log("three = " + three);
        log("four = " + four);

        typedArray.recycle();
    }

    private void log(String msg) {
        Log.i("ZDL", msg);
    }

}
