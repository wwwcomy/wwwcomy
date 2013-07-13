import java.util.ArrayList;

public class Parent {
	protected String name = "parent";
	protected String school = "grade_school";

	private ArrayList list;

	public void printName() {
		System.out.println(name);
	}

	public Parent() {
		list = new ArrayList();
		list.add("aaa");
	}

	public ArrayList getList() {
		return list;
	}

	public void setList(ArrayList list) {
		this.list = list;
	}
}
