package com.pathFinder;
import com.badlogic.gdx.utils.Array;

public interface TriplePathFinder {
  Array<Path<Node>>     findPath3(int x1, int y1, int x2, int y2, int x3, int y3);
  Path<Node>            findPath2(int x1, int y1, int x2, int y2);
  Array<Node>           hint();
  //----------------------------------------//
  static TriplePathFinder make(int[][] arr) {
    return new TFinderV1(arr);
  }
}