package com.verraros.twilike.aop;

import org.apache.log4j.Logger;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.stereotype.Component;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

@Component
@Aspect
public class RestfulResponseBodyInterceptor implements InitializingBean{
	protected Logger log = Logger.getLogger(getClass());

	private Gson gson;
	@Around("@annotation(com.verraros.twilike.aop.Rest)")
	public String proceed(ProceedingJoinPoint proceedingJoinPoint) throws Throwable {
		Object objectToDump = proceedingJoinPoint.proceed();
		return dumpToJson(objectToDump);
		
	}
	
	private String dumpToJson(Object object){
		if(object == null){
			return "{}";
		}
		
		if(object instanceof String){
			return "{ \"reponse\": \""+(String)object+"\"}";
		}
		return "{ \"reponse\": \""+gson.toJson(object)+"\"}";
	}

	@Override
	public void afterPropertiesSet() throws Exception {
		gson = new GsonBuilder().create();
	}
}
