package com.aem.impl;

import javax.jcr.Repository;

import org.apache.felix.scr.annotations.Activate;
import org.apache.felix.scr.annotations.Component;
import org.apache.felix.scr.annotations.Deactivate;
import org.apache.felix.scr.annotations.Reference;
import org.apache.felix.scr.annotations.Service;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.aem.RepositoryService;

@Component(metatype = false)
@Service(value = RepositoryService.class)
public class RepositoryServiceImpl implements RepositoryService{
	
	private static final Logger LOGGER = LoggerFactory.getLogger(RepositoryServiceImpl.class);
	
	@Reference
	private Repository repository;

	public String getRepositoryName() {
		return repository.getDescriptor(Repository.REP_NAME_DESC);
	}
	public String abc(){
		return "abc";
	}
	@Activate
	protected void activate(){
		LOGGER.info("service activated");
	}
	
	@Deactivate
	protected void deactivate(){
		LOGGER.info("service deactivated");
	}
}
