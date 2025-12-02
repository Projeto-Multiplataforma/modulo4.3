package br.com.fatec.kafkaconsumer.consumer;

import br.com.fatec.kafkaconsumer.entity.Mensagem;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.annotation.RetryableTopic;
import org.springframework.kafka.retrytopic.TopicSuffixingStrategy;
import org.springframework.stereotype.Component;

@Component
public class KafkaConsumer {

    @RetryableTopic(
        autoCreateTopics = "false",
        topicSuffixingStrategy = TopicSuffixingStrategy.SUFFIX_WITH_INDEX_VALUE)
    @KafkaListener(
        groupId = "${spring.kafka.consumer.group-id}",
        topics = "${spring.kafka.topic.consumer}",
        containerFactory = "kafkaListenerFactory"
    )
    public void listen(Mensagem message) {
        System.out.println("A mensagem chegou: " + message.valor());
    }
}