package com.simplecontextaware;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

import com.simplecontextaware.annotations.ContextAware;
import com.simplecontextaware.annotations.InternalInterceptor;
import com.simplecontextaware.annotations.Mandatory;

public abstract class InterceptorTemplate {
	
	@InternalInterceptor
	public final void intercept(Method method, ContextAware c, Object claz) {
		
		if (c.after() && !c.before()) {
			mandatoryHandle(method, true, false, claz);
		} else if (c.before() && !c.after()) {
			mandatoryHandle(method, false, true, claz);
		} else if (!c.after() && !c.before()) {
			mandatoryHandle(method,true,true,claz);
		} else if (c.after() && c.before()) {
			mandatoryHandle(method,true,true,claz);
		}
	}
	
	public final void mandatoryHandle(Method method, boolean after, boolean before, Object claz) {
		
		if (method.isAnnotationPresent(Mandatory.class)) {


			if (after) {
				if ((Boolean) execute(method, claz))
					interceptAfter();
			}
				
			if (before) {
				if (interceptBefore())
					execute(method, claz);

			}
			
		} else {
			
			if (after) {
					execute(method, claz);
					interceptAfter();
			}
			
			if (before) {
					interceptBefore();
					execute(method, claz);
			}
		}
		
	}

	public final Object execute(Method method, Object claz) {
		try {
			return method.invoke(claz);
		} catch (IllegalArgumentException e) {
			e.printStackTrace();
		} catch (IllegalAccessException e) {
			e.printStackTrace();
		} catch (InvocationTargetException e) {
			e.printStackTrace();
		}
		return null;
	}

	protected abstract boolean interceptBefore();

	protected abstract boolean interceptAfter();

}
