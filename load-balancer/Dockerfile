FROM golang:alpine AS builder

WORKDIR /app

COPY . .

RUN go mod download

RUN go build -o app ./main.go

FROM alpine

COPY --from=builder /app/app .
COPY --from=builder /app/config.json .

EXPOSE 8080

CMD ["./app"]
