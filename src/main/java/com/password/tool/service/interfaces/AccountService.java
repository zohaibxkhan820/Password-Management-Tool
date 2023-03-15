package com.password.tool.service.interfaces;


import com.password.tool.dto.AccountDto;
import com.password.tool.exception.AccountDoesNotExistException;
import com.password.tool.exception.DuplicateAccountException;

import java.util.List;

public interface AccountService {
    void saveAccount(AccountDto accountDto) throws DuplicateAccountException;
    AccountDto getAccountById(int accountId) throws AccountDoesNotExistException;
    List<AccountDto> getAllAccount();
    void updateAccountById(AccountDto accountDto, int accountId) throws AccountDoesNotExistException, DuplicateAccountException;
    void removeAccountById(int accountId) throws AccountDoesNotExistException;

    List<AccountDto> getUnassignedAccounts();
}
