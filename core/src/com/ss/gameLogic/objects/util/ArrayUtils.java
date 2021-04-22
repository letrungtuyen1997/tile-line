//package com.ss.gameLogic.objects.util;
//
//import com.badlogic.gdx.utils.Array;
////import com.ss.gameLogic.model.AniModel;
//
//import java.util.ArrayList;
//
//public class ArrayUtils {
//
//  public static <X> Array<X> findItems(X[][] input, int id) {
//    Array<X> output = new Array<>();
//    for (int i = 1; i < input.length-1; i++)
//      for (int j = 1; j < input[1].length-1; j++)
//        if (input[i][j] != null && ((Tile) input[i][j]).getId() == id)
//          output.add(input[i][j]);
//
//    return output;
//  }
//
//  public static <T> void nullSliceV(T[][] input, ArrayList<Tuple<T, D>> output, int anchor, int col) {
////    int row = input.length;
////    int col = input[0].length;
//    int sp = (anchor == 1) ? input.length - 1 : 0;
//    int ep = (anchor == 1) ? 0 : input.length - 1;
//    int d = (sp < ep) ? 1 : -1;
//
//    for (int i = sp; i != ep + d; i += d) {
//      if (input[i][col] !=null) {
//        int dr = 0;
//        for (int j = i + d; j != ep + d; j += d)
//          if (input[j][col]!=null && ((Tile)input[j][col]).isVisible()==false)
//            dr += d;
//        if (dr != 0)
//          output.add(new Tuple<>(input[i][col], new D(dr, 0)));
//      }
//    }
//
//  }
//
//  public static <T> void nullSliceH(T[][] input, ArrayList<Tuple<T, D>> output, int anchor, int row) {
//    int sp = (anchor == 0) ? input[0].length - 1 : 0;
//    int ep = (anchor == 0) ? 0 : input[0].length - 1;
//    int d = (sp < ep) ? 1 : -1;
//
//    for (int i = sp; i != ep + d; i += d) {
//      if (input[row][i] != null) {
//        int dc = 0;
//        for (int j = i + d; j != ep + d; j += d)
//          if (((Tile)input[row][j]).isVisible()==false)
//            dc += d;
//        if (dc != 0)
//          output.add(new Tuple<>(input[row][i], new D(0, dc)));
//      }
//    }
//  }
//
//  public static <T> void shiftH(T[][] input, int anchor, boolean mix) {
//    int row = input.length;
//    int col = input[0].length;
//    int sp = (anchor == 0) ? col - 2 : 1;
//    int ep = (anchor == 0) ? 1 : col - 2;
//    int d = (sp < ep) ? 1 : -1;
//
//    for (int i = 0; i < row; i++) {
//      if (mix) {
//        if (i % 2 == 0) {
//          sp = (anchor == 1) ? col - 2 : 1;
//          ep = (anchor == 1) ? 1 : col - 2;
//        } else {
//          sp = (anchor == 0) ? col - 2 : 1;
//          ep = (anchor == 0) ? 1 : col - 2;
//        }
//        d = (sp < ep) ? 1 : -1;
//      }
//
//      T lastAni = input[i][sp];
//      for (int j = sp; j != ep; j += d) {
//        T ani = input[i][j + d];
//        input[i][j] = null;
//        if (ani != null) {
//          ((Tile) ani).setRowCol(i, j);
//        }
//        input[i][j] = ani;
//      }
//      input[i][ep] = null;
//      if (lastAni != null) {
//        ((Tile) lastAni).setRowCol(i, ep);
//      }
//      input[i][ep] = lastAni;
//    }
//  }
//
//  public static <T> void shiftV(T[][] input, int anchor, boolean mix) {
//    int row = input.length;
//    int col = input[0].length;
//    int sp = (anchor == 0) ? row - 2 : 1;
//    int ep = (anchor == 0) ? 1 : row - 2;
//    int d = (sp < ep) ? 1 : -1;
//
//    for (int i = 0; i < col; i++) {
//      if (mix) {
//        if (i % 2 == 0) {
//          sp = (anchor == 1) ? row - 2 : 1;
//          ep = (anchor == 1) ? 1 : row - 2;
//        } else {
//          sp = (anchor == 0) ? row - 2 : 1;
//          ep = (anchor == 0) ? 1 : row - 2;
//        }
//        d = (sp < ep) ? 1 : -1;
//      }
//
//      T lastAni = input[sp][i];
//      for (int j = sp; j != ep; j += d) {
//        T ani = input[j + d][i];
//        input[j][i] = null;
//        if (ani != null) {
//          ((Tile) ani).setRowCol(j, i);
//        }
//        input[j][i] = ani;
//      }
//      input[ep][i] = null;
//      if (lastAni != null) {
//        ((Tile) lastAni).setRowCol(ep, i);
//      }
//      input[ep][i] = lastAni;
//    }
//  }
//
//
//  public static  <T> void rotateSpiral(T[][] arr, int sr, int sc, int er, int ec, T complement) {
//    for (int i = sr; i < er; i++) {
//      T temp = arr[i][sc];
//      arr[i][sc] = complement;
//      complement = temp;
////      if (temp != null)
////        ((Tile) temp).setRowCol(i, sc);
//    }
//    if (ec - sc <= 1) {
//      arr[1][1] = complement;
//      return;
//    }
//    for (int i = 1 + sc; i < ec; i++) {
//      T temp = arr[er - 1][i];
//      arr[er - 1][i] = complement;
//      complement = temp;
////      if (temp != null)
////        ((Tile) temp).setRowCol(er - 1, i);
//    }
//    if (er - sr <= 1) {
//      arr[1][1] = complement;
//      return;
//    }
//    for (int i = er - 2; i >= sr; i--) {
//      T temp = arr[i][ec - 1];
//      arr[i][ec - 1] = complement;
//      complement = temp;
////      if (temp != null)
////        ((Tile) temp).setRowCol(i, ec - 1);
//    }
//    for (int i = ec - 2; i >= sc + 1; i--) {
//      T temp = arr[sr][i];
//      arr[sr][i] = complement;
//      complement = temp;
////      if (temp != null)
////        ((Tile) temp).setRowCol(sc, i);
//    }
//
//    if (ec - sc <= 2 || er - sr <= 2) {
//      arr[1][1] = complement;
//      return;
//    }
//
//    rotateSpiral(arr, sr + 1, sc + 1, er - 1, ec - 1, complement);
//  }
//
//  public static <T> void rotateLeft(T[][] arr, int sr, int sc, int er, int ec, T complement) {
//    for (int i = sr; i < er; i++) {
//      T temp = arr[i][sc];
//      arr[i][sc] = complement;
//      complement = temp;
//    }
//    if (ec - sc <= 1) {
//      arr[sr][sc] = complement;
//      return;
//    }
//    for (int i = 1 + sc; i < ec; i++) {
//      T temp = arr[er - 1][i];
//      arr[er - 1][i] = complement;
//      complement = temp;
//    }
//    if (er - sr <= 1) {
//      arr[sr][sc] = complement;
//      return;
//    }
//    for (int i = er - 2; i >= sr; i--) {
//      T temp = arr[i][ec - 1];
//      arr[i][ec - 1] = complement;
//      complement = temp;
//    }
//    for (int i = ec - 2; i >= sc + 1; i--) {
//      T temp = arr[sr][i];
//      arr[sr][i] = complement;
//      complement = temp;
//    }
//
//    if (ec - sc <= 2 || er - sr <= 2) {
//      arr[sr][sc] = complement;
//      return;
//    }
//
//    arr[sr][sc] = complement;
//    rotateLeft(arr, sr + 1, sc + 1, er - 1, ec - 1, complement);
//  }
//
//  public static <T> void rotateRight(T[][] arr, int sr, int sc, int er, int ec, T complement) {
//    for (int i = sc; i < ec; i++) {
//      T temp = arr[sr][i];
//      arr[sr][i] = complement;
//      complement = temp;
//    }
//
//    if (er - sr <= 1) {
//      arr[sr][sc] = complement;
//      return;
//    }
//    for (int i = sr + 1; i < er; i++) {
//      T temp = arr[i][ec - 1];
//      arr[i][ec - 1] = complement;
//      complement = temp;
//    }
//
//    if (ec - sc <= 1) {
//      arr[sr][sc] = complement;
//      return;
//    }
//    for (int i = ec - 2; i >= sc; i--) {
//      T temp = arr[er - 1][i];
//      arr[er - 1][i] = complement;
//      complement = temp;
//    }
//
//    for (int i = er - 2; i >= sr + 1; i--) {
//      T temp = arr[i][sc];
//      arr[i][sc] = complement;
//      complement = temp;
//    }
//
//    if (ec - sc <= 2 || er - sr <= 2) {
//      arr[sr][sc] = complement;
//      return;
//    }
//
//    arr[sr][sc] = complement;
//    rotateRight(arr, sr + 1, sc + 1, er - 1, ec - 1, complement);
//  }
//
//  public static <T> void rotateMixLeft(T[][] arr, int sr, int sc, int er, int ec, T complement) {
//    for (int i = sr; i < er; i++) {
//      T temp = arr[i][sc];
//      arr[i][sc] = complement;
//      complement = temp;
//    }
//    if (ec - sc <= 1) {
//      arr[sr][sc] = complement;
//      return;
//    }
//    for (int i = 1 + sc; i < ec; i++) {
//      T temp = arr[er - 1][i];
//      arr[er - 1][i] = complement;
//      complement = temp;
//    }
//    if (er - sr <= 1) {
//      arr[sr][sc] = complement;
//      return;
//    }
//    for (int i = er - 2; i >= sr; i--) {
//      T temp = arr[i][ec - 1];
//      arr[i][ec - 1] = complement;
//      complement = temp;
//    }
//    for (int i = ec - 2; i >= sc + 1; i--) {
//      T temp = arr[sr][i];
//      arr[sr][i] = complement;
//      complement = temp;
//    }
//
//    if (ec - sc <= 2 || er - sr <= 2) {
//      arr[sr][sc] = complement;
//      return;
//    }
//
//    arr[sr][sc] = complement;
//    rotateMixRight(arr, sr + 1, sc + 1, er - 1, ec - 1, complement);
//  }
//
//
//  public static <T> void rotateMixRight(T[][] arr, int sr, int sc, int er, int ec, T complement) {
//    for (int i = sc; i < ec; i++) {
//      T temp = arr[sr][i];
//      arr[sr][i] = complement;
//      complement = temp;
//    }
//
//    if (er - sr <= 1) {
//      arr[sr][sc] = complement;
//      return;
//    }
//    for (int i = sr + 1; i < er; i++) {
//      T temp = arr[i][ec - 1];
//      arr[i][ec - 1] = complement;
//      complement = temp;
//    }
//
//    if (ec - sc <= 1) {
//      arr[sr][sc] = complement;
//      return;
//    }
//    for (int i = ec - 2; i >= sc; i--) {
//      T temp = arr[er - 1][i];
//      arr[er - 1][i] = complement;
//      complement = temp;
//    }
//
//    for (int i = er - 2; i >= sr + 1; i--) {
//      T temp = arr[i][sc];
//      arr[i][sc] = complement;
//      complement = temp;
//    }
//
//    if (ec - sc <= 2 || er - sr <= 2) {
//      arr[sr][sc] = complement;
//      return;
//    }
//
//    arr[sr][sc] = complement;
//    rotateMixLeft(arr, sr + 1, sc + 1, er - 1, ec - 1, complement);
//  }
//
//
//}
