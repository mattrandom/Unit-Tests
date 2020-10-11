package testing.account;

import org.junit.jupiter.api.RepeatedTest;
import org.junit.jupiter.api.Test;
import testing.account.Account;
import testing.account.Address;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.*;
import static org.junit.jupiter.api.Assertions.*;
import static org.junit.jupiter.api.Assumptions.assumingThat;

class AccountTest {

    @Test
    void newAccountShouldNotBeActiveAfterCreation() {
        //given
        Account newAccount = new Account();

        //then
        assertFalse(newAccount.isActive());
        assertThat(newAccount.isActive(), equalTo(false));
        assertThat(newAccount.isActive(), is(false));
    }

    @Test
    void accountShouldBeActiveAfterActivation() {
        //given
        Account newAccount = new Account();

        //when
        newAccount.activate();

        //then
        assertTrue(newAccount.isActive());
        assertThat(newAccount.isActive(), equalTo(true));
        assertThat(newAccount.isActive(), is(true));
    }

    @Test
    void newlyCreatedAccountShouldNotHaveDefaultDeliveryAddressSet() {
        //given
        Account newAccount = new Account();

        //when
        Address defaultDeliveryAddress = newAccount.getDefaultDeliveryAddress();

        //then
        assertNull(defaultDeliveryAddress);
        assertThat(defaultDeliveryAddress, is(nullValue()));

    }

    @Test
    void defaultDeliveryAddressShouldNotBeNullAfterBeingSet() {
        //given
        Address address = new Address("Poznańska", "666");
        Account account = new Account();
        account.setDefaultDeliveryAddress(address);

        //when
        Address defaultAddress = account.getDefaultDeliveryAddress();

        //then
        assertNotNull(defaultAddress);
        assertThat(defaultAddress, is(notNullValue()));
    }

    @RepeatedTest(5)
    void newAccountWithNotNullAddressShouldBeActive() {
        //given
        Address defaultAddress = new Address("Posen Avenue", "66");

        //when
        Account account = new Account(defaultAddress);

        //then
        assumingThat(defaultAddress != null, () -> {
            assertTrue(account.isActive());
            assertThat(account.isActive(), is(true));
            assertNotNull(defaultAddress);
            assertThat(defaultAddress, is(notNullValue()));
        });
    }

    @Test
    void invalidEmailShouldThrowException() {
        //given
        Account account = new Account();

        //when
        //then
        assertThrows(IllegalArgumentException.class, () -> account.setEmail("wrongEmail"));
    }

    @Test
    void validEmailShouldBeSet() {
        //given
        Account account = new Account();

        //when
        account.setEmail("username123@gmail.com");

        //then
        assertThat(account.getEmail(), is("username123@gmail.com"));

        assertThat(account.getEmail(), matchesRegex("^[A-Za-z0-9._%+-]+@[A-Za-z0-9.-]+\\.[A-Za-z]{2,6}$"));

        //when & then
        assertDoesNotThrow(() -> account.setEmail("username123@gmail.com"));
    }
}
