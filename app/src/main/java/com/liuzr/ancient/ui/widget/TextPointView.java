
package com.liuzr.ancient.ui.widget;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.drawable.ShapeDrawable;
import android.graphics.drawable.shapes.OvalShape;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.TextView;

import com.liuzr.ancient.R;
import com.liuzr.ancient.util.DisplayUtil;

import java.util.Random;

import androidx.annotation.ColorRes;
import androidx.annotation.Px;
import androidx.core.content.ContextCompat;
import androidx.core.content.res.ResourcesCompat;

/**
 * 自定义组件
 */
public class TextPointView extends FrameLayout {

    private final long random = new Random().nextLong();
    private Context context;
    private static final int DEFAULT_SIZE_DP = 30;
    private static final int DEFAULT_TEXT_SIZE = 14;
    private @Px
    int size;
    private String singleText;
    private @Px
    int textSize;
    private @ColorRes
    int circleColorRes;
    private View circleView;
    private TextView textView;

    public TextPointView(Context context) {
        this(context, null);
    }

    public TextPointView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public TextPointView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        this.context = context;
        init(attrs);
    }

    private void init(AttributeSet attrs) {
        TypedArray typedArray = context.getTheme()
                .obtainStyledAttributes(attrs, R.styleable.TextPointView, 0, 0);
        singleText = typedArray.getString(R.styleable.TextPointView_text);
        circleColorRes = typedArray.getInt(R.styleable.TextPointView_redPointViewBgColor, R.color.bright_red);
        textSize = typedArray.getDimensionPixelSize(R.styleable.TextPointView_textSize, DEFAULT_TEXT_SIZE);
        typedArray.recycle();

        circleView = new View(context);
        circleView.setLayoutParams(new LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT,
                ViewGroup.LayoutParams.MATCH_PARENT));
        setCircleBackgroundColor(circleColorRes);

        textView = new TextView(context);
        LayoutParams params = new LayoutParams(
                ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        params.gravity = Gravity.CENTER;
        textView.setLayoutParams(params);
//        textView.setGravity(Gravity.CENTER_VERTICAL);
//        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN_MR1) {
//            textView.setTextAlignment(View.TEXT_ALIGNMENT_CENTER);
//        }

//    textView.setTypeface(TypefaceUtils.load(context.getAssets(), "fonts/jianshi_default.otf"));
        //        Typeface typeface = ResourcesCompat.getFont(this, R.font.narrow);

//    textView.setTypeface(TypefaceUtils.load(context.getAssets(), "Roboto-RobotoRegular.ttf"));
        textView.setTypeface(ResourcesCompat.getFont(context, R.font.dfonts));
        textView.setTextSize(textSize);
        textView.setTextColor(ContextCompat.getColor(context, R.color.white));
        textView.setText(singleText);

        addView(circleView);
        addView(textView);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        if (MeasureSpec.getMode(widthMeasureSpec) != MeasureSpec.EXACTLY
                || MeasureSpec.getMode(heightMeasureSpec) != MeasureSpec.EXACTLY) {
            size = DisplayUtil.dip2px(context, DEFAULT_SIZE_DP);
        } else {
            int width = MeasureSpec.getSize(widthMeasureSpec);
            int height = MeasureSpec.getSize(heightMeasureSpec);
            size = Math.min(width, height);
        }
        super.onMeasure(MeasureSpec.makeMeasureSpec(size, MeasureSpec.EXACTLY),
                MeasureSpec.makeMeasureSpec(size, MeasureSpec.EXACTLY));
    }

    public void setCircleBackgroundColor(@ColorRes int circleColorRes) {
        ShapeDrawable circleShapeDrawable = new ShapeDrawable();
        circleShapeDrawable.setShape(new OvalShape());
        circleShapeDrawable.getPaint().setColor(ContextCompat.getColor(context, circleColorRes));
        circleView.setBackgroundDrawable(circleShapeDrawable);
    }

    public void setSingleText(String text) {
        if (!TextUtils.isEmpty(text)) {
            this.singleText = text.substring(0, 1);
        } else {
            this.singleText = "";
        }
        textView.setText(singleText);
    }

}
