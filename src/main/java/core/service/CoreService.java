package core.service;

import org.hibernate.Transaction;
import org.hibernate.Session;

import static core.util.HibernateUtil.getSessionFactory;

public interface CoreService {
	
	private Session getSession() {
		return getSessionFactory().getCurrentSession();
	}
	
	default Transaction beginTransaction() {
		return getSessionFactory().getCurrentSession().beginTransaction();
		
	}
	
	default void commit() {
		getSessionFactory().getCurrentSession().beginTransaction().commit();
	}
	
	default void rollback() {
		getSessionFactory().getCurrentSession().beginTransaction().rollback();
	}
}
