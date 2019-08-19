package com.liuzr.ancient.ui.theme;

import com.liuzr.ancient.R;

import java.util.Arrays;
import java.util.List;

/**
 * Created by wingjay on 10/11/15.
 */
public enum TraditionalColorNamer {
  DEFAULT(R.color.normal_bg, "묵인"),
  ZHU_QING(R.color.zhu_qing, "죽청"),
  CHI_JIN(R.color.chi_jin, "적금"),
  QIAN_BAI(R.color.qian_bai, "연백"),
  YING_BAI(R.color.ying_bai, "영백"),
  SU(R.color.su, "소"),
  YUE_BAI(R.color.yue_bai, "월백"),
  SHUI_HONG(R.color.shui_hong, "물빛"),
  CANG_HUANG(R.color.cang_huang, "노란색"),
  DING_XIANG_SE(R.color.ding_xiang_se, "라일락색"),
  AI_LV(R.color.ai_lv, "에메랄드"),
  YU_SE(R.color.yu_se, "옥색"),
  HUANG_LU(R.color.huang_lu, "황색"),
  JIANG_HUANG(R.color.jiang_huang, "강황");

  private int colorRes;
  private String colorName;
  private static List<TraditionalColorNamer> colorNamerList;

  TraditionalColorNamer(int res, String name) {
    this.colorRes = res;
    this.colorName = name;
  }

  static {
    TraditionalColorNamer[] list = {
        DEFAULT, ZHU_QING, CHI_JIN, QIAN_BAI, YING_BAI, SU, YUE_BAI, SHUI_HONG,
        CANG_HUANG, DING_XIANG_SE, AI_LV, YU_SE, HUANG_LU, JIANG_HUANG
    };
    colorNamerList = Arrays.asList(list);
  }

  public String getColorName() {
    return this.colorName;
  }

  public int getColorRes() {
    return this.colorRes;
  }

  public static List<TraditionalColorNamer> getAllColorNamer() {
    return colorNamerList;
  }
}
