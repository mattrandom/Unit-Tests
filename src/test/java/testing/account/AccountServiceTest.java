package testing.account;

import org.junit.jupiter.api.Test;
import org.mockito.junit.jupiter.MockitoSettings;
import org.mockito.quality.Strictness;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.*;
import static org.mockito.BDDMockito.given;
import static org.mockito.BDDMockito.when;
import static org.mockito.Mockito.mock;

@MockitoSettings(strictness = Strictness.STRICT_STUBS)
class AccountServiceTest {

    @Test
    void getAllActiveAccounts() {
        //given
        List<Account> accounts = prepareAccountData();

        AccountRepository accountRepositoryMock = mock(AccountRepository.class);
        AccountService accountService = new AccountService(accountRepositoryMock);

        when(accountRepositoryMock.getAllAccounts()).thenReturn(accounts); // First way
        given(accountRepositoryMock.getAllAccounts()).willReturn(accounts); // Second way

        //when
        List<Account> accountList = accountService.getAllActiveAccounts();

        //then
        assertThat(accountList, hasSize(2));
    }

    private List<Account> prepareAccountData() {
        Address address1 = new Address("Kwiatowa", "33/5");
        Account account1 = new Account(address1);

        Account account2 = new Account();

        Address address2 = new Address("Piekarska", "12b");
        Account account3 = new Account(address2);

        return Arrays.asList(account1, account2, account3);
    }

    @Test
    void getNoActiveAccounts() {
        //given
        AccountRepository accountRepositoryMock = mock(AccountRepository.class);
        AccountService accountService = new AccountService(accountRepositoryMock);
        given(accountRepositoryMock.getAllAccounts()).willReturn(Collections.emptyList());

        //when
        List<Account> noActiveAccountsList = accountService.getAllActiveAccounts();

        //then
        assertThat(noActiveAccountsList, emptyCollectionOf(Account.class)); // I
        assertThat(noActiveAccountsList, hasSize(0)); // II
    }

    @Test
    void getAccountsByName() {
        //given
        AccountRepository accountRepositoryMock = mock(AccountRepository.class);
        AccountService accountService = new AccountService(accountRepositoryMock);

        given(accountRepositoryMock.getByName("Mateo")).willReturn(Collections.singletonList("Random"));

        //when
        List<String> accountsName = accountService.findByName("Mateo");

        //then
        assertThat(accountsName, contains("Random"));
    }
}