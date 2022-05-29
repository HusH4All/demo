package com.example.demo.controller;

import com.example.demo.dao.*;
import com.example.demo.model.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import javax.servlet.http.HttpSession;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

@Controller
@RequestMapping("/collaboration")
public class CollaborationController {

    private CollaborationDao collaborationDao;
    private RequestDao requestDao;
    private OfferDao offerDao;
    private StudentDao studentDao;

    @Autowired
    public void setCollaborationDao(CollaborationDao collaborationDao) {
        this.collaborationDao = collaborationDao;
    }

    @Autowired
    public void setRequestDao(RequestDao requestDao) {
        this.requestDao = requestDao;
    }

    @Autowired
    public void setOfferDao(OfferDao offerDao) {
        this.offerDao = offerDao;
    }

    @Autowired
    public void setStudentDao(StudentDao studentDao){ this.studentDao = studentDao;}

    @RequestMapping("/list")
    public String listCollaborations(Model model, HttpSession session) {
        Student user = (Student) session.getAttribute("student");
        Map<Collaboration, StudentsColaborating> collaborationMap = new HashMap<>();
        Collaboration c;

        List<Collaboration> collaborations = new LinkedList<>(collaborationDao.getCollaborationFromOffer(user));
        collaborations.addAll(collaborationDao.getCollaborationFromRequest(user));

        for (Collaboration collaboration : collaborations) {
            if (collaboration != null && !collaboration.pending) {
                Offer offer = offerDao.getOffer(collaboration.getId_O());
                Request request = requestDao.getRequest(collaboration.getId_R());
                StudentsColaborating studentsColaborating = new StudentsColaborating(collaborationDao.getStudent(offer.getId_al()), collaborationDao.getStudent(request.getId_al()), offerDao.getSkill(offer.getId_S()));
                collaborationMap.put(collaboration, studentsColaborating);
            }
        }
        model.addAttribute("collaborations", collaborationMap);
        return "collaboration/list";
    }

    @RequestMapping("/management")
    public String listPendingCollaborations(Model model, HttpSession session) {
        Student user = (Student) session.getAttribute("student");
        Map<Collaboration, StudentsColaborating> collaborationMap = new HashMap<>();

        List<Collaboration> collaborations = new LinkedList<>(collaborationDao.getManagmentCollaborationFromOffer(user));
        collaborations.addAll(collaborationDao.getManagmentCollaborationFromRequest(user));

        for (Collaboration collaboration : collaborations) {
            if (collaboration != null && collaboration.pending) {
                Offer offer = offerDao.getOffer(collaboration.getId_O());
                Request request = requestDao.getRequest(collaboration.getId_R());
                StudentsColaborating studentsColaborating = new StudentsColaborating(collaborationDao.getStudent(offer.getId_al()), collaborationDao.getStudent(request.getId_al()), offerDao.getSkill(offer.getId_S()));
                collaborationMap.put(collaboration, studentsColaborating);
            }
        }
        model.addAttribute("collaborations", collaborationMap);
        return "collaboration/management";
    }

    @RequestMapping(value="/pending")
    public String listRequestedCollaborations(Model model, HttpSession session) {
        Student user = (Student) session.getAttribute("student");
        Map<Collaboration, StudentsColaborating> collaborationMap = new HashMap<>();

        List<Collaboration> collaborations = new LinkedList<>(collaborationDao.getPendingCollaborationFromOffer(user));
        collaborations.addAll(collaborationDao.getPendingCollaborationFromRequest(user));

        for (Collaboration collaboration : collaborations) {
            if (collaboration != null && collaboration.pending) {
                Offer offer = offerDao.getOffer(collaboration.getId_O());
                Request request = requestDao.getRequest(collaboration.getId_R());
                StudentsColaborating studentsColaborating = new StudentsColaborating(collaborationDao.getStudent(offer.getId_al()), collaborationDao.getStudent(request.getId_al()), offerDao.getSkill(offer.getId_S()));
                collaborationMap.put(collaboration, studentsColaborating);
            }
        }
        model.addAttribute("collaborations", collaborationMap);
        return "collaboration/pending";
    }

    @RequestMapping(value="/addOffer/{id_O}")
    public String addCollaborationOffer(HttpSession session, @PathVariable int id_O) {
        Offer offer = offerDao.getOffer(id_O);
        Student user = (Student) session.getAttribute("student");
        Request request = new Request(user.getId_al(), offer.getId_S(), offer.getStartDate(), offer.getEndDate(), false);
        requestDao.addRequest(request, user);
        request = requestDao.getLastRequest();
        Collaboration collaboration = new Collaboration(request.getId_R(), offer.getId_O(), offer.getStartDate(), offer.getEndDate(), true, request.getId_R());
        collaborationDao.addCollaboration(collaboration);
        return "redirect:../../collaboration/pending";
    }

