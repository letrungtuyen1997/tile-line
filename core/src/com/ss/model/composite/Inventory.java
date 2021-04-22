package com.ss.model.composite;

import com.badlogic.gdx.utils.IntMap;
import com.ss.model.Session;
import com.ss.model.dto.PropData;
import com.ss.model.effects.EffectManager;
import com.ss.model.protocols.Model;


@SuppressWarnings("unused")
public class Inventory implements Model<Inventory> {
  public IntMap<Integer> userItem;
  public transient Session session;

  public static Inventory ofDefault() {
    Inventory inventory = new Inventory();
    inventory.userItem = new IntMap<>();
    inventory.userItem.put(1, 3);
    return inventory;
  }

  @Override
  public void reBalance() {
    if (userItem == null) {
      userItem = new IntMap<>();
      userItem.put(1, 3);
    }
  }

  public void addItem(int itemId, int amount) {
    if (!userItem.containsKey(itemId))
      return;
    int oldAmount = userItem.get(itemId);
    int newAmount = oldAmount + amount;
    newAmount     = Math.max(newAmount, 0);
    userItem.put(itemId, newAmount);
  }

  public void useItem(int itemId) {
    if (!userItem.containsKey(itemId))
      return;
    int amount = userItem.get(itemId);
    if (amount <= 0)
      return;

    PropData.PropDTO dto = PropData.propMap.get(itemId);
    if (dto == null)
      return;

    String res = EffectManager.inst().handleEffect(session, dto.effect);

    if (res.equals("ok"))
      userItem.put(itemId, amount -1);
  }
}