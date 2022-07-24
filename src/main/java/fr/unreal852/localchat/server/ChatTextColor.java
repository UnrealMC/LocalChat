package fr.unreal852.localchat.server;

import net.minecraft.text.TextColor;

public enum ChatTextColor
{
    White("#ffffff"),
    Green("#47f038"),
    Orange("#eb5b15"),
    Red("#eb151f");

    private TextColor _textColor;

    ChatTextColor(String hexCode)
    {
        _textColor = TextColor.parse(hexCode);
    }

    ChatTextColor(int rgb)
    {
        _textColor = TextColor.fromRgb(rgb);
    }

    public TextColor getColor()
    {
        return _textColor;
    }
}
