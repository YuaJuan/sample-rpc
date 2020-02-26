package yu.server;

import yu.rpc.api.NameService;
import yu.rpc.api.RpcAccess;

public class Server {
	public static void main(String []args) {
		RpcAccess rpcAccess;
		rpcAccess.startServer();
		HelloService helloservice=new HelloServiceImpl();
		rpcAccess.addServiceProvider(helloservice, HelloService.class);
		NameService nameService;
		nameService.registerService("", helloservice);
	}

}
