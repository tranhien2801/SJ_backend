#!/usr/bin/env python
# -*- coding: utf-8 -*-

import mysql.connector
import pandas as pd
import numpy as np
from rank_bm25 import BM25Okapi
from gensim.utils import simple_preprocess
from gensim.parsing.preprocessing import preprocess_documents, preprocess_string

def get_name():
    return "ahihi đồ ngôc"

pd.options.display.max_colwidth=160

mydb = mysql.connector.connect(
    host="localhost",
    user="root",
    password="conan1411",
    database="court"
)

mycursor = mydb.cursor(dictionary=True)

# mycursor.execute("SELECT uid, judgment_number, judgment_name, type_document, judgment_level, court_uid, case_uid, judgment_content, date_issued, date_issued, url, pdf_viewer, file_download, corrections, count_vote, count_eyes, count_download FROM judgment;")
mycursor.execute("SELECT * FROM judgment;")

judgments = mycursor.fetchall()

# print(judgments[0]["judgment_content"])

i = 0
judgment_tokens = []
for judgment in judgments:
    judgment_tokens.append(preprocess_string(judgment["judgment_content"]))

bm25_index = BM25Okapi(judgment_tokens)

def search(search_string, num_results=10):
    search_tokens = preprocess_string(search_string)
    scores = bm25_index.get_scores(search_tokens)
    top_indexes = np.argsort(scores)[::-1][:num_results]
    return top_indexes

indexes = search('sở hữu đất')
print(indexes)

for index in indexes:
    print(judgments[index])
