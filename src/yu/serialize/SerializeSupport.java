package yu.serialize;

import java.util.HashMap;
import java.util.Map;

public class SerializeSupport {

	private static Map<Class<?>, Serializer<?>> serializerMap = new HashMap<>();
	private static Map<Byte, Class<?>> typeMap = new HashMap<>();

	static {
		for (Serializer serializer : ServiceSupport.loadAll(Serializer.class)) {
			registerType(serializer.type(), serializer.getClass(), serializer);
		}
	}

	private static byte parseType(byte[] buffer) {
		return buffer[0];
	}

	private static <E> void registerType(byte type, Class<E> eClass, Serializer<E> serializer) {
		serializerMap.put(eClass, serializer);
		typeMap.put(type, eClass);
	}

	@SuppressWarnings("unchecked")
	private static <E> E parse(byte[] buffer, int offset, int length, Class<E> eClass) {
		Object result=serializerMap.get(eClass).parse(buffer, offset, length);
		if(eClass.isAssignableFrom(result.getClass())) {
			return (E) result;
		}else {
			throw new SerializeException("Type mismatch!");
		}

	}

	private static <E> E parse(byte[] buffer, int offset, int length) {
		byte type = parseType(buffer);
		@SuppressWarnings("unchecked")
		Class<E> eClass = (Class<E>) typeMap.get(type);
		if (eClass == null) {
			throw new SerializeException(String.format("Unknown entry type %d!", type));
		}
		return parse(buffer, offset+1, length-1, eClass);

	}

	public static <E> E parse(byte[] buffer) {
		return parse(buffer, 0, buffer.length);
	}

	public static <E> byte[] serialize(E entry) { 
		Serializer<E> serializer=(Serializer<E>) serializerMap.get(entry.getClass());
		if(serializer==null) {
			throw new SerializeException(String.format("Unknown class type :%d", entry.getClass()));
		}
		byte []bytes=new byte[serializer.size(entry)+1];
		bytes[0]=serializer.type();
		serializer.serialize(entry,bytes,1,bytes.length-1);
	}
}
