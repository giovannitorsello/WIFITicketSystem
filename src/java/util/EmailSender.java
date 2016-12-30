/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package util;

import java.util.Date;
import java.util.Properties;
import javax.activation.DataHandler;
import javax.activation.FileDataSource;
import javax.mail.Message;
import javax.mail.Multipart;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeBodyPart;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.MimeMultipart;

/**
 *
 * @author torsello
 */
public class EmailSender {
    
    private String Server="";
    private String User="";
    private String Password="";
    private String From="";
    private String To="";
    private String Cc="";
    private String Bcc="";
    private String Subject="";
    private String Body="";
    private String FileToAttach="";
    
    
      /**
    * "send" method to send the message.
    */
  public void send()
  {
    try
    {
      Properties props = System.getProperties();
      props.put("mail.smtp.host", Server);
      Session session = Session.getDefaultInstance(props, null);
      Message msg = new MimeMessage(session);
      
      if (!From.isEmpty())      
        msg.setFrom(new InternetAddress(From));
      
      if (!To.isEmpty())
        msg.setRecipients(Message.RecipientType.TO,InternetAddress.parse(To, false));
      
      if (!Cc.isEmpty())
       msg.setRecipients(Message.RecipientType.CC,InternetAddress.parse(Cc, false));
      
      if (!Subject.isEmpty())
        msg.setSubject(Subject);
      
      if (!Subject.isEmpty())
        msg.setText(Body);
      
      if(!FileToAttach.isEmpty())
      {       
         FileDataSource fds = new FileDataSource(FileToAttach);
         MimeBodyPart mbp = new MimeBodyPart();
         mbp.setDataHandler(new DataHandler(fds));
         mbp.setFileName(fds.getName());
         Multipart mp = new MimeMultipart();
         mp.addBodyPart(mbp); 
         msg.setContent(mp);
      }
      
      msg.setHeader("X-Mailer", "Sistema di comunicazrione Wifinetcom");
      msg.setSentDate(new Date());
      
      Transport.send(msg);
      System.out.println("Messaggio inviato a "+To);
    }
    catch (Exception ex)
    {
      ex.printStackTrace();
    }
  }

    /**
     * @return the Server
     */
    public String getServer() {
        return Server;
    }

    /**
     * @param Server the Server to set
     */
    public void setServer(String Server) {
        this.Server = Server;
    }

    /**
     * @return the User
     */
    public String getUser() {
        return User;
    }

    /**
     * @param User the User to set
     */
    public void setUser(String User) {
        this.User = User;
    }

    /**
     * @return the Password
     */
    public String getPassword() {
        return Password;
    }

    /**
     * @param Password the Password to set
     */
    public void setPassword(String Password) {
        this.Password = Password;
    }

    /**
     * @return the From
     */
    public String getFrom() {
        return From;
    }

    /**
     * @param From the From to set
     */
    public void setFrom(String From) {
        this.From = From;
    }

    /**
     * @return the To
     */
    public String getTo() {
        return To;
    }

    /**
     * @param To the To to set
     */
    public void setTo(String To) {
        this.To = To;
    }

    /**
     * @return the FileToAttach
     */
    public String getFileToAttach() {
        return FileToAttach;
    }

    /**
     * @param FileToAttach the FileToAttach to set
     */
    public void setFileToAttach(String FileToAttach) {
        this.FileToAttach = FileToAttach;
    }

    /**
     * @return the Cc
     */
    public String getCc() {
        return Cc;
    }

    /**
     * @param Cc the Cc to set
     */
    public void setCc(String Cc) {
        this.Cc = Cc;
    }

    /**
     * @return the Bcc
     */
    public String getBcc() {
        return Bcc;
    }

    /**
     * @param Bcc the Bcc to set
     */
    public void setBcc(String Bcc) {
        this.Bcc = Bcc;
    }

    /**
     * @return the Subject
     */
    public String getSubject() {
        return Subject;
    }

    /**
     * @param Subject the Subject to set
     */
    public void setSubject(String Subject) {
        this.Subject = Subject;
    }

    /**
     * @return the Body
     */
    public String getBody() {
        return Body;
    }

    /**
     * @param Body the Body to set
     */
    public void setBody(String Body) {
        this.Body = Body;
    }

    
}
