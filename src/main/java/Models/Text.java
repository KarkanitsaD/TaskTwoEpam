package Models;

import Interfaces.Paragraphble;
import Interfaces.Textable;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Text implements Textable, Comparable<Text> {

    private static final Logger logger = LoggerFactory.getLogger(Text.class);

    private final List<Paragraphble> paragraphs;

    public Text(String text){
        logger.trace("Start of creation Text");
        text = text.replace("\r", "");
        paragraphs = new ArrayList<>();
        parseText(text);
        logger.trace("End of creation Text");
    }

    public ArrayList<Paragraphble> getParagraphs(){
        return new ArrayList<>(paragraphs);
    }

    private void parseText(String text){
        Pattern pattern = Pattern.compile(Listing.listingRegex + "|" + Paragraph.paragraphRegex);
        Matcher matcher = pattern.matcher(text);
        while (matcher.find()){
            String s = matcher.group();
            if (matcher.group().equals(""))
                continue;
            Pattern listingPattern = Pattern.compile(Listing.listingRegex);
            Matcher listingMatcher = listingPattern.matcher(matcher.group());
            if(listingMatcher.find()){
                Listing listing = new Listing(matcher.group());
                paragraphs.add(listing);
            }
            else {
                Paragraph paragraph = new Paragraph(matcher.group());
                paragraphs.add(paragraph);
            }
        }
    }

    public Paragraphble getParagraph(int index){
        return paragraphs.get(index);
    }
    public void addParagraph(Paragraphble paragraph) {
        paragraphs.add(paragraph);
    }
    public void insertParagraph(int index, Paragraphble paragraph){
        paragraphs.add(index, paragraph);
    }
    public void removeParagraph(Paragraphble paragraph) {
        paragraphs.remove(paragraph);
    }
    public void removeParagraph(int index) {
        paragraphs.remove(index);
    }
    public void swap(int first, int second) {
        Collections.swap(paragraphs, first, second);
    }

    public String getText() {
        StringBuilder text = new StringBuilder();
        for(var p : paragraphs){
            text.append(((Textable)p).getText());
            text.append('\n');
        }
        return text.toString();
    }

    @Override
    public boolean equals(Object text){
        if(text.getClass() == Text.class){
            return (((Text)text).getText() == this.getText());
        }
        throw new IllegalArgumentException("Object " + text.toString() + " is no of the class Text");
    }

    @Override
    public int compareTo(Text t) {
        return Integer.compare(paragraphs.size(), t.paragraphs.size());
    }

    public void removeFirstLettersInWords(){
        for(var p : paragraphs){
            if(p.getClass() == Paragraph.class){
                ((Paragraph) p).removeFirstLettersInWords();
            }
        }
    }

    public void replaceWordsByLength(int wordLength, String subString){
        for(var p : paragraphs){
            if(p.getClass() == Paragraph.class){
                ((Paragraph) p).replaceWordsByLength(wordLength, subString);
            }
        }
    }
}
