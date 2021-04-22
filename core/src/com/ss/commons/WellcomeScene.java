package com.ss.commons;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Pixmap;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.GlyphLayout;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.graphics.glutils.FrameBuffer;
import com.badlogic.gdx.math.Interpolation;
import com.badlogic.gdx.math.Matrix4;
import com.badlogic.gdx.scenes.scene2d.Group;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.actions.Actions;
import com.badlogic.gdx.scenes.scene2d.actions.TemporalAction;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.ss.core.exSprite.GShapeSprite;
import com.ss.core.exSprite.particle.GParticleSprite;
import com.ss.core.exSprite.particle.GParticleSystem;
import com.ss.core.util.GLayer;
import com.ss.core.util.GStage;
import com.ss.core.util.GUI;
import com.ss.effects.SoundEffect;
import com.ss.effects.effectWin;

public class WellcomeScene extends Group {
  GShapeSprite topOverLay;
  GParticleSprite ef;
  public boolean isShow;
  public Texture lbLv;

  public WellcomeScene() {
    topOverLay = GUI.createFullOverlay(1f);

  }
  public void show(String text, Runnable runnable) {
    GStage.addToLayer(GLayer.ui, topOverLay);
    topOverLay.clearListeners();
    topOverLay.clearActions();
    isShow = true;

    topOverLay.getColor().a = 0;
    topOverLay.addAction(new TemporalAction(0.3f) {
      @Override
      protected void update(float percent) {
        Color c = topOverLay.getColor();
        c.a = 0.8f * percent;
      }
    });

    topOverLay.addListener(new ClickListener() {
      @Override
      public void clicked(InputEvent event, float x, float y) {
        Gdx.app.postRunnable(() -> hide());
//        GSound.playSound("click");
        super.clicked(event, x, y);
      }
    });

    GStage.addToLayer(GLayer.ui, this);
    clearActions();
    setScale(0);
    setPosition((GStage.getWorldWidth() - getWidth()) / 2,
            ((GStage.getWorldHeight() - getHeight()) / 2));
    addAction(Actions.sequence(
            Actions.scaleTo(1, 1, 0.3f, Interpolation.swingOut),
            Actions.run(() -> {
              init(text, () -> {
                hide();
                addAction(Actions.run(runnable));
              });
            })
    ));
  }


  public void showNoTouch(String text, Runnable runnable) {
    show(text, runnable);
    topOverLay.clearListeners();
  }

  public void hide() {
    hide(() -> {
//      dispose();
    });
  }
  public void hide(Runnable action) {

    topOverLay.addAction(Actions.sequence(
            Actions.fadeOut(0.3f)
    ));
    setScale(1);
    addAction(Actions.sequence(
            Actions.alpha(0, 0.3f, Interpolation.linear),
            Actions.run(() -> {
              action.run();
              ef.setVisible(false);
              ef.reset();
              ef.dispose();
              ef.free();
              lbLv.dispose();
              isShow = false;
              topOverLay.remove();
              this.remove();

            })));
  }

  private void init(String text, Runnable runnable) {
    SoundEffect.Play(SoundEffect.nextlevel);

    ef = GParticleSystem.getGParticleSystem("wellcome").create(this, 0, 0);
    ef.getEffect().findEmitter("lb").getSprites().clear();
    lbLv = getTextImg(text);
    ef.getEffect().findEmitter("lb").getSprites().add(new Sprite(lbLv));
    ef.setVisible(true);
    ef.dispose();
    ef.setScale(1f, -1f);

    addAction(Actions.delay(2,
            Actions.run(()->{
              this.addAction(Actions.run(runnable));
            })
    ));
  }

  private Texture getTextImg(String text) {
    Matrix4 projector = new Matrix4();
    Batch batch = new SpriteBatch();
    String level = text;
    BitmapFont font = BitmapFontC.pt_45;
    GlyphLayout gly = new GlyphLayout(font, level);

    projector.setToOrtho2D(0, -10, gly.width, gly.height+20);
    batch.setProjectionMatrix(projector);
    FrameBuffer currFbo = new FrameBuffer(Pixmap.Format.RGBA8888, (int) gly.width, (int) gly.height+20, false);
    currFbo.begin();
    batch.begin();

    font.draw(batch, level, 0, 0);
    batch.end();
    currFbo.end();

    return currFbo.getColorBufferTexture();
  }
  public void dispose(){
    topOverLay.clear();
    topOverLay.remove();
    this.clear();
    this.remove();
  }

}
