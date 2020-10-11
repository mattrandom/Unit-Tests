package testing.account;

import org.junit.jupiter.api.Test;

import java.util.List;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.hasSize;

class AccountServiceStubTest {

    @Test
    void getAllActiveAccounts() {
        //given
        AccountRepositoryStub stub = new AccountRepositoryStub();
        AccountService accountService = new AccountService(stub);

        //when
        List<Account> accountList = accountService.getAllActiveAccounts();

        //then
        assertThat(accountList, hasSize(2));
    }
}