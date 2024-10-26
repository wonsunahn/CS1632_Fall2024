package edu.pitt.cs;

import com.pholser.junit.quickcheck.From;
import com.pholser.junit.quickcheck.Property;
import com.pholser.junit.quickcheck.runner.JUnitQuickcheck;
import org.junit.runner.RunWith;

import static org.junit.Assert.*;
import static org.junit.Assume.assumeThat;
import static org.hamcrest.Matchers.*;
import static org.hamcrest.MatcherAssert.assertThat;

@RunWith(JUnitQuickcheck.class)
public class StringOpsTest {
	
	/**
	 * <pre>
	 * Preconditions: s1 and s2 are strings, where lengths of s1 and s2 differ.
	 * Execution steps: Call StringOps.equals(s1, s2).
	 * Invariants: The return value of call is false.
	 * </pre>
	 * 
	 * @param s1 First string generated by QuickCheck
	 * @param s2 Second string generated by QuickCheck
	 */
	@Property(trials = 1000)
	public void testEquals(String s1, String s2) {
		// System.out.println("testEquals s1='" + s1 + "', s2='" + s2 + "'");
		// TODO: Fill in.
	}

	/**
	 * <pre>
	 * Preconditions: s is a string generated by the ValidHTMLStringGenerator.
	 * Execution steps: Call StringOps.isValidHTML(s).
	 * Invariants: The return value of call is true.
	 * </pre>
	 * 
	 * @param s A string generated by the ValidHTMLStringGenerator
	 */
	@Property(trials = 1000)
	public void testIsValidHTMLTrue(@From(ValidHTMLStringGenerator.class) String s) {
		// System.out.println("testIsValidHTMLTrue s='" + s + "'");
		assertTrue(StringOps.isValidHTML(s));
	}
}