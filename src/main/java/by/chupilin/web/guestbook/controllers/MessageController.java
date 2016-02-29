package by.chupilin.web.guestbook.controllers;

import by.chupilin.web.guestbook.models.impl.Message;
import by.chupilin.web.guestbook.models.validators.MessageValidator;
import by.chupilin.web.guestbook.services.MessageService;
import org.apache.log4j.Logger;
import org.springframework.beans.PropertyAccessException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.propertyeditors.CustomDateEditor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.DefaultBindingErrorProcessor;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.validation.Valid;
import java.sql.Date;
import java.text.DateFormat;
import java.text.SimpleDateFormat;

@Controller
public class MessageController {

    private static Logger log = Logger.getLogger(MessageController.class.getName());

    @Autowired
    private MessageValidator messageValidator;

    @Autowired
    private MessageService messageService;

    // Show all messages
    @RequestMapping(value = {"", "/", "/messages"}, method = RequestMethod.GET)
    public String getMessages(Model model) {
        if (log.isTraceEnabled()) {
            log.trace("Move to page /messages (Show all messages)");
        }
        model.addAttribute(messageService.getAll());
        return "messages";
    }

    // Show empty form for new message
    @RequestMapping(value = "/message", method = RequestMethod.GET)
    public String createMessage(Model model) {
        if (log.isTraceEnabled()) {
            log.trace("Move to page /message (New message form)");
        }
        model.addAttribute(new Message());
        return "view-message";
    }

    // Show completed form for created message
    @RequestMapping(value = "/message/{id}", method = RequestMethod.GET)
    public String getMessage(@PathVariable long id, Model model) {
        if (log.isTraceEnabled()) {
            log.trace("Move to page /message/" + id + " (Created message form)");
        }
        model.addAttribute(messageService.getById(id));
        return "view-message";
    }

    // Create message
    @RequestMapping(value = "/message", method = RequestMethod.POST)
    public String postMessage(@Valid Message message, BindingResult result,
                              final RedirectAttributes redirectAttributes) {
        if (log.isTraceEnabled()) {
            log.trace("Click button for \"Create new message\"");
        }
        if (result.hasErrors()) {
            log.warn("Validation \"Create new message\" is fail");
            return "view-message";
        }
        log.info("Validation \"Create new message\" is successful");
        messageService.insert(message);
        redirectAttributes.addFlashAttribute("isSend", new Boolean(true));
        return "redirect:/messages";
    }

    // Update message
    @RequestMapping(value = "/message/{id}", method = RequestMethod.PUT)
    public String putMessage(@Valid Message message, BindingResult result,
                             final RedirectAttributes redirectAttributes) {
        if (log.isTraceEnabled()) {
            log.trace("Click button for \"Update created message\"");
        }
        if (result.hasErrors()) {
            log.warn("Validation \"Update created message\" is fail");
            return "view-message";
        }
        log.info("Validation \"Update created message\" is successful");
        messageService.update(message);
        redirectAttributes.addFlashAttribute("isUpdate", new Boolean(true));
        return "redirect:/messages";
    }

    // Delete message
    @RequestMapping(value = "/message/{id}", method = RequestMethod.DELETE)
    public String deleteMessage(@PathVariable long id,
                                final RedirectAttributes redirectAttributes) {
        if (log.isTraceEnabled()) {
            log.trace("Click button \"Delete\"");
        }
        messageService.deleteById(id);
        redirectAttributes.addFlashAttribute("isDelete", new Boolean(true));
        return "redirect:/messages";
    }


    @InitBinder("message")
    public void initBinder(WebDataBinder binder) {
        binder.addValidators(messageValidator);
        DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        dateFormat.setLenient(false);
        binder.registerCustomEditor(Date.class, new CustomDateEditor(dateFormat, false));
        binder.setBindingErrorProcessor(new DefaultBindingErrorProcessor() {
            @Override
            public void processPropertyAccessException(PropertyAccessException ex, BindingResult bindingResult) {
                String propertyName = ex.getPropertyName();
                Object value = ex.getValue();
                bindingResult.addError(new FieldError(bindingResult.getObjectName(), propertyName, value, true,
                        new String[]{"date.format.error"}, new Object[]{propertyName, value},
                        ex.getLocalizedMessage()));
            }
        });
    }

}