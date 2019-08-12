
package com.liuzr.ancient.db;


import android.content.Context;
import android.util.Log;

import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.liuzr.ancient.sync.Operation;
import com.liuzr.ancient.util.DateUtil;
import com.raizlabs.android.dbflow.sql.language.SQLite;

import java.util.List;

import rx.Observable;


public class DiaryService {

  private Context context;

  Gson gson;

  public DiaryService(Context context) {
    this.context = context;
  }

  public Observable<Void> saveDiary(final Diary diary) {
    Log.i("zero", diary.getTitle()+"");
    Log.i("zero", diary.getContent()+"");

    gson = new Gson();

    return Observable.defer(() -> {
      JsonObject jsonObject = new JsonObject();
      diary.setTime(DateUtil.getCurrentTimeStamp());
      if (diary.getTime_removed() > 0) {
        jsonObject.add(Operation.DELETE.getAction(), gson.toJsonTree(diary));
      } else if (diary.getTime_modified() >= diary.getTime_created()) {
        jsonObject.add(Operation.UPDATE.getAction(), gson.toJsonTree(diary));
      } else {
        jsonObject.add(Operation.CREATE.getAction(), gson.toJsonTree(diary));
      }
//      Change.handleChangeByDBKey(Change.DBKey.DIARY, jsonObject);
      diary.save();
      return Observable.just(null);
    });
  }

  public Observable<List<Diary>> getDiaryList() {
    return Observable.defer(() -> Observable.just(fetchDiaryListFromDB()));
  }

  private List<Diary> fetchDiaryListFromDB() {
    return SQLite.select()
        .from(Diary.class)
        .where(Diary_Table.time_removed.eq(0))
        .queryList();
  }

  public Observable<Diary> getDiaryByUuid(final String uuid) {
    return Observable.defer(() -> {
      Diary diary = SQLite
          .select()
          .from(Diary.class)
          .where(Diary_Table.uuid.is(uuid))
          .querySingle();
      return Observable.just(diary);
    });
  }
}
