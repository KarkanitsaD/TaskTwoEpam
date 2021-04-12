package Models;

import Interfaces.SentenceElementable;
import Interfaces.Textable;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.security.InvalidParameterException;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Word implements SentenceElementable, Textable {


    private static final Logger logger = LoggerFactory.getLogger(Word.class);

    //regexString to check is input string valid
    public static final String wordRegex = "[A-Za-zа-яА-Я0-9-']+";

    //list of symbols in word
    private List<Symbol> symbols;

    //Constructor
    //check is input string valid
    public Word(String word){
        Pattern wordValidatorPattern = Pattern.compile(wordRegex);
        Matcher wordValidatorMatcher = wordValidatorPattern.matcher(word);
        if(wordValidatorMatcher.matches()){
            symbols = new ArrayList<>();
            parseWord(word);
        }
        else
            throw new InvalidParameterException("The string " + word + " is not Word.");
    }

    //parse symbols from input string
    private void parseWord(String word){
        for(int i = 0; i < word.length(); i++){
            symbols.add(new Symbol(word.charAt(i)));
        }
    }

    //get length of the word
    @Override
    public int getLength(){
        return symbols.size();
    }

    @Override
    public Symbol getSymbol(int index) {
        return symbols.get(index);
    }

    //get word in String
    @Override
    public String getText() {
        StringBuilder word = new StringBuilder();
        for(var s : symbols){
            word.append(s.getText());
        }
        return word.toString();
    }

    //equals
    @Override
    public boolean equals(Object word){
        if(word.getClass() == Word.class){
            return ((Word) word).getText() == this.getText();
        }
        else
            throw new IllegalArgumentException("Object " + word.toString() + " is no of the class Word");
    }

    public void removeFirstLetter(){
        Symbol symbol = symbols.get(0);
        logger.debug("Remove letters like " + symbol.getChar() + ", in '" + getText() +"'");
        for(int i = 1; i < symbols.size(); i++){
            if(Character.toLowerCase(symbol.getChar()) == Character.toLowerCase(symbols.get(i).getChar())){
                symbols.remove(i);
                i--;
            }
        }
        logger.debug("Result: "  + getText());
    }



}
