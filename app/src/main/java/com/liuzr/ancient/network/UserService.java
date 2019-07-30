/*
 * Created by wingjay on 11/16/16 3:32 PM
 * Copyright (c) 2016.  All rights reserved.
 *
 * Last modified 11/10/16 11:05 AM
 *
 * Reach me: https://github.com/wingjay
 * Email: yinjiesh@126.com
 */

package com.liuzr.ancient.network;

import com.liuzr.ancient.bean.ShareContent;

import retrofit2.http.GET;
import rx.Observable;

public interface UserService {


  @GET("app/share")
  Observable<JsonDataResponse<ShareContent>> getShareContent();

}
