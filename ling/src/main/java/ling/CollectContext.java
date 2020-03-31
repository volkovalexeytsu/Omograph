package ling;

import java.util.*;

public class CollectContext {

    Homograph collect(Token[][] tokens)
    {
        if (tokens == null)
            throw new NullPointerException("tokens may not be null");
	final Homograph homograph = new Homograph();
		for(int i = 0;i < tokens.length;i++)
	{
	    final Token[] sent = tokens[i];
	    final List<Token> reversedList = new LinkedList();
	    for(int j = sent.length;j >= 0;j--)
		reversedList.add(sent[j]);
	    final Token[] reversed = reversedList.toArray(new Token[reversedList.size()]);
	    for(int j = 0;j < sent.length;j++)
                if (sent[j].text.toUpperCase().equals("СТОИТ"))  {
			homograph.contextRight.save(sent, j, 3);
			homograph.contextLeft.save(reversed, sent.length - 1 - i, 3);
		    }
		            return homograph;
		}
		}
}
