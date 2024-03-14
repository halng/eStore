package main

import (
	"fmt"
	"github.com/gin-gonic/gin"
)

func main() {
	fmt.Println("App running")
	r := gin.Default()

	routers := r.Group("/api/v1/inventory")
	{
		routers.GET("/test", getMsg)
		routers.GET("/test/:name", getMsgWithName)
	}

	err := r.Run("0.0.0.0:9095")
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
