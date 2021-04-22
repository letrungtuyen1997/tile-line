package com.ss.effects;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.files.FileHandle;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.ParticleEffect;
import com.badlogic.gdx.graphics.g2d.ParticleEffectPool;
import com.badlogic.gdx.graphics.g2d.ParticleEmitter;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.Group;
import com.badlogic.gdx.utils.Array;
import com.ss.GMain;
import com.ss.commons.TextureAtlasC;


public class effectWin extends Actor {
  ////////// file handel/////
  FileHandle match    = Gdx.files.internal("particle/match");
  FileHandle match2   = Gdx.files.internal("particle/match2");
  FileHandle flyfire  = Gdx.files.internal("particle/fireFly");
  FileHandle bomb     = Gdx.files.internal("particle/bomb");
  FileHandle bombdead = Gdx.files.internal("particle/bombDead");
  FileHandle firework = Gdx.files.internal("particle/fireWork");
  FileHandle wellcome = Gdx.files.internal("particle/wellcome");
  //    FileHandle lightyelow = Gdx.files.internal("particle/lightYellow");
//    FileHandle tree = Gdx.files.internal("particle/tree");
//    FileHandle merge = Gdx.files.internal("particle/merge");
//    FileHandle test = Gdx.files.internal("particle2/cu-lu");
  //////// index handel ///////
  public static int Match = 1;
  public static int Match2 = 2;
  public static int Flyfire = 3;
  public static int Bomb = 4;
  public static int FireWork = 5;
  public static int Wellcome = 6;
  public static int BombDead = 7;
  public ParticleEffect effect;
  public ParticleEffectPool effectPool;
  public ParticleEffectPool.PooledEffect pooledEffect;
  private Actor parent = this.parent;
  private Group group;
  private Array<Sprite> arrSprite = new Array<>();
  public boolean isAlive = false;
  private float oldScl = 0;

  public effectWin(int id, int idAni, float f, float f2, float sclX, float sclY, Group group) {

    this.group = group;
    this.effect = new ParticleEffect();
    this.effectPool = new ParticleEffectPool(effect, 0, 100);
    this.pooledEffect = effectPool.obtain();
    setX(f);
    setY(f2);
    if (id == Match) {
      TextureRegion texture;
      texture = new TextureRegion(TextureAtlasC.FoodAtlas.findRegion("" + idAni));
//            texture.flip(false,true);
      TextureAtlasC.EffectAtlas.findRegion("UI_QiangHua02").setRegion(texture);
      this.effect.load(match, TextureAtlasC.EffectAtlas);

    } else if (id == Match2) {
      this.effect.load(match2, TextureAtlasC.EffectAtlas);

    } else if (id == Flyfire) {
      this.effect.load(flyfire, TextureAtlasC.EffectAtlas);
    } else if (id == Bomb) {
      this.effect.load(bomb, TextureAtlasC.EffectAtlas);
    }else if (id == FireWork) {
      this.effect.load(firework, TextureAtlasC.EffectAtlas);
    }else if (id == Wellcome) {
      this.effect.load(wellcome, TextureAtlasC.EffectAtlas);
    }else if (id == BombDead) {
      this.effect.load(bombdead, TextureAtlasC.EffectAtlas);
    }
    this.effect.setPosition(f, f2);
  }

  public void resetSprites() {
    // for (int i = 0; i < this.effect.getEmitters().size; i++) {
    this.effect.getEmitters().get(0).getSprites().clear();
    this.effect.getEmitters().get(0).getSprites().addAll(arrSprite);

    // }
  }

  public void changeSprites(int id) {
    resetSprites();
//        this.effect.getEmitters().get(0).getSprites().swap(0,(id-1));
//        System.out.println("check: "+this.effect.getEmitters().get(0).getSprites().size);
    if (this.effect.getEmitters().get(0).getSprites() != null)
      this.effect.getEmitters().get(0).getSprites().swap(0, (id - 1));

  }

  public void changeEffect(int id) {

  }

  public void SetPosition(float x, float y) {
    this.effect.setPosition(x, y);
  }

  @Override
  public boolean remove() {
    this.effect.reset();
    isAlive = false;
    if (pooledEffect != null)
      pooledEffect.free();
    return super.remove();
  }

  @Override
  public void act(float delta) {
    super.act(delta);
    this.effect.setPosition(getX(), getY());
    this.effect.update(delta);
    if (this.effect.isComplete()) {
      remove();
    }
  }


  @Override
  public void draw(Batch batch, float parentAlpha) {
    super.draw(batch, parentAlpha);
    if (!this.effect.isComplete()) {
      this.effect.draw(batch);
      return;
    }
    this.effect.dispose();
  }


  public void setScale(float ratio) {
//        this.effect.
    this.effect.scaleEffect(ratio);
  }

  public void setScale(float ratioX, float ratioY) {
    this.effect.scaleEffect(ratioX, ratioY);
  }

  public void setFlip(boolean x, boolean y) {
    this.effect.setFlip(x, y);
  }

  public void start() {
    isAlive = true;
    this.group.addActor(this);
    this.effect.start();
  }

}
