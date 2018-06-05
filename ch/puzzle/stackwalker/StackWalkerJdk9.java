package ch.puzzle.stackwalker;

import java.lang.StackWalker.StackFrame;
import java.util.List;
import java.util.function.Function;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 * An efficient standard API for stack walking that allows easy filtering and
 * lazy access to stack trace information.
 * 
 * <li>API based on StackFrame
 * <li>use foreach to traverse all StackFrames
 * <li>use walk to all or a subset of the StackFrames
 * <li>walk() opens a sequential stream of StackFrames for the current thread
 * and then applies the given function to walk the StackFrame stream.
 * 
 * References:
 * <li>Michael Inden: Java 9 – Die Neuerungen, dpunkt.de
 * <li>http://www.baeldung.com/java-9-stackwalking-api
 * <li>https://www.javaworld.com/article/3188289/core-java/java-9s-other-new-enhancements-part-5-stack-walking-api.html
 */
public class StackWalkerJdk9 {

	public void a() {
		b();
	}

	public void b() {
		c();
	}

	public void c() {
		System.out.println("\n--- java9.walk foreach only ------------------------------------");
		printStackframes_foreach();

		System.out.println("\n--- java9.walk with method reference ---------------------------");
		printStackframes1();

		System.out.println("\n--- java9.walk with a method that returns a function -----------");
		printStackframes2();

		System.out.println("\n--- java9.walk with a method that filters by class name ---------");
		printStackframes3();

		System.out.println("\n--- java9.walk with a method that converts StackFrames to String-");
		printStackframes4();
	}

	private void printStackframes_foreach() {
		StackWalker.getInstance() //
				.forEach(System.out::println);
	}

	/**
	 * walk() with a method that implements the Function Interface, which converts a
	 * <Stream<StackFrame> into a List<StackFrame>
	 * 
	 * public <T> T walk(Function<? super Stream<StackFrame>, ? extends T> function)
	 * 
	 * @return
	 */
	private void printStackframes1() {
		StackWalker.getInstance() //
				.walk(this::walkFunction1) //
				.forEach(System.out::println);
	}

	private List<StackFrame> walkFunction1(Stream<StackFrame> stackFrameStream) {
		return stackFrameStream.collect(Collectors.toList());
	}

	/**
	 * walk() with a method that returns a function, which converts a
	 * <Stream<StackFrame> into a List<StackFrame>
	 * 
	 */
	private void printStackframes2() {
		StackWalker.getInstance() //
				.walk(walkFunction2()) //
				.forEach(System.out::println);
	}

	private Function<Stream<StackFrame>, List<StackFrame>> walkFunction2() {
		return (stream) -> stream.collect(Collectors.toList());
	}

	private void printStackframes3() {
		StackWalker.getInstance() //
				.walk(walkFunction3("ch.puzzle")) //
				.forEach(System.out::println);
	}

	private Function<Stream<StackFrame>, List<StackFrame>> walkFunction3(String filter) {
		return (stream) -> stream.filter(f -> f.getClassName().contains(filter)).collect(Collectors.toList());
	}

	private void printStackframes4() {
		StackWalker.getInstance() //
				.walk(walkFunction4()) //
				.forEach(System.out::println);
	}

	private Function<Stream<StackFrame>, List<String>> walkFunction4() {
		return stream -> {
			return stream.map(frame -> format(frame)) //
					.skip(1) //
					.collect(Collectors.toList());
		};
	}

	private String format(StackFrame frame) {
		return frame.getClassName() + "." + frame.getMethodName() + "@line:" + frame.getLineNumber();
	}

	public static void main(final String[] args) {
		new StackWalkerJdk9().a();
	}

}
