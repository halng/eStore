package models

type Product struct {
	ID          string  `json:"Id"`
	Thumbnail   string  `json:"thumbnail"`
	Quantity    int     `json:"quantity"`
	Name        string  `json:"name"`
	Price       float32 `json:"price"`
	Description string  `json:"description"`
	Slug        string  `json:"slug"`
	Branch      string  `json:"branch"`
}
