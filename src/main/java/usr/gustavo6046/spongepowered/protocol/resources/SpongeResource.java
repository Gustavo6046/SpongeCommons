package usr.gustavo6046.spongepowered.protocol.resources;

import java.io.Serializable;
import java.util.Date;

/**
 * The main resource class. The parent
 * of all resource-holding objects, like
 * textures and audio. 
 *
 * @author Gustavo6046
 */
public abstract class SpongeResource implements Serializable
{
	private static final long serialVersionUID = 1L;
	
	public String name;
	public int id;
	public Date syncTime;
	
	public abstract byte[] toData();
	
	public SpongeResource(int id, String name)
	{
		this.id = id;
		this.name = name;
	}
}
