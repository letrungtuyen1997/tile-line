package com.ss.commons;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.GlyphLayout;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.math.Interpolation;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.scenes.scene2d.Action;
import com.badlogic.gdx.scenes.scene2d.Group;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Touchable;
import com.badlogic.gdx.scenes.scene2d.actions.Actions;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.utils.Align;
import com.ss.core.action.exAction.GSimpleAction;
import com.ss.core.exSprite.GShapeSprite;
import com.ss.core.util.GUI;

public class ButtonC {
  private TextureAtlas atlas;
  private Group group, parentGroup;
  private String nameImage;
  private Image image;
  private String text;
  private BitmapFont bitmapFont;
  private LabelC label;
  private GShapeSprite gshape;
  private Runnable runnableTU, runnableTD;

  public ButtonC(TextureAtlas atlas, Group parentGroup, String nameImage){
    this.atlas = atlas;
    this.parentGroup = parentGroup;
    this.nameImage = nameImage;
    text = "";

    initGroup();
    initUI();
  }

  private void initGroup(){
    group = new Group();
    parentGroup.addActor(group);
  }

  private void initUI(){
    image = GUI.createImage(atlas, nameImage);
    group.addActor(image);
    group.setSize(image.getWidth(), image.getHeight());
  }

  private void converEventTouchDown(){
    if(runnableTD != null){
      gshape.addListener(new ClickListener(){
        @Override
        public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
          group.addAction(Actions.run(runnableTD));
          return super.touchDown(event, x, y, pointer, button);
        }
      });
    }
    else{
      System.out.println("Runnable touchDown button is null!!");
    }
  }

  private void convertEventTouchUp(){
    if(runnableTU != null){
      gshape.addListener(new ClickListener(){
        @Override
        public void touchUp(InputEvent event, float x, float y, int pointer, int button) {
          super.touchUp(event, x, y, pointer, button);
          group.addAction(Actions.run(runnableTU));
        }
      });
    }
    else{
      System.out.println("Runnable touchUp button is null!!");
    }
  }

  public void addBitmapFont(BitmapFont font){
    bitmapFont = font;
  }

  public void addText(String text,float scl,float paddingX,float paddingY,int align){
    this.text = text;
    if(label != null){
      System.out.println("label null");
      GlyphLayout glText = new GlyphLayout(bitmapFont,label.getText());
      label.setText(this.text);
      label.setSize(glText.width*label.getFontScaleX(),glText.height*label.getFontScaleY());
      label.setAlignment(align);
      label.setPosition(group.getWidth()/2+paddingX,group.getHeight()/2+paddingY,align);
    }
    else{
      if(bitmapFont != null){
        label = new LabelC(this.text, new Label.LabelStyle(bitmapFont, null));
        label.setFontScale(scl);
        GlyphLayout glText = new GlyphLayout(bitmapFont,label.getText());
        label.setSize(glText.width*label.getFontScaleX(),glText.height*label.getFontScaleY());
        label.setAlignment(align);
        label.setPosition(group.getWidth()/2+paddingX,group.getHeight()/2+paddingY,align);
        group.addActor(label);
        converEventTouchDown();
        convertEventTouchUp();
      }
      else {
        System.out.println("bitmapFont of Button is null!!");
      }
    }
  }

  public void setPosition(float x, float y,int Align){
    group.setPosition(x, y,Align);
  }
  public void setScale(float sclX, float sclY){
    group.setSize(group.getWidth()*sclX,group.getHeight()*sclY);
//    group.setScale(sclX,sclY);
    group.setOrigin(Align.center);
  }


  public void addTouchDown(Runnable runnable){
    runnableTD = runnable;
    if(group != null){
      group.addListener(new ClickListener(){
        @Override
        public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
          group.addAction(Actions.run(runnable));
          return super.touchDown(event, x, y, pointer, button);
        }
      });
    }
    else {
      image.addListener(new ClickListener(){
        @Override
        public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
          group.addAction(Actions.run(runnable));
          return super.touchDown(event, x, y, pointer, button);
        }
      });
    }
  }

  public void addTouchUp(Runnable runnable){
    runnableTU = runnable;
    if(group != null){
      group.addListener(new ClickListener(){
        @Override
        public void touchUp(InputEvent event, float x, float y, int pointer, int button) {
          super.touchUp(event, x, y, pointer, button);
          group.addAction(Actions.run(runnable));
        }
      });
    }
    else {
      image.addListener(new ClickListener(){
        @Override
        public void touchUp(InputEvent event, float x, float y, int pointer, int button) {
          super.touchUp(event, x, y, pointer, button);
          group.addAction(Actions.run(runnable));
        }
      });
    }
  }
  public void addTouchAction(float sclX,float sclY,float sclX1,float sclY1,float dur,Runnable runnable){
    if(group !=null){
      group.setOrigin(Align.center);
      group.addListener(new ClickListener(){
        @Override
        public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
          group.setTouchable(Touchable.disabled);
          group.addAction(Actions.sequence(
                  Actions.scaleTo(sclX,sclY,dur),
                  Actions.scaleTo(sclX1,sclY1,dur),
                  GSimpleAction.simpleAction((d,a)->{
                    group.setTouchable(Touchable.enabled);
                    group.addAction(Actions.run(runnable));
                    return true;
                  })
          ));
          return super.touchDown(event, x, y, pointer, button);
        }
      });
    }else {
      image.setOrigin(Align.center);
      image.addListener(new ClickListener(){
        @Override
        public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
          image.setTouchable(Touchable.disabled);
          image.addAction(Actions.sequence(
                  Actions.scaleTo(sclX,sclY,dur),
                  Actions.scaleTo(sclX1,sclY1,dur),
                  GSimpleAction.simpleAction((d,a)->{
                    image.setTouchable(Touchable.enabled);
                    image.addAction(Actions.run(runnable));
                    return true;
                  })
          ));
          return super.touchDown(event, x, y, pointer, button);
        }
      });
    }
  }

  public void scaleZzAction(boolean isToSmall, float rx, float ry, float dur1, float dur2, Interpolation in1, Interpolation in2, Runnable runnable){
    int ope = isToSmall ? -1 : 1;
    group.setOrigin(Align.center);
    group.addAction(Actions.sequence(
      Actions.scaleBy(ope*rx, ope*ry, dur1, in1),
      Actions.scaleBy(-ope*rx, -ope*ry, dur2, in2),
      Actions.run(runnable)
    ));
  }

  public void setColor(Color color){
    image.setColor(color);
  }

  public void setColor(float r, float g, float b, float a){
    image.setColor(r, g, b, a);
  }
  public Vector2 getSize(){
    return new Vector2(group.getWidth(),group.getHeight());
  }
  public Vector2 getXY(){
    return new Vector2(group.getX(),group.getY());
  }
  public void addActionJump(){
    group.setOrigin(Align.center);
    group.addAction(
      Actions.sequence(
        Actions.delay(1),
        Actions.scaleTo(1.2f,1.2f,0.5f),
        Actions.scaleTo(1f,1f,0.5f),
              GSimpleAction.simpleAction((d,a)->{
                addActionJump();
                return true;
              })
      ));

  }
  public void alpha(float dur){
    group.getColor().a=0;
    group.addAction(Actions.delay(dur,Actions.alpha(1)));
  }
  public void visible(boolean set){
    group.setVisible(set);
  }
  public void touchable(Touchable set){
    group.setTouchable(set);
  }
  public void dispose(){
    group.clear();
    group.remove();
  }
}
