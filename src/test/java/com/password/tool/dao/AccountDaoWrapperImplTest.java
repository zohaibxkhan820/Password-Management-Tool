package com.password.tool.dao;

import com.password.tool.dao.interfaces.AccountDao;
import com.password.tool.dto.AccountDto;
import com.password.tool.dto.UserDto;
import com.password.tool.exception.AccountDoesNotExistException;
import com.password.tool.exception.DuplicateAccountException;
import com.password.tool.model.Account;
import com.password.tool.model.User;
import org.junit.Before;
import org.junit.Test;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;
import org.modelmapper.ModelMapper;
import java.util.List;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class AccountDaoWrapperImplTest {

    @InjectMocks
    AccountDaoWrapperImpl accountDaoWrapper;
    @Mock
    AccountDao accountDao;
    @Mock
    ModelMapper modelMapper;
    AccountDto accountDto;
    Account account;
    UserDto userDto;
    User user;

    @Before
    public void setUp(){
        accountDto = new AccountDto("" , "" , "" ,"");
        account = new Account("","","","");
        when(modelMapper.map(accountDto, Account.class)).thenReturn(account);
        when(modelMapper.map(account, AccountDto.class)).thenReturn(accountDto);
        when(modelMapper.map(userDto , User.class)).thenReturn(user);
    }

    @Test
    @DisplayName("save account should check for duplicate account")
    public void saveAccountShouldCheckForDuplicateAccount(){
        when(accountDao.countByAccountNameAndUser(anyString() , any())).thenReturn(1);
        Assertions.assertThrows(DuplicateAccountException.class , () -> accountDaoWrapper.saveAccount(accountDto, userDto));
    }

    @Test
    @DisplayName("save account should save account")
    public void saveAccountShouldSaveAccount(){
        when(accountDao.countByAccountNameAndUser(anyString() , any())).thenReturn(0);
        Assertions.assertDoesNotThrow(() -> accountDaoWrapper.saveAccount(accountDto, userDto));
    }

    @Test
    @DisplayName("getALlAccount should return all available accounts")
    public void getAllAccountsShouldReturnAllAccounts(){
        when(accountDao.findByUser(any())).thenReturn(List.of(account, account));
        Assertions.assertEquals(List.of(accountDto, accountDto) , accountDaoWrapper.getAllAccounts(userDto));
    }

    @Test
    @DisplayName("getALlAccounts should return all available account")
    public void getAllAccountsShouldReturnAllAccount1(){
        when(accountDao.findByUser(any())).thenReturn(List.of(account));
        Assertions.assertNotEquals(List.of(accountDto, accountDto) , accountDaoWrapper.getAllAccounts(userDto));
    }

    @Test
    @DisplayName("getAccountById should throw exception if account not found")
    public void getAccountByIdShouldThrowExceptionIfAccountNotFound(){
        when(accountDao.findByAccountIdAndUser(anyInt(),any())).thenReturn(List.of());
        Assertions.assertThrows(AccountDoesNotExistException.class , ()-> accountDaoWrapper.getAccountById(1,userDto));
    }

    @Test
    @DisplayName("getAccountById should return account if found")
    public void getAccountByIdShouldReturnAccountIfFound(){
        when(accountDao.findByAccountIdAndUser(anyInt(),any())).thenReturn(List.of(account));
        Assertions.assertDoesNotThrow(()-> accountDaoWrapper.getAccountById(1,userDto));
    }

    @Test
    @DisplayName("updateAccountById should check for duplicate account name if new account name is different")
    public void updateAccountByIdShouldCheckForDuplicateAccountNameIfAccountNameIsDifferent(){
        when(accountDao.countByAccountNameAndUser(anyString() , any())).thenReturn(0);
        when(accountDao.findByAccountIdAndUser(anyInt() , any())).thenReturn(List.of(account));
        Assertions.assertDoesNotThrow(()-> accountDaoWrapper.updateAccountById( new AccountDto(" sdv","","","") ,1,userDto));

    }

    @Test
    @DisplayName("updateAccountById should check for duplicate and throw exception if duplicate")
    public void updateAccountByIdShouldCheckForDuplicateAccountNameAndThrowExceptionIfDuplicate(){
        when(accountDao.countByAccountNameAndUser(anyString() , any())).thenReturn(1);
        when(accountDao.findByAccountIdAndUser(anyInt() , any())).thenReturn(List.of(account));
        Assertions.assertThrows(DuplicateAccountException.class,()-> accountDaoWrapper.updateAccountById(new AccountDto(" sdv","","","") ,1,userDto));
    }

    @Test
    @DisplayName("updateAccountById should throw exception if account does not exist")
    public void updateAccountByIdShouldThrowExceptionIfAccountDoesNotExist(){
        when(accountDao.findByAccountIdAndUser(anyInt() , any())).thenReturn(List.of());
        Assertions.assertThrows(AccountDoesNotExistException.class,()-> accountDaoWrapper.updateAccountById(new AccountDto(" sdv","","","") ,1,userDto));
    }
    @Test
    @DisplayName("deleteAccountById should throw exception if account does not exist")
    public void deleteByIdShouldThrowExceptionIfAccountDoesNotExist(){
        when(accountDao.findByAccountIdAndUser(anyInt() , any())).thenReturn(List.of());
        Assertions.assertThrows(AccountDoesNotExistException.class,()-> accountDaoWrapper.removeAccountById(1 , userDto));
    }

    @Test
    @DisplayName("deleteAccountById should delete account if exist")
    public void deleteByIdShouldDeleteAccountIfExist(){
        when(accountDao.findByAccountIdAndUser(anyInt() , any())).thenReturn(List.of(account));
        Assertions.assertDoesNotThrow(()-> accountDaoWrapper.removeAccountById(1 , userDto));
    }
    @Test
    @DisplayName("getUnAssignedAccounts should return all available unassigned accounts")
    public void getUnAssignedAccountsShouldReturnNullGroupAccounts(){
        when(accountDao.findByUserAndGroup(user , null)).thenReturn(List.of(account, account));
        Assertions.assertEquals(List.of(accountDto, accountDto) , accountDaoWrapper.getUnAssignedAccounts(userDto));
    }
}
