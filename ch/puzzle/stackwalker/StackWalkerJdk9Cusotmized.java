package ch.puzzle.stackwalker;

import java.lang.StackWalker.StackFrame;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 * Customize the default behaviour of the StackWalker.
 * 
 * References:
 * <li>Michael Inden: Java 9 – Die Neuerungen, dpunkt.de
 * <li>http://www.baeldung.com/java-9-stackwalking-api
 * <li>https://www.javaworld.com/article/3188289/core-java/java-9s-other-new-enhancements-part-5-stack-walking-api.html
 */
public class StackWalkerJdk9Cusotmized {

	public void a() {
		b();
	}

	public void b() {
		c();
	}

	public void c() {
		System.out.println("\n--- java9.customized reflection ----------------------------");
		printStackframes1();

		System.out.println("\n--- java9.customized hidden frames -------------------------");
		printStackframes2();

		System.out.println("\n--- java9.customized find caller ---------------------------");
		findCaller();
	}

	// capturing the reflection frames
	private void printStackframes1() {
		StackWalker //
				.getInstance(StackWalker.Option.SHOW_REFLECT_FRAMES).walk(this::walkFunction) //
				.forEach(System.out::println);
	}

	// capturing hidden frames (SHOW_HIDDEN_FRAMES is a superset of
	// SHOW_REFLECT_FRAMES)
	private void printStackframes2() {
		Runnable r = () -> {
			StackWalker //
					.getInstance(StackWalker.Option.SHOW_HIDDEN_FRAMES) //
					.walk(this::walkFunction) //
					.forEach(System.out::println);
		};
		r.run();
	}

	private List<StackFrame> walkFunction(Stream<StackFrame> stackFrameStream) {
		return stackFrameStream //
				.collect(Collectors.toList());
	}

	// identifying the calling Class
	public void findCaller() {
		Class<?> caller = StackWalker //
				.getInstance(StackWalker.Option.RETAIN_CLASS_REFERENCE) //
				.getCallerClass();
		System.out.println(caller.getCanonicalName());
	}

	public static void main(String[] args) {
		new StackWalkerJdk9Cusotmized().a();
	}
}
