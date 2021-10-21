package tech.jinguo.kafkastreams.zmart.transformer;

import org.apache.kafka.streams.kstream.ValueTransformer;
import org.apache.kafka.streams.processor.ProcessorContext;
import org.apache.kafka.streams.state.KeyValueStore;
import tech.jinguo.kafkastreams.zmart.model.Purchase;
import tech.jinguo.kafkastreams.zmart.model.RewardAccumulator;

import java.util.Objects;


public class PurchaseRewardTransformer implements ValueTransformer<Purchase, RewardAccumulator> {

    private KeyValueStore<String, Integer> stateStore;
    private final String storeName;
    private ProcessorContext context;

    public PurchaseRewardTransformer(String storeName) {
        Objects.requireNonNull(storeName, "Store Name can't be null");
        this.storeName = storeName;
    }

    @Override
    @SuppressWarnings("unchecked")
    public void init(ProcessorContext context) {
        this.context = context;
        //转换器类中，将对象类型转换为KeyValueStore类型
        stateStore = (KeyValueStore) this.context.getStateStore(storeName);
    }

    /**
     *在transform()方法中，首先将一个Purchase对象映射为RewardAccumulator——这与mapValues()方法中使用的操作相同。在
     * 接下来的几行代码中，状态参与到转换过程当中，按键（客户ID）进行查找，并将到目前为止任何累积的积分与当前购买对应的积分相加，
     * 将新的积分总数一直存放到状态存储中，直到再次需要它。
     * @param value
     * @return
     */
    @Override
    public RewardAccumulator transform(Purchase value) {
        //由Purchase对象构造RewardAccumulator对象
        RewardAccumulator rewardAccumulator = RewardAccumulator.builder(value).build();
        //根据客户ID检索最新积分值
        Integer accumulatedSoFar = stateStore.get(rewardAccumulator.getCustomerId());
        if (accumulatedSoFar != null) {
            //如果累积的积分数存在，则将其加到当前总数中
            rewardAccumulator.addRewardPoints(accumulatedSoFar);
        }
        //将新的总积分数存储到stateStore中
        stateStore.put(rewardAccumulator.getCustomerId(), rewardAccumulator.getTotalRewardPoints());

        return rewardAccumulator;

    }

    @Override
    public void close() {
        //no-op
    }
}
