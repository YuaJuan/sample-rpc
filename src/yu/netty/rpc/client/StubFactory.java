package yu.netty.rpc.client;

public interface StubFactory {
	<T> T createStub(Transport transport,Class<T> serviceClass);

}
