package com.ss.gameLogic.objects.setupEff;

import com.badlogic.gdx.math.Interpolation;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.Group;
import com.badlogic.gdx.scenes.scene2d.actions.Actions;
import com.ss.utils.AnimationBoard;

public class AniBoardV2 implements AnimationBoard {


  @Override
  public void StartAni(float padX, int x, float padY, int y, int row, int col, Group group, Actor actor, Runnable runnable) {
    actor.setPosition(group.getWidth()/2- padX+x * actor.getWidth(),-group.getHeight()/2);
    actor.addAction(Actions.delay(0,Actions.sequence(
            Actions.moveTo(group.getWidth()/2- padX+x * actor.getWidth(),group.getHeight()/2 - padY + y*actor.getHeight(),0.5f, Interpolation.swingOut),
            Actions.run(runnable)
    )));
  }
}
