FROM python:latest

COPY ./requirements.txt /app/requirements.txt

WORKDIR /app

RUN pip install -r requirements.txt

COPY . /app

CMD [ "sh", "-c", "python producer.py belgrade_lanes.csv & python producer.py emissions.csv"]
