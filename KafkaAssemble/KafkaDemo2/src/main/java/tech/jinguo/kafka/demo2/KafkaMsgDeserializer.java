/*
 * Copyright: 2021 jinguo.tech All rights reserved.
 * 注意：本内容仅限于jinguo.tech内部传阅，禁止外泄以及用于其他的商业目的
 *
 * @Project: knowledge
 * @File: KafkaMsgDeserializer
 * @Package: tech.jinguo.kafka.demo2
 * @Date: 2021/10/11 16:48
 * @Author: by jinguo.qian
 *
 */
package tech.jinguo.kafka.demo2;

import com.alibaba.fastjson.JSON;
import org.apache.kafka.common.errors.SerializationException;
import org.apache.kafka.common.serialization.Deserializer;

import java.io.UnsupportedEncodingException;
import java.util.Map;

/**
 * @author jinguo.qian
 * @title: KafkaMsgDeserializer
 * @projectName knowledge
 * @description: 反序列化器
 * @date 2021/10/11 16:48
 */
public class KafkaMsgDeserializer implements Deserializer<PurchaseKey> {
    private String encoding = "UTF8";

    public KafkaMsgDeserializer() {
    }

    @Override
    public void configure(Map<String, ?> configs, boolean isKey) {
        String propertyName = isKey ? "key.deserializer.encoding" : "value.deserializer.encoding";
        Object encodingValue = configs.get(propertyName);
        if (encodingValue == null) {
            encodingValue = configs.get("deserializer.encoding");
        }

        if (encodingValue instanceof String) {
            this.encoding = (String) encodingValue;
        }
    }

    @Override
    public PurchaseKey deserialize(String topic, byte[] data) {
        try {
            if (data == null) {
                return null;
            }
            return JSON.parseObject(new String(data, this.encoding), PurchaseKey.class);
        } catch (UnsupportedEncodingException e) {
            throw new SerializationException("Error when deserializing byte[] to string due to unsupported encoding " + this.encoding);
        }
    }
}
    