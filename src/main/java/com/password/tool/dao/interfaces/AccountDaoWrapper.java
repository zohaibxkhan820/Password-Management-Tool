package com.password.tool.dao.interfaces;


import com.password.tool.dto.AccountDto;
import com.password.tool.dto.UserDto;
import com.password.tool.exception.AccountDoesNotExistException;
import com.password.tool.exception.DuplicateAccountException;

import java.util.List;

public interface AccountDaoWrapper {
    void saveAccount(AccountDto accountDto, UserDto userDto) throws DuplicateAccountException;
    AccountDto getAccountById(int accountId , UserDto userDto) throws AccountDoesNotExistException;
    List<AccountDto> getAllAccounts(UserDto userDto);
    void removeAccountById(int accountId , UserDto userDto) throws AccountDoesNotExistException;
    void updateAccountById(AccountDto account, int accountId , UserDto userDto) throws AccountDoesNotExistException, DuplicateAccountException;

    List<AccountDto> getUnAssignedAccounts(UserDto userDto);
}
