package com.pathFinder;

import com.badlogic.gdx.ai.pfa.Connection;
import com.badlogic.gdx.ai.pfa.DefaultConnection;
import com.badlogic.gdx.ai.pfa.indexed.IndexedAStarPathFinder;
import com.badlogic.gdx.ai.pfa.indexed.IndexedGraph;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.Array;

//no null check, no bound check, use crashlytic and god bless america
class PathFindableGrid implements IndexedGraph<Node> {
  int                   width, height;
  Array<Node>           nodes;
  int[][]               grid;

  public PathFindableGrid(int[][] map) {
    width     =   map[0].length;
    height    =   map.length;
    nodes     =   new Array<>(width * height);
    this.grid =   map;
    buildGraph();
  }

  void buildGraph() {
    nodes.clear();
    int idx = 0;
    for (int y = 0; y < height; y++)
      for (int x = 0; x < width; x++)
        nodes.add(new Node(x, y, grid[y][x], idx++, 4));

    for (int y = 0; y < height; y++) {
      idx = y*width;
      for (int x = 0; x < width; x++) {
        Node n = nodes.get(idx + x);
        if (x > 0) addConnection(n, -1, 0);
        if (y > 0) addConnection(n, 0, -1);
        if (x < width - 1) addConnection(n, 1, 0);
        if (y < height - 1) addConnection(n, 0, 1);
      }
    }
  }

  //crashlytics :)
  private Node getNode(int x, int y) {
    return nodes.get(y * width + x);
  }

  private Node getNode(Vector2 xy) {
    return getNode((int)xy.x, (int)xy.y);
  }

  private void addConnection(Node node, int xOffset, int yOffset) {
    Node target = getNode(node.x + xOffset, node.y + yOffset);
    if (target.type == 0)
      node.connections.add(new DefaultConnection<>(node, target));
  }

  Path<Node> findPath(Vector2 from, Vector2 to) {
    Path<Node> path = new Path<>();
    IndexedAStarPathFinder<Node> phb = new IndexedAStarPathFinder<>(this, true);
    phb.searchNodePath(getNode(from), getNode(to), new EpsilonHeuristic<>(), path);
    return path;
  }

  @Override
  public int getIndex(Node node) {
    return node.index;
  }

  @Override
  public int getNodeCount() {
    return nodes.size;
  }

  @Override
  public Array<Connection<Node>> getConnections(Node node) {
    return node.connections;
  }
}