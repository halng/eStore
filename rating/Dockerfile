 
FROM python:3.11-slim

# 
WORKDIR /app

COPY ./requirements.txt /requirements.txt

RUN apt-get update \
    && apt-get -y install libpq-dev gcc 
RUN pip install --no-cache-dir --upgrade -r /requirements.txt


COPY ./app ./app

CMD ["uvicorn", "app.main:app", "--host", "0.0.0.0", "--port", "9094"]
