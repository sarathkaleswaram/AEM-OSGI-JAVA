package com.aem;

import java.util.Dictionary;

import javax.jcr.RepositoryException;
import javax.jcr.Session;

import org.apache.felix.scr.annotations.Component;
import org.apache.felix.scr.annotations.Property;
import org.apache.felix.scr.annotations.Reference;
import org.apache.felix.scr.annotations.Service;
import org.apache.sling.commons.osgi.OsgiUtil;
import org.apache.sling.jcr.api.SlingRepository;
import org.osgi.service.component.ComponentContext;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


@Component(immediate = true, metatype = true, label = "Cleanup Service")
@Service(value = Runnable.class)
@Property(name = "scheduler.expression", value = "*/5 * * * * ?")
public class CleanupServiceImpl implements Runnable{
	private static final Logger LOGGER = LoggerFactory.getLogger(CleanupServiceImpl.class);
	@Reference
	private SlingRepository repository;
	@Property(label = "Path", description = "Delete this path", value = "/cleanup")
	public static final String CLEANUP_PATH = "cleanupPath";
	private String cleanupPath;
	protected void activate(ComponentContext componentContext){
		configure(componentContext.getProperties());
	}
	protected void configure(Dictionary<?, ?> properties){
		this.cleanupPath = OsgiUtil.toString(properties.get(CLEANUP_PATH), null);
		LOGGER.info("configure: cleanupPath='{}'", this.cleanupPath);
	}
	public void run() {
		LOGGER.info("running now");
		Session session = null;
		try {
			session = repository.loginAdministrative(null);
			if(session.itemExists(cleanupPath)){
				session.removeItem(cleanupPath);
				LOGGER.info("node Deleted");
				session.save();
			}
		}
		catch (RepositoryException e) {
			LOGGER.error("exception during cleanup", e);
		} finally {
			if(session != null) {
				session.logout();
			}
		}
	}

}
