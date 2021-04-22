package com.mygdx.game;

import android.app.AlertDialog;
import android.content.Intent;
import android.graphics.Color;
import android.net.Uri;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.Display;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;

import androidx.annotation.NonNull;

import com.badlogic.gdx.Gdx;
import com.google.android.gms.ads.LoadAdError;
import com.google.android.gms.analytics.GoogleAnalytics;
import com.google.android.gms.analytics.HitBuilders;
import com.google.android.gms.analytics.Tracker;
import com.google.firebase.crashlytics.FirebaseCrashlytics;
import com.google.android.gms.ads.AdListener;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdSize;
import com.google.android.gms.ads.AdView;
import com.google.android.gms.ads.InterstitialAd;
import com.google.android.gms.ads.MobileAds;
import com.google.android.gms.ads.reward.RewardItem;
import com.google.android.gms.ads.reward.RewardedVideoAd;
import com.google.android.gms.ads.reward.RewardedVideoAdListener;
import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.common.api.ApiException;
import com.google.android.gms.games.Games;
import com.google.android.gms.games.LeaderboardsClient;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.analytics.FirebaseAnalytics;
//import com.google.firebase.iid.FirebaseInstanceId;
//import com.google.firebase.iid.InstanceIdResult;
import com.google.firebase.remoteconfig.FirebaseRemoteConfig;
import com.google.firebase.remoteconfig.FirebaseRemoteConfigSettings;
import com.platform.IPlatform;

import java.util.Locale;

public class JokerSdk implements IPlatform {
  private static final String TAG = "JokerSDK";
  FrameLayout rootView;
  AdView adView;
  InterstitialAd interstitialAd;
  RewardedVideoAd rewardedVideoAd;
  long lastInterstitialTime;
  RewardItem rewardItemReturn;
  boolean isVideoLoad = false;
  private GoogleSignInClient mGoogleSignInClient;
  private LeaderboardsClient mLeaderboardsClient;
  private FirebaseAnalytics mFirebaseAnalytics;
  private FirebaseRemoteConfig mFirebaseRemoteConfig;
  private static GoogleAnalytics sAnalytics;
  private static Tracker sTracker;
  private static String ANALYTICS_SCREENNAME = "lib3Link";

  private static final int RC_LEADERBOARD_UI = 9004;
  private static final int RC_UNUSED = 5001;
  private static final int RC_SIGN_IN = 9001;

  private static final String LEADERBOARD_ID = "12345";
//  ads test
  private static final String ADMOB_APP_ID = "ca-app-pub-9108876944724815~8160462448";
  private static final String ADMOB_BANNER_ID = "ca-app-pub-3940256099942544/6300978111";
  private static final String ADMOB_FULLSCREEN_ID = "ca-app-pub-3940256099942544/1033173712";
  private static final String ADMOB_VIDEO_ID = "ca-app-pub-3940256099942544/5224354917";

  private OnVideoRewardClosed videoRewardCallback = null;
  boolean bannerVisible = false;


  private AndroidLauncher androidLauncher;

  public JokerSdk(AndroidLauncher android) {
    this.androidLauncher = android;
  }

  public void OnCreate(View libgdxview) {
    FirebaseCrashlytics.getInstance().setCrashlyticsCollectionEnabled(true);
    rootView = new FrameLayout(androidLauncher);
    rootView.addView(libgdxview);
    androidLauncher.setContentView(rootView);

    InitGA();
    InitOldGA();
    InitAd();
    InitFirebase();
    InitRemoteConfig();
    InitLeaderboard();

    Intent appLinkIntent = androidLauncher.getIntent();
    String appLinkAction = appLinkIntent.getAction();
    Uri appLinkData = appLinkIntent.getData();
    InitNotifcationData();
  }
  void InitGA(){
    try {
      mFirebaseAnalytics = FirebaseAnalytics.getInstance(androidLauncher);
    }catch (Exception e){

    }
  }
  void InitOldGA() {
    sAnalytics = GoogleAnalytics.getInstance(androidLauncher);
    Tracker tracker = getDefaultTracker();
    tracker.setScreenName(ANALYTICS_SCREENNAME);
    tracker.send(new HitBuilders.ScreenViewBuilder().build());
  }
  synchronized public Tracker getDefaultTracker() {
    // To enable debug logging use: adb shell setprop log.tag.GAv4 DEBUG
    if (sTracker == null) {
      sTracker = sAnalytics.newTracker(R.xml.global_tracker);
    }

    return sTracker;
  }


