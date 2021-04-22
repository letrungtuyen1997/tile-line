package com.pathFinder;

import com.badlogic.gdx.ai.pfa.Heuristic;

class EpsilonHeuristic<N extends AbstractNode<N>> implements Heuristic<N> {
  @Override
  public float estimate(N start, N end) {
    return Math.abs(start.x - end.x) + Math.abs(start.y - end.y) + 0.0f;
  }
}