package be.belgacom.tv.bepandroid.customview;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.TextView;

import be.belgacom.tv.bepandroid.R;

/**
 * Created by Narendra.Kumar on 2/22/2016.
 */
public class BepTextView extends TextView {

    public BepTextView(Context context) {
        super(context);
    }

    public BepTextView(Context context, AttributeSet attrs) {
        super(context, attrs);
        FontUtil.setCustomFont(this,context,attrs,
                R.styleable.be_belgacom_tv_bepandroid_customfonts_BepTextView,
                R.styleable.be_belgacom_tv_bepandroid_customfonts_BepTextView_font);
    }

    public BepTextView(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        FontUtil.setCustomFont(this,context,attrs,
                R.styleable.be_belgacom_tv_bepandroid_customfonts_BepTextView,
                R.styleable.be_belgacom_tv_bepandroid_customfonts_BepTextView_font);
    }
}