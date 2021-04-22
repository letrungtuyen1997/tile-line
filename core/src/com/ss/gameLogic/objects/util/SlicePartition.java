//package com.ss.gameLogic.objects.util;
//
//import com.badlogic.gdx.utils.Array;
//import com.ss.utils.Level;
//
//import java.util.ArrayList;
////import com.ss.gameLogic.data.Level;
////import com.ss.gameLogic.model.AniModel;
//
//public class SlicePartition {
//  private int sliceDirection;
//  private int sliceAnchor;
//  private int sliceType;
//
//  private int start;
//  public int end;
//  private Tile[][] anis;
//  public static Array<SlicePartition> instances = new Array<>();
//  public int type;
//
//  public static void initPartitions(int type,int direction,int anchor, Tile[][] array) {
//    type = type;
//    instances = new Array<>();
////    Level lv = Level.getLevelData(level);
//    switch (type) {
//      case Level.SLIDE: {
//        SlicePartition p1 = new SlicePartition(array, type, direction, anchor, 0, array.length - 1);
//        instances.add(p1);
//      }
//      break;
//      case Level.PARTITION:
//        if (array.length < 4 || array[0].length < 4)
//          return;
//        if (direction ==Level.VERTICAL) { // vertical slice
//          int anchor1 = anchor;
//          int anchor2 = (anchor1 == 0) ? 1 : 0;
//          int e = (array.length % 2 == 0) ? array.length / 2 - 1 : array.length / 2;
//          int s = (array.length % 2 == 0) ? array.length / 2 : array.length / 2 + 1;
//          SlicePartition p1 = new SlicePartition(array, type, direction, anchor1, 0, e);
//          SlicePartition p2 = new SlicePartition(array, type, direction, anchor2, s, array.length - 1);
//          instances.add(p1);
//          instances.add(p2);
//        } else if (direction == Level.HORIZONTAL) { //horizon slice
//          int anchor1 = anchor;
//          int anchor2 = (anchor1 == 0) ? 1 : 0;
//          int e = (array[0].length % 2 == 0) ? array[0].length / 2 - 1 : array[0].length / 2;
//          int s = (array[0].length % 2 == 0) ? array[0].length / 2 : array[0].length / 2 + 1;
//          SlicePartition p1 = new SlicePartition(array, type, direction, anchor1, 0, e);
//          SlicePartition p2 = new SlicePartition(array, type, direction, anchor2, s, array[0].length - 1);
//          instances.add(p1);
//          instances.add(p2);
//        }
//        break;
//      default:
//        break;
//    }
//  }
//
//  private SlicePartition(Tile[][] anis, int type, int sliceDirection, int sliceAnchor, int start, int end) {
//    this.sliceDirection = sliceDirection;
//    this.sliceAnchor = sliceAnchor;
//    this.start = start;
//    this.end = end;
//    this.anis = anis;
//    this.sliceType = type;
//  }
//
//  private Tile[][] buildPartition() {
//    int d = end - start + 1;
//    if (sliceType == Level.SLIDE) {/// slide
//      if (d == anis.length || d == anis[0].length)
//        return anis;
//    }
//
//    if (sliceDirection == Level.VERTICAL) {// vertical
//      if (sliceType == Level.PARTITION && d == anis.length)
//        return anis;
//      Tile[][] partition = new Tile[d][anis[0].length];
//      for (int i = 0; i < d; i++) {
//        for (int j = 0; j < anis[0].length; j++) {
//          partition[i][j] = anis[start + i][j];
//        }
//      }
//      return partition;
//    } else {
//      if (sliceType == Level.PARTITION && d == anis[0].length)
//        return anis;
//      Tile[][] partition = new Tile[anis.length][d];
//      for (int i = 0; i < anis.length; i++) {
//        for (int j = 0; j < d; j++) {
//          partition[i][j] = anis[i][start + j];
//        }
//      }
//      return partition;
//    }
//  }
//
//  public ArrayList<Tuple<Tile, D>> calcSlices(Tile ani) {
//    Tile[][] partition = this.buildPartition();
//    ArrayList<Tuple<Tile, D>> slices = new ArrayList<>();
//
//    if (sliceDirection == Level.VERTICAL) {// vertical
//      ArrayUtils.nullSliceV(partition, slices, this.sliceAnchor, (int)ani.getRowCol().y);
//    } else if (sliceDirection == Level.HORIZONTAL) {/// horzential
//      ArrayUtils.nullSliceH(partition, slices, this.sliceAnchor, (int)ani.getRowCol().x);
//    }
//    return slices;
//  }
//}
