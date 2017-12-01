package usr.gustavo6046.spongepowered.protocol.serialization;

import java.io.ByteArrayOutputStream;
import java.io.IOException;

import org.spongepowered.api.data.DataContainer;
import org.spongepowered.api.data.DataQuery;
import org.spongepowered.api.data.DataSerializable;

import usr.gustavo6046.spongepowered.protocol.objchunk.SpongeObjectChunk;
import usr.gustavo6046.spongepowered.protocol.objchunk.SpongeObjectChunkWriter;
import usr.gustavo6046.spongepowered.protocol.reschunk.SpongeResourceChunkWriter;
import usr.gustavo6046.spongepowered.protocol.resources.SpongeByteResource;

public class SpongeChunkHolder implements DataSerializable
{
	public SpongeResourceChunkWriter rwriter = new SpongeResourceChunkWriter();
	public SpongeObjectChunkWriter owriter = new SpongeObjectChunkWriter(); 

	public void addResource(SpongeByteResource res)
	{
		rwriter.addResource(res);
	}
	
	public void addObject(SpongeObjectChunk chunk)
	{
		owriter.addChunk(chunk);
	}
	
	public byte[] serialize()
	throws IOException
	{
		ByteArrayOutputStream bstream = new ByteArrayOutputStream();
		
		bstream.write(rwriter.serialize());
		bstream.write(owriter.serialize());
		
		return bstream.toByteArray();
	}
	
	@Override
	public int getContentVersion()
	{
		return 0;
	}

	@Override
	public DataContainer toContainer()
	{
		DataContainer cont = DataContainer.createNew();
		
		try
		{
			cont.set(DataQuery.of('.', "binary"), serialize());
		} catch (IOException e)
		{
			return null;
		}
		
		return cont;
	}
}
