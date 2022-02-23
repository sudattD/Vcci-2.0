package sbnri.consumer.android.util;

import android.text.Html;
import android.text.Spanned;

import java.util.regex.Matcher;
import java.util.regex.Pattern;


/**
 * Created by yashThakur on 06/02/17.
 */
public class WordUtils {

    public static boolean isEmailValid(String inputStr) {
        boolean isValid = false;
        String expression = "^[\\w\\.-]+@([\\w\\-]+\\.)+[A-Z]{2,4}$";

        Pattern pattern = Pattern.compile(expression, Pattern.CASE_INSENSITIVE);
        Matcher matcher = pattern.matcher(inputStr);

        if (matcher.matches()) {
            isValid = true;
        }

        return isValid;
    }

    public static Spanned fromHtml(String html) {
        if (html == null)
            return null;
        Spanned result;
        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.N) {
            result = Html.fromHtml(html, Html.FROM_HTML_MODE_COMPACT);
        } else {
            result = Html.fromHtml(html);
        }
        return result;
    }

    public static String toCamelCase(String inputString) {
        StringBuilder result = new StringBuilder();
        if (inputString.length() == 0) {
            return result.toString();
        }
        char firstChar = inputString.charAt(0);
        char firstCharToUpperCase = Character.toUpperCase(firstChar);
        result.append(firstCharToUpperCase);
        for (int i = 1; i < inputString.length(); i++) {
            char currentChar = inputString.charAt(i);
            char previousChar = inputString.charAt(i - 1);
            if (previousChar == ' ') {
                char currentCharToUpperCase = Character.toUpperCase(currentChar);
                result.append(currentCharToUpperCase);
            } else {
                char currentCharToLowerCase = Character.toLowerCase(currentChar);
                result.append(currentCharToLowerCase);
            }
        }
        return result.toString();
    }

    public static String toSentenceCase(String inputString) {
        StringBuilder result = new StringBuilder();
        if (inputString.length() == 0) {
            return result.toString();
        }
        char firstChar = inputString.charAt(0);
        char firstCharToUpperCase = Character.toUpperCase(firstChar);
        result.append(firstCharToUpperCase);
        boolean terminalCharacterEncountered = false;
        char[] terminalCharacters = {'.', '?', '!'};
        for (int i = 1; i < inputString.length(); i++) {
            char currentChar = inputString.charAt(i);
            if (terminalCharacterEncountered) {
                if (currentChar == ' ') {
                    result.append(currentChar);
                } else {
                    char currentCharToUpperCase = Character.toUpperCase(currentChar);
                    result.append(currentCharToUpperCase);
                    terminalCharacterEncountered = false;
                }
            } else {
                char currentCharToLowerCase = Character.toLowerCase(currentChar);
                result.append(currentCharToLowerCase);
            }
            for (char terminalCharacter : terminalCharacters) {
                if (currentChar == terminalCharacter) {
                    terminalCharacterEncountered = true;
                    break;
                }
            }
        }
        return result.toString();
    }


}