package LineCoverageTest;

import Palindrome;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

class Exo1Test {
    @Test void nullInput()      { assertThrows(NullPointerException.class, () -> Palindrome.isPalindrome(null)); }
    @Test void simplePalindrome() { assertTrue(Palindrome.isPalindrome("kayak")); }
    @Test void notPalindrome()    { assertFalse(Palindrome.isPalindrome("hello")); }
    @Test void withSpaces()       { assertTrue(Palindrome.isPalindrome("Esope reste ici et se repose")); }
}