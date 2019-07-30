
package com.liuzr.ancient.ui;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Pair;
import android.view.View;
import android.widget.HorizontalScrollView;
import android.widget.ScrollView;
import android.widget.TextView;

import com.liuzr.ancient.R;
import com.liuzr.ancient.bean.ShareContent;
import com.liuzr.ancient.db.model.Diary;
import com.liuzr.ancient.db.service.DiaryService;
import com.liuzr.ancient.global.Constants;
import com.liuzr.ancient.manager.ScreenshotManager;
import com.liuzr.ancient.network.JsonDataResponse;
import com.liuzr.ancient.network.UserService;
import com.liuzr.ancient.prefs.UserPrefs;
import com.liuzr.ancient.ui.base.BaseActivity;
import com.liuzr.ancient.ui.widget.MultipleRowTextView;
import com.liuzr.ancient.ui.widget.TextPointView;
import com.liuzr.ancient.util.DisplayUtil;
import com.liuzr.ancient.util.IntentUtil;
import com.liuzr.ancient.util.LanguageUtil;

import java.io.File;

import butterknife.BindView;
import butterknife.OnClick;
import rx.Observable;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Action0;
import rx.functions.Action1;
import rx.functions.Func1;
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

    //  @Inject
    UserService userService;

    //  @Inject
    ScreenshotManager screenshotManager;

    @OnClick(R.id.view_share)
    void share() {

        final View target = verticalStyle ? container : normalContainer;
        final ProgressDialog dialog = ProgressDialog.show(this, "", "加载中...");
        screenshotManager.shotScreen(target, "temp.jpg")
                .observeOn(Schedulers.io())
                .filter(new Func1<String, Boolean>() {
                    @Override
                    public Boolean call(String s) {
                        return !TextUtils.isEmpty(s);
                    }
                })
                .flatMap(new Func1<String, Observable<Pair<String, ShareContent>>>() {
                    @Override
                    public Observable<Pair<String, ShareContent>> call(String path) {
                        ShareContent shareContent = new ShareContent();
                        try {
                            JsonDataResponse<ShareContent> response = userService.getShareContent().toBlocking().first();
                            if (response.getRc() == Constants.ServerResultCode.RESULT_OK && response.getData() != null) {
                                shareContent = response.getData();
                            }
                        } catch (Exception e) {
                            return Observable.just(Pair.create(path, shareContent));
                        }
                        return Observable.just(Pair.create(path, shareContent));
                    }
                })
                .observeOn(AndroidSchedulers.mainThread())
                .doOnTerminate(new Action0() {
                    @Override
                    public void call() {
                        dialog.dismiss();
                    }
                })
                .subscribe(new Action1<Pair<String, ShareContent>>() {
                    @Override
                    public void call(Pair<String, ShareContent> stringShareContentPair) {
                        if (!isUISafe()) {
                            return;
                        }
                        IntentUtil.shareLinkWithImage(ViewActivity.this,
                                stringShareContentPair.second,
                                Uri.fromFile(new File(stringShareContentPair.first)));
                    }
                }, new Action1<Throwable>() {
                    @Override
                    public void call(Throwable throwable) {
                        makeToast(getString(R.string.share_failure));
                        dialog.dismiss();
                    }
                });
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view);
        diaryService = new DiaryService(this);
//    JianShiApplication.getAppComponent().inject(this);
        userPrefs = new UserPrefs(ViewActivity.this);
        screenshotManager = new ScreenshotManager(this);
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
