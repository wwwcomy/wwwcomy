public class IntReverse {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		int a = 1234;
		int temp = a;
		int k=0;
		while(temp>0) {
			int result = 0;
			result = (int) (temp % 10);
			temp = temp / 10;
//			if(temp<10)
//				k=k*10+result;
			k=k*10+result;
			System.out.println(k);
		}

	}

}
