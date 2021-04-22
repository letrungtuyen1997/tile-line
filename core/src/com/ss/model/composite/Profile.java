package com.ss.model.composite;

import com.badlogic.gdx.Gdx;
import com.ss.utils.Utils;

import java.util.HashMap;

public class Profile {
  public long   id;
  public String name;
  public int    bestScore;
  public int    bestToday;
  public int    money;
  public int    level;
  public long   lastOnline;
  public int    exp;
  public int    star;
  public String idlang;
  public boolean isfirst;
  public boolean istutobomb;
  public HashMap<String,Boolean> dataStore;

  public static Profile ofDefault() {
    Profile p     = new Profile();
    p.bestScore   = 200;
    p.bestToday   = 200;
    p.level       = 1;
    p.exp         = 0;
    p.money       = 3000;
    p.name        = "";
    p.lastOnline  = 0;
    p.star        = 0;
    p.idlang      = "";
    p.isfirst     = true;
    p.isfirst     = false;
    p.dataStore   = new HashMap<String, Boolean>(){{
      for (int i = 0; i< Utils.GetJsV(Gdx.files.internal("data/Store.json").readString()).size; i++){
        if(i==0){
          put("itemsT"+i,true);
          put("itemsBg"+i,true);
        }else {
          put("itemsT"+i,false);
          put("itemsBg"+i,false);
        }
      }
    }};

    p.id          = (System.currentTimeMillis() << 20) | (System.nanoTime() & ~9223372036854251520L);
    return p;
  }

  public void reBalance() {
    if (id == 0)
      id = (System.currentTimeMillis() << 20) | (System.nanoTime() & ~9223372036854251520L);

    if (name == null)
      name = "";
//    if (level < 1 || level > 20)
//      level = 1;
//    level = 200;

    if(dataStore==null){
      dataStore   = new HashMap<String, Boolean>(){{
        for (int i = 0; i< Utils.GetJsV(Gdx.files.internal("data/Store.json").readString()).size; i++){
          if(i==0){
            put("itemsT"+i,true);
            put("itemsBg"+i,true);
          }else {
            put("itemsT"+i,false);
            put("itemsBg"+i,false);
          }
        }
      }};
    }

  }

}