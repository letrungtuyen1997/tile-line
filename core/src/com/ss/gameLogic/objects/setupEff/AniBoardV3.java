package com.ss.gameLogic.objects.setupEff;

import com.badlogic.gdx.math.Interpolation;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.Group;
import com.badlogic.gdx.scenes.scene2d.actions.Actions;
import com.ss.utils.AnimationBoard;

public class AniBoardV3 implements AnimationBoard {


  @Override
  public void StartAni(float padX, int x, float padY, int y, int row, int col, Group group, Actor actor, Runnable runnable) {
    if((row+col)%2==0)
      actor.setPosition(-actor.getWidth(),group.getHeight()/2 - padY + y*actor.getHeight());
    else
      actor.setPosition(group.getWidth()+actor.getWidth(),group.getHeight()/2 - padY + y*actor.getHeight());

    actor.addAction(Actions.delay(0,Actions.sequence(
            Actions.moveTo(group.getWidth()/2- padX+x * actor.getWidth(),group.getHeight()/2 - padY + y*actor.getHeight(),0.5f, Interpolation.swingOut),
            Actions.run(runnable)
    )));
  }
}
