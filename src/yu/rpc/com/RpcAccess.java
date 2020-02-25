package yu.rpc.com;

import java.io.Closeable;
import java.net.URI;

public interface RpcAccess extends Closeable {
	/**
	 * 客户端获取远程服务的引用
	 * @param <T> 服务接口的类型
	 * @param uri 远程服务的地址
	 * @param serviceClass 服务的接口类
	 * @return <T> 远程服务引用
	 */
	<T> T getRemoteService(URI uri,Class<T> serviceClass);
	/**
	 * 服务器端注册服务实例
	 * @param <T> 服务接口类型
	 * @param service  实现实例
	 * @param serviceClass 服务的接口类
	 * @return 服务地址
	 */
	
	<T> URI addServiceProvider(T service,Class<T> serviceClass);
	Closeable startServer() throws Exception;

}
