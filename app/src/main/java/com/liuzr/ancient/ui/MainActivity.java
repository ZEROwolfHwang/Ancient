
package com.liuzr.ancient.ui;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;

import com.liuzr.ancient.R;
import com.liuzr.ancient.bean.ImagePoem;
import com.liuzr.ancient.global.Constants;
import com.liuzr.ancient.manager.FullDateManager;
import com.liuzr.ancient.prefs.UserPrefs;
import com.liuzr.ancient.ui.base.BaseActivity;
import com.liuzr.ancient.ui.widget.TextPointView;
import com.liuzr.ancient.ui.widget.ThreeLinePoemView;
import com.liuzr.ancient.ui.widget.VerticalTextView;
import com.squareup.picasso.Picasso;

import org.joda.time.DateTime;

import butterknife.BindView;
import butterknife.OnClick;

public class MainActivity extends BaseActivity {

    private final static String YEAR = "year";
    private final static String MONTH = "month";
    private final static String DAY = "day";

    @BindView(R.id.background_image)
    ImageView backgroundImage;

    @BindView(R.id.year)
    VerticalTextView yearTextView;

    @BindView(R.id.month)
    VerticalTextView monthTextView;

    @BindView(R.id.day)
    VerticalTextView dayTextView;

    @BindView(R.id.writer)
    TextPointView writerView;

    @BindView(R.id.reader)
    TextPointView readerView;

    @BindView(R.id.three_line_poem)
    ThreeLinePoemView threeLinePoemView;

    UserPrefs userPrefs;


    private volatile int year, month, day;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_main);
        userPrefs = UserPrefs.getInstance(this);

        if (savedInstanceState != null) {
            year = savedInstanceState.getInt(YEAR);
            month = savedInstanceState.getInt(MONTH);
            day = savedInstanceState.getInt(DAY);
        } else {
            setTodayAsFullDate();
        }
        updateFullDate();

        writerView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(MainActivity.this, EditActivity.class);
                startActivity(i);
            }
        });

        readerView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(MainActivity.this, DiaryListActivity.class));
            }
        });

//    SyncService.syncImmediately(this);
    }

    @Override
    protected void onStart() {
        super.onStart();
        setupImagePoemBackground();
    }

    private void setupImagePoemBackground() {
        if (!userPrefs.getHomeImagePoemSetting()) {
            setContainerBgColorFromPrefs();
            return;
        }

        if (!userPrefs.canFetchNextHomeImagePoem() && userPrefs.getLastHomeImagePoem() != null) {
            // use last imagePoem data
            setImagePoem(userPrefs.getLastHomeImagePoem());
            return;
        }

//    if (backgroundImage.getWidth() == 0) {
//      backgroundImage.getViewTreeObserver().addOnGlobalLayoutListener(new ViewTreeObserver.OnGlobalLayoutListener() {
//        @Override
//        public void onGlobalLayout() {
//          backgroundImage.getViewTreeObserver().removeGlobalOnLayoutListener(this);
//          loadImagePoem();
//        }
//      });
//    } else {
//      loadImagePoem();
//    }
    }

//  private void loadImagePoem() {
//    userService.getImagePoem(backgroundImage.getWidth(), backgroundImage.getHeight())
//        .compose(RxUtil.<JsonDataResponse<ImagePoem>>normalSchedulers())
//        .filter(new Func1<JsonDataResponse<ImagePoem>, Boolean>() {
//          @Override
//          public Boolean call(JsonDataResponse<ImagePoem> response) {
//            return (response.getRc() == Constants.ServerResultCode.RESULT_OK)
//                && (response.getData() != null);
//          }
//        })
//        .subscribe(new Action1<JsonDataResponse<ImagePoem>>() {
//          @Override
//          public void call(JsonDataResponse<ImagePoem> response) {
//            setImagePoem(response.getData());
//            userPrefs.setLastHomeImagePoem(response.getData());
//            userPrefs.setNextFetchHomeImagePoemTime(response.getData().getNextFetchTimeSec());
//          }
//        }, new Action1<Throwable>() {
//          @Override
//          public void call(Throwable throwable) {
//            Timber.e(throwable, "getImagePoem() failure");
//          }
//        });
//  }

    private void setImagePoem(ImagePoem imagePoem) {
        setContainerBgColor(R.color.transparent);
        if (imagePoem == null) {
            Picasso.with(this)
                    .load(R.mipmap.default_home_image)
                    .fit()
                    .centerCrop()
                    .into(backgroundImage);
        } else {
            Picasso.with(MainActivity.this)
                    .load(imagePoem.getImageUrl())
                    .placeholder(R.mipmap.default_home_image)
                    .fit()
                    .centerCrop()
                    .into(backgroundImage);
            threeLinePoemView.setThreeLinePoem(imagePoem.getPoem());
        }
    }

    @OnClick(R.id.setting)
    void toSettingsPage(View v) {
        Intent intent = new Intent(MainActivity.this, SettingActivity.class);
        startActivityForResult(intent, Constants.RequestCode.REQUEST_CODE_BG_COLOR_CHANGE);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == Constants.RequestCode.REQUEST_CODE_BG_COLOR_CHANGE) {
            if (resultCode == RESULT_OK) {
                setContainerBgColorFromPrefs();
            }
        }
    }

    private void setDate(DateTime date) {
        year = date.getYear();
        month = date.getMonthOfYear();
        day = date.getDayOfMonth();
    }

    private void setTodayAsFullDate() {
        DateTime currentDateTime = new DateTime();
        setDate(currentDateTime);
    }

    private void updateFullDate() {
        FullDateManager fullDateManager = new FullDateManager();
        yearTextView.setText(fullDateManager.getYear(year));
        monthTextView.setText(fullDateManager.getMonth(month));
        dayTextView.setText(fullDateManager.getDay(day));
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        outState.putInt(YEAR, year);
        outState.putInt(MONTH, month);
        outState.putInt(DAY, day);
        super.onSaveInstanceState(outState);
    }

}
