import json
from tqdm import tqdm

from ko_divider import decompose_sentence

class AutoKomplete:
    WEIGHTS_PATH = './autokomplete_weights.json'

    def __init__(self, k, train_data=None):
        self.k = k

        if train_data:
            self.set_weights(train_data)
            # self.save_weights()
        else:
            self.load_weights()

    def set_weights(self, train_data):
        self.database = dict()
        self.frequency = dict()

        db = open(train_data, mode='r')

        for line in tqdm(db.read().split('\n')):
            line_split = line.split(',')
            word = line_split[0]
            freq = int(line_split[1])
            self.frequency[word] = freq

            decomposed = decompose_sentence(word)
            for idx in range(1, len(decomposed) + 1):
                key = decomposed[:idx]
                self.database.setdefault(key, []).append(word)
                if len(self.database[key]) > 1:
                    self.database[key].sort(reverse=True, key=lambda x: self.frequency[x])
                if len(self.database[key]) > self.k:
                    self.database[key] = self.database[key][0:self.k]

        db.close()
    
    def save_weights(self):
        weights_json = json.dumps([self.frequency, self.database])
        with open(self.WEIGHTS_PATH, mode='w') as f:
            f.write(weights_json)

    def load_weights(self):
        with open(self.WEIGHTS_PATH, mode='r') as f:
            weights_json = json.load(f)
        self.frequency = weights_json[0]
        self.database = weights_json[1]

    def autokomplete(self, query):
        decomposed = decompose_sentence(query)
        return self.database.get(decomposed, [])

ak = AutoKomplete(10, 'input_large.txt')
print(ak.autokomplete('ㄱ'))
