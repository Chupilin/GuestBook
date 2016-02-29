package by.chupilin.web.guestbook.controllers;

import by.chupilin.web.guestbook.models.impl.Message;
import by.chupilin.web.guestbook.models.validators.MessageValidator;
import by.chupilin.web.guestbook.services.MessageService;
import by.chupilin.web.guestbook.utils.RandomStringUtil;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.mockito.invocation.InvocationOnMock;
import org.mockito.stubbing.Answer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mock.web.MockHttpServletRequest;
import org.springframework.test.util.ReflectionTestUtils;
import org.springframework.ui.ExtendedModelMap;
import org.springframework.validation.BeanPropertyBindingResult;
import org.springframework.validation.BindingResult;
import org.springframework.validation.ValidationUtils;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import org.springframework.web.servlet.mvc.support.RedirectAttributesModelMap;

import java.sql.Date;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.*;
import static org.mockito.Matchers.any;
import static org.mockito.Mockito.*;


public class MessageControllerTest extends AbstractControllerTest {

    @Autowired
    private MessageValidator messageValidator;

    private MessageService messageService;
    private List<Message> messages;         // Imitation DB
    private Message message;
    private Message messageForUpdeting;

    @Before
    public void setUp() {
        messageService = mock(MessageService.class);
        messages = new ArrayList<>();
        String textCorrectly = RandomStringUtil.generate(messageValidator.getTextMinLength());
        String authorCorrectly = RandomStringUtil.generate(messageValidator.getAuthorMinLength());
        message = new Message(1L, authorCorrectly, textCorrectly);
        messages.add(message);
    }

    @After
    public void tearDown() throws Exception {
        messages.clear();
    }


    // Test show all message
    @Test
    public void testGetMessages() {
        when(messageService.getAll()).thenReturn(messages);
        MessageController messageController = new MessageController();
        ReflectionTestUtils.setField(messageController, "messageService", messageService);
        ExtendedModelMap uiModel = new ExtendedModelMap();

        String viewName = messageController.getMessages(uiModel);
        assertNotNull("View is null", viewName);
        assertEquals("View name is fail", "messages", viewName);

        List<Message> messageList = (List<Message>) uiModel.get("messageList");
        assertNotNull("Attribute is null", messageList);
        assertEquals("Attribute size is fail", 1, messageList.size());
    }


    @Test
    // Testing empty form for new message
    public void testCreateMessage() {
        MessageController messageController = new MessageController();
        ReflectionTestUtils.setField(messageController, "messageService", messageService);
        ExtendedModelMap uiModel = new ExtendedModelMap();
        String viewName = messageController.createMessage(uiModel);
        assertNotNull("View is null", viewName);
        assertEquals("View name is fail", "view-message", viewName);

        Message messageAttribute = (Message) uiModel.get("message");
        assertNotNull("Attribute is null", messageAttribute);

        assertNull("Author not null", messageAttribute.getAuthor());
        assertNull("Text not null", messageAttribute.getText());

        Date dateNow = new Date(System.currentTimeMillis());
        String expectedDateCreate = dateNow.toString();
        assertEquals("Date create is fail",
                expectedDateCreate,
                messageAttribute.getDateCreate().toString());
    }


    // Test creating correctly message
    @Test
    public void testPostMessage_successfulValidation() {
        doPostMessage(message, "redirect:/messages");
        assertEquals(2, messages.size());
    }

    // Test creating incorrect message
    @Test
    public void testPostMessage_authorEmptyLengthFailValidation() {
        message.setAuthor("");
        doPostMessage(message, "view-message");
        assertEquals(1, messages.size());
    }

    // Test creating incorrect message
    @Test
    public void testPostMessage_authorShortLengthFailValidation() {
        String authorShort = RandomStringUtil.generate(messageValidator.getAuthorMinLength() - 1);
        message.setAuthor(authorShort);
        doPostMessage(message, "view-message");
        assertEquals(1, messages.size());
    }

    // Test creating incorrect message
    @Test
    public void testPostMessage_authorLongLengthFailValidation() {
        String authorLong = RandomStringUtil.generate(messageValidator.getAuthorMaxLength() + 1);
        message.setAuthor(authorLong);
        doPostMessage(message, "view-message");
        assertEquals(1, messages.size());
    }

    // Test creating incorrect message
    @Test
    public void testPostMessage_textEmptyLengthFailValidation() {
        message.setText("");
        doPostMessage(message, "view-message");
        assertEquals(1, messages.size());
    }

    // Test creating incorrect message
    @Test
    public void testPostMessage_textShortLengthFailValidation() {
        String textShort = RandomStringUtil.generate(messageValidator.getTextMinLength() - 1);
        message.setText(textShort);
        doPostMessage(message, "view-message");
        assertEquals(1, messages.size());
    }

    // Test creating incorrect message
    @Test
    public void testPostMessage_textLongLengthFailValidation() {
        String textLong = RandomStringUtil.generate(messageValidator.getTextMaxLength() + 1);
        message.setText(textLong);
        doPostMessage(message, "view-message");
        assertEquals(1, messages.size());
    }


