package ir.inabama;

import org.junit.Assert;
import org.junit.Test;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class ApplicationTest {

    public int runMatchTest(String regex, String text) {
        Pattern pattern = Pattern.compile(regex);
        Matcher matcher = pattern.matcher(text);
        int match = 0;
        while (matcher.find()) {
            match ++;
        }
        return match;
    }

    @Test
    public void givenRepeatedText_whenMatchesOnceWithDotMatch_thenCorrect() {
        int matches = runMatchTest("foo.", "foofoo");
        Assert.assertEquals(1, matches);
    }

    @Test
    public void givenORSet_whenMatchesAny_thenCorrect() {
        int matches = runMatchTest("[abc]", "b");
        Assert.assertEquals(1, matches);
    }

    @Test
    public void givenORSet_whenMatchesAnyAndAll_thenCorrect() {
        int matches = runMatchTest("[abc]", "cab");
        Assert.assertEquals(3, matches);
    }

    @Test
    public void givenORSet_whenMatchesAllCombinations_thenCorrect() {
        int matches = runMatchTest("[bcr]at", "bat cat rat");
        Assert.assertEquals(3, matches);
    }

    @Test
    public void givenNORSet_whenMatchesNon_thenCorrect() {
        int matches = runMatchTest("[^abc]", "g");
        Assert.assertTrue(matches > 0);
    }

    @Test
    public void givenNORSet_whenMatchesAllExceptElements_thenCorrect() {
        int matches = runMatchTest("[^bcr]at", "satmateat");
        Assert.assertTrue(matches > 0);
    }

    @Test
    public void givenUpperCaseRange_whenMatchesUpperCase_thenCorrect() {
        int matches = runMatchTest("[a-zA-Z]", "Two Uppercase alphabets 34 overall");
        Assert.assertEquals(28, matches);
    }

    @Test
    public void givenNumberRange_whenMatchesAccurately_thenCorrect() {
        int matches = runMatchTest("[1-5]", "Two Uppercase alphabets 34 overall");
        Assert.assertEquals(2, matches);
    }

    @Test
    public void givenTwoSets_whenMatchesIntersection_thenCorrect() {
        int matches = runMatchTest("[1-6&&[3-9]]", "123456789");
        Assert.assertEquals(4, matches);
    }

    @Test
    public void givenSetWithSubtraction_whenMatchesAccurately_thenCorrect() {
        int matches = runMatchTest("[0-9&&[^2468]]", "123456789");
        Assert.assertEquals(5, matches);
    }

    @Test
    public void givenDigits_whenMatches_thenCorrect() {
        int matches = runMatchTest("\\d", "123");
        Assert.assertEquals(3, matches);
    }

    @Test
    public void givenNonDigits_whenMatches_thenCorrect() {
        int matches = runMatchTest("\\D", "a6c");
        Assert.assertEquals(2, matches);
    }

    @Test
    public void givenWhiteSpace_whenMatches_thenCorrect() {
        int matches = runMatchTest("\\s", "a c");
        Assert.assertEquals(1, matches);
    }

    @Test
    public void givenNonWhiteSpace_whenMatches_thenCorrect() {
        int matches = runMatchTest("\\S", "a c");
        Assert.assertEquals(2, matches);
    }

    @Test
    public void givenZeroOrOneQuantifier_whenMatches_thenCorrect() {
        int matches = runMatchTest("\\a{1,}", "hi");
        Assert.assertEquals(0, matches);
    }

    @Test
    public void givenBraceQuantifier_whenMatches_thenCorrect() {
        int matches = runMatchTest("a{3}", "aaaaaaaa");
        Assert.assertEquals(2, matches);
    }
}
