package com.amigoscode.contact;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1")
public class ContactController {

    @Autowired
    private EmailService emailService;

    @PostMapping("/send-email")
    public String sendEmail(@RequestBody ContactForm contactForm) {
        String subject = "Message from " + contactForm.getName();
        String text = "Message: " + contactForm.getMessage() + "\n\nFrom: " + contactForm.getEmail();
        emailService.sendSimpleMessage("spartaklysman@gmail.com", subject, text); 
        return "Message sent successfully!";
    }
}
