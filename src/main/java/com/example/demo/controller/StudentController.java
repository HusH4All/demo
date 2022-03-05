package com.example.demo.controller;

import com.example.demo.dao.StudentDao;
import com.example.demo.model.Student;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@Controller
@RequestMapping("/student")
public class StudentController {

    private StudentDao studentDao;

    @Autowired
    public void setStudentDao(StudentDao studentDao) {
        this.studentDao = studentDao;
    }

    @RequestMapping("/list")
    public String listStudents(Model model) {
        model.addAttribute("students", studentDao.getStudents());
        return "request/list";
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
            @ModelAttribute("student") Student student,
            BindingResult bindingResult) {
        if (bindingResult.hasErrors())
            return "student/update";
        studentDao.updateStudent(student);
        return "redirect:list";
    }

    @RequestMapping(value = "/delete/{id_al}")
    public String processDelete(@PathVariable String id_al) {
        studentDao.deleteStudent(id_al);
        return "redirect:../list";
    }
}
