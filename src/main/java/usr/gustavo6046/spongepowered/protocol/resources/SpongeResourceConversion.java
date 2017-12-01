package usr.gustavo6046.spongepowered.protocol.resources;

public abstract class SpongeResourceConversion
{
	static Class<Object> conversionType = null;
	
	public abstract SpongeByteResource toResource(Object Other);
	public abstract Object fromResource(SpongeByteResource Other);
}