  @Override
  public void log(String str) {

  }

  @Override
  public String GetDefaultLanguage() {
    String lang = Locale.getDefault().getLanguage();
    Gdx.app.log("LANG", lang);
    return lang;
  }

  @Override
  public boolean isVideoRewardReady() {
    return isVideoLoad;
  }

  @Override
  public void ShowVideoReward(OnVideoRewardClosed callback) {
    videoRewardCallback = callback;
    androidLauncher.runOnUiThread(new Runnable() {
      @Override
      public void run() {
        if (rewardedVideoAd.isLoaded()) {
          rewardedVideoAd.show();
          TrackCustomEvent("videoshow");
        }
      }
    });
  }

  @Override
  public void ShowFullscreen() {
    long fullscreenTime = GetConfigIntValue("fullscreenTime", 0);
    if(System.currentTimeMillis() - lastInterstitialTime > fullscreenTime) {
      androidLauncher.runOnUiThread(new Runnable() {
        @Override
        public void run() {
          if (interstitialAd.isLoaded()) {
            TrackCustomEvent("fullscreenShow");
            interstitialAd.show();
            lastInterstitialTime = System.currentTimeMillis();
          }

        }
      });
    }
  }

  @Override
  public void ShowBanner(boolean visible) {
    ShowGameBanner(visible);
    bannerVisible = visible;
  }

  @Override
  public void Onshow() {

  }

  @Override
  public void ShareFb() {

  }

  @Override
  public void Resize() {

  }

  @Override
  public int GetConfigIntValue(String name, int defaultValue) {
    try {

      String v = GetConfigStringValue(name, "");
      if(v.equals(""))
        return defaultValue;

      return Integer.parseInt(v);
    }
    catch(Exception e){
      return defaultValue;
    }
  }

  @Override
  public float GetConfigFloatValue(String name, float defaultValue) {
    try {

      String v = GetConfigStringValue(name, "");
      if(v.equals(""))
        return defaultValue;

      return Float.parseFloat(v);
    }
    catch(Exception e){
      return defaultValue;
    }
  }

  @Override
  public boolean GetConfigBooleanValue(String name, boolean defaultValue) {
    try {

      String v = GetConfigStringValue(name, "");
      if(v.equals(""))
        return defaultValue;

      return Boolean.parseBoolean(v);
    }
    catch(Exception e){
      return defaultValue;
    }
  }

  @Override
  public String GetConfigStringValue(String name, String defaultValue) {
    try {
      String v = mFirebaseRemoteConfig.getString(name);
      Log.i("remoteConfig", "name=" + name + " v="+v);
      if (v.equals(""))
        return defaultValue;
      return v;
    }
    catch(Exception e){
      return defaultValue;
    }
  }

  @Override
  public void CrashKey(String key, String value) {
    FirebaseCrashlytics.getInstance().setCustomKey(key, value);
  }

  @Override
  public void CrashLog(String log) {
    FirebaseCrashlytics.getInstance().log(log);
  }

  @Override
  public void TrackCustomEvent(String event) {
    try{
      Bundle bundle = new Bundle();
      mFirebaseAnalytics.logEvent(event, bundle);
    }catch(Exception e){}

//    FirebaseAnalytics.getInstance(androidLauncher).logEvent(event,new Bundle());

//    Bundle params = new Bundle();
//    params.putString(FirebaseAnalytics.Param.ITEM_CATEGORY, "GamePlay");
//    params.putString(FirebaseAnalytics.Param.ITEM_NAME, event);
//    FirebaseAnalytics.getInstance(androidLauncher).logEvent(FirebaseAnalytics.Event.VIEW_ITEM, params);
  }

  public void TrackingLevel(int lv) {
    TrackCustomEvent("EnterLevel"+lv);
  }

  @Override
  public void TrackLevelInfo(String event, int mode, int difficult, int level) {

  }

  @Override
  public void TrackPlayerInfo(String event, int mode, int difficult, int level) {

  }