    // Testing completed form for created message (Message is existing)
    @Test
    public void testGetMessage_existingMessage() {

        doAnswer(new Answer<Message>() {
            public Message answer(InvocationOnMock invocation) {
                Long idSearchMessage = (Long) invocation.getArguments()[0];
                for (Message message : messages) {
                    if (message.getId().equals(idSearchMessage)) {
                        return message;
                    }
                }
                return null;
            }
        }).when(messageService).getById(any(Long.class));

        MessageController messageController = new MessageController();
        ReflectionTestUtils.setField(messageController, "messageService", messageService);
        ExtendedModelMap uiModel = new ExtendedModelMap();
        String viewName = messageController.getMessage(1L, uiModel);
        assertNotNull("View is null", viewName);
        assertEquals("View name is fail", "view-message", viewName);

        Message messageAttribute = (Message) uiModel.get("message");
        assertNotNull("Attribute is null", messageAttribute);
        assertEquals("Attribute not equals needed object", message, messageAttribute);
    }

    // Testing completed form for created message (Message is not existing)
    @Test(expected = IllegalArgumentException.class)
    public void testGetMessage_notExistingMessage() {

        doAnswer(new Answer<Message>() {
            public Message answer(InvocationOnMock invocation) {
                Long idSearchMessage = (Long) invocation.getArguments()[0];
                for (Message message : messages) {
                    if (message.getId().equals(idSearchMessage)) {
                        return message;
                    }
                }
                return null;
            }
        }).when(messageService).getById(any(Long.class));

        messages.clear();

        MessageController messageController = new MessageController();
        ReflectionTestUtils.setField(messageController, "messageService", messageService);
        ExtendedModelMap uiModel = new ExtendedModelMap();
        messageController.getMessage(1L, uiModel);
    }


    // Test updating correctly message
    @Test
    public void testPutMessage_successfulValidation() {
        setUpMessageForUpdeting();
        doPutMessage(messageForUpdeting, "redirect:/messages");
        assertEquals("author was n't updated", messageForUpdeting.getAuthor(), messages.get(0).getAuthor());
        assertEquals("text was n't updated", messageForUpdeting.getText(), messages.get(0).getText());
        assertEquals("dateCreate was n't updated",
                messageForUpdeting.getDateCreate().toString(), messages.get(0).getDateCreate().toString());
    }

    // Test updating incorrect message
    @Test
    public void testPutMessage_authorEmptyLengthFailValidation() {
        setUpMessageForUpdeting();
        messageForUpdeting.setAuthor("");
        doPutMessage(messageForUpdeting, "view-message");
        assertNotEquals("author was updated", messageForUpdeting.getAuthor(), messages.get(0).getAuthor());
        assertNotEquals("text was updated", messageForUpdeting.getText(), messages.get(0).getText());
        assertNotEquals("dateCreate was updated",
                messageForUpdeting.getDateCreate().toString(), messages.get(0).getDateCreate().toString());
    }

    // Test updating incorrect message
    @Test
    public void testPutMessage_textEmptyLengthFailValidation() {
        setUpMessageForUpdeting();
        messageForUpdeting.setText("");
        doPutMessage(messageForUpdeting, "view-message");
        assertNotEquals("author was updated", messageForUpdeting.getAuthor(), messages.get(0).getAuthor());
        assertNotEquals("text was updated", messageForUpdeting.getText(), messages.get(0).getText());
        assertNotEquals("dateCreate was updated",
                messageForUpdeting.getDateCreate().toString(), messages.get(0).getDateCreate().toString());
    }

    // Test updating incorrect message
    @Test
    public void testPutMessage_authorMinLengthFailValidation() {
        setUpMessageForUpdeting();
        String authorShort = RandomStringUtil.generate(messageValidator.getAuthorMinLength() - 1);
        messageForUpdeting.setAuthor(authorShort);
        doPutMessage(messageForUpdeting, "view-message");
        assertNotEquals("author was updated", messageForUpdeting.getAuthor(), messages.get(0).getAuthor());
        assertNotEquals("text was updated", messageForUpdeting.getText(), messages.get(0).getText());
        assertNotEquals("dateCreate was updated",
                messageForUpdeting.getDateCreate().toString(), messages.get(0).getDateCreate().toString());
    }

    // Test updating incorrect message
    @Test
    public void testPutMessage_textMinLengthFailValidation() {
        setUpMessageForUpdeting();
        String textShort = RandomStringUtil.generate(messageValidator.getTextMinLength() - 1);
        messageForUpdeting.setText(textShort);
        doPutMessage(messageForUpdeting, "view-message");
        assertNotEquals("author was updated", messageForUpdeting.getAuthor(), messages.get(0).getAuthor());
        assertNotEquals("text was updated", messageForUpdeting.getText(), messages.get(0).getText());
        assertNotEquals("dateCreate was updated",
                messageForUpdeting.getDateCreate().toString(), messages.get(0).getDateCreate().toString());
    }

