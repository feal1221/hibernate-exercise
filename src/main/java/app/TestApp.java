package app;

import java.util.List;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.query.Query;

import core.util.HibernateUtil;
import web.member.pojo.Member;

public class TestApp {
	public static void main(String[] args) {
		SessionFactory sessionFactory = HibernateUtil.getSessionFactory();
		Session session = sessionFactory.openSession();
		//select
		CriteriaBuilder criteriaBuilder = session.getCriteriaBuilder();
		CriteriaQuery<Member> criteriaQuery = criteriaBuilder.createQuery(Member.class);
		//from MEMBER
		Root<Member> root = criteriaQuery.from(Member.class);
		//where USERNAME = ? and PASSWORD= ?
		criteriaQuery.where(criteriaBuilder.and(
			criteriaBuilder.equal(root.get("username"), "admin"),
			criteriaBuilder.equal(root.get("password"),"P@ssw0rd")
		));
		
		//select username nickname
		criteriaQuery.multiselect(root.get("username"),root.get("nickname"));
		//加入orderby
		criteriaQuery.orderBy(criteriaBuilder.asc(root.get("id")));
		
		Member member = session.createQuery(criteriaQuery).uniqueResult();
		System.out.println(member.getNickname());
		
		
//		Member mem = new Member();
//		//新增
////		mem.setUsername("使用者名稱");
////		mem.setPassword("密碼");
////		mem.setNickname("暱稱");
//		TestApp test = new TestApp();
////		test.insert(mem);
////		System.out.println(mem.getId());
//		//刪除
////		System.out.println(test.deleteById(3));
//		//修改
////		mem.setId(1);
////		mem.setPass(false);
////		mem.setRoleId(1);
////		System.out.println(test.updateById(mem));
//		//查詢
////		System.out.println(test.selectById(2).getNickname());
//		//
////		test.selectAll().forEach(member -> System.out.println(member.getNickname()));
//		for (Member member : test.selectAll()) {
//			System.out.println(member.getNickname());
//		}
	}
	
	public Integer insert(Member member) {
		SessionFactory sessionFactory = HibernateUtil.getSessionFactory();
		Session session = sessionFactory.openSession();
		try {
			Transaction transaction = session.beginTransaction();
			session.persist(member);
			transaction.commit();
			return member.getId();
		} catch (Exception e) {
			e.printStackTrace();
			session.getTransaction().rollback();
			return null;
		}
	}
	
	public Integer deleteById(Integer id) {
		SessionFactory sessionFactory = HibernateUtil.getSessionFactory();
		Session session = sessionFactory.openSession();
		try {
			Transaction transaction = session.beginTransaction();
			Member member = session.get(Member.class,id);
			session.remove(member);
			transaction.commit();
			return member.getId();
		} catch (Exception e) {
			e.printStackTrace();
			session.getTransaction().rollback();
			return null;
		}
	}
	
	public Integer updateById(Member newMember) {
		SessionFactory sessionFactory = HibernateUtil.getSessionFactory();
		Session session = sessionFactory.openSession();
		try {
			Transaction transaction = session.beginTransaction();
			Member oldMember = session.get(Member.class,newMember.getId());
			//若有傳值才傳給oldMember
			if (newMember.getPass() != null) {
				oldMember.setPass(newMember.getPass());
			}
			if(newMember.getRoleId() != null) {
				oldMember.setRoleId(newMember.getRoleId());
			}
			
			transaction.commit();
			return 1;
		} catch (Exception e) {
			e.printStackTrace();
			session.getTransaction().rollback();
			return -1;
		}
	}
	
	public Member selectById(Integer id) {
		SessionFactory sessionFactory = HibernateUtil.getSessionFactory();
		Session session = sessionFactory.openSession();
		try {
			Transaction transaction = session.beginTransaction();
			Member member = session.get(Member.class,id);		
			transaction.commit();
			return member;
		} catch (Exception e) {
			e.printStackTrace();
			session.getTransaction().rollback();
			return null;
		}
	}
	
	public List<Member> selectAll() {
		SessionFactory sessionFactory = HibernateUtil.getSessionFactory();
		Session session = sessionFactory.openSession();
		try {
			Transaction transaction = session.beginTransaction();
			Query<Member> query =session.createQuery("SELECT new web.member.pojo.Member(username,nickname)FROM MEMBER",Member.class);
			List<Member> list = query.getResultList();
	
			
			transaction.commit();
			return list;
		} catch (Exception e) {
			e.printStackTrace();
			session.getTransaction().rollback();
			return null;
		}
		
		
	}
}
