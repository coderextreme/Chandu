#include "ann.h"

int formsentence(string rhyme)
{
    
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
