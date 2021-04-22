package com.ss.commons;

import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.Group;
import com.ss.core.exSprite.particle.GParticleSprite;
import com.ss.core.exSprite.particle.GParticleSystem;
import com.ss.core.util.GLayer;
import com.ss.core.util.GStage;
import com.ss.gameLogic.config.Config;

public class LoadParticle {
  public static Group gr = new Group();

  public static void init(){
    GStage.addToLayer(GLayer.top,gr);
//    new GParticleSystem("timeFire","effect.atlas",1,1);
    new GParticleSystem("select","effect.atlas",10,20);
    new GParticleSystem("fireWork","effect.atlas",1,1);
    new GParticleSystem("wellcome","effect.atlas",0,0);
//    new GParticleSystem("match2","effect.atlas",10,20);

  }
}


