package com.ss.gameLogic.objects.setupEff;

import com.badlogic.gdx.math.Interpolation;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.Group;
import com.badlogic.gdx.scenes.scene2d.actions.Actions;
import com.ss.core.util.GStage;
import com.ss.utils.AnimationBoard;

public class AniBoardV5 implements AnimationBoard {


  @Override
  public void StartAni(float padX, int x, float padY, int y, int row, int col, Group group, Actor actor, Runnable runnable) {
    float passX1 = 0, passX2 = 0;
    if ((row + col) % 2 == 0) {
      actor.setPosition(-actor.getWidth(), -group.getHeight() / 2);
    } else {
      actor.setPosition(GStage.getWorldWidth() + actor.getWidth(), group.getHeight() / 2);
    }

    actor.addAction(Actions.delay(0,Actions.sequence(
            Actions.parallel(
            Actions.moveTo(group.getWidth() / 2 - padX + x * actor.getWidth(), group.getHeight() / 2 - padY + y * actor.getHeight(), 0.5f, Interpolation.pow2),
            Actions.rotateTo(360, 0.5f)
            ),
            Actions.run(runnable)
            )));
  }
}