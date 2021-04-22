package com.ss.utils;

import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.Group;

public interface AnimationBoard {
   void StartAni(float padX, int x, float padY, int y, int row, int col, Group group, Actor actor, Runnable runnable);
}
