package by.chupilin.web.guestbook.dao.impl;

import by.chupilin.web.guestbook.dao.MessageDAO;
import by.chupilin.web.guestbook.models.impl.Message;
import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.criterion.Order;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class MessageDAOImpl extends ItemDAOImpl implements MessageDAO {

    public void insert(Message message) {
        currentSession().save(message);
        log.info("Message inserted successfully, message id = " + message.getId());
    }

    @SuppressWarnings("unchecked")
    public List<Message> getAll() {
        Criteria criteria = currentSession().createCriteria(Message.class);
        List<Message> messages = criteria.addOrder(Order.desc("dateCreate")).list();
        log.info("Messages loaded successfully, number of messages = " + messages.size());
        return messages;
    }

    public Message getById(Long id) {
        Session session = currentSession();
        Message message = (Message) session.get(Message.class, id);
        log.info("Message loaded successfully, message id = " + id);
        return message;
    }

    public void update(Message message) {
        Session session = currentSession();
        session.update(message);
        session.flush();
        log.info("Message updated successfully, message id = " + message.getId());
    }

    public void deleteById(Long id) {
        Session session = currentSession();
        Message message = (Message) session.get(Message.class, id);
        if (message != null) {
            session.delete(message);
            session.flush();
            log.info("Message deleted successfully, message id = " + id);
        }
    }

}