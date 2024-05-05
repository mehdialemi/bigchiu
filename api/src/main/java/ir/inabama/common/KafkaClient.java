package ir.inabama.common;//package ir.advest.common;
//
//import com.google.common.io.Closeables;
//import org.apache.kafka.clients.consumer.Consumer;
//import org.apache.kafka.clients.consumer.ConsumerConfig;
//import org.apache.kafka.clients.consumer.ConsumerRecords;
//import org.apache.kafka.clients.consumer.KafkaConsumer;
//import org.apache.kafka.clients.producer.KafkaProducer;
//import org.apache.kafka.clients.producer.Producer;
//import org.apache.kafka.clients.producer.ProducerConfig;
//import org.apache.kafka.clients.producer.ProducerRecord;
//import org.apache.kafka.common.serialization.StringDeserializer;
//import org.apache.kafka.common.serialization.StringSerializer;
//
//import java.io.Closeable;
//import java.io.IOException;
//import java.util.*;
//
//public class KafkaClient implements Closeable {
//
//    private String topic;
//    private Producer<String, String> producer;
//    private Consumer<String, String> consumer;
//
//    public KafkaClient(String servers, String topic) {
//        this.topic = topic;
//
//        Properties properties = new Properties();
//        properties.put(ProducerConfig.BOOTSTRAP_SERVERS_CONFIG, servers);
//
//        properties.put(ProducerConfig.KEY_SERIALIZER_CLASS_CONFIG, StringSerializer.class);
//        properties.put(ProducerConfig.VALUE_SERIALIZER_CLASS_CONFIG, StringSerializer.class);
//
//        properties.put(ConsumerConfig.KEY_DESERIALIZER_CLASS_CONFIG, StringDeserializer.class);
//        properties.put(ConsumerConfig.VALUE_DESERIALIZER_CLASS_CONFIG, StringDeserializer.class);
//
//        properties.put(ConsumerConfig.GROUP_ID_CONFIG, "advest");
//        properties.put(ConsumerConfig.AUTO_OFFSET_RESET_CONFIG, "latest");
//
//        properties.put(ConsumerConfig.ENABLE_AUTO_COMMIT_CONFIG, "false");
//
//        properties.put(ConsumerConfig.SESSION_TIMEOUT_MS_CONFIG, 6000);
//        properties.put(ConsumerConfig.HEARTBEAT_INTERVAL_MS_CONFIG, 2000);
//        producer = new KafkaProducer<>(properties);
//
//        consumer = new KafkaConsumer<>(properties);
//        consumer.subscribe(Collections.singletonList(topic));
//    }
//
//    public void send(String message) {
//        ProducerRecord<String, String> record = new ProducerRecord<>(topic, message);
//        producer.send(record);
//        producer.flush();
//    }
//
//    public List<String> consume() {
//        ConsumerRecords<String, String> events = consumer.poll(2000);
//
//        List<String> list = new ArrayList();
//        events.forEach(record -> {
//            list.add(record.value());
//        });
//        consumer.commitAsync();
//
//        return list;
//    }
//
//    @Override
//    public void close() throws IOException {
//        Closeables.close(producer, true);
//        Closeables.close(consumer, true);
//    }
//}