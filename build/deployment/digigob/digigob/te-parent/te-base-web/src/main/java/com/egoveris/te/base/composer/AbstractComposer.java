package com.egoveris.te.base.composer;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.zkoss.zk.ui.util.GenericForwardComposer;

@SuppressWarnings("serial")
public abstract class AbstractComposer extends GenericForwardComposer {
  private static Logger logger = LoggerFactory.getLogger(AbstractComposer.class);

  public AbstractComposer(char separator) {
    super(separator);
  }

  public AbstractComposer() {
  }

  public Logger getLogger() {
    return logger;
  }

}
