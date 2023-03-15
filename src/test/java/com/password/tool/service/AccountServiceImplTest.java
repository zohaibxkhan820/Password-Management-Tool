package com.password.tool.service;


import com.password.tool.dao.interfaces.AccountDaoWrapper;
import com.password.tool.dto.AccountDto;
import com.password.tool.dto.GroupDto;
import com.password.tool.dto.UserDto;
import com.password.tool.exception.AccountDoesNotExistException;
import com.password.tool.exception.DuplicateAccountException;
import com.password.tool.service.interfaces.UserService;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockedStatic;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import java.util.List;

import static org.mockito.Mockito.*;

@RunWith(MockitoJUnitRunner.class)
public class AccountServiceImplTest {

    @InjectMocks
    AccountServiceImpl accountServiceImpl;
    @Mock
    Authentication authentication;
    @Mock
    SecurityContext securityContext;
    @Mock
    UserService userService;
    @Mock
    AccountDaoWrapper accountDaoWrapper;
    AccountDto accountBean;
    UserDto userBean;

    private static MockedStatic<SecurityContextHolder> mockedSettings;

    @BeforeClass
    public static void init(){
        mockedSettings = mockStatic(SecurityContextHolder.class);
    }

    @AfterClass
    public static void close(){
        mockedSettings.close();
    }
    @Before
    public void setUp()   {
        userBean = new UserDto();
        GroupDto groupBean = new GroupDto();
        Assertions.assertNotNull(accountServiceImpl);
        when(SecurityContextHolder.getContext()).thenReturn(securityContext);
        when(securityContext.getAuthentication()).thenReturn(authentication);
        when(authentication.getName()).thenReturn("name");
        when(userService.getUserByUserName(anyString())).thenReturn(userBean);
        accountBean = new AccountDto();
        accountBean.setGroup(groupBean);
    }


    @Test
    @DisplayName("save account should check for duplicate account")
    public void saveAccountShouldCheckForDuplicateAccount() throws DuplicateAccountException {
        doThrow(DuplicateAccountException.class).when(accountDaoWrapper).saveAccount(accountBean,userBean);
        Assertions.assertThrows(DuplicateAccountException.class,() -> accountServiceImpl.saveAccount(accountBean));
    }

    @Test
    @DisplayName("save account should save account")
    public void saveAccountShouldSaveAccount()  {
        Assertions.assertDoesNotThrow(() -> accountServiceImpl.saveAccount(accountBean));
    }

    @Test
    @DisplayName("getAccountById should return account if found")
    public void getAccountByIdShouldReturnAccountIfFound(){
        Assertions.assertDoesNotThrow(() -> accountServiceImpl.getAccountById(accountBean.getAccountId()));
    }

    @Test
    @DisplayName("getAccountById should throw exception if account not found")
    public void getAccountByIdShouldThrowExceptionIfAccountNotFound() throws AccountDoesNotExistException {
        doThrow(AccountDoesNotExistException.class).when(accountDaoWrapper).getAccountById(accountBean.getAccountId(),userBean);
        Assertions.assertThrows(AccountDoesNotExistException.class ,() -> accountServiceImpl.getAccountById(accountBean.getAccountId()));
    }


    @Test
    @DisplayName("Account Service should return all accounts")
    public void getAllAccountsShouldReturnAllAccounts()  {
        when(accountDaoWrapper.getAllAccounts(any())).thenReturn(List.of(accountBean,accountBean));
        Assertions.assertEquals(List.of(accountBean,accountBean) , accountServiceImpl.getAllAccount());
    }

    @Test
    @DisplayName("Account Service should return all accounts")
    public void getAllAccountsShouldReturnAllAccounts1() {
        when(accountDaoWrapper.getAllAccounts(any())).thenReturn(List.of());
        Assertions.assertEquals(List.of() , accountServiceImpl.getAllAccount());
    }
    @Test
    @DisplayName("Account Service should return all accounts")
    public void getAllAccountsShouldReturnAllAccounts2()  {
        when(accountDaoWrapper.getAllAccounts(any())).thenReturn(List.of(accountBean));
        Assertions.assertNotEquals(List.of(accountBean,accountBean) , accountServiceImpl.getAllAccount());
    }

    @Test
    @DisplayName("deleteAccountById should delete account if exist")
    public void deleteByIdShouldDeleteAccountIfExist()  {
        Assertions.assertDoesNotThrow(() -> accountServiceImpl.removeAccountById(accountBean.getAccountId()));
    }

    @Test
    @DisplayName("deleteAccountById should throw exception if account does not exist")
    public void deleteByIdShouldThrowExceptionIfAccountDoesNotExist() throws AccountDoesNotExistException {
        doThrow(AccountDoesNotExistException.class).when(accountDaoWrapper).removeAccountById(accountBean.getAccountId() , userBean);
        Assertions.assertThrows(AccountDoesNotExistException.class ,() -> accountServiceImpl.removeAccountById(accountBean.getAccountId()));
    }


    @Test
    @DisplayName("updateAccountById should throw exception if account does not exist")
    public void updateAccountByIdShouldThrowExceptionIfAccountDoesNotExist() throws AccountDoesNotExistException, DuplicateAccountException {
        doThrow(AccountDoesNotExistException.class).when(accountDaoWrapper).updateAccountById(accountBean ,accountBean.getAccountId() , userBean);
        Assertions.assertThrows(AccountDoesNotExistException.class,() -> accountServiceImpl.updateAccountById(accountBean , accountBean.getAccountId()));
    }

    @Test
    @DisplayName("updateAccountById should check for duplicate account name if new account name is different")
    public void updateAccountByIdShouldCheckForDuplicateAccountNameIfAccountNameIsDifferent()  {
        Assertions.assertDoesNotThrow(() -> accountServiceImpl.updateAccountById(accountBean , accountBean.getAccountId()));
    }

    @Test
    @DisplayName("getUnAssignedAccounts should return all available unassigned accounts")
    public void getUnAssignedAccountsShouldReturnNullGroupAccounts()  {
        Assertions.assertDoesNotThrow(() -> accountServiceImpl.getUnassignedAccounts());
    }



}
