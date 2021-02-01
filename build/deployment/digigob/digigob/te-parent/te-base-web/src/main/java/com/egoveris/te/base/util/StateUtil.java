package com.egoveris.te.base.util;

import java.util.ArrayList;
import java.util.List;

public class StateUtil {
  
  private StateUtil() {
    // Utility constructor
  }
  
  public static String calculateNextStateTask(List<String> possibleStates, String actualState, String previousState) {
    return calculateNextState(possibleStates, actualState, previousState, ConstantesCommon.ESTADO_GUARDA_TEMPORAL);
  }
  
  public static String calculateNextStateOperation(List<String> possibleStates, String actualState, String previousState) {
    return calculateNextState(possibleStates, actualState, previousState, ConstantesCommon.ESTADO_CIERRE);
  }
  
  /**
   * Calculates next state inside a set of possible states, and a actual state
   * 
   * @param possibleStates
   * @param actualState
   * @return
   */
  public static String calculateNextState(List<String> possibleStates, String actualState, String previousState, String finalState) {
    String nextState = null;
    
    if (possibleStates == null) {
      possibleStates = new ArrayList<>();
    }
    
    // If has final state, return it
    if (possibleStates.contains(finalState)) {
      nextState = finalState;
    }
    else {
      // Removes subsanacion state (we don't need it)
      if (possibleStates.contains(ConstantesCommon.ESTADO_SUBSANACION)) {
        possibleStates.remove(ConstantesCommon.ESTADO_SUBSANACION);
      }
      
      if (previousState == null) {
        previousState = "";
      }
      
      for (String state : possibleStates) {
        // If there's only 1 possible state, return it - Case first state
        if (possibleStates.size() == 1) {
          nextState = state;
        }
        // Else, searchs first state that isn't actual state or previous state
        else if (!state.equalsIgnoreCase(actualState) && !state.equalsIgnoreCase(previousState)
            && !"forkEach".equalsIgnoreCase(state)) { // forkEach is a solicitud flow state
          nextState = state;
          break;
        }
      }
    }
    
    return nextState;
  }
  
}
