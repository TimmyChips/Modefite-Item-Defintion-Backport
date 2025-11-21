package timmychips.modefiteitemdefinitions.comp.util;

public class TextUtils {

    // The strings to remove from input to get base string
    private static final String TEXT_LITERAL = "{\"text\":\"";
    private static final String END_LITERAL = "\"}";

    /**
     *
     * @param text The String Minecraft text object
     * <p>Example text String object looks like: " {"text":"Hammer of Justice"} "
     * @return Removes and returns just the contents from the text literal
     * <p>Example return string: " Hammer of Justice "
     */
    public static String fromLiteral(String text) {
        String str = text.replace(TEXT_LITERAL, "");
        str = str.replace(END_LITERAL, "");

        return str;
    }
}
