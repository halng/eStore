package consumer

import (
	"fmt"
	"github.com/IBM/sarama"
)

const (
	ConsumerTopic   = "db.product-new"
	ConsumerGroupId = "inventory-consumer"
)

var BootStrapServer = []string{"localhost:9092"}

func main() {
	// initialize Sarama consumer
	config := sarama.NewConfig()
	config.Consumer.Return.Errors = true
	consumer, err := sarama.NewConsumerGroup(BootStrapServer, ConsumerGroupId, config)

	if err != nil {
		fmt.Println("Error creating consumer: ", err)
		return
	}

	defer consumer.Close()
	
}
