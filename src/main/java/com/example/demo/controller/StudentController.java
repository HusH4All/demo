package com.example.demo.controller;

import com.example.demo.dao.*;
import com.example.demo.model.*;
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
import java.util.LinkedList;
import java.util.List;

@Controller
@RequestMapping("/student")
public class StudentController {

    private StudentDao studentDao;
    private OfferDao offerDao;
    private RequestDao requestDao;
    private StadisticsDao stadisticsDao;

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
    public void setStadisticsDao(StadisticsDao stadisticsDao) {this.stadisticsDao = stadisticsDao;}

    @RequestMapping("/profile")
    public String profile(HttpSession session, Model model) {
        if (session.getAttribute("student") == null) {
            model.addAttribute("student", new Student());
            session.setAttribute("nextUrl", "/student/profile");
            return "login";
        }
        Student student = (Student) session.getAttribute("student");
        List<Stadistics> stadistics = new LinkedList<>();
        Stadistics stat;
        stat = stadisticsDao.bestOfferSkillActive(student.getId_al());
        stat.setCategory("Most skill offered");
        stat.setLabel("Total Offers: ");
        stadistics.add(stat);

        stat = stadisticsDao.bestRequestSkillActive(student.getId_al());
        stat.setCategory("Most skill requested");
        stat.setLabel("Total Requests: ");
        stadistics.add(stat);

        stat = stadisticsDao.rate(student.getName());
        stat.setCategory("Total Rate");
        stat.setLabel("Score: ");
        stadistics.add(stat);
        model.addAttribute("stadistics", stadistics);
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

    @RequestMapping(value = "/stadistics")
    public String processStadistics(Model model){
        List<Stadistics> stadistics = new LinkedList<>();
        Stadistics stat = stadisticsDao.bestSkillActive("offer");
        stat.setCategory("Best Skill of active Offers");
        stat.setLabel("Total Offers: ");
        stadistics.add(stat);

        stat = stadisticsDao.bestSkillActive("request");
        stat.setCategory("Best Skill of active Requests");
        stat.setLabel("Total Requests: ");
        stadistics.add(stat);

        stat = stadisticsDao.bestSkillAllTime("offer");
        stat.setCategory("Best Skill of all Offers");
        stat.setLabel("Total Offers: ");
        stadistics.add(stat);

        stat = stadisticsDao.bestSkillAllTime("request");
        stat.setCategory("Best Skill of all Requests");
        stat.setLabel("Total Requests");
        stadistics.add(stat);

        stat = stadisticsDao.bestStudent("offer");
        stat.setCategory("Student with the most active Offers");
        stat.setLabel("Total Offers: ");
        stadistics.add(stat);

        stat = stadisticsDao.bestStudent("request");
        stat.setCategory("Student with the most active Requests");
        stat.setLabel("Total Requests");
        stadistics.add(stat);

        stat = stadisticsDao.bestStudentAllTime("offer");
        stat.setCategory("Student with the most Offers");
        stat.setLabel("Total Offers");
        stadistics.add(stat);

        stat = stadisticsDao.bestStudentAllTime("request");
        stat.setCategory("Student with the most Requests");
        stat.setLabel("Total Requests");
        stadistics.add(stat);

        stat = stadisticsDao.studentWithMoreHours();
        stat.setCategory("Student that teached the most");
        stat.setLabel("Total Hours: ");
        stadistics.add(stat);

        stat = stadisticsDao.mostSkillCollaboratedAllTime();
        stat.setCategory("Best Skill in Collaborations");
        stat.setLabel("Total Collaborations: ");
        stadistics.add(stat);

        stat = stadisticsDao.mostSkillClosedCollaboration();
        stat.setCategory("Best Skill in closed Collaborations");
        stat.setLabel("Total Collaborations: ");
        stadistics.add(stat);

        stat = stadisticsDao.bestRatedStudent();
        stat.setCategory("Best rated student");
        stat.setLabel("Total Score: ");
        stadistics.add(stat);
        model.addAttribute("stadistics", stadistics);
        return "student/stadistics";
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
        offerDao.disableMyOffers(student.getId_al());
        requestDao.disableMyRequests(student.getId_al());
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
