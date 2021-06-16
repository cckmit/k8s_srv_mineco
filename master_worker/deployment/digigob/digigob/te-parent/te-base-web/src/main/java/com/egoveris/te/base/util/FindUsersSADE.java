package com.egoveris.te.base.util;

import java.util.ArrayList;
import java.util.List;

public class FindUsersSADE extends FindUsersAbstract {

  /**
  * 
  */
  private static final long serialVersionUID = -195389809174624171L;

  @Override
  protected List<?> lookForItems(String value) {
    List<String> list = new ArrayList<String>();
    list.add("Cacho");
    list.add("PEPE");
    list.add("Josefo");
    return list;
  }

}