  @Override
  public void TrackPlaneInfo(String event, int planeid, int level) {

  }

  @Override
  public void TrackVideoReward(String type) {

  }

  @Override
  public void TrackPlayerDead(String event, int mode, int difficult, int level, int parentModel, int shooterModel, boolean isBoss) {

  }

  @Override
  public void ReportScore(long score) {
    ReportGameScore(LEADERBOARD_ID, score);
  }

  @Override
  public void ShowLeaderboard() {
    try {
      ShowGameLeaderBoard();
    }
    catch (Exception e){
      e.printStackTrace();
    }
  }

  public void InitAd() {
    MobileAds.initialize(androidLauncher, ADMOB_APP_ID);

    androidLauncher.runOnUiThread(new Runnable() {
      @Override
      public void run() {
        try {
          InitVideoReward();
          InitBanner();
          InitInterstitial();
        }catch(Exception e){}
      }
    });
  }


  void InitVideoReward(){
    rewardedVideoAd = MobileAds.getRewardedVideoAdInstance(androidLauncher);
    rewardedVideoAd.setRewardedVideoAdListener(new RewardedVideoAdListener() {
      @Override
      public void onRewardedVideoAdLoaded() {
        Log.i("VIDEO", "onRewardedVideoAdLoaded");
        isVideoLoad = true;
      }

      @Override
      public void onRewardedVideoAdOpened() {
        Log.i("VIDEO", "onRewardedVideoAdOpened");
      }

      @Override
      public void onRewardedVideoStarted() {
        Log.i("VIDEO", "onRewardedVideoStarted");
      }

      @Override
      public void onRewardedVideoAdClosed() {
        Log.i("VIDEO", "onRewardedVideoAdClosed");

        if(videoRewardCallback!=null){
          if(rewardItemReturn != null) {
            videoRewardCallback.OnEvent(true);
            //GameAnalytics.addDesignEventWithEventId("rewardedVideo");
            //zenObj.TrackCustomEvent("videoShow");

          }
          else
            videoRewardCallback.OnEvent(false);
          videoRewardCallback = null;
        }

        isVideoLoad = false;
        LoadRewardedVideoAd();


      }

      @Override
      public void onRewarded(RewardItem rewardItem) {
        lastInterstitialTime = System.currentTimeMillis();
        rewardItemReturn = rewardItem;

        Log.i("VIDEO", "onRewarded");

      }

      @Override
      public void onRewardedVideoAdLeftApplication() {
        Log.i("VIDEO", "onRewardedVideoAdLeftApplication");
      }

      @Override
      public void onRewardedVideoAdFailedToLoad(int i) {
        Log.i("VIDEO", "onRewardedVideoAdFailedToLoad");

        isVideoLoad = false;
        LoadRewardedVideoAd();
      }

      @Override
      public void onRewardedVideoCompleted() {

      }
    });
    LoadRewardedVideoAd();
  }
  private void LoadRewardedVideoAd() {

    rewardItemReturn = null;
    AdRequest.Builder adrequest = new AdRequest.Builder();

    rewardedVideoAd.loadAd(ADMOB_VIDEO_ID,  adrequest.build());
  }


  void InitInterstitial(){
    interstitialAd = new InterstitialAd(androidLauncher);
    interstitialAd.setAdUnitId(ADMOB_FULLSCREEN_ID);
    interstitialAd.loadAd(new AdRequest.Builder().build());
    lastInterstitialTime = System.currentTimeMillis();

    interstitialAd.setAdListener(new AdListener() {
      @Override
      public void onAdLeftApplication() {
        interstitialAd.loadAd(new AdRequest.Builder().build());
      }

      @Override
      public void onAdClosed() {
        interstitialAd.loadAd(new AdRequest.Builder().build());
      }
    });
  }

