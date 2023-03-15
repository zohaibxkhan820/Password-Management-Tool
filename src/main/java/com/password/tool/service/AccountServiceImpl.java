package com.password.tool.service;


import com.password.tool.dao.interfaces.AccountDaoWrapper;
import com.password.tool.dto.AccountDto;
import com.password.tool.dto.UserDto;
import com.password.tool.exception.AccountDoesNotExistException;
import com.password.tool.exception.DuplicateAccountException;
import com.password.tool.service.interfaces.AccountService;
import com.password.tool.service.interfaces.UserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import java.util.*;

@Service("accountService")
public class AccountServiceImpl implements AccountService {
    @Autowired
    private AccountDaoWrapper accountDaoWrapper;
    @Autowired
    private UserService userService;
    private static final Logger logger
            = LoggerFactory.getLogger(AccountServiceImpl.class);


    @Override
    public void saveAccount(AccountDto accountDto) throws DuplicateAccountException {
        logger.info("saving the account");
        accountDto.setCreatedAt(new Date());
        accountDto.setLastModifiedAt(new Date());
        accountDto.setUser(getUserBean());
        setNullToUnAssignedGroup(accountDto);
        accountDaoWrapper.saveAccount(accountDto, getUserBean());
        logger.info("account saved successfully");
    }

    @Override
    public AccountDto getAccountById(int accountId) throws AccountDoesNotExistException {
        logger.info("Getting account by Id ");
        return accountDaoWrapper.getAccountById(accountId , getUserBean());
    }

    @Override
    public List<AccountDto> getAllAccount() {
        logger.info("Getting all accounts");
        return accountDaoWrapper.getAllAccounts(getUserBean());
    }

    @Override
    public void updateAccountById(AccountDto accountDto, int accountId) throws AccountDoesNotExistException, DuplicateAccountException {
        logger.info("updating the account");
        accountDto.setLastModifiedAt(new Date());
        setNullToUnAssignedGroup(accountDto);
        accountDaoWrapper.updateAccountById(accountDto, accountId , getUserBean());
        logger.info("account updated successfully");
    }

    private void setNullToUnAssignedGroup(AccountDto accountDto) {
        if (accountDto.getGroup() != null && accountDto.getGroup().getGroupId() == 0) {
            logger.info("setting group to null");
            accountDto.setGroup(null);
        }
    }

    @Override
    public void removeAccountById(int accountId) throws AccountDoesNotExistException {
        logger.info("removing account by Id");
        accountDaoWrapper.removeAccountById(accountId , getUserBean());
        logger.info("account removed by id successfully");
    }

    @Override
    public List<AccountDto> getUnassignedAccounts() {
        return accountDaoWrapper.getUnAssignedAccounts(getUserBean());
    }

    private UserDto getUserBean() {
        logger.info("Getting user bean");
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        return userService.getUserByUserName(authentication.getName());
    }
}
