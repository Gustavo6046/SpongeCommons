package usr.gustavo6046.spongepowered.protocol.reschunk;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.util.Date;
import java.util.LinkedHashMap;
import java.util.LinkedList;

import usr.gustavo6046.spongepowered.protocol.exceptions.EndlessLoopException;
import usr.gustavo6046.spongepowered.protocol.resources.SpongeByteResource;

public class SpongeResourceChunkReader extends LinkedList<SpongeByteResource>
{
	private static final long serialVersionUID = 2L;
	
	public long readChunks(byte[] chunksToRead)
	throws IOException, ClassNotFoundException, EndlessLoopException
	{
		ByteArrayInputStream bstream = new ByteArrayInputStream(chunksToRead);
		ObjectInputStream ostream = new ObjectInputStream(bstream);
		long totalRead = 0;
		long safeLimit = (2 ^ 24) - 1;
		long safeResLimit = safeLimit;
		
		while ( safeLimit-- > 0 )
		{
			Date time;
			
			if ( !ostream.readUTF().equals("RCHK") )
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
				ostream.readLong();
			}
			
			catch ( IOException err )
			{
				return totalRead;
			}
			
			while ( true )
			{
				if ( safeResLimit-- <= 0 )
					throw new EndlessLoopException();
				
				add(new SpongeByteResource(ostream.readInt(), ostream.readUTF()));
				peek().syncTime = time;
				ByteArrayOutputStream dataStream = new ByteArrayOutputStream();
				long dataLen = ostream.readLong();
				
				for ( int i = 0; i++ < dataLen; )
					dataStream.write(new byte[] { ostream.readByte() });
				
				peek().setData(dataStream.toByteArray());
			}
		}
		
		throw new EndlessLoopException();
	}
	
	public LinkedHashMap<Integer, SpongeByteResource> getResourceMap()
	{
		LinkedHashMap<Integer, SpongeByteResource> map = new LinkedHashMap<>();
				
		for ( SpongeByteResource res : this )
			map.put(res.id, res);
		
		return map;
	}
}
