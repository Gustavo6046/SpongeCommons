package usr.gustavo6046.spongepowered.protocol.objchunk;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.util.Date;
import java.util.LinkedList;

/**
 * SpongeChunkWriter is the main class that allows for
 * writing the Object Chunks that compose each
 * synchronization request.
 *
 * @author Gustavo6046
 */
public class SpongeObjectChunkWriter
{
	private LinkedList<Serializable> queue = new LinkedList<>();

	public boolean addObject(Serializable obj)
	{
		return queue.add(obj);
	}
	
	public int addChunk(SpongeObjectChunk obj)
	{
		int successes = 0;
		
		for ( Serializable ser : obj )
			if ( queue.add(ser) )
				successes++;
		
		return successes;
	}
	
	public byte[] serialize()
	throws IOException
	{
		ByteArrayOutputStream bstream = new ByteArrayOutputStream();
		ObjectOutputStream ostream = new ObjectOutputStream(bstream);

		//================
		// HEADER
		ostream.writeUTF("OCHK"); // Object Chunk signature
		ostream.writeLong(new Date().getTime()); // temporal dimension
		ostream.writeLong(queue.size()); // spatial dimension
		//================
		
		while ( !queue.isEmpty() )
			ostream.writeObject(queue.poll());
		
		ostream.writeChar(';');
		ostream.close();
		
		return bstream.toByteArray();
	}
}
