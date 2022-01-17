package tech.jinguo.test.spi;

import java.util.Iterator;
import java.util.ServiceLoader;

/**
 * @author jinguo
 */
public class SPITest {
    public static void main(String[] args) {
        ServiceLoader<IRepository> serviceLoader = ServiceLoader.load(IRepository.class);
        Iterator<IRepository> it = serviceLoader.iterator();
        while (it != null && it.hasNext()){
            IRepository demoService = it.next();
            System.out.println("class:" + demoService.getClass().getName());
            demoService.save("tom");
        }
    }
}
