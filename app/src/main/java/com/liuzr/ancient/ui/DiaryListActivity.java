
package com.liuzr.ancient.ui;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;

import com.liuzr.ancient.R;
import com.liuzr.ancient.db.Diary;
import com.liuzr.ancient.db.DiaryService;
import com.liuzr.ancient.global.BaseActivity;
import com.liuzr.ancient.global.Constants;
import com.liuzr.ancient.ui.adapter.DiaryListAdapter;
import com.liuzr.ancient.util.DateUtil;

import java.util.ArrayList;
import java.util.List;

import androidx.appcompat.app.AlertDialog;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import butterknife.BindView;
import butterknife.OnClick;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Action1;
import rx.schedulers.Schedulers;

public class DiaryListActivity extends BaseActivity implements DiaryListAdapter.RecyclerClickListener{

  private final List<Diary> diaryList = new ArrayList<>();
  private DiaryListAdapter adapter;

  @BindView(R.id.diary_list)
  RecyclerView diaryListView;

  DiaryService diaryService;

  @OnClick(R.id.view_write)
  void write() {
    startActivity(new Intent(this, EditActivity.class));
  }

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_diary_list);
    diaryService = new DiaryService(this);
    diaryListView.setHasFixedSize(true);
    diaryListView.setLayoutManager(
        new LinearLayoutManager(
            DiaryListActivity.this,
            LinearLayoutManager.HORIZONTAL,
            true));

    // get all local diaries
    adapter = new DiaryListAdapter(DiaryListActivity.this, diaryList);
    adapter.setRecyclerClickListener(this);
    diaryListView.setAdapter(adapter);
  }

  @Override
  protected void onResume() {
    super.onResume();
    diaryService.getDiaryList()
        .subscribeOn(Schedulers.io())
        .observeOn(AndroidSchedulers.mainThread())
        .subscribe(new Action1<List<Diary>>() {
          @Override
          public void call(List<Diary> diaries) {
            diaryList.clear();
            diaryList.addAll(diaries);
            adapter.notifyDataSetChanged();
          }
        });
  }

  @Override
  protected void onActivityResult(int requestCode, int resultCode, Intent data) {
    super.onActivityResult(requestCode, resultCode, data);
    if (requestCode == Constants.RequestCode.REQUEST_CODE_VIEW_DIARY_FROM_LIST
        && resultCode == RESULT_OK) {
      adapter.notifyItemRangeChanged(0, diaryList.size());
      adapter.notifyDataSetChanged();
    }
  }

  @Override
  public void onItemClick(Diary diary) {
    startActivity(ViewActivity.createIntent(this, diary.getUuid()));
  }

  @Override
  public void onItemLongClick(final Diary diary) {
    AlertDialog.Builder builder = new AlertDialog.Builder(this);
    builder.setTitle(R.string.do_you_want_to_delete_diary)
        .setPositiveButton(R.string.yes, new DialogInterface.OnClickListener() {
          @Override
          public void onClick(DialogInterface dialog, int which) {
            diary.setTime_removed(DateUtil.getCurrentTimeStamp());
            diaryService.saveDiary(diary)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Action1<Void>() {
                  @Override
                  public void call(Void aVoid) {
                    diaryList.remove(diary);
                    adapter.notifyDataSetChanged();
                  }
                });
          }
        })
        .setNegativeButton(R.string.no, null)
        .create()
        .show();
  }
}
