# Install dependencies only when needed
FROM node:18-alpine as dependencies

WORKDIR /app

COPY package.json /app/package.json

RUN npm i

# Run app
FROM node:18-alpine as runner
WORKDIR /app

ENV NODE_ENV production

RUN addgroup --system --gid 1001 groupRunner
RUN adduser --system --uid 1001 userRunner

COPY src/ /app/src
COPY --from=dependencies /app/node_modules ./node_modules
COPY --from=dependencies /app/package.json ./package.json
COPY . .
USER userRunner

EXPOSE 3000
CMD [ "npm","start" ]