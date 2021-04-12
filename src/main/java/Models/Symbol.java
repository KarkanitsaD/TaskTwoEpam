package Models;

import Enums.SymbolType;
import Interfaces.Textable;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class Symbol implements Textable {


    private static final Logger logger = LoggerFactory.getLogger(Symbol.class);

    private char symbol;

    public char getChar() {
        return symbol;
    }

    public void setSymbol(char symbol) {
        this.symbol = symbol;
        type = SymbolVerifier.VerifierSymbol(symbol);
    }

    public SymbolType getType() {
        return type;
    }

    private SymbolType type;

    public Symbol(char symbol){
        this.symbol = symbol;
        type = SymbolVerifier.VerifierSymbol(symbol);
        /*System.out.println(symbol + " " + type);*/
    }

    @Override
    public String getText() {
        return Character.toString(symbol);
    }

    @Override
    public boolean equals(Object obj) {
        if (obj.getClass() == Symbol.class){
            return symbol == ((Symbol) obj).symbol;
        }
        throw new IllegalArgumentException("The class of the object should be Symbol.");
    }

}
