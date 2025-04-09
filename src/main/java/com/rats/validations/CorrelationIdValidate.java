package com.rats.validations;

import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;

public class CorrelationIdValidate {
	 private static final Set<String> authorizeds  = ConcurrentHashMap.newKeySet();

	 public static void add(String correlationId) {
	    authorizeds.add(correlationId);
	 }

	 public static boolean isValid(String correlationId) {
	     return authorizeds.contains(correlationId);
	 }

	 public static void remove(String correlationId) {
		 authorizeds.remove(correlationId);
	 }
}