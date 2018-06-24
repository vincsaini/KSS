import pymongo
from pymongo import MongoClient
import urllib2
import requests
import json
import datetime
from datetime import timedelta

client = MongoClient('localhost', 27017)
db = client['mandi']
collection = db['mandi_price']

def loadData():
	total_record_count = 0
	#read the records from service and insert into Mongo collection
	offset = 0
	while(True):
		records = getMandiData(offset)
		total_record_count = total_record_count + len(records)
		if len(records) == 0:
			break
		collection.insert_many(records)
		#for rc in records:
			#print(rc['commodity'])
		offset = offset + 10
	print("Total record published ",total_record_count)



def getMandiData(offset):
	#need to replace <key> with actual key to fetch the records
	url = 'https://api.data.gov.in/resource/9ef84268-d588-465a-a308-a864a43d0070?api-key=<key>&format=json&limit=10&offset=' + str(offset)
	#offset = 0
	req = requests.get(url)
	#print(req.status_code)
	json_response = json.loads(req.content)
	#print(json_response['records'])
	records = json_response['records']
	# lowering the case of commodity for search
	for record in records:
		record['commodity'] = record['commodity'].lower()
		record['arrival_date'] = datetime.datetime.strptime(record['arrival_date'],'%Y-%m-%dT%H:%M:%SZ')
	#print(records)
	print('number of records returned ',len(records))
	return records

def deleteOldRecords(days):
	# delete the records which are older then days passed in arg
	todays_date = datetime.datetime.now()
	daysdelta = datetime.timedelta(days)
	daysfrom = todays_date - daysdelta
	print(daysfrom)

	collection.delete_many({"arrival_date": {"$lt": daysfrom}});
	

deleteOldRecords(7)
#getMandiData(10);
loadData()
