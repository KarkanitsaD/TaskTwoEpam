import Models.*;

import java.io.FileReader;
import java.io.IOException;
import java.util.regex.Pattern;

public class Main {
    public static void main(String[] args) {
        StringBuilder test = new StringBuilder();
        try(FileReader reader = new FileReader("src/main/resources/text.txt"))
        {
            int c;
            while((c=reader.read())!=-1){
                test.append((char)c);
            }
        }
        catch(IOException ex){

            System.out.println(ex.getMessage());
        }
        String s = test.toString();

        Text text = new Text(s);

        /*Paragraph paragraph = (Paragraph) text.getParagraph(0);
        paragraph.getSentence(0).replaceWordsByLength(3, "LOL KEK, CHEBUREK");


        text.removeFirstLettersInWords();*/

        System.out.println(text.getText());

    }
}

