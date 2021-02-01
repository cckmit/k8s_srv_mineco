package com.egoveris.edt.base.util.zk;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.zkoss.zk.ui.Component;

/**
 * The Class ZkUtil.
 *
 * @author difarias
 */
public final class ZkUtil {

  private static Logger logger = LoggerFactory.getLogger(ZkUtil.class);

  /**
   * Find by id.
   *
   * @param <T> the generic type
   * @param container the container
   * @param componentId the component id
   * @return the t
   */
  @SuppressWarnings("unchecked")
  public static <T> T findById(Component container, String componentId) {
    Component founded = null;

    if (container != null) {
      try {
        founded = container.getFellow(componentId);
      } catch (Exception ex) {
        logger.error("Error ZkUtil", ex);
      }

      if (founded == null) {
        for (Object childComp : container.getChildren()) {
          Component child = (Component) childComp;
          founded = findById(child, componentId);
          if (founded != null) {
            break;
          }
        }
      }
    }

    return (T) founded;
  }

  /**
   * Find by type.
   *
   * @param <T> the generic type
   * @param obj the obj
   * @param componentType the component type
   * @return the list
   */
  @SuppressWarnings("unchecked")
  public static <T> List<T> findByType(Object obj, Class<T> componentType) {
    Component container = (Component) obj;
    List<T> lstFound = new ArrayList<T>();
    List<Class<?>> toSearch = new ArrayList<Class<?>>();

    toSearch.add(container.getClass());
    toSearch.addAll(Arrays.asList(container.getClass().getInterfaces()));

    for (Class<?> clz : toSearch) {
      if (clz.equals(componentType)) {
        lstFound.add((T) container);
        break;
      }
    }

    for (Object childComp : container.getChildren()) {
      Component child = (Component) childComp;
      lstFound.addAll(findByType(child, componentType));
    }

    return lstFound;
  }

  /**
   * Find parent by type.
   *
   * @param <T> the generic type
   * @param container the container
   * @param containerType the container type
   * @return the t
   */
  @SuppressWarnings("unchecked")
  public static <T> T findParentByType(Component container, Class<T> containerType) {
    if (container.getClass().equals(containerType)) {
      return (T) container;
    }
    return (T) findParentByType(container.getParent(), containerType);
  }

}
