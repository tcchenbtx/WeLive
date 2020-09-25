package communitymanagement.dao;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import communitymanagement.model.Ticket;


@Repository
public class TicketDao {
	
	@Autowired
	private SessionFactory sessionFactory;
	
	public void addTicket(Ticket ticket) {
		Session session = null;
		try {
			session = sessionFactory.openSession();
			session.beginTransaction();
			session.saveOrUpdate(ticket);
			session.getTransaction().commit();
		} catch (Exception e) {
			e.printStackTrace();
			session.getTransaction().rollback();
		} finally {
			if (session != null) {
				session.close();
			}
		} 
	}
	
	public Ticket getTicketById(int ticketId) {
		Ticket ticket = null;
		try (Session session = sessionFactory.openSession()) {
			session.beginTransaction();
			ticket = (Ticket) session.get(Ticket.class, ticketId);
			session.getTransaction().commit();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return ticket;
	}
	
	public List<Ticket> getAllTicketsByUserId(int userId) {
		List<Ticket> tickets = new ArrayList<>();
		try (Session session = sessionFactory.openSession()) {
			session.beginTransaction();
			CriteriaBuilder criteriaBuilder = session.getCriteriaBuilder();
			CriteriaQuery<Ticket> criteriaQuery = criteriaBuilder.createQuery(Ticket.class);
			Root<Ticket> root = criteriaQuery.from(Ticket.class);
			criteriaQuery.select(root).where(criteriaBuilder.equal(root.get("user_id"), userId));
			tickets = session.createQuery(criteriaQuery).getResultList();
			session.getTransaction().commit();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return tickets;
	}
	
	public void removeTicket(int ticketId) {
		Session session = null;
		try {
			session = sessionFactory.openSession();
			Ticket ticket = (Ticket) session.get(Ticket.class, ticketId);
			session.delete(ticket);
			session.getTransaction().commit();
		} catch (Exception e) {
			e.printStackTrace();
			session.getTransaction().rollback();
		} finally {
			if (session != null) {
				session.close();
			}
		}
	}
}