package fr.unreal852.proximitytextchat.utils;

import java.util.HashMap;
import java.util.Map;

public final class StringSubstitutor
{
    /**
     * Replace values surrounded by '{' and '}' in the specified text with the values contained in the specified map.
     * If a value does not exist in the map, it will be replaced with an empty string.
     * If a value does exit in the map but does not exist in the text, it will be ignored.
     *
     * @param text The text to replace values
     * @param map  The map containing values to replace
     * @return The formatted text.
     */
    @SuppressWarnings("rawtypes")
    public static String replace(String text, Map map)
    {
        StringBuilder sb = new StringBuilder();
        char[] strArray = text.toCharArray();
        int i = 0;
        while (i < strArray.length - 1)
        {
            if (strArray[i] == '{')
            {
                i += 1;
                int begin = i;
                while (strArray[i] != '}')
                    ++i;
                sb.append(map.get(text.substring(begin, i++)));
            }
            else
            {
                sb.append(strArray[i]);
                ++i;
            }
        }
        if (i < strArray.length)
            sb.append(strArray[i]);
        return sb.toString();
    }

    public static Map<String, Object> createNewMap()
    {
        return new HashMap<>()
        {
            @Override
            public Object get(Object key)
            {
                Object result = super.get(key);
                return result == null ? "" : result;
            }
        };
    }
}
