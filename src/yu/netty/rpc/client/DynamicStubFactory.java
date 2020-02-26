package yu.netty.rpc.client;
import java.util.Map;

import com.github.liyue2008.rpc.client.ServiceStub;
import com.itranswarp.compiler.JavaStringCompiler;

public class DynamicStubFactory implements StubFactory{
	private final static String STUB_SOURCE_TEMPLATE= 
			 "package yu.netty.rpc.client.stubs;\n" +
			            "import yu.serialize.SerializeSupport;\n" +
			            "\n" +
			            "public class %s extends AbstractStub implements %s {\n" +
			            "    @Override\n" +
			            "    public String %s(String arg) {\n" +
			            "        return SerializeSupport.parse(\n" +
			            "                invokeRemote(\n" +
			            "                        new RpcRequest(\n" +
			            "                                \"%s\",\n" +
			            "                                \"%s\",\n" +
			            "                                SerializeSupport.serialize(arg)\n" +
			            "                        )\n" +
			            "                )\n" +
			            "        );\n" +
			            "    }\n" +
			            "}";

	@SuppressWarnings("unchecked")
	@Override
	public <T> T createStub(Transport transport, Class<T> serviceClass) {
		
		String stubSimpleName=serviceClass.getSimpleName()+"Stub";
		String classFullName=serviceClass.getName();
		String stubFullName=""+stubSimpleName;
		String methodName=serviceClass.getMethods()[0].getName();
		String source=String.format(STUB_SOURCE_TEMPLATE, stubSimpleName,classFullName,methodName);
		JavaStringCompiler compiler=new JavaStringCompiler();
		Map<String,byte[]> results=compiler.compile(stubSimpleName +".java",source);
		Class<?> myclass=compiler.loadClass(stubFullName, results);
		
		//
		// 把Transport赋值给桩
        ServiceStub stubInstance = (ServiceStub) myclass.newInstance();
        stubInstance.setTransport(transport);
        // 返回这个桩
        return (T) stubInstance;
		
		
	}
	
	
	
	
	

}
