import nltk
from nltk.tokenize import word_tokenize
from nltk.corpus import stopwords
from nltk.corpus import wordnet
from nltk.tag.stanford import StanfordNERTagger

import pymongo
from pymongo import MongoClient

from flask import Flask
import json

# This will be exposed as entity resolver service
app = Flask(__name__)

# database connection detail
client = MongoClient('localhost', 27017)
db = client['mandi']
mandi_coll = db['mandi_price']

# This is program to process the query

#replace example with the input text
#example = "price of wheat in bangalore"

# Take user input and return the entities dictionary
def entityresolver(example):
	#entities['produce'] = ['wheat']
	#entities['location'] = ['roorkee']
	entities = {'produce':list(),'location':list()}

	if 'price' in example:
		entities['query'] = 'price'
	if 'weather' in example:
		entities['query'] = 'weather'

	#split the sentence in tokens
	tokens = word_tokenize(example)#[t for t in example.split()]
	
	#remove the stop worrds from the token
	clean_tokens = tokens[:]
	sr = stopwords.words('english')
	for token in tokens:
	    if token in stopwords.words('english'):
	        clean_tokens.remove(token)
	
	for word in clean_tokens:
		#if word exist in the location then add it
		loc_docs = mandi_coll.find({"$text": { "$search": ""+word }})
		for doc in loc_docs:
			entities['location'] = [word]
			break;
		
		#if word exist in the commodity then add it
		com_docs = mandi_coll.find({"commodity": word})
		for doc in com_docs:
			entities['produce'] = [word]
			break;
	return entities

# Example calling the entity resolver
example = "price of Carrot in Surat"
entities  = entityresolver(example)
print(entities)

@app.route("/kssnlp/<query>")
def kssnlp(query):
    return json.dumps(entityresolver(query))


if __name__ == '__main__':
    app.run(debug=True)