package com.mygdx.game.desktop;

import com.badlogic.gdx.backends.lwjgl.LwjglApplication;
import com.badlogic.gdx.backends.lwjgl.LwjglApplicationConfiguration;
//import com.pathFinder.ThreeConnect;
import com.platform.IPlatform;
import com.ss.GMain;
import com.ss.gameLogic.config.Config;

public class DesktopLauncher {
  public static void main (String[] arg) {
//    LwjglApplicationConfiguration config = new LwjglApplicationConfiguration();
//    config.width = 720*2/3;
//    config.height = 1280*2/3;
//    config.x= 1180;
      LwjglApplicationConfiguration config = new LwjglApplicationConfiguration();
//      config.width = 720;
//      config.height = 800;
    config.width = 720/2;
    config.height = 1280/2;
    config.title = "Three Connect";
    config.samples = 10;
//      new LwjglApplication(new ThreeConnect(), config);
    new LwjglApplication(new GMain(new IPlatform() {
      @Override
      public void log(String str) {

      }

      @Override
      public String GetDefaultLanguage() {
        return null;
      }

      @Override
      public boolean isVideoRewardReady() {
        return false;
      }

      @Override
      public void ShowVideoReward(OnVideoRewardClosed callback) {

      }

      @Override
      public void ShowFullscreen() {

      }

      @Override
      public void ShowBanner(boolean visible) {

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
        return defaultValue;
      }

      @Override
      public float GetConfigFloatValue(String name, float defaultValue) {
        return defaultValue;
      }

      @Override
      public boolean GetConfigBooleanValue(String name, boolean defaultValue) {
        return defaultValue;
      }

      @Override
      public String GetConfigStringValue(String name, String defaultValue) {
//        if(name.equals(""))
          return defaultValue;
//        else
//          return name;
      }

      @Override
      public void CrashKey(String key, String value) {

      }

      @Override
      public void CrashLog(String log) {

      }

      @Override
      public void TrackCustomEvent(String event) {

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

      }

      @Override
      public void ShowLeaderboard() {

      }

      @Override
      public void Restart() {

      }

      @Override
      public int GetNotifyId() {
        return 0;
      }

      @Override
      public void SetDailyNotification(int id, String header, String content, int days, int hours) {

      }

      @Override
      public void CancelDailyNotification(int id) {

      }
    }), config);
  }
}
