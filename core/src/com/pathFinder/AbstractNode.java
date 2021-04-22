package com.pathFinder;

import com.badlogic.gdx.ai.pfa.Connection;
import com.badlogic.gdx.utils.Array;


abstract class AbstractNode<T extends AbstractNode<T>> {
  public int x,y;
  int type,index;
  Array<Connection<T>> connections;

  public AbstractNode(int x, int y, int type, int index, Array<Connection<T>> connections) {
    this.x            = x;
    this.y            = y;
    this.type         = type;
    this.index        = index;
    this.connections  = connections;
  }
}