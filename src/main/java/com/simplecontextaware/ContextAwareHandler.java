package com.simplecontextaware;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.Arrays;

import com.simplecontextaware.annotations.ContextAware;
import com.simplecontextaware.annotations.Interceptor;
import com.simplecontextaware.annotations.InternalInterceptor;
import com.simplecontextaware.exceptions.InterceptorNotFoundException;
import com.simplecontextaware.interfaces.IContextType;

public class ContextAwareHandler implements IContextType {

	private IContextType contextType;

	private InterceptorTemplate interceptorTemplate;

	public ContextAwareHandler(IContextType contextType) {
		this.contextType = contextType;
	}


	public void execute() throws InterceptorNotFoundException {

		interceptorTemplate = null;

		if (contextType != null) {

			for (Field field : Arrays.asList(contextType.getClass().getSuperclass()
					.getDeclaredFields())) {
				if (field.isAnnotationPresent(Interceptor.class)) {
					try {
						interceptorTemplate = contextType.getInterceptor();
							
					} catch (IllegalArgumentException e) {
						e.printStackTrace();
					} catch (SecurityException e) {
						e.printStackTrace();
					}
				}
			}
			
			if (interceptorTemplate == null) throw new InterceptorNotFoundException();
			
			for (Method method : Arrays.asList(contextType.getClass().getMethods())) {
				if (method.isAnnotationPresent(ContextAware.class)
						&& interceptorTemplate != null) {

					for (Method interceptorMethod : Arrays
							.asList(interceptorTemplate.getClass().getMethods())) {
						if (interceptorMethod.getDeclaringClass()
								.isAssignableFrom(contextType.getInterceptor()
										.getClass().getSuperclass().getSuperclass())
								&& interceptorMethod.getName().compareTo("intercept") == 0) {
							try {
								interceptorMethod
										.invoke(interceptorTemplate,
												method,
												method.getAnnotation(ContextAware.class),
												contextType);
							} catch (IllegalArgumentException e) {
								e.printStackTrace();
							} catch (IllegalAccessException e) {
								e.printStackTrace();
							} catch (InvocationTargetException e) {
								e.printStackTrace();
							} 
						}

					}
				}
			}

		}

	}


	public InterceptorTemplate getInterceptor() {
		// TODO Auto-generated method stub
		return null;
	}

}
