package fr.unreal852.localchat.utils;

import fr.unreal852.localchat.LocalChat;

import java.util.Random;

public final class StringConfuser
{
    public static String Confuse(String text, int percentAmount)
    {
        final char replaceChar = LocalChat.CONFIG.confuseMessages.character;
        int amountToChange = text.length() * percentAmount / 100;
        char[] chars = text.toCharArray();
        for (int i = 0; i < amountToChange; i++)
        {
            Random rdm = new Random();
            chars[rdm.nextInt(text.length())] = replaceChar;
        }
        return new String(chars);
    }
}
