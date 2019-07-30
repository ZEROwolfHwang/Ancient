
package com.liuzr.ancient.ui.widget;

import android.content.Context;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.FrameLayout;

import com.liuzr.ancient.R;
import com.liuzr.ancient.manager.FullDateManager;

public class DayChooser extends FrameLayout {

  private Context context;
  private OnDayChooserClickListener onDayChooserClickListener;

  public DayChooser(Context context, AttributeSet attrs) {
    super(context, attrs);
    init(context);
  }

  private void init(Context context) {
    this.context = context;
    LayoutInflater.from(context).inflate(R.layout.view_day_chooser, this);
    VerticalTextView minDayChooser = (VerticalTextView) findViewById(R.id.min_day_chooser);
    VerticalTextView midDayChooser = (VerticalTextView) findViewById(R.id.mid_day_chooser);
    VerticalTextView maxDayChooser = (VerticalTextView) findViewById(R.id.max_day_chooser);

    addVerticalText(maxDayChooser, 25);
    addVerticalText(midDayChooser, 15);
    addVerticalText(minDayChooser, 5);

  }

  private void addVerticalText(VerticalTextView textView, final int day) {
    FullDateManager fullDateManager = new FullDateManager();
    textView.setText(fullDateManager.getPureDay(day));
    textView.setOnClickListener(new OnClickListener() {
      @Override
      public void onClick(View v) {
        if (onDayChooserClickListener != null) {
          onDayChooserClickListener.onDayChoose(day);
        }
      }
    });
  }

  public void setOnDayChooserClickListener(OnDayChooserClickListener OnDayChooserClickListener) {
    this.onDayChooserClickListener = OnDayChooserClickListener;
  }

  public interface OnDayChooserClickListener {
    void onDayChoose(int chooseDay);
  }

}
