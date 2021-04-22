package com.ss.utils;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.files.FileHandle;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.JsonReader;
import com.badlogic.gdx.utils.JsonValue;
import com.badlogic.gdx.utils.JsonWriter;
import com.ss.GMain;

public final class Level {

  public final static int NONE = 0;
  public final static int SLIDE = 1;
  public final static int PARTITION = 2;
  public final static int SHIFT = 3;
  public final static int ROTATE = 4;
  public final static int HORIZONTAL = 0;
  public final static int VERTICAL = 1;
  public final static int ANCHOR_START = 0;
  public final static int ANCHOR_END = 1;
  public final static int ANCHOR_IN = 0;
  public final static int ANCHOR_OUT = 1;
  public final static int SHIFT_LEFT = 0;
  public final static int SHIFT_RIGHT = 1;
  public final static int SHIFT_UP = 2;
  public final static int SHIFT_DOWN = 3;
  public final static int SHIFT_MIX_HORIZONTAL = 4;
  public final static int SHIFT_MIX_VERTICAL = 5;
  public final static int ROTATE_LEFT = 0;
  public final static int ROTATE_RIGHT = 1;
  public final static int ROTATE_MIXLEFT = 2;
  public final static int ROTATE_MIXRIGHT = 3;
  public String name;
  public int row;
  public int col;
  public int time;
  public int direction;
  public int anchor;
  public int numPair;
  public int type;
  public int idStart;
  public int clock;
  public int[][] levelData;

  public Level() {

  }

  /**
   * @param type      is NONE | SLIDE | PARTITION | SHIFT | ROTATE.
   * @param direction is VERTICAL | HORIZONTAL
   * @param anchor    is ANCHOR_START | ROTATE_RIGHT.
   */

  private Level(int row, int col, int numPair, int time, int direction, int type, int anchor,int idStart,int clock) {
    this.row        = row;
    this.col        = col;
    this.time       = time;
    this.numPair    = numPair;
    this.direction  = direction; //0 ngang | 1 dung
    this.anchor     = anchor; //0 slice ve dau array | 1 slice ve cuoi array
    this.type       = type;
    this.idStart    = idStart;
    this.clock      = clock;

  }

  private static Level[] defaultLevelData;

  public static int getMaxLevel() {
    return defaultLevelData.length;
  }



