package usr.gustavo6046.spongepowered.protocol.objchunk;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.Serializable;
import java.util.Date;
import java.util.LinkedList;
import java.util.Optional;

import usr.gustavo6046.spongepowered.protocol.exceptions.EndlessLoopException;

public class SpongeObjectChunkReader extends LinkedList<SpongeObjectChunk>
{
	private static final long serialVersionUID = 2L;
	private SpongeObjectChunk _currentPoll;
	
	/**
	 * Polls the first object from the first chunk
	 * in this reader. 
	 * 
	 * @return An Optional object that may contain
	 *         the polled object.
	 */
	public Optional<Serializable> pollObject()
	{
		if ( _currentPoll == null || _currentPoll.isEmpty() )
		{
			if ( (_currentPoll = poll()) == null )
				return Optional.empty();
			
			else
				return Optional.of(_currentPoll.poll());
		}
		
		else
			return Optional.of(_currentPoll.poll());
	}
	
	public long readChunks(byte[] chunksToRead)
	throws IOException, ClassNotFoundException, EndlessLoopException
	{
		ByteArrayInputStream bstream = new ByteArrayInputStream(chunksToRead);
		ObjectInputStream ostream = new ObjectInputStream(bstream);
		long totalRead = 0;
		long safeLimit = (2 ^ 24) - 1;
		
		while ( safeLimit-- > 0 )
		{
			Date time;
			long chunkSize;
			
			if ( !ostream.readUTF().equals("OCHK") )
				return totalRead;
			
			try
			{
				time = new Date(ostream.readLong());
			}
			
			catch ( IOException err )
			{
				return totalRead;
			}
			
			try
			{
				chunkSize = ostream.readLong();
			}
			
			catch ( IOException err )
			{
				return totalRead;
			}
			
			SpongeObjectChunk chunk = new SpongeObjectChunk();
			chunk.syncTime = time;
			
			while ( chunkSize-- > 0 )
				chunk.add((Serializable) ostream.readObject());
			
			totalRead++;
			add(chunk);

			if ( ostream.readChar() != ';' )
				return totalRead;
		}
		
		throw new EndlessLoopException();
	}
}
