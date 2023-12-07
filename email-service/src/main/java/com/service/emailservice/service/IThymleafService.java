/**
 * 
 */
package com.service.emailservice.service;

import java.util.Map;

public interface IThymleafService {

	String createContext(String tempalte, Map<String, Object> variables);

}
