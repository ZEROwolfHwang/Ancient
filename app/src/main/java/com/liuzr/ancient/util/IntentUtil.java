

package com.liuzr.ancient.util;


import android.content.Context;
import android.content.Intent;
import android.net.Uri;

import com.liuzr.ancient.bean.ShareContent;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

public class IntentUtil {

  private IntentUtil() {
  }

  public static void shareLinkWithImage(@NonNull Context context,
                                        @NonNull ShareContent shareContent,
                                        @Nullable Uri imageUri) {
    Intent shareIntent = new Intent();
    shareIntent.setAction(Intent.ACTION_SEND);
    shareIntent.putExtra(Intent.EXTRA_TEXT, shareContent.getShareText());
    shareIntent.setType("text/plain");
    if (imageUri != null) {
      shareIntent.putExtra(Intent.EXTRA_STREAM, imageUri);
      shareIntent.setType("image/*");
    }
    shareIntent.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);

    Intent chooserIntent = Intent.createChooser(shareIntent, "分享");
    context.startActivity(chooserIntent);
  }
}
