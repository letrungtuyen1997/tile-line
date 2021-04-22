package com.ss.core.action.exAction;

import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.scenes.scene2d.Action;
import com.badlogic.gdx.utils.Pool;

public class IPAction extends Action implements Pool.Poolable {
  private static Vector2 tempS = new Vector2();
  private static Vector2 tempV = new Vector2();
  private static Vector2 tempG = new Vector2();
  private static Vector2 stepP = new Vector2();

  private Vector2 gravity;
  private Vector2 startPos;
  private Vector2 vel;
  private float acc;
  private float timeScale;
  private float angularVel;

  private IPAction() {
    startPos = new Vector2();
    vel = new Vector2();
    acc = 0;
    gravity = new Vector2();
  }

  private void getProjectionPoint(Vector2 startPosition,
                                  Vector2 startVelocity,
                                  float step,
                                  Vector2 output) {
    float t = 1 / 60f;
    tempV.set(startVelocity).scl(t);
    tempG.set(gravity).scl(t * t);
    tempS.set(startPosition);
    output.set(tempS.add(tempV.scl(step))
        .add(tempG.scl(step * step + step).scl(0.5f)));
  }

  @Override
  public boolean act(float delta) {
    acc += (delta * 60 * timeScale);
    getProjectionPoint(startPos, vel, acc, stepP);
    actor.setPosition(stepP.x, stepP.y);
    actor.setRotation(actor.getRotation() + delta * angularVel);
    return false;
  }

  @Override
  public void reset() {
    vel.set(0, 0);
    startPos.set(0, 0);
    acc = 0;
    timeScale = 0;
    angularVel = 1;
    super.reset();
  }

  float getTimeScale() {
    return timeScale;
  }

  void setTimeScale(float timeScale) {
    this.timeScale = timeScale;
  }

  private static final Pool<IPAction> actionPool = new Pool<IPAction>() {
    @Override
    protected IPAction newObject() {
      return new IPAction();
    }
  };

  public static IPAction obtain(float startX, float startY, float velX, float velY,
                                float speed, float angularVel, float gravityY) {
    IPAction action = actionPool.obtain();
    action.timeScale = speed;
    action.angularVel = angularVel;

    action.startPos.set(startX, startY);
    action.vel.set(velX, velY);
    action.gravity.set(0, gravityY);

    return action;
  }

  public static void free(IPAction action) {
    if (action != null)
      actionPool.free(action);
  }
}
