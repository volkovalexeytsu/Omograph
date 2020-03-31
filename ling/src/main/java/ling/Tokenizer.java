
package ling;

import java.util.*;
import java.io.*;

public final class Tokenizer extends AbstractTokenizer
{
    final LinkedList<Character> qu = new LinkedList();
    final Reader reader;

    public Tokenizer(Reader reader)
    {
	if (reader == null)
	    throw new NullPointerException("reader may not be null");
	this.reader = reader;
    }

    @Override public char getCh()
    {
	if (qu.isEmpty())
	    throw new RuntimeException("Trying to get a char with the empty queue");
	final Character ch = qu.pollFirst(); //Извлекает и удаляет первый элемент из списка
	return ch.charValue(); //Возвращает значение символа
    }

    @Override public boolean hasCh()
    {
	if (!qu.isEmpty())
	    return true;
	final int ch;
	try {
	    ch = reader.read();
	}
	catch(IOException e)
	{
	    throw new RuntimeException(e);
	}
	if (ch < 0)
	    return false;
	qu.add(Character.valueOf((char)ch));
	return true;
    }

    @Override public void backCh(char ch)
    {
	qu.addFirst(Character.valueOf(ch));
    }

    static public Token[] tokenize(String text)
    {
	if (text == null)
	    throw new NullPointerException("text may not be null");
	final Tokenizer t = new Tokenizer(new StringReader(text));
	t.tokenize();
	return t.getOutput();
    }
}
