/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package util;

/**
 *
 * @author torsello
 */
public class EmailProcessorThread extends Thread {
    private EmailSender sender=new EmailSender();
    
    public EmailProcessorThread()
    {
        sender.setServer("smtp.wifinetcom.net");
        sender.setUser("ticket@wifinetcom.net");
        sender.setPassword("ticketwifinetcom");
        sender.setFrom("ticket@wifinetcom.net");
    }
    
    
    @Override
    public void run()
    {
        sender.send();
    }

    /**
     * @return the sender
     */
    public EmailSender getSender() {
        return sender;
    }

    /**
     * @param sender the sender to set
     */
    public void setSender(EmailSender sender) {
        this.sender = sender;
    }
}
