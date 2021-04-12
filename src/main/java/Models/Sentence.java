package Models;

import Enums.SentenceType;
import Enums.SymbolType;
import Interfaces.SentenceElementable;
import Interfaces.Textable;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.security.InvalidParameterException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Sentence implements Textable {

    private static final Logger logger = LoggerFactory.getLogger(Sentence.class);


    //regexString to check is the input string valid
    public static final String sentenceRegex = "[^!?.\\n\\r]*[!|.|?]+";

    //List of sentence elements(words, symbolsAfterWords);
    private List<SentenceElementable> sentence;

    //Constructor
    //Check is the input string valid.
    public Sentence(String sentence){
        logger.trace("Start of creation Sentence");
        Pattern sentenceValidatorPattern = Pattern.compile(sentenceRegex);
        Matcher sentenceValidatorMatcher = sentenceValidatorPattern.matcher(sentence);
        if(sentenceValidatorMatcher.matches()){
            sentence = sentence.trim();
            this.sentence = parseSentence(sentence);
        }
        else{
            throw new InvalidParameterException("This string is not Sentence.");
        }
        logger.trace("End of creation Sentence");
    }


    //parse words and symbols between words from string
    private ArrayList<SentenceElementable> parseSentence(String sentence){
        ArrayList<SentenceElementable> sentenceElements = new ArrayList<>();
        Pattern pattern = Pattern.compile(Word.wordRegex + "|" + SymbolsBetweenWords.symbolsBetweenWords);
        Matcher matcher = pattern.matcher(sentence);
        while(matcher.find()){
            if (matcher.group().equals(""))
                continue;
            Pattern wordPattern = Pattern.compile(Word.wordRegex);
            Matcher wordMatcher = wordPattern.matcher(matcher.group());
            if(wordMatcher.find()){
                sentenceElements.add(new Word(matcher.group()));
            }else{
                sentenceElements.add(new SymbolsBetweenWords(matcher.group()));
            }
        }
        return sentenceElements;
    }


    //replace all words with length "wordLength"
    public void replaceWordsByLength(int wordLength, String subString){
        logger.debug("Start replace word with length = " + wordLength + ", substring: " + subString);
        ArrayList<SentenceElementable> sentenceElements = parseSentence(subString);
        for(int i = 0; i < sentence.size(); i++){
            if(sentence.get(i).getClass() == Word.class){
                if(((Word)sentence.get(i)).getLength() == wordLength){
                    sentence.remove(i);
                    sentence.addAll(i, new ArrayList<>(sentenceElements));
                    i += sentenceElements.size();
                }
            }
        }
    }

    //define type of sentence
    public SentenceType defineType(){
        if(sentence.get(sentence.size()-1).getClass() == Word.class)
            return SentenceType.NO_IDENTIFY;
        for(int i = sentence.get(sentence.size()-1).getLength()-1; i>=0; i--)
        {
            if(sentence.get(sentence.size()-1).getSymbol(i).getType() == SymbolType.SENTENCE_PUNCTUATION_MARK) {
                switch (sentence.get(sentence.size()-1).getSymbol(i).getChar()) {
                    case '.': return SentenceType.NARRATIVE;
                    case '!': return SentenceType.EXCLAMATION;
                    case '?': return SentenceType.INTERROGATIVE;
                }
            }
        }
        return SentenceType.NO_IDENTIFY;
    }

    //add elements
    public void add(SentenceElementable element){
        sentence.add(element);
    }
    public void add(int index, SentenceElementable element) {
        sentence.add(index, element);
    }

    //remove elements
    public SentenceElementable remove(int index){
        return sentence.remove(index);
    }
    public boolean remove(SentenceElementable element) {
        return sentence.remove(element);
    }

    //swap elements
    public void swap(int first, int second){
        Collections.swap(sentence, first, second);
    }

    //get sentence in String
    @Override
    public String getText() {
        StringBuilder sentence = new StringBuilder();
        for (var el : this.sentence){
            sentence.append(((Textable)el).getText());
        }
        return sentence.toString();
    }

    @Override
    public boolean equals(Object sentence){
        if(sentence.getClass() == Sentence.class){
            return (((Sentence)sentence).getText() == this.getText());
        }
        throw new IllegalArgumentException("Object " + sentence.toString() + " is no of the class Sentence");
    }


    public void removeFirstLettersInWords(){
        for(var w : sentence){
            if(w.getClass() == Word.class){
                ((Word) w).removeFirstLetter();
            }
        }
    }
}
