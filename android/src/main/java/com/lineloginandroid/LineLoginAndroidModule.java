package com.lineloginandroid;

import static android.util.Log.*;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.strictmode.IntentReceiverLeakedViolation;
import android.provider.SyncStateContract;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.app.ActivityCompat;

import com.facebook.react.bridge.ActivityEventListener;
import com.facebook.react.bridge.BaseActivityEventListener;
import com.facebook.react.bridge.NativeModule;
import com.facebook.react.bridge.Promise;
import com.facebook.react.bridge.ReactApplicationContext;
import com.facebook.react.bridge.ReactContext;
import com.facebook.react.bridge.ReactContextBaseJavaModule;
import com.facebook.react.bridge.ReactMethod;

import java.util.Arrays;
import java.util.Map;
import java.util.HashMap;
import java.lang.Object;

import com.linecorp.linesdk.Scope;
import com.linecorp.linesdk.auth.LineAuthenticationParams;
import com.linecorp.linesdk.auth.LineLoginApi;
import com.linecorp.linesdk.auth.LineLoginResult;
import com.google.gson.Gson;

public class LineLoginAndroidModule extends ReactContextBaseJavaModule implements ActivityEventListener {
  public static final String NAME = "LineLoginAndroid";

  private static final int REQUEST_CODE = 1;
  private static final String E_ACTIVITY_DOES_NOT_EXIST = "E_ACTIVITY_DOES_NOT_EXIST";

  private ReactApplicationContext reactContext = null;
  private Promise mPromise;

  public LineLoginAndroidModule(ReactApplicationContext reactContext) {
    super(reactContext);
    reactContext.addActivityEventListener(this);
    this.reactContext = reactContext;
  }

  @Override
  @NonNull
  public String getName() {
    return NAME;
  }

  @Override
  public void onActivityResult(Activity activity, int requestCode, int resultCode, @Nullable Intent data) {
    if (requestCode != REQUEST_CODE) {
      Log.e("ERROR", "Unsupported Request");
      return;
    }
    if (resultCode == Activity.RESULT_CANCELED) {
      mPromise.reject("RESULT_CANCELED", " Fail");
    } else if (resultCode == Activity.RESULT_OK) {
      LineLoginResult result = LineLoginApi.getLoginResultFromIntent(data);
      Log.d("da gui", ":" + result.toString());
      if (result == null) {
        mPromise.reject("result null", " fail");
      } else {
        Gson gson = new Gson();
        String json = gson.toJson(result);
        mPromise.resolve(json);
      }
    }


  }

  @Override
  public void onNewIntent(Intent intent) {

  }

  @ReactMethod
  public void Login(String channelId, final Promise promise) {
    Activity currentActivity = getCurrentActivity();

    if (currentActivity == null) {
      promise.reject(E_ACTIVITY_DOES_NOT_EXIST, "Activity doesn't exist");
      return;
    }
    mPromise = promise;

    try {
      Intent loginIntent = LineLoginApi.getLoginIntent(
        reactContext,
        channelId,
        new LineAuthenticationParams.Builder()
          .scopes(Arrays.asList(Scope.PROFILE))
          .build());
      currentActivity.startActivityForResult(loginIntent, REQUEST_CODE);
    } catch (Exception e) {
      Log.e("ERRRRRRRRRRRRRR", e.toString());
    }

  }
}

