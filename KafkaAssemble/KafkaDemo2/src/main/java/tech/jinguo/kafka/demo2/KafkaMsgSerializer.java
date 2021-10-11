/*
 * Copyright: 2021 jinguo.tech All rights reserved.
 * 注意：本内容仅限于车巴达公司内部传阅，禁止外泄以及用于其他的商业目的
 *
 * @Project: knowledge
 * @File: KafkaMsgSerializer
 * @Package: tech.jinguo.kafka.demo2
 * @Date: 2021/10/11 16:37
 * @Author: by jinguo.qian
 *
 */
package tech.jinguo.kafka.demo2;

import com.alibaba.fastjson.JSON;
import org.apache.kafka.common.errors.SerializationException;
import org.apache.kafka.common.serialization.Serializer;

import java.io.UnsupportedEncodingException;
import java.util.Map;

/**
 * @author jinguo.qian
 * @title: KafkaMsgSerializer
 * @projectName knowledge
 * @description: 序列化器
 * @date 2021/10/11 16:37
 */
public class KafkaMsgSerializer implements Serializer<PurchaseKey> {
    private String encoding = "UTF8";

    @Override
    public void configure(Map<String, ?> configs, boolean isKey) {
        String propertyName = isKey ? "key.serializer.encoding" : "value.serializer.encoding";
        Object encodingValue = configs.get(propertyName);
        if (encodingValue == null) {
            encodingValue = configs.get("serializer.encoding");
        }

        if (encodingValue instanceof String) {
            this.encoding = (String) encodingValue;
        }
    }

    @Override
    public byte[] serialize(String topic, PurchaseKey data) {
        try {
            if (data == null) {
                return null;
            }
            return JSON.toJSONString(data).getBytes(this.encoding);
        } catch (UnsupportedEncodingException var4) {
            throw new SerializationException("Error when serializing string to byte[] due to unsupported encoding " + this.encoding);
        }
    }
}
    