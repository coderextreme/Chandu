#include <vector>
#include "ann.h"

int formSentence(string rhyme) {
    stringstream ss(rhyme);
    string words = "";
    ifstream myfile;
    myfile.open("texts/dict.txt");
    string string1 = "";
    string string2 = "";
    string sentence = "";
    while (ss >> words) {
        vector<string>newWords;
        while (!myfile.eof()) {
            myfile >> string1>>string2;
            transform(string1.begin(), string1.end(), string1.begin(), ::tolower);
            if (words == string1) {
                if (string2 == "n." || string2 == "v." || string2 == "adv." || string2 == "adj.") {

                    while (!myfile.eof()) {
                        myfile >> string2;
                        if (string2 == "n." || string2 == "v." || string2 == "adv." || string2 == "adj.")break;
                        newWords.push_back(string2);
                    }
                }
                
                if (newWords.size() == 0)continue;
                int rr = rand() % newWords.size();
                sentence = sentence + " " + newWords[rr];
            }
        }
    }
    cout << sentence << "\n";
}

int parsePronouns() {
    ifstream myfile;
    myfile.open("texts/pronounRaw.txt");
    string string1 = "";
    ofstream outfile;
    outfile.open("texts/pronouns.txt");
    while (true) {

        myfile >> string1;
        if (string1.substr(string1.length() - 1, 1) == ",") {

            outfile << string1.substr(0, string1.length() - 1) << "\n";
        } else
            outfile << string1 << " ";
        if (myfile.eof())break;
    }
    myfile.close();
    outfile.close();
    return 0;
}

int parseWords() {
    // parsePronouns(); //uncomment to parsepronouns,already done.
    ifstream myfile;
    myfile.open("texts/dict.txt");
    string string1 = "";
    string string2 = "";
    ofstream outfile;
    outfile.open("texts/noun.txt");
    string line = "";
    while (true) {
        if (myfile.eof())break;
        myfile >> string1>>string2;
        if (string2 == ".n")
            outfile << string1 << "\n";
    }
    myfile.close();
    outfile.close();
    return 0;
}
