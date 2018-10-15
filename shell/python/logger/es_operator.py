from elasticsearch import Elasticsearch
from datetime import datetime

es = Elasticsearch()

doc = {
    "unix_time": 1545234035,
    "data_time": "2018-08-08 23:00:00",
    "job_name": "WeeklyJob",
    "step_name": "UDS",
    "status": "running"
}

res = es.index(index="test-index", doc_type="test-type", body = doc)
