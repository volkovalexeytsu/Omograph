package ling;

import java.util.HashMap;

public class ContextInfo {
    String word;
    HashMap<String, ContextInfo> next = new HashMap();

    void save(Token[] tokens, int startFrom, int level)
    {
	if (level == 0)
	    return;
	int pos = startFrom;
	while(pos < tokens.length && tokens[pos].type != Token.Type.CYRIL)
	    pos++;
	if (pos >= tokens.length)
	    return;
	final String word = tokens[pos].text.toUpperCase();
	if (!next.containsKey(word))
	    next.put(word, new ContextInfo());
	final ContextInfo nextInfo = next.get(word);
	nextInfo.save(tokens, pos + 1, level - 1);
    }
}
