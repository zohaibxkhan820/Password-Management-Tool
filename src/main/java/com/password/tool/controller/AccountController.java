package com.password.tool.controller;

import com.password.tool.dto.AccountDto;
import com.password.tool.dto.GroupDto;
import com.password.tool.exception.AccountDoesNotExistException;
import com.password.tool.exception.DuplicateAccountException;
import com.password.tool.service.interfaces.AccountService;
import com.password.tool.service.interfaces.GroupService;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import javax.validation.Valid;
import java.util.*;

@Controller
public class AccountController {

    @Autowired
    AccountService accountService;
    @Autowired
    GroupService groupService;

    @GetMapping("/accounts/account")
    public String addAccount(@ModelAttribute("accountBean") AccountDto accountDto, ModelMap model){
        model.put("head" , "Add Account");
        setDropDownGroups(model);
        return "alterAccount";
    }

    @GetMapping("/accounts/account/{accountId}")
    public String getAccountById(@PathVariable int accountId, @ModelAttribute("accountBean") AccountDto accountDto, ModelMap model) throws AccountDoesNotExistException {
        setDropDownGroups(model);
        model.put("head" , "Update Account");
        AccountDto account = accountService.getAccountById(accountId);
        BeanUtils.copyProperties(account, accountDto);
        return "alterAccount";
    }

    @PostMapping("/accounts/account")
    public String saveAccount(@Valid @ModelAttribute("accountBean") AccountDto accountDto, BindingResult bindingResult , ModelMap model) {
        setDropDownGroups(model);
        model.put("head" , "Add Account");
        if(bindingResult.hasErrors()){
            return "alterAccount";
        }
        try {
            accountService.saveAccount(accountDto);
        } catch (DuplicateAccountException e) {
            model.put("errorMessage" , e.getMessage());
            return "alterAccount";
        }
        return "redirect:/groups";
    }

    @PostMapping("/accounts/account/{accountId}")
    public String updateAccount(@PathVariable int accountId , @Valid @ModelAttribute("accountBean") AccountDto accountDto, BindingResult bindingResult , ModelMap model) throws AccountDoesNotExistException {
        setDropDownGroups(model);
        model.put("head" , "Update Account");
        if(bindingResult.hasErrors()){
            return "alterAccount";
        }
        try {
            accountService.updateAccountById(accountDto, accountId);
        }catch ( DuplicateAccountException e) {
            model.put("errorMessage" , e.getMessage());
            return "alterAccount";
        }
        return "redirect:/groups";
    }
    @PostMapping("/accounts/account/delete/{accountId}")
    public String deleteAccount(@PathVariable int accountId) throws AccountDoesNotExistException {
        accountService.removeAccountById(accountId);
        return "redirect:/groups";
    }

    private void setDropDownGroups(ModelMap model) {
        List<GroupDto> groups = groupService.getAllGroups();
        Map<GroupDto, String> groupDropDown = new HashMap<>();
        groups.forEach(group -> groupDropDown.put(group, group.getGroupName()));
        model.put("groupDropDown", groupDropDown);
    }

    @ExceptionHandler(AccountDoesNotExistException.class)
    public String accountDoesNotExistHandler(AccountDoesNotExistException exception){
        return "error";
    }

}
