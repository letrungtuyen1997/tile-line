package com.ss.commons;

import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.ss.core.util.GAssetsManager;

public class TextureAtlasC {
  public static TextureAtlas uiAtlas;
//  public static TextureAtlas AnimalsAtlas;
  public static TextureAtlas FoodAtlas;
  public static TextureAtlas EffectAtlas;
  public static TextureAtlas LeaderAtlas;
  public static TextureAtlas BgAtlas;
  public static TextureAtlas SpineAtlas;

  public static void LoadAtlas(){

    GAssetsManager.loadTextureAtlas("ui.atlas");
//    GAssetsManager.loadTextureAtlas("animals.atlas");
    GAssetsManager.loadTextureAtlas("effect.atlas");
    GAssetsManager.loadTextureAtlas("bg.atlas");
    GAssetsManager.loadTextureAtlas("food.atlas");
    GAssetsManager.loadTextureAtlas("win.atlas");
  }

  public static void InitAtlas(){

    uiAtlas       = GAssetsManager.getTextureAtlas("ui.atlas");
//    AnimalsAtlas  = GAssetsManager.getTextureAtlas("animals.atlas");
    EffectAtlas   = GAssetsManager.getTextureAtlas("effect.atlas");
    BgAtlas       = GAssetsManager.getTextureAtlas("bg.atlas");
    FoodAtlas     = GAssetsManager.getTextureAtlas("food.atlas");
    SpineAtlas     = GAssetsManager.getTextureAtlas("win.atlas");
  }
}
