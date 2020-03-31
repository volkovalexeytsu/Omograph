
package ling;

import java.util.*;
import java.io.*;
import java.net.*;

final class Segmentation
{
    private final String[] abbrevs;

    //Читаем файл с аббревиатурами и заносим в список
    Segmentation()
    {
	final List<String> res = new LinkedList();
	try {
	    final URL url = this.getClass().getClassLoader().getResource("ling/abbrevs.txt");
	    final BufferedReader r = new BufferedReader(new InputStreamReader(url.openStream()));
	    try {
		String line = r.readLine();
		while(line != null)
		{
		    if (!line.trim().isEmpty())
			res.add(line.trim().toUpperCase());
		    line = r.readLine();
		}
	    }
	    finally {
		r.close();
	    }
	}
	catch(IOException e)
	{
	    throw new RuntimeException(e);
	}
	this.abbrevs = res.toArray(new String[res.size()]); //Получаем массив аббревиатур
    }

    Token[][] segment(String[] text)
    {
	if (text == null)
	    throw new NullPointerException("text may not be null");
	final List<Token> tl = new LinkedList();
	for(int i = 0; i < text.length; i++)
	{
	    final String t = text[i];
	    final Token[] tokens = Tokenizer.tokenize(t);
	    for(Token tt: tokens)
		tl.add(tt);
	    if (i + 1 != text.length)
	    tl.add(new Token(Token.Type.SPACE, " ")); //После каждой строки с токенами, добавляем пробел, если это не последняя строка
	}
	final Token[] tokens = tl.toArray(new Token[tl.size()]);
	for(int i = 0; i < tokens.length; i++)
	    if (isSentSep(tokens, i))
		tokens[i].sentSep = true; //Пробел между предложениями
	final List res = new LinkedList();
	final List<Token> sent = new LinkedList();
	for(Token t: tokens)
	{
	    if (t.sentSep)
	    {
		res.add(sent.toArray(new Token[sent.size()]));
		sent.clear();
		continue;
	    }
	    sent.add(t);
	}
	if (!sent.isEmpty())
	    res.add(sent.toArray(new Token[sent.size()]));
	return (Token[][])res.toArray(new Token[res.size()][]);
    }

    private boolean isSentSep(Token[] tokens, int pos)
    {
	if (pos < 0 || pos >= tokens.length)
	    throw new IllegalArgumentException("pos (" + String.valueOf(pos) + " must be non-negative and less than " + String.valueOf(tokens.length));
	final Token t1 = tokens[pos];
	//It must be a space
	if (t1.type != Token.Type.SPACE)
	    return false;
	final Token t0 = pos > 0?tokens[pos - 1]:null; //Берём предыдущий токен
	//The Predecessor character must mark the end of the sentence
	if (t0 == null || t0.type != Token.Type.PUNC ||
	    (t0.text.charAt(0) != '.' && t0.text.charAt(0) != '!' && t0.text.charAt(0) != '?'))
	    return false;
	final Token pt = pos > 1?tokens[pos - 2]:null; //Берём предпредыдущий токен
	//Checking if we have a dot and a single cyrillic letter before it, it could be a name like В. Высоцкий
	if (t0.text.charAt(0) == '.' && pt != null)
	    if (pt.type == Token.Type.CYRIL && isAbbrevWithDot(pt.text))
		return false;
	final Token t2 = pos + 1 < tokens.length?tokens[pos + 1]:null; //Берём следующий токен
	if (t2 == null)
	    return false;
	//If the next token starts with the lower case letter, it is not a sentence end
	if ((t2.type == Token.Type.CYRIL || t2.type == Token.Type.LATIN) && Character.isLowerCase(t2.text.charAt(0)))
	    return false;
	return true;
    }

    private boolean isAbbrevWithDot(String s)
    {
	if (s.length() == 1 && Character.isUpperCase(s.charAt(0)))
	    return true;
	for(String i: abbrevs)
	    if (i.equals(s.toUpperCase()))
		return true;
	return false;
    }
}
