
package ling;

import java.io.*;
import java.util.*;

public final class Main
{
    static private final Segmentation segmentation = new Segmentation();
	static private final CollectContext collectContext = new CollectContext();
	static private Homograph homograph =  new Homograph();

    static public void main(String[] args) throws Exception
    {
	if (args.length == 0)
	{
	    System.out.println("Usage: ling [DIR1 [DIR2 [...]]]");
	    return;
	}
	for(String a: args)
	{
	    final File f = new File(a);
	    if (f.isFile())
		onFile(f); else
		onDir(f);
	}
		homograph.view(); //Выводим на экран
    }

    static private void onDir(File f) throws IOException
    {
	if (f.isFile() && f.getName().toLowerCase().endsWith(".txt"))
	{
	    onFile(f);
	    return;
	}
	final File[] files = f.listFiles();
	for(File ff: files)
	    onDir(ff);
    }

    static private void onFile(File f) throws IOException
    {
	final List<String> lines = new LinkedList();
	final BufferedReader r = new BufferedReader(new InputStreamReader(new FileInputStream(f)));
	try {
	    String line = r.readLine();
	    while(line != null)
	    {
		lines.add(line);
		line = r.readLine();
	    }
	}
	finally {
	    r.close();
	}
	Token[][] tokens = segmentation.segment(lines.toArray(new String[lines.size()]));
	homograph = collectContext.collect(tokens); //Собираем контекст
	for(int i = 0; i < tokens.length; i++)
	{
	    final Token[] t = tokens[i];
	    final StringBuilder b = new StringBuilder();
	    b.append("[");
	    for(int k = 0; k < t.length; k++)
	    {
		b.append("'").append(t[k].text).append("'");
		if (k + 1 < t.length)
		    b.append(", ");
	    }
	    b.append("]");
	    //System.out.println(new String(b));
	    
	}
    }
}
