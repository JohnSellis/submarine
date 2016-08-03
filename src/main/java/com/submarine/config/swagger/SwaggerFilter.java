package com.submarine.config.swagger;

import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Component;

import com.wordnik.swagger.core.filter.SwaggerSpecFilter;
import com.wordnik.swagger.model.ApiDescription;
import com.wordnik.swagger.model.Operation;
import com.wordnik.swagger.model.Parameter;

@Component
public class SwaggerFilter implements SwaggerSpecFilter {

	@Override
	public boolean isOperationAllowed(final Operation operation, final ApiDescription apiDescription,
			final Map<String, List<String>> stringListMap, final Map<String, String> stringStringMap,
			final Map<String, List<String>> stringListMap2) {
		return true;
	}

	@Override
	public boolean isParamAllowed(final Parameter parameter, final Operation operation,
			final ApiDescription apiDescription, final Map<String, List<String>> stringListMap,
			final Map<String, String> stringStringMap, final Map<String, List<String>> stringListMap2) {
		if (parameter.paramAccess().isDefined() && "internal".equals(parameter.paramAccess().get())) {
			return false;
		}
		return true;
	}

}