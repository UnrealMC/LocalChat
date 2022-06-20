package fr.unreal852.localchat.utils;

import java.util.Random;

public final class StringConfuser
{
    /**
     * Confuse the specified text by changing random characters and replacing them with the given char.
     *
     * @param text          The text to confuse.
     * @param percentAmount How much of the text have to be confused.
     * @return The confused text.
     */
    public static String Confuse(String text, final int percentAmount, final char replaceChar)
    {
        if (percentAmount <= 0 || text.length() <= 0)
            return text;
        int amountToChange = text.length() * percentAmount / 100;
        char[] chars = text.toCharArray();
        Random rdm = new Random();
        for (int i = 0; i < amountToChange; i++)
            chars[rdm.nextInt(text.length())] = replaceChar;
        return new String(chars);
    }
}
