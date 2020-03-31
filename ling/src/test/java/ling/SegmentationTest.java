
package ling;

import org.junit.*;

public final class SegmentationTest extends Assert
{
    @Test public void general() throws Exception
    {
	final String[] input = new String[]{
	    "В. В. Высоцкий был одним из самых востребованных поэтов. Его творчество",
	    "известно далеко за пределами России. До сих пор невозможно однозначно",
	    "установить, где он брал вдохновение.",
	};
	final Token[][] tokens = new Segmentation().segment(input);
	assertNotNull(tokens);
	assertTrue(tokens.length == 3);
	final String[] lines = new String[tokens.length];
	for(int i = 0;i < lines.length;i++)
	{
	    final StringBuilder b = new StringBuilder();
	    for(Token t: tokens[i])
		b.append(t.text);
	    lines[i] = new String(b);
	}
	assertTrue(lines[0].equals("В. В. Высоцкий был одним из самых востребованных поэтов."));
	assertTrue(lines[1].equals("Его творчество известно далеко за пределами России."));
	assertTrue(lines[2].equals("До сих пор невозможно однозначно установить, где он брал вдохновение."));
    }
}
