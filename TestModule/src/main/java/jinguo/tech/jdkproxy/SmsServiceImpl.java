package jinguo.tech.jdkproxy;

/**
 * @author jinguo
 */
public class SmsServiceImpl implements SmsService{

    @Override
    public String sendMessage(String message) {
        System.out.println(message);
        return "res:"+message;
    }
}
