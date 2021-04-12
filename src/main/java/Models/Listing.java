package Models;

import Interfaces.Paragraphble;
import Interfaces.Textable;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.security.InvalidParameterException;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Listing implements Paragraphble, Textable {

    static public final String listingRegex = "((?s)\\n*LISTING:[\\s\\S]+?LISTING\\.\\n*)";
    private static final Logger logger = LoggerFactory.getLogger(Listing.class);
    private String listing;

    public Listing(String listing) {
        logger.trace("Start of creation LISTING");
        Pattern listingValidatorPattern = Pattern.compile(listingRegex);
        Matcher listingValidatorMatcher = listingValidatorPattern.matcher(listing);
        if (listingValidatorMatcher.matches()) {
            parseListing(listing);
        } else {
            throw new InvalidParameterException("The text " + listing + " is not LISTING.");
        }
        logger.trace("End of creation LISTING");
    }

    private void parseListing(String listing) {
        StringBuilder s = new StringBuilder(listing);
        for (int i = 0; s.charAt(i) != 'L'; i++) {
            if (s.charAt(i) == '\n') {
                s.deleteCharAt(i);
                i--;
            }
        }
        for (int i = s.length() - 1; s.charAt(i) != '.'; i--) {
            if (s.charAt(i) == '\n') {
                s.deleteCharAt(i);
            }
        }
        this.listing = s.toString();
    }

    @Override
    public String getText() {
        return listing;
    }

    @Override
    public boolean equals(Object obj) {
        if (obj.getClass() == Listing.class) {
            return getText() == ((Listing) obj).getText();
        }
        throw new IllegalArgumentException("The class of the object should be Listing.");
    }

}
