package com.example.demo.controller;

import com.example.demo.dao.CollaborationDao;
import com.example.demo.dao.OfferDao;
import com.example.demo.dao.RequestDao;
import com.example.demo.dao.StudentDao;
import com.example.demo.model.Collaboration;
import com.example.demo.model.Offer;
import com.example.demo.model.Request;
import com.example.demo.model.Student;
import org.jasypt.util.password.BasicPasswordEncryptor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import javax.servlet.http.HttpSession;

@Controller
@RequestMapping("/student")
public class StudentController {

    private StudentDao studentDao;
    private OfferDao offerDao;
    private RequestDao requestDao;
    private CollaborationDao collaborationDao;

    @Autowired
    public void setStudentDao(StudentDao studentDao) {
        this.studentDao = studentDao;
    }

    @Autowired
    public void setOfferDao(OfferDao offerDao) {
        this.offerDao = offerDao;
    }

    @Autowired
    public void setRequestDao(RequestDao requestDao) {
        this.requestDao = requestDao;
    }

    @Autowired
    public void setCollaborationDao(CollaborationDao collaborationDao) {
        this.collaborationDao = collaborationDao;
    }

    @RequestMapping("/profile")
    public String profile(HttpSession session, Model model) {
        if (session.getAttribute("student") == null) {
            model.addAttribute("student", new Student());
            session.setAttribute("nextUrl", "/student/profile");
            return "login";
        }
        Student student = (Student) session.getAttribute("student");
        model.addAttribute("student", studentDao.getStudent(student.getId_al()));
        return "student/profile";
    }

    @RequestMapping(value = "/add")
    public String addStudent(Model model) {
        model.addAttribute("student", new Student());
        return "student/add";
    }

    @RequestMapping(value = "/add", method = RequestMethod.POST)
    public String processAddSubmit(
            @ModelAttribute("student") Student student,
            BindingResult bindingResult) {
        if (bindingResult.hasErrors())
            return "student/add";
        BasicPasswordEncryptor passwordEncryptor = new BasicPasswordEncryptor();
        String pass;
        pass = passwordEncryptor.encryptPassword(student.getPassword());
        student.setPassword(pass);
        studentDao.addStudent(student);
        return "redirect:list";
    }

    @RequestMapping(value = "/update/{id_al}", method = RequestMethod.GET)
    public String editStudent(Model model, @PathVariable String id_al) {
        model.addAttribute("student", studentDao.getStudent(id_al));
        return "student/update";
    }

    @RequestMapping(value = "/update", method = RequestMethod.POST)
    public String processUpdateSubmit(
            @ModelAttribute("student") Student student, HttpSession session,
            BindingResult bindingResult) {
        if (bindingResult.hasErrors())
            return "student/update";
        Student s = (Student) session.getAttribute("student");
        student.setAll(s.getId_al(),s.getPassword(),s.getEmail(),s.getHours(),s.getSKP(),s.getActive(), s.getBanned());
        studentDao.updateStudent(student);
        return "redirect:profile";
    }

    @RequestMapping(value = "/disable/{id_al}")
    public String processDisable(@PathVariable String id_al, HttpSession session) {
        studentDao.disableStudent(id_al);
        session.invalidate();
        return "redirect:/";
    }

    @RequestMapping(value = "/ban")
    public String banStudent(Model model) {
        model.addAttribute("students", studentDao.getBanneableStudents());
        return "student/ban";
    }

    @RequestMapping(value = "/msg/{id_al}", method = RequestMethod.GET)
    public String processBan(Model model,@PathVariable String id_al){
        model.addAttribute("student", studentDao.getStudent(id_al));
        return "/student/msg";
    }

    @RequestMapping(value = "/msg", method = RequestMethod.POST)
    public String processBanSubmit(
            @ModelAttribute("student") Student student) {
        studentDao.banStudent(student.getId_al());
        studentDao.setMsg(student);
        for (Offer offer : offerDao.getMyOffers(student))
            offerDao.disableOffer(offer);
        for (Request request : requestDao.getMyRequests(student))
            requestDao.disableRequest(request);
        return "redirect:/student/ban";
    }

    @RequestMapping(value = "/unban/{id_al}")
    public String processUnBan(@PathVariable String id_al){
        studentDao.unBanStudent(id_al);
        studentDao.setMsgUnBan(studentDao.getStudent(id_al));
        return "redirect:/student/ban";
    }

    @RequestMapping(value = "/enable/{id_al}")
    public String proccesEnable(@PathVariable String id_al){
        studentDao.enableStudent(id_al);
        return "redirect:/student/profile";
    }
}
