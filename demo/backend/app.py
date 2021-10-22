from flask import Flask, jsonify, request
from flask_cors import CORS

import os, sys
sys.path.insert(0, os.getcwd().split('demo/backend')[0])
from autokomplete import AutoKomplete

k = 10
query_data = '../../input_large.txt'

ak = AutoKomplete(k, query_data)
print('AutoKomplete loaded.')

app = Flask(__name__)
CORS(app)

@app.route('/autocomplete', methods=['POST'])
def add_income():
  query = request.get_json()
  suggestions = []
  try:
    suggestions = ak.autokomplete(query['query'])
  except:
    pass
  return {'suggestions': suggestions}