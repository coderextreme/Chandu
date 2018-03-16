#!/bin/sh
pip install nltk
python <<EOF
import nltk
nltk.download()
EOF
python > syns.json <<SYNEOF
import nltk
from nltk.corpus import wordnet
import json
import sys

words = {};
for syn in wordnet.all_synsets():
    for l in syn.lemmas():
        for l2 in syn.lemmas():
            if l.name() != l2.name():
                        try:
                            keys = words[l.name()].keys()
                        except:
                            words[l.name()] = {}
                        try:
                            words[l.name()][syn.pos()].append(l2.name())
                        except:
                            words[l.name()][syn.pos()] = []
                            words[l.name()][syn.pos()].append(l2.name())
json.dump(words, sys.stdout)
# print(words)
SYNEOF
