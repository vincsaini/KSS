#!/usr/bin/env python3
from nltk import word_tokenize
from fuzzywuzzy import fuzz, process
import re

from flask import Flask
from flask import Response
import json

# This will be exposed as entity resolver service
app = Flask(__name__)



def spell_check(input_text):
    spellCheckDictionary = open('a.dat','r')
    print(input_text)
    corpus_text = [line.strip() for line in spellCheckDictionary]
    input_text = re.sub(","," ",input_text)
    tokenized = input_text.split()
    corrected_text = []

    for each_word in tokenized:
        max_ratio = 0
        index = 0
        for each_word_1 in corpus_text:
            if fuzz.ratio(each_word.lower(),each_word_1.lower()) > max_ratio:
                max_ratio = fuzz.ratio(each_word.lower(),each_word_1.lower())
                matched_index = index
            index = index + 1
        if max_ratio > 50:
            corrected_text.append(corpus_text[matched_index])
        else:
            corrected_text.append(each_word)

    corrected_string = ' '.join(corrected_text)

    return corrected_string

print(spell_check("wht is you nme wat"))

@app.route("/kssspell/<query>")
def kssspell(query):
    #print(query)
    #return spell_check(str(query)
    return Response(json.dumps(spell_check(query)),mimetype='application/json')

if __name__ == '__main__':
    app.run(debug=True,port=5002)