package com.example.plan_voyage.services.impl;

import com.example.plan_voyage.entity.Trip;
import com.example.plan_voyage.services.EmailService;
import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;

import java.nio.file.Files;
import java.nio.file.Paths;
import java.text.SimpleDateFormat;
import java.time.format.DateTimeFormatter;
import java.util.UUID;

@Service
public class EmailServiceImpl implements EmailService {

    @Autowired
    private JavaMailSender mailSender;

    @Value("${front-end-url}")
    private String FRONT_END_URL;

    @Override
    public void sendInvitationEmail(String email, String subject, String senderName, Trip trip, UUID invitationId, boolean isRegistered) {
        try {
            SimpleDateFormat sdf = new SimpleDateFormat("MMMM dd, yyyy");
            String startDate = sdf.format(trip.getStartDate());
            String endDate = sdf.format(trip.getEndDate());

            String htmlContent = buildTripInviteEmail(senderName,
                                            trip.getDestination(),
                                            startDate,
                                            endDate,
                                    FRONT_END_URL + "/invitation/" + invitationId,
                                            isRegistered
                                );

            MimeMessage message = mailSender.createMimeMessage();
            MimeMessageHelper helper = new MimeMessageHelper(message, true, "UTF-8");

            helper.setFrom("planvoyageinfo@gmail.com");
            helper.setTo(email);
            helper.setSubject(subject);
            helper.setText(htmlContent, true);

            mailSender.send(message);
        } catch (MessagingException e) {
            throw new RuntimeException("Failed to send email", e);
        }
    }

    private String buildTripInviteEmail(String senderName, String destination, String startDate, String endDate, String joinLink, boolean isRegistered) {
        try {
            if(isRegistered) {
                String template = Files.readString(Paths.get("src/main/resources/templates/invite-template.html"));

                return template
                        .replace("${senderName}", senderName)
                        .replace("${destination}", destination)
                        .replace("${startDate}", startDate)
                        .replace("${endDate}", endDate)
                        .replace("${joinLink}", joinLink);
            } else {
                String template = Files.readString(Paths.get("src/main/resources/templates/signup-invitation-template.html"));

                return template
                        .replace("${senderName}", senderName)
                        .replace("${destination}", destination)
                        .replace("${startDate}", startDate)
                        .replace("${endDate}", endDate)
                        .replace("${signUpLink}", joinLink);
            }
        } catch (Exception e) {
            throw new RuntimeException("Failed to load email template", e);
        }
    }
}
