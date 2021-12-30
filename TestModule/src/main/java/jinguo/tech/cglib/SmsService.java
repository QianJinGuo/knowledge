package jinguo.tech.cglib;

/**
 * @author jinguo
 */
public class SmsService {
    public String sendMessage(String message){
        System.out.println(message);
        return "res:"+message;
    }
}
