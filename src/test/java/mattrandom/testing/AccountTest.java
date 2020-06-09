package mattrandom.testing;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertFalse;

public class AccountTest {

    @Test
    public void myTest() {
        Account newAccount = new Account();

        assertFalse(newAccount.isActive());
    }
}
