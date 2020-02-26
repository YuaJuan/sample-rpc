package yu.serialize;

import java.nio.charset.StandardCharsets;
import java.sql.Types;


public class StringSerializer implements Serializer<String>{

	@Override
	public int size(String entry) {
		// TODO Auto-generated method stub
		return entry.getBytes(StandardCharsets.UTF_8).length;
	}

	@Override
	public void serialize(String entry, byte[] bytes, int offset, int length) {
		byte []strByte=entry.getBytes(StandardCharsets.UTF_8);
		System.arraycopy(strByte, 0, bytes, offset, strByte.length);
		// TODO Auto-generated method stub
		
	}

	@Override
	public String parse(byte[] bytes, int offset, int length) {
		// TODO Auto-generated method stub
		return  new String(bytes, offset, length, StandardCharsets.UTF_8);
	}

	@Override
	public byte type() {
		// TODO Auto-generated method stub
		return Types.TYPE_STRING;
	}

	@Override
	public Class<String> getSerializeClass() {
		// TODO Auto-generated method stub
		return String.class;
	}

	

	

}