    @RequestMapping(value="/addRequest/{id_R}")
    public String addCollaborationRequest(HttpSession session, @PathVariable int id_R) {
        Request request = requestDao.getRequest(id_R);
        Student user = (Student) session.getAttribute("student");
        Offer offer = new Offer(user.getId_al(), request.getId_S(), request.getStartDate(), request.getEndDate(), false);
        offerDao.addOffer(offer, user);
        offer = offerDao.getLastOffer();
        Collaboration collaboration = new Collaboration(request.getId_R(), offer.getId_O(), request.getStartDate(), request.getEndDate(), true, offer.getId_O());
        collaborationDao.addCollaboration(collaboration);
        return "redirect:../../collaboration/pending";
    }

    @RequestMapping(value="/addSR/{id_r}")
    public String processAddSR(@PathVariable int id_r, HttpSession session) {
        Offer offer = (Offer) session.getAttribute("offer");
        Request request = requestDao.getRequest(id_r);
        Collaboration collaboration = new Collaboration(request.getId_R(), offer.getId_O(), offer.getStartDate(), offer.getEndDate(), true, offer.getId_O());
        collaborationDao.addCollaboration(collaboration);
        offerDao.disableOffer(offer);
        return "redirect:../../collaboration/pending";
    }

    @RequestMapping(value="/addSO", method= RequestMethod.POST)
    public String processAddSO(
            @ModelAttribute("collaboration") Collaboration collaboration,
            BindingResult bindingResult) {
        if (bindingResult.hasErrors())
            return "collaboration/add";
        collaborationDao.addCollaboration(collaboration);
        return "redirect:list";
    }

    @RequestMapping(value="/update/{id_C}", method = RequestMethod.GET)
    public String editCollaboration(Model model, @PathVariable int id_C) {
        model.addAttribute("collaboration", collaborationDao.getCollaboration(id_C));
        return "collaboration/update";
    }

    @RequestMapping(value="/update", method = RequestMethod.POST)
    public String processUpdateSubmit(
            @ModelAttribute("collaboration") Collaboration collaboration,
            BindingResult bindingResult) {
        if (bindingResult.hasErrors())
            return "collaboration/update";
        collaborationDao.updateCollaboration(collaboration);
        return "redirect:list";
    }

    @RequestMapping(value="/close/{id_C}", method = RequestMethod.GET)
    public String closeCollaboration(Model model, @PathVariable int id_C, HttpSession session){
        Collaboration collaboration = collaborationDao.getCollaboration(id_C);
        Offer offer = offerDao.getOffer(collaboration.getId_O());
        Request request = requestDao.getRequest(collaboration.getId_R());
        StudentsColaborating studentsColaborating = new StudentsColaborating(collaborationDao.getStudent(offer.getId_al()), collaborationDao.getStudent(request.getId_al()), offerDao.getSkill(offer.getId_S()));
        model.addAttribute("studentsColab", studentsColaborating);
        session.setAttribute("studentsColab", studentsColaborating);
        session.setAttribute("collaboration", collaboration);
        System.out.println(collaboration.getRequesting());
        return "collaboration/close";
    }

    @RequestMapping(value="/close", method = RequestMethod.POST)
    public String proccessCloseSubmit(@ModelAttribute("studentsCollab") StudentsColaborating studentsColaborating, BindingResult bindingResult, HttpSession session){
        if (bindingResult.hasErrors())
            return "collaboration/close";
        StudentsColaborating studentsColaborating1 = (StudentsColaborating) session.getAttribute("studentsColab");
        studentsColaborating.setTeacher(studentsColaborating1.getTeacher());
        studentsColaborating.setStudent(studentsColaborating1.getStudent());
        Collaboration collaboration = (Collaboration) session.getAttribute("collaboration");
        System.out.println(collaboration.getRequesting());
        collaboration.setState(false);
        collaboration.setScore(studentsColaborating.getScore());
        collaborationDao.updateCollaboration(collaboration);
        Student teacher = studentDao.getStudent(studentsColaborating.getTeacher().getId_al());
        teacher.setHours(studentsColaborating.getHours() + teacher.getHours());
        Student student = studentDao.getStudent(studentsColaborating.getStudent().getId_al());
        student.setHours(studentsColaborating.getHours() + student.getHours());
        studentDao.setHours(teacher);
        studentDao.setHours(student);
        return "redirect:list";
    }

    @RequestMapping(value = "/accept/{id_C}")
    public String processAcceptSubmit(@PathVariable int id_C){
        collaborationDao.acceptCollaboration(id_C);
        return "redirect:../list";
    }

    @RequestMapping(value="/deny/{id_C}")
    public String processDeny(@PathVariable int id_C) {
        Collaboration collaboration = collaborationDao.getCollaboration(id_C);
        if (collaboration.getId_O() == collaboration.getRequesting())
            offerDao.deleteOffer(collaboration.getId_O());
        else
            requestDao.deleteRequest(collaboration.getId_R());
        collaborationDao.deleteCollaboration(id_C);
        return "redirect:../management";
    }
}