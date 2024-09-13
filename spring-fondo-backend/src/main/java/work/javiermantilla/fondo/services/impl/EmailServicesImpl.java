package work.javiermantilla.fondo.services.impl;

import java.io.ByteArrayOutputStream;
import java.nio.ByteBuffer;
import java.util.Properties;

import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import lombok.extern.log4j.Log4j2;
import software.amazon.awssdk.awscore.AwsRequestOverrideConfiguration;
import software.amazon.awssdk.core.SdkBytes;
import software.amazon.awssdk.services.ses.SesClient;
import software.amazon.awssdk.services.ses.model.RawMessage;
import software.amazon.awssdk.services.ses.model.SendRawEmailRequest;
import software.amazon.awssdk.services.ses.model.SesException;
import work.javiermantilla.fondo.dto.FondoDTO;
import work.javiermantilla.fondo.security.ContextSession;
import work.javiermantilla.fondo.security.dto.UserContextSessionDTO;
import work.javiermantilla.fondo.services.EmailServices;
import javax.mail.Message;
import javax.mail.Session;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeBodyPart;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.MimeMultipart;

@Service
@RequiredArgsConstructor
@Log4j2
public class EmailServicesImpl implements EmailServices {

	private final SesClient sesClient;
	private final ContextSession contextSession;
	
	@Override
	@SneakyThrows
	@Async
	public boolean sendEmail(FondoDTO fondo) {		
		  
		UserContextSessionDTO usuario = this.contextSession.getContextSessionThread();
        String bodyText = "Sr(a) " + usuario.getName() + "\r\n Gracias por inscribir el fondo: " + fondo.getNombre();
        String bodyHTML = "<html>" + "<head></head>" + "<body>" + "<h1>Sr(a) " + usuario.getName() +" </h1>"
                + "<p> Gracias por inscribirse al fondo: " +fondo.getNombre() +"</p>" + "</body>" + "</html>";
        
        Session session = Session.getDefaultInstance(new Properties());
        MimeMessage message = new MimeMessage(session);

        // Add subject, from and to lines.
        message.setSubject("Incripcion Fondo", "UTF-8");
        message.setFrom(new InternetAddress("jmantillap@gmail.com"));
        message.setRecipients(Message.RecipientType.TO, InternetAddress.parse(usuario.getEmail()));

        // Create a multipart/alternative child container.
        MimeMultipart msgBody = new MimeMultipart("alternative");

        // Create a wrapper for the HTML and text parts.
        MimeBodyPart wrap = new MimeBodyPart();
        // Define the text part.
        MimeBodyPart textPart = new MimeBodyPart();
        textPart.setContent(bodyText, "text/plain; charset=UTF-8");
        // Define the HTML part.
        MimeBodyPart htmlPart = new MimeBodyPart();
        htmlPart.setContent(bodyHTML, "text/html; charset=UTF-8");
        // Add the text and HTML parts to the child container.
        msgBody.addBodyPart(textPart);
        msgBody.addBodyPart(htmlPart);
        // Add the child container to the wrapper object.
        wrap.setContent(msgBody);
        // Create a multipart/mixed parent container.
        MimeMultipart msg = new MimeMultipart("mixed");
        // Add the parent container to the message.
        message.setContent(msg);
        // Add the multipart/alternative part to the message.
        msg.addBodyPart(wrap);
        
        try {
            log.info("Attempting to send an email through Amazon SES " + "using the AWS SDK for Java...");
            ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
            message.writeTo(outputStream);
            ByteBuffer buf = ByteBuffer.wrap(outputStream.toByteArray());

            byte[] arr = new byte[buf.remaining()];
            buf.get(arr);

            SdkBytes data = SdkBytes.fromByteArray(arr);
            RawMessage rawMessage = RawMessage.builder()
                    .data(data)
                    .build();

            AwsRequestOverrideConfiguration myConf = AwsRequestOverrideConfiguration.builder()
                    .build();

            SendRawEmailRequest rawEmailRequest = SendRawEmailRequest.builder()
                    .rawMessage(rawMessage)
                    .overrideConfiguration(myConf)
                    .build();

            sesClient.sendRawEmail(rawEmailRequest);
            
            log.info("Email message Sent");

        } catch (SesException e) {
            log.error(e.awsErrorDetails().errorMessage());
            return false;
        } catch (Exception e1) {
        	log.error(e1.getMessage(),e1);
        	return false;
        }        
		return true;
	}

	
}
