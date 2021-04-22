package com.ss.effects;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Graphics;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.audio.Sound;
import com.ss.core.util.GAssetsManager;
import com.ss.core.util.GSound;
import com.ss.gdx.NSound;

/* renamed from: com.ss.effect.SoundEffect */
public class SoundEffect {
  public static int MAX_COMMON = 38;
  public static Music bgSound = null;
  public static Music bgSound2 = null;
  public static Music bgSound3 = null;
  public static Sound[] commons = null;
//  public static GSound[] commons = null;
  public static boolean music = false;
  public static boolean mute = false;

  private static Sound[] explode;
  public static int bg            = 1;
  public static int click         = 2;
  public static int clickTile     = 3;
  public static int match         = 4;
  public static int unmatch       = 5;
  public static int win           = 6;
  public static int getStar1      = 7;
  public static int getStar2      = 8;
  public static int getStar3      = 9;
  public static int lose          = 10;
  public static int drop          = 11;
  public static int hint          = 12;
  public static int shuffle       = 13;
  public static int tic           = 14;
  public static int nice          = 15;
  public static int great         = 16;
  public static int perfect       = 17;
  public static int unbelievable  = 18;
  public static int nextlevel     = 19;
  public static int magic         = 20;
  public static int bonusTime     = 21;
  public static int flyCoin       = 22;
  public static int collectGems   = 23;
  public static int panelClose    = 24;
  public static int panelOpen     = 25;
  public static int boom          = 26;
  public static int droptile      = 27;



  public static void initSound() {
    explode = new Sound[6];
    commons = new Sound[MAX_COMMON];
    commons[click] = GAssetsManager.getSound("click.mp3");
    commons[clickTile] = GAssetsManager.getSound("clickTile.mp3");
    commons[match] = GAssetsManager.getSound("match3.mp3");
    commons[win] = GAssetsManager.getSound("finishwindow.mp3");
    commons[lose] = GAssetsManager.getSound("lose.mp3");
    commons[hint] = GAssetsManager.getSound("hint.mp3");
    commons[shuffle] = GAssetsManager.getSound("shuffle.mp3");
    commons[nice] = GAssetsManager.getSound("nice.mp3");
    commons[great] = GAssetsManager.getSound("great.mp3");
    commons[perfect] = GAssetsManager.getSound("perfect.mp3");
    commons[unbelievable] = GAssetsManager.getSound("unbelievable.mp3");
    commons[nextlevel] = GAssetsManager.getSound("nextlevel.mp3");
    commons[magic] = GAssetsManager.getSound("magic.mp3");
    commons[bonusTime] = GAssetsManager.getSound("bonus.mp3");
    commons[flyCoin] = GAssetsManager.getSound("flyCoin.mp3");
    commons[collectGems] = GAssetsManager.getSound("collectGems.mp3");
    commons[panelOpen] = GAssetsManager.getSound("panel_open.mp3");
    commons[panelClose] = GAssetsManager.getSound("panel_close.mp3");
    commons[boom] = GAssetsManager.getSound("boom.mp3");
    commons[droptile] = GAssetsManager.getSound("droptile.mp3");
    commons[tic] = GAssetsManager.getSound("tic.mp3");
////        commons[coins] = GAssetsManager.getSound("Coin.mp3");
////        commons[coins].setVolume(2,5);
    bgSound = GAssetsManager.getMusic("bg1.mp3");

  }

  public static void explode(int level) {
    if(!mute)
      explode[(level > 5) ? 5 : level].play();
  }
  public static long Play(int i) {
    long id = -1;
    if (!mute) {
      id = commons[i].play();
//      commons[i].setVolume(id,0.5f);
    }
    return id;
  }
  public static void Stop(int i){
//    long id = -1;
    if (!mute) {
      commons[i].stop();
//      commons[i].setVolume(id,0.5f);
    }
//    return id;
  }



  public static void Playmusic() {
    bgSound.play();
    bgSound.setLooping(true);
  }

  public static void Stopmusic(){
    bgSound.stop();
  }
  public static void Pausemusic(){
    bgSound.pause();
  }
}
