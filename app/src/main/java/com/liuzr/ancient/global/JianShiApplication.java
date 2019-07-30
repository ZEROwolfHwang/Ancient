
package com.liuzr.ancient.global;

import android.app.Application;

import com.raizlabs.android.dbflow.config.FlowConfig;
import com.raizlabs.android.dbflow.config.FlowManager;


public class JianShiApplication extends Application {

  private static JianShiApplication instance;

  public static JianShiApplication getInstance() {
    return instance;
  }

  @Override
  public void onCreate() {
    super.onCreate();

    instance = this;

    // 创建数据库
    FlowManager.init(new FlowConfig.Builder(this).openDatabasesOnInit(true).build());

  }

}
