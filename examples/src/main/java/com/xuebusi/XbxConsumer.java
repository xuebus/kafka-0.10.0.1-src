package com.xuebusi;

import org.apache.kafka.clients.consumer.ConsumerConfig;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.apache.kafka.clients.consumer.ConsumerRecords;
import org.apache.kafka.clients.consumer.KafkaConsumer;
import org.apache.kafka.common.TopicPartition;

import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by SYJ on 2017/12/8.
 */
public class XbxConsumer {

    public static void main(String[] args) {
        Map<String, String> props = new HashMap<>();
        props.put(ConsumerConfig.BOOTSTRAP_SERVERS_CONFIG, "localhost:9092");

        props.put(ConsumerConfig.ENABLE_AUTO_COMMIT_CONFIG, "true");
        props.put(ConsumerConfig.AUTO_COMMIT_INTERVAL_MS_CONFIG, "1000");
        props.put(ConsumerConfig.SESSION_TIMEOUT_MS_CONFIG, "30000");
        //props.put(ConsumerConfig.AUTO_OFFSET_RESET_CONFIG, "earliest");

        props.put(ConsumerConfig.KEY_DESERIALIZER_CLASS_CONFIG, "org.apache.kafka.common.serialization.StringDeserializer");
        props.put(ConsumerConfig.VALUE_DESERIALIZER_CLASS_CONFIG, "org.apache.kafka.common.serialization.StringDeserializer");

        props.put(ConsumerConfig.GROUP_ID_CONFIG, "group-xbs-test");

        KafkaConsumer consumer = new KafkaConsumer(props);
        /*while (true) {
            consumer.subscribe(Collections.singletonList("zp"));
            ConsumerRecords<String, String> records = consumer.poll(1000);
            for (ConsumerRecord record : records) {
                System.out.println("===========消费消息====" + record.toString() + "==========");
            }
        }*/

        TopicPartition partition = new TopicPartition("zp", 0);
        //手动指定消费位置，无法自动负载均衡，所以要手动注册，才能消费
        consumer.assign(Arrays.asList(partition));
        consumer.seek(partition, 531);
        ConsumerRecords<String, String> records = consumer.poll(1000);
        for (ConsumerRecord record : records) {
            System.out.println("===========消费消息====" + record.toString() + "==========");
        }

    }


}
