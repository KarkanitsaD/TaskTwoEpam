package Models;

import Enums.SymbolType;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class SymbolVerifier {


    private static final Logger logger = LoggerFactory.getLogger(SymbolVerifier.class);

    private static final String delimiterRegex = "[-']";
    private static final String letterRegex = "[A-Za-zА-Яа-я]";
    private static final String sentencePunctuationMarkRegex = "[!.?]";
    private static final String wordPunctuationMarkRegex = "[,:{}()–]";
    private static final String numeralRegex = "[1-9]";
    private static final String quotationMarkRegex = "[\"]";
    private static final String spaceMarkRegex = "[\t\s]";

    public static SymbolType VerifierSymbol(char symbol){
        if(Character.isLetter(symbol)){
            return SymbolType.LETTER;
        }
        else{
            Pattern pattern = Pattern.compile(spaceMarkRegex);
            Matcher matcher = pattern.matcher(Character.toString(symbol));
            if(matcher.matches()){
                return SymbolType.SPACE_MARK;
            }else{
                pattern = Pattern.compile(wordPunctuationMarkRegex);
                matcher = pattern.matcher(Character.toString(symbol));
                if(matcher.matches()){
                    return SymbolType.WORD_PUNCTUATION_MARK;
                }else{
                    pattern = Pattern.compile(sentencePunctuationMarkRegex);
                    matcher = pattern.matcher(Character.toString(symbol));
                    if(matcher.matches()){
                        return SymbolType.SENTENCE_PUNCTUATION_MARK;
                    }else{
                        pattern = Pattern.compile(numeralRegex);
                        matcher = pattern.matcher(Character.toString(symbol));
                        if(matcher.matches()){
                            return SymbolType.NUMERAL;
                        }else{
                            pattern = Pattern.compile(quotationMarkRegex);
                            matcher = pattern.matcher(Character.toString(symbol));
                            if(matcher.matches()){
                                return SymbolType.QUOTATION_MARK;
                            }else {
                                pattern = Pattern.compile(delimiterRegex);
                                matcher = pattern.matcher(Character.toString(symbol));
                                if (matcher.matches()) {
                                    return SymbolType.DELIMITER;
                                } else {
                                    return SymbolType.NO_IDENTIFY;
                                }
                            }
                        }
                    }
                }
            }
        }
    } // end of method
}
