package com.ss.gameLogic.objects.setupEff;

import com.ss.effects.SoundEffect;
import com.ss.scenes.LoadingScene;
import com.ss.utils.EffectMatch;

public class EffectV1 implements EffectMatch {
  private int count =0;
  @Override
  public void startEff(int id, float x, float y, float scl) {
    count++;
    if (count ==3)
    {
      SoundEffect.Play(SoundEffect.match);
      count =0;
    }
//    LoadingScene.effect.StartEff2(x,y,scl);

  }
}
