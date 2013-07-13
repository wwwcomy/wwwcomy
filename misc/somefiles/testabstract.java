public abstract class testabstract {
	public static int a = 2;

	public static void main(String[] args) {
		System.out.println(a);
		System.out.println(B());
	}

	public abstract String toString();

	public static String B() {
		return "s";
	}

	abstract int A();
}
