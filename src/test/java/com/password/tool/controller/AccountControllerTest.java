package com.password.tool.controller;

import com.password.tool.dto.AccountDto;
import com.password.tool.exception.AccountDoesNotExistException;
import com.password.tool.exception.DuplicateAccountException;
import com.password.tool.service.interfaces.AccountService;
import com.password.tool.service.interfaces.GroupService;
import com.password.tool.service.interfaces.UserService;
import org.junit.Before;
import org.junit.Test;
import org.junit.jupiter.api.DisplayName;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.forwardedUrl;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.view;

@RunWith(SpringRunner.class)
@WebMvcTest(value = AccountController.class)

public class AccountControllerTest {
    @MockBean
    AccountService accountService;
    @MockBean
    UserService userService;
    @MockBean
    GroupService groupService;
    @Autowired
    MockMvc mockMvc;
    AccountDto accountDto;

    @Before
    public void setUp(){
        accountDto = new AccountDto();
    }

    @Test
    @DisplayName("getAccountById Should Give alterAccount page If Account Exist")
    public void getAccountByIdShouldGiveAlterAccountPageIfAccountExist() throws Exception {
        when(accountService.getAccountById(anyInt())).thenReturn(accountDto);
        mockMvc.perform(MockMvcRequestBuilders
                        .get("/accounts/account/2"))
                        .andExpect(view().name("alterAccount"))
                .andExpect(forwardedUrl("/WEB-INF/jsp/alterAccount.jsp"));
    }

    @Test
    @DisplayName("addAccount Should Give alterAccount page")
    public void addAccountShouldReturnAlterAccountPage() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders
                        .get("/accounts/account"))
                .andExpect(view().name("alterAccount"))
                .andExpect(forwardedUrl("/WEB-INF/jsp/alterAccount.jsp"));
    }

    @Test
    @DisplayName("getAccountById Should Give Error page If Account not Exist")
    public void getAccountByIdShouldGiveErrorPageIfAccountNotExist() throws Exception {
        doThrow(AccountDoesNotExistException.class).when(accountService).getAccountById(anyInt());
        mockMvc.perform(MockMvcRequestBuilders
                        .get("/accounts/account/2"))
                .andExpect(view().name("error"))
                .andExpect(forwardedUrl("/WEB-INF/jsp/error.jsp"));
    }

    @Test
    @DisplayName("deleteAccount Should redirect to viewAccounts page If Account Exist and deleted")
    public void deleteShouldRedirectToViewAccountsPageIfAccountExist() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders
                        .post("/accounts/account/delete/2"))
                .andExpect(view().name("redirect:/groups"));
    }

    @Test
    @DisplayName("deleteAccount Should Give Error page If Account not Exist")
    public void deleteAccountShouldGiveErrorPageIfAccountNotExist() throws Exception {
        doThrow(AccountDoesNotExistException.class).when(accountService).removeAccountById(anyInt());
        mockMvc.perform(MockMvcRequestBuilders
                        .post("/accounts/account/delete/2"))
                .andExpect(view().name("error"))
                .andExpect(forwardedUrl("/WEB-INF/jsp/error.jsp"));
    }

    @Test
    @DisplayName("saveAccount Should Give same page with error for invalid account")
    public void saveAccountShouldGiveSamePageWithErrorForInvalidAccount() throws Exception {
        AccountDto accountDto = new AccountDto("","","","");
        mockMvc.perform(MockMvcRequestBuilders
                        .post("/accounts/account")
                        .flashAttr("accountBean",accountDto))
                .andExpect(view().name("alterAccount"))
                .andExpect(forwardedUrl("/WEB-INF/jsp/alterAccount.jsp"));
    }

    @Test
    @DisplayName("saveAccount Should Give same page with error If Account is duplicate")
    public void saveAccountShouldGiveSamePageWithErrorForDuplicateAccount() throws Exception {
        AccountDto accountDto = new AccountDto("cse","gmailaccount","Giri@1234","");
        doThrow(DuplicateAccountException.class).when(accountService).saveAccount(any());
        mockMvc.perform(MockMvcRequestBuilders
                        .post("/accounts/account")
                        .flashAttr("accountBean",accountDto))
                .andExpect(view().name("alterAccount"))
                .andExpect(forwardedUrl("/WEB-INF/jsp/alterAccount.jsp"));
    }

    @Test
    @DisplayName("saveAccount Should redirect to viewAccounts page If Account saved")
    public void saveAccountShouldRedirectIfAccountSaved() throws Exception {
        AccountDto accountDto = new AccountDto("cse","gmailaccount","Giri@1234","");
        mockMvc.perform(MockMvcRequestBuilders
                        .post("/accounts/account")
                        .flashAttr("accountBean",accountDto))
                .andExpect(view().name("redirect:/groups"));
    }

    @Test
    @DisplayName("updateAccount Should Give same page with error for invalid account")
    public void updateAccountShouldGiveSamePageWithErrorForInvalidAccount() throws Exception {
        AccountDto accountDto = new AccountDto("","","","");
        mockMvc.perform(MockMvcRequestBuilders
                        .post("/accounts/account/1")
                        .flashAttr("accountBean",accountDto))
                .andExpect(view().name("alterAccount"))
                .andExpect(forwardedUrl("/WEB-INF/jsp/alterAccount.jsp"));
    }

    @Test
    @DisplayName("updateAccount Should Give same page with error If Account is duplicate")
    public void updateAccountShouldGiveSamePageWithErrorForDuplicateAccount() throws Exception {
        AccountDto accountDto = new AccountDto("cse","gmailaccount","Giri@1234","");
        doThrow(DuplicateAccountException.class).when(accountService).updateAccountById(any() , anyInt());
        mockMvc.perform(MockMvcRequestBuilders
                        .post("/accounts/account/1")
                        .flashAttr("accountBean",accountDto))
                .andExpect(view().name("alterAccount"))
                .andExpect(forwardedUrl("/WEB-INF/jsp/alterAccount.jsp"));
    }

    @Test
    @DisplayName("updateAccount Should redirect to viewAccounts page If Account updated")
    public void updateAccountShouldRedirectIfAccountUpdate() throws Exception {
        AccountDto accountDto = new AccountDto("cse","gmailaccount","Giri@1234","");
        mockMvc.perform(MockMvcRequestBuilders
                        .post("/accounts/account/1")
                        .flashAttr("accountBean",accountDto))
                .andExpect(view().name("redirect:/groups"));
    }



}
