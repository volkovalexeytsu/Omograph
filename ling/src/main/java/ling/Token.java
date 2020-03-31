
package ling;

public final class Token
{
    public enum Type {
	NUM,
	LATIN,
	CYRIL,
	SPACE,
	PUNC};

    public final Type type;
    public final String text;

    public boolean sentSep = false;

    public Token(Type type, String text)
    {
	if (type == null)
	    throw new NullPointerException("type may not be null");
	if (text == null)
	    throw new NullPointerException("text may not be null");
	if (text.isEmpty())
	    throw new IllegalArgumentException("text may not be empty");
	this.type = type;
	this.text = text;
    }

    @Override public String toString()
    {
	return text;
    }

    public boolean isFirstUpperCase()
    {
	if (type != Type.LATIN && type != Type.CYRIL)
	    return false;
	return Character.isUpperCase(text.charAt(0));
    }

    public boolean isLastUpperCase()
    {
	if (type != Type.LATIN && type != Type.CYRIL)
	    return false;
	return Character.isUpperCase(text.charAt(text.length() - 1));
    }
}