  public static Level _Getlv(int lv) {
    lv-=1;
    if (lv >=loadData().length||lv<0 || lv<0) {
      JsonValue levelJV = loadData()[0];
      Level level = new Level();
      level.row = levelJV.getInt("r")+2;
      level.col = levelJV.getInt("c")+2;
      level.numPair = levelJV.getInt("n");
      level.time = levelJV.getInt("t");
      level.type = levelJV.getInt("ty");
      level.direction = levelJV.getInt("d");
      level.anchor = levelJV.getInt("a");
      level.idStart = levelJV.getInt("id");
      level.clock   = levelJV.getInt("ck");
      level.levelData = ConvertLvData(levelJV.get("dt"));
      return level;
    }

      String data = GMain.platform.GetConfigStringValue("lv_"+(lv+1),"");
    System.out.println("data: "+data);
      JsonValue levelJV = loadData()[lv];
      if(data.equals("")||data.equals("lv_"+(lv+1))){
        levelJV = loadData()[lv];
      }else {
        levelJV = Utils.GetJsV(data);
      }
      Level level = new Level();
//      System.out.println("check row: "+levelJV);
      level.row = levelJV.getInt("r")+2;
      level.col = levelJV.getInt("c")+2;
      level.numPair = levelJV.getInt("n");
      level.time = levelJV.getInt("t");
      level.type = levelJV.getInt("ty");
      level.direction = levelJV.getInt("d");
      level.anchor = levelJV.getInt("a");
      level.idStart = levelJV.getInt("id");
      level.clock   = levelJV.getInt("ck");
      level.levelData = ConvertLvData(levelJV.get("dt"));
//    System.out.println("Level Data");
      for (int i=0; i < level.levelData.length; i++){
        for (int j=0; j < level.levelData[i].length; j++){
          System.out.printf(" "+level.levelData[i][j]);
        }
        System.out.println();
      }
    return level;
  }
  public static JsonValue[] loadData() {
    FileHandle files = Gdx.files.internal("level/");
    return loadLevel(files.list((file, s) -> {
      String lowercaseName = s.toLowerCase();
      if (lowercaseName.endsWith(".ds_store"))
        return false;
      return true;
    }));
  }
  public static JsonValue[] loadLevel(FileHandle[] list) {
    if (list == null)
      return new JsonValue[0];
    Array<JsonValue> result = new Array<>();

    for (int i = 0; i < list.length; i++) {
      JsonValue levelJV = Utils.GetJsV(list[i].readString());
      result.add(levelJV);
      result.get(i).setName(list[i].nameWithoutExtension());
    }

    result.sort((a, b) -> Integer.parseInt(a.name) - Integer.parseInt(b.name));

//    for (int i = 0; i < result.size; i++) {
////      JsonValue levelJV = json.parse(list[i]);
//      JsonValue levelJV = result.get(i);
////      if (Integer.parseInt(result.get(i).name()) < 10) {
//        levelJV.get("c").setName("ck");
//        levelJV.get("row").setName("r");
//        levelJV.get("col").setName("c");
//        levelJV.get("numPair").setName("n");
//        levelJV.get("time").setName("t");
//        levelJV.get("type").setName("ty");
//        levelJV.get("direction").setName("d");
//        levelJV.get("anchor").setName("a");
//        levelJV.get("idStart").setName("id");
//        levelJV.get("data").setName("dt");
////        levelJV.addChild("clock",new JsonValue(0));
//        String str = levelJV.toJson(JsonWriter.OutputType.json);
////      list[i].writeString(, false);
//        FileHandle file = new FileHandle("level/" + result.get(i).name());
//        file.writeString(str, false);
////      }
//    }
//      }else if(Integer.parseInt(result.get(i).name())>=0 && Integer.parseInt(result.get(i).name())<40) {
//        levelJV.get("clock").set("1");
////        levelJV.addChild("clock",new JsonValue(1));
//        String str = levelJV.toJson(JsonWriter.OutputType.json);
////      list[i].writeString(, false);
//        FileHandle file = new FileHandle("level/" + result.get(i).name());
//        file.writeString(str, false);
//      }
//      else if(Integer.parseInt(result.get(i).name())>=40 && Integer.parseInt(result.get(i).name())<80) {
////        levelJV.addChild("clock",new JsonValue(2));
//
//        levelJV.get("clock").set("2");
//        String str = levelJV.toJson(JsonWriter.OutputType.json);
////      list[i].writeString(, false);
//        FileHandle file = new FileHandle("level/" + result.get(i).name());
//        file.writeString(str, false);
//      }
//      else if(Integer.parseInt(result.get(i).name())>=80 ) {
//        levelJV.get("clock").set("3");
////        levelJV.addChild("clock",new JsonValue(3));
//
//        String str = levelJV.toJson(JsonWriter.OutputType.json);
////      list[i].writeString(, false);
//        FileHandle file = new FileHandle("level/" + result.get(i).name());
//        file.writeString(str, false);
//      }
//
//
//
////      result.add(levelJV);
////      result.get(i).setName(list[i].nameWithoutExtension());
//    }
    return result.toArray(JsonValue.class);
  }
  private static int[][] ConvertLvData(JsonValue data){
    int[][] arr = new int[data.size+2][data.child.size+2];
    for (int i=1;i<arr.length-1;i++){
      for (int j=1;j<arr[i].length-1;j++){
        arr[arr.length-1-i][j] = data.get(i-1).get(j-1).asInt();
      }
    }
    return arr;
  }
}