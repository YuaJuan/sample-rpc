package yu.rpc.nameserviceImpl;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.RandomAccessFile;
import java.net.URI;
import java.nio.ByteBuffer;
import java.nio.channels.FileChannel;
import java.nio.channels.FileLock;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;

import com.github.liyue2008.rpc.nameservice.Metadata;
import com.github.liyue2008.rpc.serialize.SerializeSupport;

import yu.rpc.api.NameService;

public class LocalFileNameService implements NameService {
	private static final Collection<String> schemes = Collections.singleton("file");
	private File file;

	@Override
	public void registerService(URI uri, String serviceName) throws IOException {
		RandomAccessFile raf = new RandomAccessFile(file, "rw");
		FileChannel filechannel = raf.getChannel();
		FileLock lock = filechannel.lock();
		try {
			int fileLength = (int) raf.length();
			byte[] bytes;
			if (fileLength > 0) {
				Metadata metadata=new Metadata();
				bytes = new byte[(int) raf.length()];
				ByteBuffer buffer = ByteBuffer.wrap(bytes);
				while (buffer.hasRemaining()) {
					filechannel.read(buffer);
				}
				metadata = SerializeSupport.parse(bytes);
			} else {
				metadata = new Metadata();
			}
			List<URI> uris = metadata.computeIfAbsent(serviceName, k -> new ArrayList<>());
			if (!uris.contains(uri)) {
				uris.add(uri);
			}
			bytes=yu.serialize.SerializeSupport.serialize(metadata);
			 filechannel.truncate(bytes.length);
             filechannel.position(0L);
             filechannel.write(ByteBuffer.wrap(bytes));
             filechannel.force(true);

		} finally {
			lock.close();
		}

		// TODO Auto-generated method stub

	}

	@Override
	public URI lookupService(String serviceName) throws IOException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Collection<String> supportedSchemes() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void connect(URI nameServiceUri) {
		// TODO Auto-generated method stub
		if (schemes.contains(nameServiceUri.getScheme())) {
			file = new File(nameServiceUri);
		} else {
			throw new RuntimeException("Unsupported scheme!");
		}

	}

}
