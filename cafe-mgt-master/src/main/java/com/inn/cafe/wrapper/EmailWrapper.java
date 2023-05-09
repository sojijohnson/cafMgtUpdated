package com.inn.cafe.wrapper;

import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMailMessage;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;

import java.util.List;

@Slf4j
@Service
public class EmailWrapper {
    @Autowired
   private JavaMailSender javaMailSender;
   // @Autowired
  //  private MailConfig mailConfig;


    public void sendSimpleMessage(String to, String subject, String text, List<String>list){

      SimpleMailMessage mail = new SimpleMailMessage();
      mail.setTo(to);
      mail.setSubject(subject);
     mail.setFrom("sojijohnson.aj@gmail.com");
     mail.setText(text);
if (list !=null && list.size()>0)
     mail.setCc(getCc(list));
        log.info("before email");
       javaMailSender.send(mail);
       // mailSender.send(mail);
       // mailConfig.javaMailSender(mail);
    }

    private String[] getCc( List<String> lists){

        String[] cc = new String[lists.size()];
               for (int i=0; i< lists.size(); i++) {
                   cc[i]= lists.get(i);
               }
               log.info("list are {}",cc);
        return cc;
    }

public void forgotPasswordMail(String to,String subject, String password) throws MessagingException {

    //MimeMessage message = javaMailSender.createMimeMessage();
    MimeMessage message = javaMailSender.createMimeMessage();
    MimeMessageHelper helper = new MimeMessageHelper(message);
    helper.setTo(to);
    //helper.setTo(to);
    helper.setFrom("59092782f2e442");
    helper.setSubject(subject);
      String htmpge= "<p><b> Your login password for this app  </b><br><b>EMAIL:</b> "+ to +" <br><b>Password: </b>" + password+"" +
              "" +
              "" +
              "</p>";
      message.setContent(htmpge,"text/html");
     // helper.setText(htmpge);

      log.info("send to {}", to);
      javaMailSender.send(message);



}

}
