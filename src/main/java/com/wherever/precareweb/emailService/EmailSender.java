package com.wherever.precareweb.emailService;

import javax.mail.MessagingException;

import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.MailException;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.scheduling.annotation.Async;

    public class EmailSender  {
        @Autowired
        public JavaMailSender  mailSender;
        
        @Async("threadPoolTaskExecutor")
        public void SendEmail(Email email) throws Exception {
             
            MimeMessage msg = mailSender.createMimeMessage();
            try {
                msg.setSubject(email.getSubject());
                msg.setText(email.getContent());
                msg.setRecipients(MimeMessage.RecipientType.TO , InternetAddress.parse(email.getReceiver()));
               
            }catch(MessagingException e) {
                System.out.println("MessagingException");
                e.printStackTrace();
            }
            try {
                mailSender.send(msg);
            }catch(MailException e) {
                System.out.println("MailException�߻�");
                e.printStackTrace();
            }
        }
}

