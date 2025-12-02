package br.com.fatec.kafkaconsumer.configuration;

import br.com.fatec.kafkaconsumer.entity.Mensagem;
import org.springframework.kafka.support.serializer.JsonDeserializer;
import org.apache.kafka.common.serialization.StringDeserializer;
import org.springframework.boot.autoconfigure.kafka.KafkaProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.config.ConcurrentKafkaListenerContainerFactory;
import org.springframework.kafka.core.ConsumerFactory;
import org.springframework.kafka.core.DefaultKafkaConsumerFactory;
import org.springframework.kafka.support.mapping.DefaultJackson2JavaTypeMapper;

@Configuration
public class KafkaListenerConfig {

    @Bean
    public ConcurrentKafkaListenerContainerFactory<String, Mensagem> kafkaListenerFactory(
            final KafkaProperties kafkaProperties
    ) {
        var factory = new ConcurrentKafkaListenerContainerFactory<String, Mensagem>();
        factory.setConsumerFactory(consumerFactory(kafkaProperties));
        return factory;
    }

    private ConsumerFactory<String, Mensagem> consumerFactory(
            final KafkaProperties kafkaProperties
    ) {
        DefaultJackson2JavaTypeMapper typeMapper = new DefaultJackson2JavaTypeMapper();
        typeMapper.addTrustedPackages("*");
        JsonDeserializer<Mensagem> valueDeserializer = new JsonDeserializer<>(Mensagem.class);
        valueDeserializer.setTypeMapper(typeMapper);
        valueDeserializer.setUseTypeMapperForKey(true);

        return new DefaultKafkaConsumerFactory<>(kafkaProperties.buildConsumerProperties(),new StringDeserializer(),valueDeserializer);
    }
}
