package com.difesoft.sharebooks.util;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;
import org.hibernate.cfg.Configuration;
import org.hibernate.context.internal.ThreadLocalSessionContext;
import org.hibernate.service.ServiceRegistry;

public class HibernateUtil {
	
	private static SessionFactory sessionFactory = null;
	
	public static synchronized void buildSessionFactory() {
		try {
			Configuration conf = new Configuration();
			conf.configure();
			ServiceRegistry serviceRegistry = new StandardServiceRegistryBuilder().
					applySettings(conf.getProperties()).build();
			sessionFactory = conf.buildSessionFactory(serviceRegistry);
		}
		catch (Throwable e) {
			System.err.println("SesionFactory creation failed." + e);
			throw new ExceptionInInitializerError(e);
		}
	}
	
	public static SessionFactory getSessionFactory() {
		if (sessionFactory == null) buildSessionFactory();
		return sessionFactory;
	}

	public static void openSessionAndBindToThread() {
		Session session = sessionFactory.openSession();
		ThreadLocalSessionContext.bind(session);
	}
	
	public static void closeSessionAndUnbindFromThread() {
		Session session = ThreadLocalSessionContext.unbind(sessionFactory);
		if (session != null) session.close();
	}
	
	public static void closeSessionFactory() {
		if ((sessionFactory != null) && (!sessionFactory.isClosed())) {
			sessionFactory.close();
		}
	}
}
