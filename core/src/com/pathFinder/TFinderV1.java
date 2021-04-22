package com.pathFinder;

import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.IntMap;

import java.util.Comparator;

class TFinderV1 extends PathFindableGrid implements TriplePathFinder{
  private Vector2 v1,v2,v3;
  IntMap<Array<Node>> cache;

  public TFinderV1(int[][] map) {
    super(map);
    v1 = Vector2.Zero.cpy();
    v2 = Vector2.Zero.cpy();
    v3 = Vector2.Zero.cpy();
    cache = new IntMap<>();
  }

  private void addCache(Node n) {
    if (n.type <= 0)
      return;
    if (cache.get(n.type) == null)
      cache.put(n.type, new Array<>());
    cache.get(n.type).add(n);
  }

  private Array<Path<Node>> findPath(Vector2 n1, Vector2 n2, Vector2 n3) {
    Path<Node> p1 = findPath(n1,n2);
    Path<Node> p2 = findPath(n2,n3);
    Path<Node> p3 = findPath(n3,n1);

    Array<Path<Node>> res = new Array<>();
    if (p1.nodes.size == 0)
      return res;
    if (p2.nodes.size == 0)
      return res;
    if (p3.nodes.size == 0)
      return res;
    res.add(p1,p2,p3);
    res.sort((a, b) -> {
     return a.nodes.size - b.nodes.size;
    });
    res.truncate(2);
    return res;
  }

  @Override
  public Array<Path<Node>> findPath3(int x1, int y1, int x2, int y2, int x3, int y3) {
    buildGraph();
    int t1 = grid[y1][x1];
    int t2 = grid[y2][x2];
    int t3 = grid[y3][x3];
    grid[y1][x1] = 0;
    grid[y2][x2] = 0;
    grid[y3][x3] = 0;
    buildGraph();
    Array<Path<Node>> res = findPath(v1.set(x1,y1), v2.set(x2,y2),v3.set(x3,y3));
    grid[y1][x1] = t1;
    grid[y2][x2] = t2;
    grid[y3][x3] = t3;
    return res;
  }

  @Override
  public Path<Node> findPath2(int x1, int y1, int x2, int y2) {
    buildGraph();
    int t1 = grid[x1][y1];
    int t2 = grid[x2][y2];
    grid[x1][y1] = 0;
    grid[x2][y2] = 0;
    buildGraph();
    Path<Node> res =  findPath(v1.set(x1,y1), v2.set(x2,y2));
    grid[x1][y1] = t1;
    grid[x2][y2] = t2;
    return res;
  }

  @Override
  public Array<Node> hint() {
    buildGraph();
    cache.clear();
    for (Node node : nodes)
      addCache(node);

    Array<Node> res = new Array<>();
    for (Array<Node> sames : cache.values())
      if (sames.size == 3) {
        int x1,x2,x3,y1,y2,y3;
        x1 = sames.get(0).x;
        x2 = sames.get(1).x;
        x3 = sames.get(2).x;
        y1 = sames.get(0).y;
        y2 = sames.get(1).y;
        y3 = sames.get(2).y;
        Array<Path<Node>> path = findPath3(x1, y1, x2, y2, x3, y3);
        if (path.size == 2) {
          return sames;
        }
      }
    return res;
  }
}