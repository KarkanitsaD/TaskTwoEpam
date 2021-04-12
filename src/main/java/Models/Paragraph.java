package Models;

import Interfaces.Paragraphble;
import Interfaces.Textable;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Paragraph implements Textable, Paragraphble, Comparable<Paragraph> {


    //regexString to check is input string valid
    static public final String paragraphRegex = "[\\n]*.*[\\n]*";
    private static final Logger logger = LoggerFactory.getLogger(Paragraph.class);
    //list of sentences in paragraph
    private final List<Sentence> sentences;

    //Constructor
    public Paragraph(String paragraph) {
        logger.trace("Start of creation Paragraph");
        sentences = new ArrayList<>();
        paragraph = paragraph.replace("\n", "");
        parseParagraph(paragraph);
        logger.trace("End of creation Paragraph");
    }

    //split sentences from input string
    private void parseParagraph(String paragraph) {
        Pattern pattern = Pattern.compile(Sentence.sentenceRegex);
        Matcher matcher = pattern.matcher(paragraph);
        while (matcher.find()) {
            if (matcher.group().equals(""))
                continue;
            Sentence sentence = new Sentence(matcher.group());
            sentences.add(sentence);
        }
    }


    public Sentence getSentence(int index) {
        return sentences.get(index);
    }

    //add new sentence
    public void addSentence(Sentence sentence) {
        sentences.add(sentence);
    }

    //remove sentence
    public void removeSentence(Sentence sentence) {
        sentences.remove(sentence);
    }

    //remove sentence by index
    public void removeSentence(int index) {
        sentences.remove(index);
    }

    //swap sentences
    public void swapSentences(int first, int second) {
        Collections.swap(sentences, first, second);
    }

    //insert sentence
    public void insertSentence(int index, Sentence sentence) {
        sentences.add(index, sentence);
    }

    //get Paragraph in String
    @Override
    public String getText() {
        StringBuilder paragraph = new StringBuilder();
        for (var s : sentences) {
            paragraph.append(s.getText());
            paragraph.append(" ");
        }
        return paragraph.toString();
    }

    @Override
    public int compareTo(Paragraph p) {
        return Integer.compare(sentences.size(), p.sentences.size());
    }

    public void removeFirstLettersInWords() {
        for (var s : sentences) {
            s.removeFirstLettersInWords();
        }
    }

    public void replaceWordsByLength(int wordLength, String subString) {
        for (var s : sentences) {
            s.replaceWordsByLength(wordLength, subString);
        }
    }


}