    // Test updating incorrect message
    @Test
    public void testPutMessage_authorMaxLengthFailValidation() {
        setUpMessageForUpdeting();
        String authorLong = RandomStringUtil.generate(messageValidator.getAuthorMaxLength() + 1);
        messageForUpdeting.setAuthor(authorLong);
        doPutMessage(messageForUpdeting, "view-message");
        assertNotEquals("author was updated", messageForUpdeting.getAuthor(), messages.get(0).getAuthor());
        assertNotEquals("text was updated", messageForUpdeting.getText(), messages.get(0).getText());
        assertNotEquals("dateCreate was updated",
                messageForUpdeting.getDateCreate().toString(), messages.get(0).getDateCreate().toString());
    }

    // Test updating incorrect message
    @Test
    public void testPutMessage_textMaxLengthFailValidation() {
        setUpMessageForUpdeting();
        String textLong = RandomStringUtil.generate(messageValidator.getTextMaxLength() + 1);
        messageForUpdeting.setText(textLong);
        doPutMessage(messageForUpdeting, "view-message");
        assertNotEquals("author was updated", messageForUpdeting.getAuthor(), messages.get(0).getAuthor());
        assertNotEquals("text was updated", messageForUpdeting.getText(), messages.get(0).getText());
        assertNotEquals("dateCreate was updated",
                messageForUpdeting.getDateCreate().toString(), messages.get(0).getDateCreate().toString());
    }


    @Test
    public void testDeleteMessage() {

        doAnswer(new Answer<Void>() {
            public Void answer(InvocationOnMock invocation) {
                Long idDeleteElement = (Long) invocation.getArguments()[0];
                for (int i = 0; i < messages.size(); i++) {
                    if (messages.get(i).getId().equals(idDeleteElement)) {
                        messages.remove(i);
                    }
                }
                return null;
            }
        }).when(messageService).deleteById(any(Long.class));

        MessageController messageController = new MessageController();
        ReflectionTestUtils.setField(messageController, "messageService", messageService);
        RedirectAttributes redirectAttributes = new RedirectAttributesModelMap();
        String viewName = messageController.deleteMessage(1L, redirectAttributes);
        assertNotNull("View is null", viewName);
        assertEquals("View name is fail", "redirect:/messages", viewName);
        assertEquals("Size messages is fail", 0, messages.size());
    }


    private void doPostMessage(Message message, String expectedViewName) {

        doAnswer(new Answer<Void>() {
            public Void answer(InvocationOnMock invocation) {
                Message message = (Message) invocation.getArguments()[0];
                messages.add(message);
                return null;
            }
        }).when(messageService).insert(any(Message.class));

        MessageController messageController = new MessageController();
        ReflectionTestUtils.setField(messageController, "messageService", messageService);
        ReflectionTestUtils.setField(messageController, "messageValidator", messageValidator);

        BindingResult result = new BeanPropertyBindingResult(message, "message");
        messageValidator.validate(message, result);

        RedirectAttributes redirectAttributes = new RedirectAttributesModelMap();
        String actualViewName = messageController.postMessage(message, result, redirectAttributes);
        assertNotNull("View is null", actualViewName);
        assertEquals("View name is fail", expectedViewName, actualViewName);
    }

    private void doPutMessage(Message messageForUpdeting, String expectedViewName) {

        doAnswer(new Answer<Void>() {
            public Void answer(InvocationOnMock invocation) {
                Message updateMessage = (Message) invocation.getArguments()[0];
                for (int i = 0; i < messages.size(); i++) {
                    if (messages.get(i).getId().equals(updateMessage.getId())) {
                        messages.set(i, updateMessage);
                    }
                }
                return null;
            }
        }).when(messageService).update(any(Message.class));

        MessageController messageController = new MessageController();
        ReflectionTestUtils.setField(messageController, "messageService", messageService);
        ReflectionTestUtils.setField(messageController, "messageValidator", messageValidator);

        BindingResult result = new BeanPropertyBindingResult(messageForUpdeting, "message");
        messageValidator.validate(message, result);

        RedirectAttributes redirectAttributes = new RedirectAttributesModelMap();
        String actualViewName = messageController.putMessage(messageForUpdeting, result, redirectAttributes);
        assertNotNull("View is null", actualViewName);
        assertEquals("View name is fail", expectedViewName, actualViewName);
        assertEquals("Changed number messages when do PUT method (update message)", messages.size(), 1);
        assertEquals(message, messageForUpdeting);
    }

    // Setup new correctly message for updating
    private void setUpMessageForUpdeting() {
        String newCorrectlyAuthor = RandomStringUtil.generate(messageValidator.getAuthorMinLength());
        String newCorrectlyText = RandomStringUtil.generate(messageValidator.getTextMinLength());
        messageForUpdeting = new Message(message.getId(), newCorrectlyAuthor, newCorrectlyText);
        //Set yesterday
        messageForUpdeting.setDateCreate(new Date(System.currentTimeMillis() - 1000 * 60 * 60 * 24));
    }

}
