
package com.liuzr.ancient.ui;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.HorizontalScrollView;
import android.widget.ScrollView;
import android.widget.TextView;

import com.liuzr.ancient.R;
import com.liuzr.ancient.db.model.Diary;
import com.liuzr.ancient.db.service.DiaryService;
import com.liuzr.ancient.prefs.UserPrefs;
import com.liuzr.ancient.ui.base.BaseActivity;
import com.liuzr.ancient.ui.widget.MultipleRowTextView;
import com.liuzr.ancient.ui.widget.TextPointView;
import com.liuzr.ancient.util.DisplayUtil;
import com.liuzr.ancient.util.LanguageUtil;

import butterknife.BindView;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Action1;
import rx.schedulers.Schedulers;

public class ViewActivity extends BaseActivity {

    @BindView(R.id.view_edit)
    TextPointView edit;

    @BindView(R.id.hori_container)
    ScrollView verticalScrollView;

    @BindView(R.id.view_title)
    TextView horizTitle;

    @BindView(R.id.view_content)
    TextView horizContent;

    @BindView(R.id.vertical_container)
    HorizontalScrollView horizontalScrollView;

    @BindView(R.id.vertical_view_title)
    MultipleRowTextView verticalTitle;

    @BindView(R.id.vertical_view_content)
    MultipleRowTextView verticalContent;

    @BindView(R.id.container)
    View container;

    @BindView(R.id.normal_container)
    View normalContainer;

    @BindView(R.id.bottom_container)
    View bottomContainer;

    @BindView(R.id.vertical_view_date)
    MultipleRowTextView verticalDate;

    private String diaryUuid;
    private boolean verticalStyle = false;

    //  @Inject
    DiaryService diaryService;

    //  @Inject
    UserPrefs userPrefs;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view);
        diaryService = new DiaryService(this);
//    JianShiApplication.getAppComponent().inject(this);
        userPrefs = new UserPrefs(ViewActivity.this);
        verticalStyle = userPrefs.getVerticalWrite();
        setVisibilityByVerticalStyle();

        diaryUuid = getIntent().getStringExtra(EditActivity.DIARY_UUID);
        if (diaryUuid == null) {
            finish();
        }

        loadDiary();

        edit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                setResult(RESULT_OK);
                Intent i = EditActivity.createIntentWithId(ViewActivity.this, diaryUuid);
                startActivity(i);
                finish();
            }
        });
    }

    private void loadDiary() {
        diaryService.getDiaryByUuid(diaryUuid)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Action1<Diary>() {
                    @Override
                    public void call(Diary diary) {
                        if (diary != null) {
                            showDiary(
                                    diary.getTitle(),
                                    diary.getContent(),
                                    LanguageUtil.getDiaryDateEnder(
                                            getApplicationContext(),
                                            diary.getTime_created()));
                        }
                    }
                });
    }

    private void showDiary(String titleString, String contentString, String contentDate) {
        setVisibilityByVerticalStyle();

        if (verticalStyle) {
            verticalTitle.setText(titleString);
            verticalContent.setText(contentString);
            verticalDate.setText(contentDate);
            container.setBackgroundResource(userPrefs.getBackgroundColor());
            container.post(new Runnable() {
                @Override
                public void run() {
                    int scrollOffsetX = container.getWidth() - DisplayUtil.getDisplayWidth();
                    if (scrollOffsetX > 0) {
                        horizontalScrollView.scrollBy(scrollOffsetX, 0);
                    }
                }
            });
        } else {
            normalContainer.setBackgroundResource(userPrefs.getBackgroundColor());
            horizTitle.setText(titleString);
            horizContent.setText(contentString + getString(R.string.space_of_date_record_end) + contentDate);
        }
    }

    private void setVisibilityByVerticalStyle() {
        verticalScrollView.setVisibility(verticalStyle ? View.GONE : View.VISIBLE);
        horizontalScrollView.setVisibility(verticalStyle ? View.VISIBLE : View.GONE);
    }

    public static Intent createIntent(Context context, String diaryId) {
        Intent i = new Intent(context, ViewActivity.class);
        i.putExtra(EditActivity.DIARY_UUID, diaryId);
        return i;
    }

}
