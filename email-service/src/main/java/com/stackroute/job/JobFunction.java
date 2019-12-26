package com.stackroute.job;

import java.nio.charset.StandardCharsets;

import org.quartz.Job;
import org.quartz.JobDataMap;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.mail.MailProperties;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Component;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;


@Component
public class JobFunction implements Job {
    private static final Logger logger = LoggerFactory.getLogger(JobFunction.class);


    private static JavaMailSender mailSender;
    private static MailProperties mailProperties;

    @Autowired
    public JobFunction(JavaMailSender mailSender, MailProperties mailProperties) {
        JobFunction.mailSender = mailSender;
        JobFunction.mailProperties = mailProperties;
    }

    public JobFunction() {
    }

    /**
     * <p>
     * Empty constructor for job initilization
     * </p>
     * <p>
     * Quartz requires a public empty constructor so that the
     * scheduler can instantiate the class whenever it needs.
     * </p>
     */



    //job is executed here
    @Override
    public void execute(JobExecutionContext context) throws JobExecutionException {
        JobDataMap dataMap = context.getJobDetail().getJobDataMap();
        String subject = dataMap.getString("subject");
        String body = dataMap.getString("body");
        String recipientEmail = dataMap.getString("bcc");

        sendMail(mailProperties.getUsername(), recipientEmail, subject, body);

    }
    private void sendMail(String fromEmail, String toEmail, String subject, String body) {
        try {
            logger.info("Sending Email to {}", toEmail);
            MimeMessage message = mailSender.createMimeMessage();

            MimeMessageHelper messageHelper = new MimeMessageHelper(message, StandardCharsets.UTF_8.toString());
            messageHelper.setSubject(subject);
            messageHelper.setText(body);
            messageHelper.setFrom(fromEmail);
            messageHelper.setTo(toEmail);

            mailSender.send(message);
        } catch (MessagingException ex) {
            logger.error("Failed to send email to {}", toEmail);
        }
    }
}
