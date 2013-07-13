public class Child extends Parent {
	private String name = "child";
	private String school = "colleage_school";

	public static void main(String[] args) {
		Parent parent = new Child();
		parent.printName();
		System.out.println(parent.school);
	}

	public void printName() {
		System.out.println(name);
	}
}