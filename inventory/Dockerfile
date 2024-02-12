ARG GO_VERSION=1.20

FROM golang:${GO_VERSION}-alpine AS builder

WORKDIR /src

COPY go.mod .
COPY go.sum .
RUN go mod download

COPY . .
RUN go build -o ./main

FROM alpine:latest
COPY --from=builder /src/main .

EXPOSE 8080

ENTRYPOINT ["./main"]