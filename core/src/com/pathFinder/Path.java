package com.pathFinder;

import com.badlogic.gdx.ai.pfa.DefaultGraphPath;
import com.badlogic.gdx.ai.pfa.SmoothableGraphPath;
import com.badlogic.gdx.math.Vector2;

//quỳ
public class Path<N extends AbstractNode<N>> extends DefaultGraphPath<N> implements SmoothableGraphPath<N, Vector2> {
  private Vector2 temp = Vector2.Zero.cpy();

  @Override
  public Vector2 getNodePosition(int i) {
    N n = nodes.get(i);
    return temp.set(n.x, n.y);
  }

  @Override
  public void swapNodes(int i1, int i2) {
    nodes.set(i1, nodes.get(i2));
  }

  @Override
  public void truncatePath(int newLength) {
    nodes.truncate(newLength);
  }
}