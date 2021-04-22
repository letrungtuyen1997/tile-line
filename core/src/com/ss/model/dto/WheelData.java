package com.ss.model.dto;

import java.util.HashMap;

public class WheelData {
  public static class WheelDto{
    public String region;
    public int id;
    public int quantity;
    public int percent;

    public static WheelDto of(String region, int id, int quantity, int percent){
      WheelDto wheelDto = new WheelDto();
      wheelDto.region = region;
      wheelDto.id = id;
      wheelDto.quantity = quantity;
      wheelDto.percent = percent;
      return wheelDto;
    }

    public static HashMap<Integer, WheelDto> wheelMap;

    static{
      wheelMap = new HashMap<>();
      wheelMap.put(1, WheelDto.of("spin_coin_4", 0, 30, 2000));
      wheelMap.put(2, WheelDto.of("spin_coin_4", 1, 31, 2000));
      wheelMap.put(3, WheelDto.of("spin_coin_3", 2, 32, 1000));
      wheelMap.put(4, WheelDto.of("spin_coin_4", 3, 33, 1000));
      wheelMap.put(5, WheelDto.of("spin_coin_4", 4, 34, 1000));
      wheelMap.put(6, WheelDto.of("spin_coin_1", 5, 35, 1000));
      wheelMap.put(7, WheelDto.of("spin_coin_1", 6, 36, 1000));
      wheelMap.put(8, WheelDto.of("spin_coin_1", 7, 37, 1000));
    }
  }
}
