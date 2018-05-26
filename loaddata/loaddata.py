import pymongo
from pymongo import MongoClient
import urllib2
import requests
import json


def loadData():
	client = MongoClient('localhost', 27017)
	db = client['mandi']
	collection = db['mandi_price']
	#read the records from service and insert into Mongo collection
	offset = 0
	while(True):
		records = getMandiData(offset)
		if len(records) == 0:
			break
		collection.insert_many(records)
		#for rc in records:
			#print(rc['commodity'])
		offset = offset + 50


def getMandiData(offset):
	url = 'https://api.data.gov.in/resource/9ef84268-d588-465a-a308-a864a43d0070?api-key=<key>&format=json&limit=10&offset=' + str(offset)
	offset = 0
	req = requests.get(url)
	#print(req.status_code)
	json_response = json.loads(req.content)
	#print(json_response['records'])
	records = json_response['records']
	return records

loadData()


