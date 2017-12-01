package usr.gustavo6046.spongepowered.protocol.reschunk;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.util.Date;
import java.util.LinkedHashMap;
import java.util.Map.Entry;

import usr.gustavo6046.spongepowered.protocol.resources.SpongeByteResource;

public class SpongeResourceChunkWriter
{
	public LinkedHashMap<Integer, SpongeByteResource> resourceMap = new LinkedHashMap<>();
	
	public void addResource(SpongeByteResource resource)
	{
		resourceMap.put(resource.id, resource);
	}
	
	public byte[] serialize()
	throws IOException
	{
		ByteArrayOutputStream bstream = new ByteArrayOutputStream();
		ObjectOutputStream ostream = new ObjectOutputStream(bstream);

		//================
		// HEADER
		ostream.writeUTF("RCHK"); // Object Chunk signature
		ostream.writeLong(new Date().getTime()); // temporal dimension
		ostream.writeLong(resourceMap.size()); // spatial dimension
		//================
		
		for ( Entry<Integer, SpongeByteResource> entry : resourceMap.entrySet() )
		{
			byte[] targ = entry.getValue().toData();
			ostream.writeInt(entry.getKey());
			ostream.writeUTF(entry.getValue().name);
			ostream.writeLong(targ.length);
			ostream.flush();
			
			bstream.write(targ);
		}
		
		ostream.writeChar(';');
		ostream.close();
		
		return bstream.toByteArray();
	}
}
