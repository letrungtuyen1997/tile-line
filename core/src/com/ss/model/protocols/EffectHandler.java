package com.ss.model.protocols;

import com.ss.model.Session;

import java.util.List;


/*
Representing [data]effect handling protocols:
  - ExtArgs: External Argument, use in case 4 integer number is not enough to express the context
  - [1,1,100,0]
    + First argument mean the operator: +,-,*,/ or random or any function like ax + b
    + Second argument represent the field of the object to store the value after calculation
    + Third and last argument will be the coefficient of the function or something like thats :)
 */

@FunctionalInterface
@SuppressWarnings("unused")
public interface EffectHandler {
  int    PARAM0  = 0;
  int    PARAM1  = 1;
  int    PARAM2  = 2;
  int    PARAM3  = 3;

  String handleEffect(Session session, final List<Integer> effectFormat);
}