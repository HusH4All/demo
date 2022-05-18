package com.example.demo.controller;

import com.example.demo.dao.StudentDao;
import com.example.demo.model.Student;
import org.jasypt.util.password.BasicPasswordEncryptor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import javax.servlet.http.HttpSession;

class UserValidator implements Validator{
    @Override
    public boolean supports(Class<?> cls){
        return Student.class.isAssignableFrom(cls);
    }
    @Override
    public void validate(Object obj, Errors errors){
        Student student = (Student) obj;
        if (student.getId_al().trim().equals(""))
            errors.rejectValue("id_al", "required", "Username is required");
        if (student.getPassword().trim().equals(""))
            errors.rejectValue("password", "required", "Password is required");
    }
}

@Controller
public class LoginController {

    @Autowired
    private StudentDao studentDao;

    @RequestMapping("login")
    public String login(Model model){
        model.addAttribute("student", new Student());
        return "login";
    }

    @RequestMapping(value = "/login", method = RequestMethod.POST)
    public String checkLogin(@ModelAttribute("student") Student student, BindingResult bindingResult, HttpSession session){
        student = studentDao.loadUserByUsername(student.getId_al(), student.getPassword());
        if (student == null) {
            bindingResult.rejectValue("password", "bad pw", "Incorrect password");
            return "login";
        }

        UserValidator userValidator = new UserValidator();
        userValidator.validate(student, bindingResult);

        if (bindingResult.hasErrors()) {
            return "login";
        }

        String ban = studentDao.getStudent(student.getId_al()).getBanMsg();
        if (student.getBanned()) {
            bindingResult.rejectValue("id_al", "banned", "Ban reason: " + student.getBanMsg());
            return "login";
        }

        session.setAttribute("student", student);

        if ( session.getAttribute("nextUrl") != null){
            String redirect = (String) session.getAttribute("nextUrl");
            session.removeAttribute("nextUrl");
            return "redirect:" + redirect;
        }

        return "redirect:/";
    }

    @RequestMapping("signup")
    public String signin(Model model){
        model.addAttribute("student", new Student());
        return "signup";
    }

    @RequestMapping(value = "/signup", method = RequestMethod.POST)
    public String checkSignin(@ModelAttribute("student") Student student, BindingResult bindingResult, HttpSession session){
        UserValidator userValidator = new UserValidator();
        userValidator.validate(student, bindingResult);

        if (bindingResult.hasErrors()) {
            return "signup";
        }
        session.setAttribute("student", student);
        BasicPasswordEncryptor passwordEncryptor = new BasicPasswordEncryptor();
        String pass;
        pass = passwordEncryptor.encryptPassword(student.getPassword());
        student.setPassword(pass);
        studentDao.addStudent(student);
        if (session.getAttribute("nextUrl") != null){
            String redirect = (String) session.getAttribute("nextUrl");
            session.removeAttribute("nextUrl");
            return "redirect:" + redirect;
        }

        return "redirect:/";
    }

    @RequestMapping("/logout")
    public String logout(HttpSession session) {
        session.invalidate();
        return "redirect:/";
    }
}
