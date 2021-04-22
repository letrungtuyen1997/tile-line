package com.ss.model.dto;

import com.badlogic.gdx.utils.IntMap;
import com.ss.utils.StrUtils;

import java.util.ArrayList;
import java.util.List;


public class DropData {
  public static class Drop {
    public int          id;
    public String       desc  = "";
    public int          count;
    public int          type;
    public List<Pack>   packs;
    public int          totalRate;

    public static Drop of (int id, int type) {
      Drop drop = new Drop();
      drop.id = id;
      drop.type = type;
      drop.packs = new ArrayList<>();
      return drop;
    }

    public Drop p(String effect, int rate) {
      Pack pack = Pack.of(StrUtils.parseListInt(effect), rate);
      packs.add(pack);
      totalRate += pack.rate;
      count++;
      return this;
    }

    public Drop d(String desc) {
      this.desc = desc;
      return this;
    }
  }

  public static class Pack {
    public List<Integer> effect;
    public int rate;
    public static Pack of (List<Integer> effect, int rate) {
      Pack pack = new Pack();
      pack.effect = effect;
      pack.rate = rate;
      return pack;
    }
  }

  public static IntMap<Drop> dropMap;

  static {
    dropMap = new IntMap<>();
    //type 1 mean only 1 of them
    dropMap.put(1, Drop.of(1, 1).p("1,2,30,0", 330).p("1,1,50,0", 160).p("100,1,1,0", 400));
    //type 2 mean 1 or 2 or all or none of them
    dropMap.put(2, Drop.of(2, 2).p("1,1,100,0", 33).p("1,2,20,0", 50).p("100,1,2,0",17));
  }
}