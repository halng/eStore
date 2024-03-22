package main

import (
	"eStore/inventory/app/consumer"
	"fmt"
	"github.com/gin-gonic/gin"
	"os"
)

func main() {
	// prepare port and run the app

	// kafka
	os.Setenv("KAFKA_BOOTSTRAP_SERVER", "kafka:29092")
	os.Setenv("KAFKA_CONSUMER_GROUP_ID", "inventory-consumer")

	fmt.Println("App running")
	r := gin.Default()

	routers := r.Group("/api/v1/inventory")
	{
		routers.GET("/test", getMsg)
		routers.GET("/test/:name", getMsgWithName)
	}

	go consumer.StartConsumer()

	err := r.Run("0.0.0.0:9096")
	if err != nil {
		return
	}
}

// getMsg is a func get message for test purpose
func getMsg(c *gin.Context) {
	c.JSON(200, gin.H{"msg": "Hello World"})
}

// getMsgWithName is a func get message for test purpose
func getMsgWithName(c *gin.Context) {
	name := c.Param("name")
	c.JSON(200, gin.H{"msg": "Hello " + name})
}
