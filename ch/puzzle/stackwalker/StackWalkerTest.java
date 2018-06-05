package ch.puzzle.stackwalker;

import org.junit.jupiter.api.Test;

/**
 * JUnit tests for the StackWalker examples.
 * 
 * References:
 * <li>Michael Inden: Java 9 – Die Neuerungen, dpunkt.de
 * <li>http://www.baeldung.com/java-9-stackwalking-api
 * <li>https://www.javaworld.com/article/3188289/core-java/java-9s-other-new-enhancements-part-5-stack-walking-api.html
 */
class StackWalkerTest {

	@Test
	public void test_java8() {
		new StackTraceJdk8().a();
	}

	@Test
	public void test_java9() {
		new StackWalkerJdk9().a();
	}

	@Test
	public void test_stackWalker_whenWalkingTheStack_thenShowStackFrames() {
		new StackWalkerJdk9Cusotmized().a();
	}

	@Test
	public void test_stackwalker_giveStalkWalker_whenInvokingFindCaller_thenFindCallingClass() {
		new StackWalkerJdk9Cusotmized().findCaller();
	}
}
