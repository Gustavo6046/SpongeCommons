package usr.gustavo6046.spongepowered.protocol.objchunk;

import java.io.Serializable;
import java.util.Date;
import java.util.LinkedList;

public class SpongeObjectChunk extends LinkedList<Serializable>
{
	private static final long serialVersionUID = 8955676061151883610L;
	public Date syncTime;
}
