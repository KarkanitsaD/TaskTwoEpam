package Interfaces;

import Models.Symbol;

public interface SentenceElementable {
    public abstract int getLength();
    public abstract Symbol getSymbol(int index);
}
