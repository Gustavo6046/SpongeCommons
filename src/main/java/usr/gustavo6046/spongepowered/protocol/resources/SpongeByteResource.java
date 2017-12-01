package usr.gustavo6046.spongepowered.protocol.resources;

public class SpongeByteResource extends SpongeResource
{
	private static final long serialVersionUID = 1L;
	
	public byte[] byteData;

	@Override
	public byte[] toData()
	{
		return byteData;
	}

	public void setData(byte[] data)
	{
		byteData = data;
	}
	
	public SpongeByteResource(int id, String name)
	{
		super(id, name);
	}
	
	public SpongeByteResource(int id, String name, byte[] data)
	{
		super(id, name);
		setData(data);
	}
}
