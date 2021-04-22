package com.ss.model;

import com.ss.model.composite.Inventory;
import com.ss.model.composite.PlayData;
import com.ss.model.composite.Profile;
import com.ss.model.protocols.Model;

import java.util.ArrayList;
import java.util.List;


/*
Use this for all later game, simplify data persistent, serialization
 */
@SuppressWarnings("unused")
public class Session implements Model<Session> {
  public Profile profile;
  public Inventory inventory;
  public transient List<List<Integer>>  effectResult;
  public transient PlayData playData;

  public static Session ofDefault() {
    Session session             = new Session();
    session.profile             = Profile.ofDefault();
    session.inventory           = Inventory.ofDefault();
    session.effectResult        = new ArrayList<>();
    session.inventory.session   = session;
    return session;
  }

  @Override
  public void reBalance() {
    if (profile == null)
      profile = Profile.ofDefault();
    profile.reBalance();

    if (inventory == null)
      inventory = Inventory.ofDefault();

    if (effectResult == null)
      effectResult = new ArrayList<>();

    inventory.reBalance();
    inventory.session = this;
  }
}