package org.nucleodevel.sisacad.utils;

import java.lang.reflect.ParameterizedType;

public class ReflectUtil {

	public static Class<?> getParameterClassFromParameterizedClass(Class<?> parameterizedClass, int parameterIndex) {
		try {
			String parameterClassName = ((ParameterizedType) parameterizedClass.getGenericSuperclass())
					.getActualTypeArguments()[parameterIndex].getTypeName();
			return Class.forName(parameterClassName);
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		}
		return null;
	}

}
