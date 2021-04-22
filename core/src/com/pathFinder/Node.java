package com.pathFinder;

import com.badlogic.gdx.utils.Array;

public class Node extends AbstractNode<Node> {
  public Node(int x, int y, int type, int index, int connectionCapacity) {
    super(x, y, type, index, new Array<>(connectionCapacity));
  }
}