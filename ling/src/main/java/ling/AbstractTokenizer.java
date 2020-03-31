
package ling;

import java.util.*;

public abstract class AbstractTokenizer
{
    static public final char NBSP = 160; //Неразрывный пробел

    protected final List<Token> output = new LinkedList();

    abstract char getCh();
    abstract public boolean hasCh();
    abstract public void backCh(char ch);

    public void tokenize()
    {
	while (hasCh())
	{
	    final char ch = getCh();
	    if (ch >= '0' && ch <= '9')
		onNumToken(ch); else
		if ((ch >= 'a' && ch <= 'z') || (ch >= 'A' && ch <= 'Z'))
		    onLatinToken(ch); else
		    if ((ch >= 'а' && ch <= 'я') || (ch >= 'А' && ch <= 'Я') || ch == 'ё' || ch == 'Ё')
			onCyrilToken(ch); else
			if (Character.isSpace(ch) || ch == NBSP)
			    onSpaceToken(ch); else
			    onPuncToken(ch);
	}
    }

    private void onNumToken(char ch)
    {
	final StringBuilder b = new StringBuilder();
	b.append(ch);
	while(hasCh())
	{
	    final char nextCh = getCh();
	    if (nextCh >= '0' && nextCh <= '9')
	    {
		b.append(nextCh);
		continue;
	    }
	    backCh(nextCh); //Если символ не относится к токену, возращаем его в очередь
	    break;
	}
	output.add(new Token(Token.Type.NUM, new String(b)));
    }

    private void onLatinToken(char ch)
    {
	final StringBuilder b = new StringBuilder();
	b.append(ch);
	while(hasCh())
	{
	    final char nextCh = getCh();
	    if ((nextCh >= 'a' && nextCh <= 'z') || (nextCh >= 'A' && nextCh <= 'Z'))
	    {
		b.append(nextCh);
		continue;
	    }
	    backCh(nextCh);
	    break;
	}
	output.add(new Token(Token.Type.LATIN, new String(b)));
    }

    private void onCyrilToken(char ch)
    {
	final StringBuilder b = new StringBuilder();
	b.append(ch);
	while(hasCh())
	{
	    final char nextCh = getCh();
	    if ((nextCh >= 'а' && nextCh <= 'я') || (nextCh >= 'А' && nextCh <= 'Я') || nextCh == 'ё' || nextCh == 'Ё')
	    {
		b.append(nextCh);
		continue;
	    }
	    backCh(nextCh);
	    break;
	}
	output.add(new Token(Token.Type.CYRIL, new String(b)));
    }

    private void onSpaceToken(char ch)
    {
	final StringBuilder b = new StringBuilder();
	b.append(ch);
	while(hasCh())
	{
	    final char nextCh = getCh();
	    if (Character.isSpace(nextCh) || nextCh == NBSP)
	    {
		b.append(nextCh);
		continue;
	    }
	    backCh(nextCh);
	    break;
	}
	output.add(new Token(Token.Type.SPACE, new String(b)));
    }

    private void onPuncToken(char ch)
    {
	output.add(new Token(Token.Type.PUNC, new Character(ch).toString()));
    }

    public Token[] getOutput()
    {
	return output.toArray(new Token[output.size()]);
    }
}
