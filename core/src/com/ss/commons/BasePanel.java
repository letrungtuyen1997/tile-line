package com.ss.commons;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.NinePatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Interpolation;
import com.badlogic.gdx.scenes.scene2d.Group;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.actions.Actions;
import com.badlogic.gdx.scenes.scene2d.actions.TemporalAction;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.utils.Align;
import com.ss.GMain;
import com.ss.core.exSprite.GImageGrayscalable;
import com.ss.core.exSprite.GShapeSprite;
import com.ss.core.util.GLayer;
import com.ss.core.util.GStage;
import com.ss.core.util.GUI;
import com.ss.effects.SoundEffect;
import com.ss.gameLogic.config.Config;


public class BasePanel extends Group {
  GShapeSprite topOverLay;
  protected GImageGrayscalable bg_panel;
  GImageGrayscalable title_bar;
  protected Label lb_title;
  public boolean isShow;

  public BasePanel() {
    topOverLay = GUI.createFullOverlay(1f);

    TextureRegion bg = new TextureRegion(TextureAtlasC.uiAtlas.findRegion("border"));
    bg.flip(false, true);

    bg_panel = new GImageGrayscalable(new NinePatch(bg, 44, 44, 215, 58));
    bg_panel.setScaleY(-1);

    title_bar = new GImageGrayscalable(TextureAtlasC.uiAtlas.findRegion("title_bar_panel"));
    lb_title = new Label("title", new Label.LabelStyle(BitmapFontC.pt_60, Color.WHITE));
    lb_title.setSize(title_bar.getWidth(), title_bar.getHeight());
    lb_title.setAlignment(Align.center);
    this.addActorAt(2, bg_panel);
    this.addActor(title_bar);
    this.addActor(lb_title);
  }

  public void show() {
//    Config.CountShowFullScreen++;
//    System.out.println("count: "+Config.CountShowFullScreen);
//    if(Config.CountShowFullScreen==Config.MaxShowFullScreen){
//      System.out.println("showfullscreen");
//      Config.CountShowFullScreen=0;
//      GMain.platform.ShowFullscreen();
//    }
    SoundEffect.Play(SoundEffect.panelOpen);
    GStage.addToLayer(GLayer.ui, topOverLay);
    topOverLay.clearListeners();
    topOverLay.clearActions();
    isShow = true;

    bg_panel.setOrigin(Align.center);
    bg_panel.setPosition(0, 0, Align.center);

    title_bar.setPosition(bg_panel.getX(Align.center), -bg_panel.getHeight() / 2 + 20, Align.center);
    lb_title.setPosition(title_bar.getX(Align.center), title_bar.getY(Align.center), Align.center);

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
    addAction(Actions.scaleTo(1, 1, 0.3f, Interpolation.swingOut));
  }


  public void showNoTouch() {
    show();
    topOverLay.clearListeners();
  }

  public void hide() {
    SoundEffect.Stop(SoundEffect.panelClose);
    SoundEffect.Play(SoundEffect.panelClose);
    hide(() -> {
    });
  }

  public void hide(Runnable action) {
    topOverLay.addAction(Actions.sequence(
        Actions.fadeOut(0.3f)
    ));
    setScale(1);
    addAction(Actions.sequence(
        Actions.scaleTo(0, 0, 0.3f, Interpolation.swingIn),
        Actions.run(() -> {
          action.run();
          isShow = false;
          topOverLay.remove();
          this.remove();
        })));
  }
}
