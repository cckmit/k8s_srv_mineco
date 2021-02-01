package com.egoveris.edt.base.util;

import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;

/**
 * The Class ApplicationContextProvider.
 */
public class ApplicationContextProvider implements ApplicationContextAware {

  private static ApplicationContext applicationContext = null;

  @Override
  public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
    ApplicationContextProvider.applicationContext = applicationContext;

  }

  public static ApplicationContext getApplicationContext() {
    return applicationContext;
  }

}
