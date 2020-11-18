package com.jiwell.mail.utils;

import javax.mail.*;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import java.util.Date;
import java.util.Properties;
import java.util.logging.Level;
import java.util.logging.Logger;

public class MailUtils {

    private static final Logger LOGGER = Logger.getAnonymousLogger();

    private static final String SERVIDOR_SMTP = "smtp.gmail.com";
    private static final int PORTA_SERVIDOR_SMTP = 587;
    private static final String CONTA_PADRAO = "xxxx@gmail.com";
    private static final String SENHA_CONTA_PADRAO = "xxxx";

    private final String from = "xxxx@gmail.com";
    private final String to = "xxxx@sleepace.net";

    private final String subject = "Teste";
    private final String messageContent = "Teste de Mensagem";

    public void sendEmail() {
        final Session session = Session.getInstance(this.getEmailProperties(), new Authenticator() {
            @Override
            protected PasswordAuthentication getPasswordAuthentication() {
                return new PasswordAuthentication(CONTA_PADRAO, SENHA_CONTA_PADRAO);
            }
        });

        try {
            final Message message = new MimeMessage(session);
            message.setRecipient(Message.RecipientType.TO, new InternetAddress(to));
            message.setFrom(new InternetAddress(from));
            message.setSubject(subject);
            message.setText(messageContent);
            message.setSentDate(new Date());
            Transport.send(message);
        } catch (final MessagingException ex) {
            LOGGER.log(Level.WARNING, "Erro ao enviar mensagem: " + ex.getMessage(), ex);
        }
    }

    public Properties getEmailProperties() {
        final Properties config = new Properties();
        config.put("mail.smtp.auth", "true");
        config.put("mail.smtp.starttls.enable", "true");
        config.put("mail.smtp.host", SERVIDOR_SMTP);
        config.put("mail.smtp.port", PORTA_SERVIDOR_SMTP);
        //这个参数会把和邮箱服务器的交互过程都打印出来
        config.put("mail.debug", "true");
        return config;
    }

}
