package com.egoveris.shared.collection;

import java.util.List;

public class CollectionUtils {

	public static <T> List<T> asList(Object o, Class<T> cls) {
		return asList(o, cls, false);
	}
	
	public static <T> List<T> asList(Object o, Class<T> cls, boolean checkElements) {
		if (o == null) {
			return null;
		}
		if (o instanceof List<?>) {
			List<?> list = (List<?>) o; // no warning
			if (checkElements) {
				for (Object element : list) {
					if (element != null && !cls.isInstance(element)) {
						throw new ClassCastException(element.getClass().getName() + " cannot be cast to "
								+ cls.getName());
					}
				}
			}

			@SuppressWarnings("unchecked")
			List<T> result = (List<T>) list; // localize the suppression
			return result;
		}
		throw new ClassCastException(o.getClass().getName() + " cannot be cast to " + List.class.getName());
	}

}
