package com.ss.model.impl;

import com.badlogic.gdx.Gdx;
import com.ss.model.protocols.DataAccess;
import com.ss.model.protocols.Model;


/*
Wrapper Utility
 */
@SuppressWarnings("unused")
public class PersistentLoader<T extends Model<T>> {
  DataAccess<T> da;
  float             interval;
  float             acc;
  public T          model;
  String            key;

  public PersistentLoader(float interval, String key, T model, Class<T> type) {
    this.interval   = interval;
    this.da         = new PrefDA<>(type);
    this.key        = key;
    this.model      = model;
  }

  //sync by interval
  public void sync(float dt) {
    acc += dt;

    if (acc >= interval) {
      acc = 0;
      da.sync(key, model);
    }
  }

  //active sync
  public void sync() {
    da.sync(key, model);
  }

  public T load() {
    try {
      T loaded = da.load(key);
      if (loaded != null) {
        model = loaded;
        model.reBalance();
      }
    }
    catch (Exception e) {
      Gdx.app.log("PersistentLoader", "Fallback to default: " + e.getMessage());
    }
    return model;
  }
}