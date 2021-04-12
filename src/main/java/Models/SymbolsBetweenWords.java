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

public class SymbolsBetweenWords implements SentenceElementable, Textable {



    private static final Logger logger = LoggerFactory.getLogger(SymbolsBetweenWords.class);

    //stringRegex to check is input string valid
    public static  final String  symbolsBetweenWords = "[^A-Za-zа-яА-Я0-9-']*";

    //List of symbols between words
    private List<Symbol> symbols;

    //Constructor
    //Check is input string valid
    public SymbolsBetweenWords(String symbolsBetweenWords){
        Pattern wordValidatorPattern = Pattern.compile(symbolsBetweenWords);
        Matcher wordValidatorMatcher = wordValidatorPattern.matcher(symbolsBetweenWords);
        if(wordValidatorMatcher.matches()){
            symbols = new ArrayList<>();
            parseSymbols(symbolsBetweenWords);
        }
        else
            throw new InvalidParameterException("This string is not SymbolsBetweenWord.");
    }

    //parse symbols from input string
    private void parseSymbols(String symbolsBetweenWords){
        for (int i = 0; i < symbolsBetweenWords.length(); i++){
            symbols.add(new Symbol(symbolsBetweenWords.charAt(i)));
        }
    }

    //get symbols in String
    @Override
    public String getText() {
        StringBuilder symbols = new StringBuilder();
        for(var s : this.symbols){
            symbols.append(s.getText());
        }
        return  symbols.toString();
    }

    @Override
    public int getLength() {
        return symbols.size();
    }

    @Override
    public Symbol getSymbol(int index) {
        return symbols.get(index);
    }

    @Override
    public boolean equals(Object symbolsBetweenWords){
        if(symbolsBetweenWords.getClass() == SymbolsBetweenWords.class)
            return (((SymbolsBetweenWords)symbolsBetweenWords).getText() == this.getText());
        throw new IllegalArgumentException("Object " + symbolsBetweenWords.toString() + " is no of the class SymbolsBetweenWords");
    }
}
