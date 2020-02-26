package yu.server;

public class HelloServiceImpl implements HelloService{

	@Override
	public String hello(String word) {
		// TODO Auto-generated method stub
		return "rpc method "+word;
		
	}
	

}
