package com.liuzr.ancient.db;

import com.raizlabs.android.dbflow.annotation.Database;

@Database(name = AncientDatabase.NAME, version = AncientDatabase.VERSION)
public class AncientDatabase {
  public static final String NAME = "lzr_ancient";
  public static final int VERSION = 8;
}
