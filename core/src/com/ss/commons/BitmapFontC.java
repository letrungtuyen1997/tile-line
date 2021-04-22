package com.ss.commons;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.files.FileHandle;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.utils.I18NBundle;
import com.ss.GMain;
import com.ss.core.util.GAssetsManager;

import java.util.Locale;

public class BitmapFontC {
  public static BitmapFont pt_60;
  public static BitmapFont pt_45;
  public static BitmapFont pt_30;
  public static BitmapFont pt_35;
  public static BitmapFont hel_30;

  public static void LoadBitmapFont(){
  GAssetsManager.loadBitmapFont("pt_60.fnt");
  GAssetsManager.loadBitmapFont("pt_45.fnt");
  GAssetsManager.loadBitmapFont("pt_30.fnt");
  GAssetsManager.loadBitmapFont("pt_35.fnt");
  GAssetsManager.loadBitmapFont("hel_30.fnt");
  ////// korea //////
  GAssetsManager.loadBitmapFont("hel_30_kr.fnt");
  GAssetsManager.loadBitmapFont("pt_45_kr.fnt");
  GAssetsManager.loadBitmapFont("pt_60_kr.fnt");
  ////// japan //////
  GAssetsManager.loadBitmapFont("hel_30_jp.fnt");
  GAssetsManager.loadBitmapFont("pt_45_jp.fnt");
  GAssetsManager.loadBitmapFont("pt_60_jp.fnt");
  ////// potugal //////
  GAssetsManager.loadBitmapFont("hel_30_pt.fnt");
  GAssetsManager.loadBitmapFont("pt_45_pt.fnt");
  GAssetsManager.loadBitmapFont("pt_60_pt.fnt");

  }
  public static void InitBitmapFont(){
    pt_30           = GAssetsManager.getBitmapFont("pt_30.fnt");
    pt_35           = GAssetsManager.getBitmapFont("pt_35.fnt");
    hel_30          = GAssetsManager.getBitmapFont("hel_30.fnt");
    pt_60           = GAssetsManager.getBitmapFont("pt_60.fnt");
    pt_45           = GAssetsManager.getBitmapFont("pt_45.fnt");


  }
  public static void changeFont(int id){
    FileHandle specFilehandle = Gdx.files.internal("i18n/lang");;
    if(id==0){
        hel_30          = GAssetsManager.getBitmapFont("hel_30.fnt");
        pt_60          = GAssetsManager.getBitmapFont("pt_60.fnt");
        pt_45          = GAssetsManager.getBitmapFont("pt_45.fnt");
        specFilehandle = Gdx.files.internal("i18n/lang");
        GMain.locale = I18NBundle.createBundle(specFilehandle, new Locale(""));
        return;
      }
      if(id==1) {
        hel_30 = GAssetsManager.getBitmapFont("hel_30.fnt");
        pt_60 = GAssetsManager.getBitmapFont("pt_60.fnt");
        pt_45 = GAssetsManager.getBitmapFont("pt_45.fnt");
        specFilehandle = Gdx.files.internal("i18n/lang_en");
        GMain.locale = I18NBundle.createBundle(specFilehandle, new Locale(""));
        return;
      }
      if(id==2) {

        hel_30 = GAssetsManager.getBitmapFont("hel_30.fnt");
        pt_60 = GAssetsManager.getBitmapFont("pt_60.fnt");
        pt_45 = GAssetsManager.getBitmapFont("pt_45.fnt");
        specFilehandle = Gdx.files.internal("i18n/lang_id");
        GMain.locale = I18NBundle.createBundle(specFilehandle, new Locale(""));
        return;
      }
      if(id==3) {
        hel_30 = GAssetsManager.getBitmapFont("hel_30_jp.fnt");
        pt_60 = GAssetsManager.getBitmapFont("pt_60_jp.fnt");
        pt_45 = GAssetsManager.getBitmapFont("pt_45_jp.fnt");
        specFilehandle = Gdx.files.internal("i18n/lang_jp");
        GMain.locale = I18NBundle.createBundle(specFilehandle, new Locale(""));
        return;
      }
      if(id==4) {
        hel_30          = GAssetsManager.getBitmapFont("hel_30_pt.fnt");
        pt_60          = GAssetsManager.getBitmapFont("pt_60_pt.fnt");
        pt_45          = GAssetsManager.getBitmapFont("pt_45_pt.fnt");
        specFilehandle = Gdx.files.internal("i18n/lang_pt");
        GMain.locale = I18NBundle.createBundle(specFilehandle, new Locale(""));
        return;
      }
      if(id==5) {
        hel_30          = GAssetsManager.getBitmapFont("hel_30_kr.fnt");
        pt_60          = GAssetsManager.getBitmapFont("pt_60_kr.fnt");
        pt_45          = GAssetsManager.getBitmapFont("pt_45_kr.fnt");
        specFilehandle = Gdx.files.internal("i18n/lang_kr");
        GMain.locale = I18NBundle.createBundle(specFilehandle, new Locale(""));
        return;
      }

  }
  public static int setLangDefault(String id) {
    System.out.println("lang: "+id);
    int i = 0;
    if (id.equals("vi"))
      i = 0;
    if (id.equals("en"))
      i = 1;
    if (id.equals("id"))
      i = 2;
    if (id.equals("jp"))
      i = 3;
    if (id.equals("pt"))
      i = 4;
    if (id.equals("kr"))
      i = 5;
    return i;
  }
}
