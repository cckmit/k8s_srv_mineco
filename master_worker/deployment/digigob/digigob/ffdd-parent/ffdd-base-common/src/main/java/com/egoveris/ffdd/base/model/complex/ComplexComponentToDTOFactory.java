package com.egoveris.ffdd.base.model.complex;

import com.egoveris.ffdd.base.util.FFDDConstants;
import com.egoveris.ffdd.model.exception.UnexpectedFormError;
import com.egoveris.ffdd.model.model.TransaccionDTO;
import com.egoveris.ffdd.model.model.ValorFormCompDTO;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class ComplexComponentToDTOFactory {

  private ComplexComponentToDTOFactory() {
  }

  public static Collection<ComplexComponentDTO> createComponent(
      final TransaccionDTO transaccionDTO,
      final Class<? extends ComplexComponentDTO> componentDTOClass, final String key) {
    final List<ComplexComponentDTO> returnValue = new ArrayList<>();
    final Integer repetitions = getRepetitions(transaccionDTO, key);
    for (int i = 0; i <= repetitions; i++) {
      ComplexComponentDTO comp;
      try {
        final Constructor<ComplexComponentDTO> constructor = (Constructor<ComplexComponentDTO>) componentDTOClass
            .getConstructor(TransaccionDTO.class, String.class, Integer.class);
        comp = constructor.newInstance(transaccionDTO, key, i);
        returnValue.add(comp);
      } catch (InstantiationException | IllegalAccessException | IllegalArgumentException
          | InvocationTargetException | SecurityException | NoSuchMethodException e) {
        throw new UnexpectedFormError("Errr creating complex Component", e);
      }
    }
    return returnValue;
  }

  /**
   * Gets the repetitions for a key component. 0 if only 1 component.
   *
   * @param transaccionDTO
   * @param key
   * @return
   */
  private static Integer getRepetitions(final TransaccionDTO transaccionDTO, final String key) {
    final Set<String> repeat = new HashSet<>();
    for (final ValorFormCompDTO value : transaccionDTO.getValorFormComps()) {
      if (value.getInputName().split(FFDDConstants.NAME_SEPARATOR)[0].equals(key)
          && value.getInputName().split(FFDDConstants.NAME_SEPARATOR).length > 2) {
        repeat.add(value.getInputName().split(FFDDConstants.NAME_SEPARATOR)[2]);
      }
    }
    return repeat.size();
  }
}