package com.lardi;

import com.lardi.dal.DaoFactory;
import com.lardi.dal.interfaces.RecordDaoInterface;
import com.lardi.dal.interfaces.UserDaoInterface;
import com.lardi.dal.pojo.RecordPojo;
import com.lardi.dal.pojo.UserPojo;
import org.apache.log4j.Logger;
import org.springframework.context.annotation.Bean;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.thymeleaf.templateresolver.ServletContextTemplateResolver;

import javax.validation.Valid;
import java.util.List;

@org.springframework.stereotype.Controller

public class Controller {
    final static private Logger logger = Logger.getLogger(Controller.class);
    UserDaoInterface userDao = DaoFactory.getUserDao();
    RecordDaoInterface recordDao = DaoFactory.getRecordDao();

    @RequestMapping("/")
    public String index( Model model) {
        if (logger.isDebugEnabled()) {   logger.debug("RequestMapping /" ); }
                         return "index";

    }

    @RequestMapping("/login")
    public String login() {
        if (logger.isDebugEnabled()) {   logger.debug("RequestMapping /login" ); }
                return "login";

    }

    @RequestMapping("/login-error")
        public String loginError() {
        if (logger.isDebugEnabled()) {   logger.debug("RequestMapping /login" ); }
                return "login-error";
    }


    @GetMapping("/user/list")
    public String list(Model model) {
        if (logger.isDebugEnabled()) {   logger.debug("RequestMapping /user/list" ); }
        String userName= SecurityContextHolder.getContext().getAuthentication().getName();
        List<RecordPojo> allRecords = recordDao.getAllRecords(userName);
        model.addAttribute("allRecords", allRecords);
        return "user/list";
    }

    @PostMapping("/user/list")
    public String listFiltered(@RequestParam("keyword") String keyword,Model model) {
        if (logger.isDebugEnabled()) {   logger.debug("RequestMapping /user/list Filter keyword: "+keyword ); }
        String userName= SecurityContextHolder.getContext().getAuthentication().getName();
        List<RecordPojo> allRecords = recordDao.getFiltered(keyword,userName);
        model.addAttribute("allRecords", allRecords);
        return "user/list";
    }


    @RequestMapping("/user/delete/{id}")
    public String delete(@PathVariable int id) {
        if (logger.isDebugEnabled()) {   logger.debug("RequestMapping /user/delete  delete user wit ID:"+id ); }
        String userName= SecurityContextHolder.getContext().getAuthentication().getName();
        recordDao.deleteRecord(id,userName);
        return "redirect:/user/list";
    }




    @GetMapping("/user/edit/{id}")
    public String editUser(Model model, @PathVariable int id) {
        if (logger.isDebugEnabled()) {   logger.debug("RequestMapping /user/edit modify user id"+id); }
        String userName= SecurityContextHolder.getContext().getAuthentication().getName();
        RecordPojo recordPojo=recordDao.getRecordById(id,userName);
        model.addAttribute("recordPojo", recordPojo);

        return "user/add";
    }
    @GetMapping("/user/add")
    public String addUser(RecordPojo recordPojo) {
        if (logger.isDebugEnabled()) {   logger.debug("RequestMapping /user/add add  user "+recordPojo.getfName()); }
        return "user/add";
    }

    @PostMapping("/user/add")
    public String addUser(@Valid RecordPojo recordPojo,BindingResult bindingResult) {
        if (logger.isDebugEnabled()) {   logger.debug("RequestMapping /user/add add or edit user "+recordPojo.getfName()); }
                String userName= SecurityContextHolder.getContext().getAuthentication().getName();
        if (! bindingResult.hasErrors()) {
           if (recordPojo.getId()==0){
               recordDao.addRecord(recordPojo,userName);
           }
            if (recordPojo.getId()!=0){

            recordDao.editRecord(recordPojo,userName);
            }
            return "redirect:/user/list";
        }
        return "user/add";

    }


    @GetMapping("/register")
    public String register(UserPojo userPojo) {
        if (logger.isDebugEnabled()) {   logger.debug("RequestMapping /register"); }
        return "registerForm";
    }


    @PostMapping("/register")
    public String register( @Valid UserPojo userPojo, BindingResult bindingResult) {
        if (logger.isDebugEnabled()) {   logger.debug("RequestMapping /register new user "+userPojo.getNameLogin()); }
        if (! bindingResult.hasErrors()) {
         if(   userDao.isUserExist(userPojo.getNameLogin())){
             bindingResult.rejectValue("nameLogin","1","Вказаний логін вже зайнятий");
         }
        }
        if (bindingResult.hasErrors()) {
            return "registerForm";
        }
        userDao.addNewUser(userPojo);
        return "redirect:/";
    }


}