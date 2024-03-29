package com.liuzr.ancient.ui.widget;

import android.content.Context;
import android.content.res.TypedArray;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.widget.TextView;

import com.liuzr.ancient.R;
import com.liuzr.ancient.util.DisplayUtil;

import androidx.core.content.res.ResourcesCompat;


public class VerticalTextView extends TextView {

  public VerticalTextView(Context context) {
    this(context, null);
    setTypeface(ResourcesCompat.getFont(context, R.font.dfonts));
  }

  public VerticalTextView(Context context, AttributeSet attrs) {
    super(context, attrs, 0);
    setTypeface(ResourcesCompat.getFont(context, R.font.dfonts));
  }

  public VerticalTextView(Context context, AttributeSet attrs, int defStyleAttr) {
    super(context, attrs, defStyleAttr);
    TypedArray typedArray = context.getTheme()
        .obtainStyledAttributes(attrs, R.styleable.VerticalTextView, 0, 0);
    try {
      float textSizePixel = typedArray.getDimension(R.styleable.VerticalTextView_verticalTextSize,
          getResources().getDimension(R.dimen.normal_text_size));
      int textSizeSp = DisplayUtil.px2sp(context, textSizePixel);
      setTextSize(textSizeSp);
    } finally {
      typedArray.recycle();
    }
    setTypeface(ResourcesCompat.getFont(context, R.font.dfonts));
  }

  @Override
  public void setText(CharSequence text, BufferType type) {
    if (TextUtils.isEmpty(text)) {
      super.setText(text, type);
      return;
    }
    StringBuffer stringBuffer = new StringBuffer();
    int length = text.length();
    for (int i=0; i<length; i++) {
      CharSequence sequence = text.toString().subSequence(i, i+1);
      stringBuffer.append(sequence);
      if (i < length - 1) {
        stringBuffer.append("\n");
      }
    }
    super.setText(stringBuffer, type);
  }
}
