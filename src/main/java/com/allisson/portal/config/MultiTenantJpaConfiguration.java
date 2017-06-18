package com.allisson.portal.config;

import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;

import javax.persistence.EntityManagerFactory;
import javax.sql.DataSource;

import org.hibernate.MultiTenancyStrategy;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Environment;
import org.hibernate.context.spi.CurrentTenantIdentifierResolver;
import org.hibernate.engine.jdbc.connections.spi.MultiTenantConnectionProvider;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.jdbc.DataSourceBuilder;
import org.springframework.boot.autoconfigure.orm.jpa.JpaProperties;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.ImportResource;
import org.springframework.orm.hibernate5.HibernateTransactionManager;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;
import org.springframework.orm.jpa.vendor.HibernateJpaVendorAdapter;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.annotation.EnableTransactionManagement;

import com.allisson.portal.config.MultiTenantRastreamentoProperties.DataSourceProperties;
import com.allisson.portal.model.Tenant;

@Configuration
@EnableConfigurationProperties({ MultiTenantRastreamentoProperties.class, JpaProperties.class })
@ImportResource(locations = { "classpath:applicationContent.xml" })
@EnableTransactionManagement
public class MultiTenantJpaConfiguration {
	
	@Autowired
	private JpaProperties jpaProperties;
	
	@Autowired
	private MultiTenantRastreamentoProperties multiTenantRastreamentoProperties;
	
	@Bean(name = "dataSourcesRastreamento" )
	public Map<String, DataSource> dataSourceRastreamento(){
		Map<String, DataSource> result = new HashMap<>();
		
		for(DataSourceProperties dsProperties : this.multiTenantRastreamentoProperties.getDataSources()){
			DataSourceBuilder factory = DataSourceBuilder
					.create()
					.url(dsProperties.getUrl())
					.username(dsProperties.getUsername())
					.password(dsProperties.getPassword())
					.driverClassName(dsProperties.getDriverClassName());
				result.put(dsProperties.getTenantId(), factory.build());
		}
		return result;
	}
	
	@Bean
	public MultiTenantConnectionProvider multiTenantConnectionProvider() {
		// Autowires dataSourcesDvdRental
		return new RastreamentoDataSourceMultiTenantConnectionProviderImpl();
	}

	@Bean
	public CurrentTenantIdentifierResolver currentTenantIdentifierResolver() {
		return new TenantRastreamentoIdentifierResolverImpl();
	}
	
	@Bean
	public LocalContainerEntityManagerFactoryBean entityManagerFactoryBean(MultiTenantConnectionProvider multiTenantConnectionProvider,
		CurrentTenantIdentifierResolver currentTenantIdentifierResolver) {

		Map<String, Object> hibernateProps = new LinkedHashMap<>();
		hibernateProps.putAll(this.jpaProperties.getProperties());
		hibernateProps.put(Environment.MULTI_TENANT, MultiTenancyStrategy.DATABASE);
		hibernateProps.put(Environment.MULTI_TENANT_CONNECTION_PROVIDER, multiTenantConnectionProvider);
		hibernateProps.put(Environment.MULTI_TENANT_IDENTIFIER_RESOLVER, currentTenantIdentifierResolver);

		// No dataSource is set to resulting entityManagerFactoryBean
		LocalContainerEntityManagerFactoryBean result = new LocalContainerEntityManagerFactoryBean();
		
		result.setPackagesToScan(new String[] { Tenant.class.getPackage().getName() });
		result.setJpaVendorAdapter(new HibernateJpaVendorAdapter());
		result.setJpaPropertyMap(hibernateProps);

		return result;
	}
	
	
	@Bean
	public EntityManagerFactory entityManagerFactory(LocalContainerEntityManagerFactoryBean entityManagerFactoryBean) {
		return entityManagerFactoryBean.getObject();
	}

	@Bean
	public PlatformTransactionManager txManager(EntityManagerFactory entityManagerFactory) {
		SessionFactory sessionFactory = entityManagerFactory.unwrap(SessionFactory.class);
		HibernateTransactionManager result = new HibernateTransactionManager();
		result.setAutodetectDataSource(false);
		result.setSessionFactory(sessionFactory);
		return result;
	}

}
