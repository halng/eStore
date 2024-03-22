package consumer

import (
	"context"
	"fmt"
	"github.com/IBM/sarama"
	"log"
	"os"
)

const (
	ConsumerTopic   = "db.product-new"
	ConsumerGroupId = "inventory-consumer"
)

var BootStrapServer = []string{os.Getenv("KAFKA_BOOTSTRAP_SERVER")}

type ConsumerGroupHandler struct {
}

func (ConsumerGroupHandler) Setup(_ sarama.ConsumerGroupSession) error {
	return nil
}

func (ConsumerGroupHandler) Cleanup(_ sarama.ConsumerGroupSession) error {
	return nil
}

func (h ConsumerGroupHandler) ConsumeClaim(sess sarama.ConsumerGroupSession, claim sarama.ConsumerGroupClaim) error {
	for msg := range claim.Messages() {
		fmt.Printf("Message topic:%q partition:%d offset:%d\n", msg.Topic, msg.Partition, msg.Offset)
		sess.MarkMessage(msg, "")
	}
	return nil

}

func StartConsumer() {
	config := sarama.NewConfig()
	config.Consumer.Return.Errors = true
	config.Version = sarama.V2_1_0_0

	consumer, err := sarama.NewConsumerGroup(BootStrapServer, ConsumerGroupId, config)
	if err != nil {
		log.Panicf("Error creating consumer group client: %v", err)
	}

	ctx := context.Background()
	handler := ConsumerGroupHandler{}

	for {
		err := consumer.Consume(ctx, []string{ConsumerTopic}, handler)
		if err != nil {
			log.Panicf("Error from consumer: %v", err)
		}
	}
}
