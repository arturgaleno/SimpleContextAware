Project created by Artur Galeno Muniz

The objective that project is allow developers a simple way to do android context oriented applications.

For now, that version is implemented for use with AndroidAnnotations framework.

That version is just a useful simple implementation of an ideia. Feel free to fork and improve.

Usage:

@ContextAware
 
This annotation is used to annotate methods that is context orientated, you can specify the atributes "before" or "after". If you use "before", that method will be executed before the interceptor method "interceptBefore", if you use "after", that method will be executed after the interceptor method "interceptAfter". If you want, you can specify both at same time.

public class foo {

	@ContextAware(before=true,after=true)
	public boolean method() {
		.
		.
		.
	}
}

InterceptorTemplate Class

To use correctly @ContextAware some other implementations are necessary, the interceptor is mandatory. You must to extend InterceptorTemplate to implement the methods "intercetBefore" and "interceptAfter"

public class MyInterceptor extends InterceptorTemplate {
	
	@Override
	public boolean interceptBefore() {}
	@Override
	public boolean interceptAfter() {}
}

@Interceptor

That annotation is used to register the interceptor on the class that contain some method with @ContextAware annotation.

public class foo {
	
	@Interceptor
	MyInterceptor myInterceptor;

	@ContextAware(before=true,after=true)
	public boolean method() {
	}

}

@Mandatory

That annotation says the annotated method will execute only if the intercept method return true, in case of before=true, or only if the annotated method return is true in case of after=true. Or in both cases.

public class foo {

	@Interceptor
	MyInterceptor myInterceptor;

	@ContextAware(before=true,after=true)
	@Mandatory
	public boolean method() {
	}
}

IContextType Interface

This interface says that class which implements the interface is a context.
And forces you to implement the "getInterceptor" method. This method must return the interceptor that you was registrated on class.

public class foo implements IContextType {
	
	@Interceptor
	MyInterceptor myInterceptor;

	@ContextAware(before=true,after=true)
	@Mandatory
	public boolean method(){
	}

	@Override
	public InterceptorTemplate getInterceptor(){
		return this.myInterceptor;
	}
}

SimpleContextAware allow to use Otto framework. For this offers the BusManager class. But you must to use the Otto version compatible with AndroidAnnotation framework.

Links:

Otto Oficial Web Site: http://square.github.io/otto/
AndroidAnnotations Oficial Web Site: http://androidannotations.org/
Otto - AnndroidAnnotations Compatible: https://github.com/jasonparekh/otto-androidannotations-compatible

License

Copyright 2013 Artur Galeno Muniz.

Licensed under the Apache License, Version 2.0 (the "License");
you may not use this file except in compliance with the License.
You may obtain a copy of the License at

   http://www.apache.org/licenses/LICENSE-2.0

Unless required by applicable law or agreed to in writing, software
distributed under the License is distributed on an "AS IS" BASIS,
WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
See the License for the specific language governing permissions and
limitations under the License.