  void InitBanner(){

    adView = new AdView(androidLauncher);
    adView.setAdUnitId(ADMOB_BANNER_ID);
    AdSize adsize = getAdSize();
    adView.setAdSize(adsize);
    adView.setBackgroundColor(Color.TRANSPARENT);

    RelativeLayout relativeLayout = new RelativeLayout(androidLauncher);
    rootView.addView(relativeLayout);

    RelativeLayout.LayoutParams adViewParams = new RelativeLayout.LayoutParams(AdView.LayoutParams.WRAP_CONTENT, AdView.LayoutParams.WRAP_CONTENT);
    adViewParams.addRule(RelativeLayout.ALIGN_PARENT_TOP);
    adViewParams.addRule(RelativeLayout.CENTER_IN_PARENT, RelativeLayout.TRUE);
    adViewParams.height = adsize.getHeightInPixels(androidLauncher);
    relativeLayout.addView(adView, adViewParams);

    adView.setAdListener(new AdListener() {
      @Override
      public void onAdLoaded() {
        super.onAdLoaded();
        adView.setVisibility(View.GONE);
        ShowGameBanner(bannerVisible);
      }

      @Override
      public void onAdFailedToLoad(LoadAdError loadAdError) {
        super.onAdFailedToLoad(loadAdError);
        adView.setVisibility(View.INVISIBLE);
        Gdx.app.log("onAdFailedToLoad", loadAdError.toString());
      }
    });

    adView.loadAd(new AdRequest.Builder().build());



  }
  private AdSize getAdSize() {
    // Step 2 - Determine the screen width (less decorations) to use for the ad width.
    Display display = androidLauncher.getWindowManager().getDefaultDisplay();
    DisplayMetrics outMetrics = new DisplayMetrics();
    display.getMetrics(outMetrics);

    float widthPixels = outMetrics.widthPixels;
    float density = outMetrics.density;

    int adWidth = (int) (widthPixels / density);

    // Step 3 - Get adaptive ad size and return for setting on the ad view.
    return AdSize.getCurrentOrientationAnchoredAdaptiveBannerAdSize(androidLauncher, adWidth);
  }

  void InitFirebase(){
    try {
//      mFirebaseAnalytics = FirebaseAnalytics.getInstance(androidLauncher);
//      FirebaseInstanceId.getInstance().getInstanceId()
//              .addOnCompleteListener(new OnCompleteListener<InstanceIdResult>() {
//                @Override
//                public void onComplete(Task<InstanceIdResult> task) {
//                  //Log.d("IID_TOKEN", task.getResult().getToken());
//                }
//              });
    }
    catch(Exception e){
      e.printStackTrace();
    }
  }

  public void ShowGameBanner(boolean visible) {
    final boolean v = visible;
    androidLauncher.runOnUiThread(new Runnable() {
      @Override
      public void run() {
        if(v)
          adView.setVisibility(View.VISIBLE);
        else
          adView.setVisibility(View.GONE);
      }
    });

  }

  public void InitRemoteConfig(){
    try {
      mFirebaseRemoteConfig = FirebaseRemoteConfig.getInstance();
      FirebaseRemoteConfigSettings configSettings = new FirebaseRemoteConfigSettings.Builder().build();
      mFirebaseRemoteConfig.setConfigSettingsAsync(configSettings);
      mFirebaseRemoteConfig.setDefaultsAsync(R.xml.remote_config_defaults);
      int cacheExpiration = 1;
      mFirebaseRemoteConfig.fetch(cacheExpiration)
              .addOnCompleteListener(androidLauncher, new OnCompleteListener<Void>() {
                @Override
                public void onComplete(@NonNull Task<Void> task) {
                  if (task.isSuccessful()) {
                    Log.i("remoteconfig", "ok");
//                    mFirebaseRemoteConfig.activateFetched();
                    mFirebaseRemoteConfig.fetchAndActivate();
                  } else {
                    Log.i("remoteconfig", "false");
                  }

                }
              });
    }catch (Exception e){
      e.printStackTrace();
    }

  }


  void InitLeaderboard() {
    GoogleSignInOptions gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_GAMES_SIGN_IN)
            .build();

