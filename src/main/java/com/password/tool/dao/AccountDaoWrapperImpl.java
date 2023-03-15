package com.password.tool.dao;


import com.password.tool.dao.interfaces.AccountDao;
import com.password.tool.dao.interfaces.AccountDaoWrapper;
import com.password.tool.dto.AccountDto;
import com.password.tool.dto.UserDto;
import com.password.tool.exception.AccountDoesNotExistException;
import com.password.tool.exception.DuplicateAccountException;
import com.password.tool.model.Account;
import com.password.tool.model.User;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;

@Repository("accountDaoWrapper")
public class AccountDaoWrapperImpl implements AccountDaoWrapper {
    @Autowired
    private AccountDao accountDao;
    @Autowired
    private ModelMapper modelMapper;

    @Override
    public void saveAccount(AccountDto accountDto, UserDto userDto) throws DuplicateAccountException {
        Account account = modelMapper.map(accountDto, Account.class);
        User user = getUserFromBean(userDto);
        checkForDuplicate(account.getAccountName(), user);
        accountDao.save(account);
    }

    private User getUserFromBean(UserDto userDto) {
        return modelMapper.map(userDto, User.class);
    }

    private void checkForDuplicate(String accountName, User user) throws DuplicateAccountException {
        if(accountDao.countByAccountNameAndUser(accountName, user)>0){
            throw new DuplicateAccountException("Account Already Exist");
        }
    }

    @Override
    public AccountDto getAccountById(int accountId, UserDto userDto) throws AccountDoesNotExistException {
        User user = getUserFromBean(userDto);
        List<Account> accounts = accountDao.findByAccountIdAndUser(accountId , user);
        if(accounts.isEmpty()){
            throw new AccountDoesNotExistException("Account Does not Exist with Account Id "+accountId);
        }
        return modelMapper.map(accounts.get(0) , AccountDto.class);
    }

    @Override
    public List<AccountDto> getAllAccounts(UserDto userDto) {
        User user = getUserFromBean(userDto);
        List<Account> accounts = accountDao.findByUser(user);
        List<AccountDto> accountDtos = new ArrayList<>();
        accounts.forEach(account -> accountDtos.add(modelMapper.map(account , AccountDto.class)));
        return accountDtos;
    }

    @Override
    public void removeAccountById(int accountId, UserDto userDto) throws AccountDoesNotExistException {
        AccountDto accountDto = getAccountById(accountId , userDto);
        Account account = modelMapper.map(accountDto, Account.class);
        accountDao.delete(account);
    }

    @Override
    public void updateAccountById(AccountDto account, int accountId, UserDto userDto) throws AccountDoesNotExistException, DuplicateAccountException {
        AccountDto accountDto = getAccountById(accountId , userDto);
        User user = modelMapper.map(userDto, User.class);
        if(!accountDto.getAccountName().equals(account.getAccountName())){
            checkForDuplicate(account.getAccountName() , user);
        }
        copyProperties(account, accountDto);
        Account account1 = modelMapper.map(accountDto, Account.class);
        accountDao.save(account1);
    }

    @Override
    public List<AccountDto> getUnAssignedAccounts(UserDto userDto) {
        User user = getUserFromBean(userDto);
        List<Account> accounts = accountDao.findByUserAndGroup(user , null);
        List<AccountDto> accountDtos = new ArrayList<>();
        accounts.forEach(account -> accountDtos.add(modelMapper.map(account , AccountDto.class)));
        return accountDtos;

    }

    private void copyProperties(AccountDto account, AccountDto accountDto) {
        accountDto.setAccountName(account.getAccountName());
        accountDto.setUserName(account.getUserName());
        accountDto.setPassword(account.getPassword());
        accountDto.setUrl(account.getUrl());
        accountDto.setGroup(account.getGroup());
        accountDto.setLastModifiedAt(account.getLastModifiedAt());
    }

}
