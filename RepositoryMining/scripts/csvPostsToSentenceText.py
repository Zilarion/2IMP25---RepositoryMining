import nltk.data
import csv
import json
import io
from politeness import model

sent_detector = nltk.data.load('tokenizers/punkt/english.pickle')
# input_politeness = io.open('input_politeness_no_dep.json','wt', encoding='utf-8')

count = 0;

with open('Posts_CPP_Python.csv', 'rb') as inf, open('Posts_CPP_Python_Results_Large.csv', 'wb') as outf:
	reader = csv.DictReader(inf)
	fieldnames =  reader.fieldnames + ['Polite', 'Impolite']
	print fieldnames
	writer = csv.DictWriter(outf, fieldnames)
	writer.writeheader()

	for i, row in enumerate(reader):
		# Fetch text from csv
		text = row['Body'].decode('utf-8')

		#Create request
		request = {'text' : text, 'sentences': sent_detector.tokenize(text.strip())}

		# Execute request
		result = model.score(request);

		# print "------"
		# print text
		# print result

		# Write score
		row['Polite'] = result['polite']
		row['Impolite'] = result['impolite']
		writer.writerow(row)
		
		count = count + 1;
		if (count % 10000 == 0):
			print count