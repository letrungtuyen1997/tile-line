package com.ss.model.protocols;

//this mean every Model have an ability of repair when data corrupt
public interface Model<A> {
  void reBalance();
}