#spacy.load('en')
# execute the below command from the kssnlpmodule nlp project
#command to create the model from the sample sentences
python3 -m rasa_nlu.train -c config/config_spacy_sklearn.json -d data/ -o projects/
#command to run the rasa nlu server 
python3 -m rasa_nlu.server -c config/config_spacy_sklearn.json --path projects -w logs/

#mongo index definition
db.mandi_price.createIndex({district:"text",state:"text",market:"text"})

db.kccquery.createIndex({Crop:"text",QueryText:"text"})

db.mandi_price.distinct("commodity")
mvn package ; java -jar target/ksswebapp-0.1.0.jar

./mongod --dbpath /Users/vineet/data