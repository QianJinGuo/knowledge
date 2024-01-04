# KnowledgeWarehouse
个人知识体系


import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import java.lang.reflect.Field;
import java.lang.reflect.Modifier;
import java.lang.reflect.Type;
import java.util.Map;

public class DynamicClassFromJson {

    public static void main(String[] args) throws Exception {
        String jsonString = "{\"name\":\"John\",\"age\":30,\"email\":\"john@example.com\"}";

        Gson gson = new Gson();
        Type type = new TypeToken<Map<String, Object>>() {}.getType();

        // 将JSON字符串转换为Map对象
        Map<String, Object> jsonMap = gson.fromJson(jsonString, type);

        // 创建一个类名为DynamicClass的动态类
        Class<?> dynamicClass = createDynamicClass("DynamicClass", jsonMap);

        // 使用反射创建该动态类的实例并输出属性值
        Object dynamicObject = dynamicClass.getDeclaredConstructor().newInstance();
        for (Field field : dynamicClass.getDeclaredFields()) {
            field.setAccessible(true);
            System.out.println(field.getName() + ": " + field.get(dynamicObject));
        }
    }

    // 创建动态类的方法
    private static Class<?> createDynamicClass(String className, Map<String, Object> fieldMap) throws Exception {
        ClassLoader classLoader = DynamicClassFromJson.class.getClassLoader();
        Class<?> dynamicClass = Class.forName(className);

        if (dynamicClass == null) {
            dynamicClass = Class.forName("java.lang.Object");
        }

        // 创建类
        sun.misc.ProxyGenerator.generateProxyClass(className, new Class[]{dynamicClass}, Modifier.PUBLIC);

        // 添加属性
        for (Map.Entry<String, Object> entry : fieldMap.entrySet()) {
            Field field = Field.class.getDeclaredConstructor().newInstance();
            field.setAccessible(true);
            field.setName(entry.getKey());
            field.set(dynamicClass, entry.getValue());
        }

        return dynamicClass;
    }
}