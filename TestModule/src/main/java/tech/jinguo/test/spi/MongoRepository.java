package tech.jinguo.test.spi;

/**
 * @author jinguo
 */
public class MongoRepository implements IRepository{
    @Override
    public void save(String data) {
        System.out.println("Save " + data + " to Mongo");
    }
}
