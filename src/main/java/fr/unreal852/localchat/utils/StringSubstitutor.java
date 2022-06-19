package fr.unreal852.localchat.utils;

import java.util.HashMap;
import java.util.Map;

public final class StringSubstitutor
{
    @SuppressWarnings("rawtypes")
    public static String replace(String str, Map map)
    {
        StringBuilder sb = new StringBuilder();
        char[] strArray = str.toCharArray();
        int i = 0;
        while (i < strArray.length - 1)
        {
            if (strArray[i] == '{')
            {
                i += 1;
                int begin = i;
                while (strArray[i] != '}')
                    ++i;
                sb.append(map.get(str.substring(begin, i++)));
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
