public class Pass {
	public static void test(StringBuffer str)

	{
		str = new StringBuffer("World");
		// = 赋值，而不是 append 操作
	}

	public static void main(String[] args)

	{
		StringBuffer string = new StringBuffer("Hello");
		System.out.println(string);
		test(string);
		System.out.println(string);
		// what is it now ?
	}
}