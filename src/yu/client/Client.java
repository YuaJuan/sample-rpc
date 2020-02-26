package yu.client;

import java.net.URI;

import yu.rpc.api.RpcAccess;
import yu.server.HelloService;

public class Client {

	public static void main(String []args) {
		RpcAccess rpcAccess;
		URI url=null;
		HelloService helloservice=rpcAccess.getRemoteService(url, HelloService.class);
		String response=helloservice.hello("client");
		System.out.println("get response"+response);
		
	}
}
