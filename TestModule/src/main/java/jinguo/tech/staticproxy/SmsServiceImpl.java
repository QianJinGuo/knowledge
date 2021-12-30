package jinguo.tech.staticproxy;

/**
 * @author jinguo
 */
public class SmsServiceImpl implements SmsService {

    @Override
    public String sendMessage(String message) {
        System.out.println(message);
        return "res:"+message;
    }
}
