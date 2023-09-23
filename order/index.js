const express = require('express')
const dotenv = require('dotenv')

dotenv.config()

const app = express()
const PORT = process.env.PORT | 3000

app.use(express.json())

app.get('/', (req, res) => {
    return res.send({ msg: 'hello world' })
})

app.listen(PORT, () => {
    console.log(`Server started at ${PORT}`)
})
