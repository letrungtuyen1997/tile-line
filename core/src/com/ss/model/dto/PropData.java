package com.ss.model.dto;

import com.badlogic.gdx.utils.IntMap;
import com.ss.utils.StrUtils;

import java.util.List;


@SuppressWarnings("unused")
public class PropData {
  public static class PropDTO {
    public int            id;
    public String         name;
    public String         desc;
    public List<Integer>  effect;

    public static PropDTO of(int id, String effect, String name, String desc) {
      PropDTO dto   = new PropDTO();
      dto.id        = id;
      dto.name      = name;
      dto.desc      = desc;
      dto.effect    = StrUtils.parseListInt(effect);
      return dto;
    }
  }

  public static IntMap<PropDTO> propMap;

  static {
    propMap = new IntMap<>();
    propMap.put(1, PropDTO.of(1, "1,1,100,0",     "Item1", "+100 gold"));
    propMap.put(2, PropDTO.of(2, "1,2,100,0",     "Item2", "+100 exp"));
    propMap.put(3, PropDTO.of(3, "2,1,1000,5000", "Item3", "+(1000 -> 5000) gold"));
    propMap.put(4, PropDTO.of(4, "1,3,10,20",     "Item4", "+(10 -> 20) level"));
    propMap.put(5, PropDTO.of(5, "200,1,0,0",     "Item5", "[+30 exp | +50 gold | +1 Item1"));
    propMap.put(6, PropDTO.of(6, "200,2,0,0",     "Item6", "[+100 gold , +20 exp, +1 Item2]"));
  }
}