package ch.puzzle.stackwalker;

import java.util.Arrays;

/**
 * Working with StackTraces before Java 9
 * <li>API based on StackTraceElement
 * <li>describes an element representing a stack frame in a stack trace
 * <li>entry point: Thread.currentThread().getStackTrace()
 * 
 * <p>
 * Problems
 * </p>
 * <li>The Java Virtual Machine (JVM) eagerly captures a snapshot of the entire
 * stack (except for hidden stack frames), even when you only need the first few
 * frames.
 * <li>you cannot access the actual java.lang.Class instance of the class that
 * declared the method represented by a stack frame.
 * 
 * References:
 * <li>Michael Inden: Java 9 – Die Neuerungen, dpunkt.de
 * <li>http://www.baeldung.com/java-9-stackwalking-api
 * <li>https://www.javaworld.com/article/3188289/core-java/java-9s-other-new-enhancements-part-5-stack-walking-api.html
 */
public class StackTraceJdk8 {

	public void a() {
		b();
	}

	public void b() {
		c();
	}

	public void c() {
		System.out.println("\n--- java8.all ---------------------------");
		printStackframes1();

		System.out.println("\n--- java8.skip & limit ------------------");
		printStackframes2(10);

		System.out.println("\n--- java8.filter by class name ----------");
		printStackframes3("ch.puzzle");
	}

	private void printStackframes1() {
		Arrays.stream(Thread.currentThread().getStackTrace()) //
				.forEach(System.out::println);
	}

	private void printStackframes2(int maxSize) {
		Arrays.stream(Thread.currentThread().getStackTrace()) //
				.skip(2) //
				.limit(maxSize) //
				.forEach(System.out::println);
	}

	private void printStackframes3(String filter) {
		Arrays.stream(Thread.currentThread().getStackTrace()) //
				.filter(f -> f.getClassName().contains(filter)) //
				.forEach(System.out::println);
	}

	public static void main(final String[] args) {
		new StackTraceJdk8().a();
	}

}
