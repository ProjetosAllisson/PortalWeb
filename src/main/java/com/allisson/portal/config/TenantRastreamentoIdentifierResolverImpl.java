package com.allisson.portal.config;

import org.hibernate.context.spi.CurrentTenantIdentifierResolver;

public class TenantRastreamentoIdentifierResolverImpl implements CurrentTenantIdentifierResolver{
	
	private static String DEFAULT_TENANT_ID = "tenant_1";

	@Override
	public String resolveCurrentTenantIdentifier() {
		String currentTenantId = RastreamentoTenantContext.getTenantId();
		return (currentTenantId != null) ? currentTenantId : DEFAULT_TENANT_ID;
	}

	@Override
	public boolean validateExistingCurrentSessions() {
		return true;
	}

}
