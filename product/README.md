# Product service

List end point and function

| Method | func                                   | api                            | Note                              |
|--------|----------------------------------------|--------------------------------|-----------------------------------|
| GET    | get all product have paging and offset | /api/v1/products/:page/:offset |                                   |
| GET    | get product by id                      | /api/v1/product/:id            |                                   |
| GET    | get product by name                    | /api/v1/product/:name          |                                   |
| GET    | get product by filter                  | /api/v1/products               | filter will in a body (JSON type) |
| GET    | search product                         | /api/v1/product/:text          |                                   |
| GET    | get product by creator                 | /api/v1/product                | id of creator get from token      |