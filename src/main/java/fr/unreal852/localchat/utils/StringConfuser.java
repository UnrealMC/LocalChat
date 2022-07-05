package fr.unreal852.localchat.utils;

import java.util.Random;

public final class StringConfuser
{
    /**
     * Confuse the specified text by changing random characters and replacing them with the given char.
     *
     * @param text          The text to confuse.
     * @param percentAmount How much of the text have to be confused.
     * @param charsPool     The characters to use when replacing other characters.
     * @return The confused text.
     */
    public static String ConfuseReplace(String text, final int percentAmount, final String charsPool)
    {
        if (percentAmount <= 0 || text.isBlank())
            return text;
        StringBuilder sb = new StringBuilder(text);
        int amountToChange = text.length() * percentAmount / 100;
        Random rdm = new Random();
        for (int i = 0; i < amountToChange; i++)
            sb.setCharAt(rdm.nextInt(text.length()), charsPool.charAt(rdm.nextInt(charsPool.length())));
        return sb.toString();
    }

    /**
     * Confuse the specified text by shuffling random characters.
     *
     * @param text          the text to confuse.
     * @param percentAmount How much of the text have to be confused.
     * @return The confused text.
     */
    public static String ConfuseShuffle(String text, final int percentAmount)
    {
        if (percentAmount <= 0 || text.isBlank())
            return text;
        StringBuilder sb = new StringBuilder(text);
        int amountToChange = text.length() * percentAmount / 100;
        Random rdm = new Random();
        for (int i = 0; i < amountToChange; i++)
            sb.setCharAt(rdm.nextInt(sb.length()), sb.charAt(rdm.nextInt(sb.length())));
        return sb.toString();
    }

    /**
     * Confuse the specified text by removing random characters.
     *
     * @param text          the text to confuse.
     * @param percentAmount How much of the text have to be confused.
     * @return The confused text.
     */
    public static String ConfuseRemove(String text, final int percentAmount)
    {
        if (percentAmount <= 0 || text.isBlank())
            return text;
        StringBuilder sb = new StringBuilder(text);
        int amountToChange = text.length() * percentAmount / 100;
        Random rdm = new Random();
        for (int i = 0; i < amountToChange; i++)
            sb.deleteCharAt(rdm.nextInt(text.length()));
        return sb.toString();
    }
}
