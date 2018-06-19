#spacy.load('en')
# execute the below command from the kssnlpmodule nlp project
#command to create the model from the sample sentences
python3 -m rasa_nlu.train -c config/config_spacy_sklearn.json -d data/ -o projects/
#command to run the rasa nlu server 
python3 -m rasa_nlu.server -c config/config_spacy_sklearn.json --path projects