    mGoogleSignInClient = GoogleSignIn.getClient(androidLauncher, gso);
  }

  //Notify
  public static final int RESTART_ID = 1998;
  int notifyId=-1;

  void InitNotifcationData(){
    notifyId = androidLauncher.getIntent().getIntExtra("id", -1);
    if(notifyId == RESTART_ID)
      notifyId = -1;
  }

  public void RestartApp(){
    Intent mStartActivity = new Intent(androidLauncher, AndroidLauncher.class);
    DailyNotification.PendingRestartApplication(mStartActivity, androidLauncher, RESTART_ID);
    System.exit(0);
  }

  @Override
  public void Restart() {
    RestartApp();
  }

  @Override
  public int GetNotifyId() {
    return notifyId;
  }

  @Override
  public void SetDailyNotification(int id, String header, String content, int days, int hours) {
    DailyNotification.SetDailyNotification(androidLauncher, id, header, content, days, hours);
  }

  @Override
  public void CancelDailyNotification(int id){
    DailyNotification.CancelDailyNotification(androidLauncher, id);
  }

  public void ReportGameScore(String leaderboardID, long score) {
    try {
      if (isSignedIn()) {
        Games.getLeaderboardsClient(androidLauncher, GoogleSignIn.getLastSignedInAccount(androidLauncher))
                .submitScore(LEADERBOARD_ID, score);
      }
    }
    catch(Exception e){}
  }

  public void ShowGameLeaderBoard() {
    try {
      if (isSignedIn()) {
        Games.getLeaderboardsClient(androidLauncher, GoogleSignIn.getLastSignedInAccount(androidLauncher))
                .getLeaderboardIntent(LEADERBOARD_ID)
                .addOnSuccessListener(new OnSuccessListener<Intent>() {
                  @Override
                  public void onSuccess(Intent intent) {
                    try {
                      androidLauncher.startActivityForResult(intent, RC_LEADERBOARD_UI);
                    }
                    catch (Exception e){

                    }
                  }
                });
      } else {
        forceSignIn = true;
        signInSilently(new Runnable() {
          @Override
          public void run() {
            ShowGameLeaderBoard();
          }
        });
      }
    }
    catch (Exception e){}
  }

  private boolean isSignedIn() {
    return GoogleSignIn.getLastSignedInAccount(androidLauncher) != null;
  }

  boolean forceSignIn = false;

  Runnable signinAction;

  private void signInSilently(Runnable action) {
    signinAction = action;
    mGoogleSignInClient.silentSignIn().addOnCompleteListener(androidLauncher,
            new OnCompleteListener<GoogleSignInAccount>() {
              @Override
              public void onComplete(@NonNull Task<GoogleSignInAccount> task) {
                if (task.isSuccessful()) {
                  Log.d(TAG, "signInSilently(): success");
                  if (signinAction != null)
                    signinAction.run();

                  signinAction = null;
                  //onConnected(task.getResult());
                } else {
                  Log.d(TAG, "signInSilently(): failure", task.getException());
                  //onDisconnected();
                  if (forceSignIn) {
                    startSignInIntent();
                  }
                }
              }
            });
  }

  private void startSignInIntent() {
    try {
      androidLauncher.startActivityForResult(mGoogleSignInClient.getSignInIntent(), RC_SIGN_IN);
    }catch (Exception e){

    }

  }

  GoogleSignInAccount mSignedInAccount = null;

  private void onConnected(GoogleSignInAccount googleSignInAccount) {
    Log.d(TAG, "onConnected(): connected to Google APIs");
    if (mSignedInAccount != googleSignInAccount) {
      mSignedInAccount = googleSignInAccount;
    }

  }

  public void onResume() {
    rewardedVideoAd.resume(androidLauncher);
    Log.d(TAG, "onResume()");
  }

  public void onPause() {
    rewardedVideoAd.pause(androidLauncher);
  }

  public void onDestroy() {
    rewardedVideoAd.destroy(androidLauncher);
  }

  public void onActivityResult(int requestCode, int resultCode, Intent intent) {
    if (requestCode == RC_SIGN_IN) {

      Task<GoogleSignInAccount> task =
              GoogleSignIn.getSignedInAccountFromIntent(intent);

      try {
        GoogleSignInAccount account = task.getResult(ApiException.class);
        onConnected(account);
        Log.d("SignIn", "SignIn");

        if (signinAction != null) {
          signinAction.run();
        }
        signinAction = null;
      } catch (ApiException apiException) {
        String message = apiException.getMessage();
        if (message == null || message.isEmpty()) {
          message = "Sign In Error";
        }

        //onDisconnected();

        new AlertDialog.Builder(androidLauncher)
                .setMessage(message)
                .setNeutralButton(android.R.string.ok, null)
                .show();
      }
    }
  }


}
