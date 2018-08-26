package com.springdemo.aspect;

import java.util.logging.Logger;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.stereotype.Component;



@Aspect
@Component
public class CMSLoggingAspect {

	// kreiramo logger
	
	private Logger myLogger = Logger.getLogger(getClass().getName());
	
	// kreiramo pointcut declaration
	@Pointcut("execution(* com.springdemo.controller.*.*(..))")
	private void forControllerPackage() {}
	
	// pointcut for service package
	@Pointcut("execution(* com.springdemo.service.*.*(..))")
	private void forServicePackage() {}
	
	// pointcut for dao package
	@Pointcut("execution(* com.springdemo.dao.*.*(..))")
	private void forDaoPackage() {}
	
	// kombinujemo sva 3 pointcut-a, da se odnose aspekti samo na ta tri paketa
	@Pointcut("forControllerPackage() || forServicePackage() || forDaoPackage()") 
	private void forAppFlow() {}
	
	// add @Before advice
	
	@Before("forAppFlow()")
	public void before(JoinPoint joinpoint) {
		
		// ucitamo naziv metode koja se poziva i ispisemo je 
		String metoda = joinpoint.getSignature().toShortString();
		
		myLogger.info("=====> @Before: metoda koja se poziva: " + metoda);
		
		// ucitamo argumente koje se nalaze u metodi
		Object[] argumenti = joinpoint.getArgs();
		
		for(Object arg: argumenti) {
			myLogger.info("=====> @Before: argumenti metode: " + arg);
		}
		
	}
	
	// add @AfterReturning advice
	@AfterReturning(
			pointcut="forAppFlow()",
			returning="result"
			)
	public void afterReturning(JoinPoint joinpoint, Object result) {
		
		// ispisemo naziv metode iz koje se vracaju rezultati
		String metoda = joinpoint.getSignature().toShortString();
		myLogger.info("=====> @AfterReturning: metoda iz koje se vracaju rezultati: " + metoda);
		
		// prikazemo podatke koji se vracaju
		myLogger.info("=====> @AfterReturning: rezultat koji se vraca: " + result.toString());
	}
	
	
	
	
	
	
	
	
}
