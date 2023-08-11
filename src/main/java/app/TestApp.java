package app;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.List;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import javax.sql.DataSource;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.query.Query;
import org.springframework.beans.factory.xml.XmlBeanDefinitionReader;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.context.support.GenericApplicationContext;

import core.util.HibernateUtil;
import web.emp.entity.Dept;
import web.emp.entity.Emp;
import web.member.dao.MemberDao;
import web.member.entity.Member;
import web.member.service.impl.MemberServiceImpl;


public class TestApp {
	public static void main(String[] args) {
//		GenericApplicationContext applicationContext = new GenericApplicationContext();
//        new XmlBeanDefinitionReader(applicationContext).loadBeanDefinitions("applicationContext.xml");
//        applicationContext.refresh();
//        
//        MemberDao memberDao = applicationContext.getBean(MemberDao.class);
//        System.out.println(memberDao.selectById(1).getNickname());
//            
//        ((ConfigurableApplicationContext) applicationContext).close();
		
		
		
//		//Spring xml
//		ApplicationContext applicationContext = new ClassPathXmlApplicationContext("applicationContext.xml");
//		DataSource dataSource = applicationContext.getBean(DataSource.class);
//		
//		try(Connection con = dataSource.getConnection();
//			PreparedStatement pre = con.prepareStatement("select * from MEMBER where ID=?");
//			) {
//			pre.setInt(1, 2);
//			try(ResultSet rs = pre.executeQuery()){
//				if(rs.next()) {
//					System.out.println(rs.getString("Nickname"));
//				}
//				
//			}
//			
//		}catch (Exception e) {
//			e.printStackTrace();
//		}
		
		
		
		//Hibernate
//		SessionFactory sessionFactory = HibernateUtil.getSessionFactory();
//		Session session = sessionFactory.openSession();
		//單向一對N
//		Dept dept = session.get(Dept.class, 30);
//		var emps = dept.getEmps();
//		for (var emp:emps) {
//			System.out.println(emp.getEname());
		//單向一對N
//		Emp emp = session.get(Emp.class, 7369);
//		Dept dept= emp.getDept();
//		System.out.println(dept.getDeptno());
//		System.out.println(dept.getDname());
		
		//雙向
//		Emp emp = session.get(Emp.class,7369);
//		Dept dept = emp.getDept();
//		List<Emp> emps=dept.getEmps();
//		for (Emp tmp : emps) {
//			System.out.println(tmp.getEname());
//		}
		//註冊
//		MemberServiceImpl memImpl = new MemberServiceImpl();
//		Member mem = new Mem
//		memImpl.register(null)
		
		
		
		}
		
		
		
		
		//select
//		CriteriaBuilder criteriaBuilder = session.getCriteriaBuilder();
//		CriteriaQuery<Member> criteriaQuery = criteriaBuilder.createQuery(Member.class);
//		//from MEMBER
//		Root<Member> root = criteriaQuery.from(Member.class);
//		//where USERNAME = ? and PASSWORD= ?
//		criteriaQuery.where(criteriaBuilder.and(
//			criteriaBuilder.equal(root.get("username"), "admin"),
//			criteriaBuilder.equal(root.get("password"),"P@ssw0rd")
//		));
//		
//		//select username nickname
//		criteriaQuery.multiselect(root.get("username"),root.get("nickname"));
//		//加入orderby
//		criteriaQuery.orderBy(criteriaBuilder.asc(root.get("id")));
//		
//		Member member = session.createQuery(criteriaQuery).uniqueResult();
//		System.out.println(member.getNickname());
//		
		
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
