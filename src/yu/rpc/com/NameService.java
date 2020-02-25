package yu.rpc.com;

import java.io.IOException;
import java.net.URI;

public interface NameService {
	
	/**
	 * 服务器端注册服务
	 * @param url 服务器端
	 * @param serviceName
	 * @throws IOException
	 */
	 void  registerService(URI url,String serviceName) throws IOException;
	 
	 /**
	  * 查找对应的服务
	  * @param serviceName 服务的名称
	  * @return 服务的地址
	  * @throws IOException 
	  */
	 URI lookupService(String serviceName) throws IOException;

}
