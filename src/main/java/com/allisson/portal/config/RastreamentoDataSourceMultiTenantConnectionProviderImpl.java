package com.allisson.portal.config;

import java.util.Map;

import javax.sql.DataSource;

import org.hibernate.engine.jdbc.connections.spi.AbstractDataSourceBasedMultiTenantConnectionProviderImpl;
import org.springframework.beans.factory.annotation.Autowired;

public class RastreamentoDataSourceMultiTenantConnectionProviderImpl extends AbstractDataSourceBasedMultiTenantConnectionProviderImpl{

	private static final long serialVersionUID = 1L;

	@Autowired
	private Map<String, DataSource> dataSourcesRastreamento;

	@Override
	protected DataSource selectAnyDataSource() {
		return this.dataSourcesRastreamento.values().iterator().next();
	}

	@Override
	protected DataSource selectDataSource(String tenantIdentifier) {
		return this.dataSourcesRastreamento.get(tenantIdentifier);
	}
}
