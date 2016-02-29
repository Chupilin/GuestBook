package by.chupilin.web.guestbook.services.impl;

import by.chupilin.web.guestbook.dao.MessageDAO;
import by.chupilin.web.guestbook.models.impl.Message;
import by.chupilin.web.guestbook.services.MessageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class MessageServiceImpl implements MessageService {

    @Autowired
    private MessageDAO messageDAO;

    @Override
    @Transactional
    public void insert(Message message) {
        messageDAO.insert(message);
    }

    @Override
    @Transactional
    public Message getById(Long id) {
        return messageDAO.getById(id);
    }

    @Override
    @Transactional
    public List<Message> getAll() {
        return messageDAO.getAll();
    }

    @Override
    @Transactional
    public void update(Message message) {
        messageDAO.update(message);
    }

    @Override
    @Transactional
    public void deleteById(Long id) {
        messageDAO.deleteById(id);
    }

}
