package com.example.demo.controller;

import com.example.demo.dao.OfferDao;
import com.example.demo.dao.RequestDao;
import com.example.demo.dao.SkillTypeDao;
import com.example.demo.model.Offer;
import com.example.demo.model.Request;
import com.example.demo.model.SkillType;
import com.example.demo.model.Student;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import javax.servlet.http.HttpSession;
import java.time.LocalDate;
import java.util.HashMap;
import java.util.Map;

@Controller
@RequestMapping("/request")
public class RequestController {

    private RequestDao requestDao;

    private SkillTypeDao skillTypeDao;

    @Autowired
    public void setSkillTypeDao(SkillTypeDao skillTypeDao) {
        this.skillTypeDao = skillTypeDao;
    }

    @Autowired
    public void setRequestDao(RequestDao requestDao) {
        this.requestDao = requestDao;
    }

    @RequestMapping("/list")
    public String listRequests(HttpSession session, Model model) {
        Map<Request, SkillType> requestSkillTypeMap = new HashMap<>();
        if (session.getAttribute("student") == null) {
            for (Request request : requestDao.getRequests(null)){
                if (requestDao.getRequest(request.getId_R()).getActive())
                    requestSkillTypeMap.put(request, requestDao.getSkill(requestDao.getRequest(request.getId_R()).getId_S()));
            }
        }
        else for (Request request : requestDao.getRequests((Student) session.getAttribute("student"))){
            if (requestDao.getRequest(request.getId_R()).getActive())
                requestSkillTypeMap.put(request, requestDao.getSkill(requestDao.getRequest(request.getId_R()).getId_S()));
        }

        model.addAttribute("requests", requestSkillTypeMap);
        return "request/list";
    }

    @RequestMapping("/myrequests")
    public String listMyRequests(HttpSession session, Model model) {
        Map<Request, SkillType> requestSkillTypeMap = new HashMap<>();
        for (Request request : requestDao.getMyRequests((Student) session.getAttribute("student"))) requestSkillTypeMap.put(request, requestDao.getSkill(requestDao.getRequest(request.getId_R()).getId_S()));

        model.addAttribute("requests", requestSkillTypeMap);
        return "request/list";
    }

    @RequestMapping("/similarRequests")
    public String listSimilarRequests(HttpSession session, Model model) {
        Map<Request, SkillType> requestSkillTypeMap = new HashMap<>();
        Offer offer = (Offer) session.getAttribute("offer");
        for (Request request : requestDao.getSimilarRequests(offer.getId_S(), (Student) session.getAttribute("student"))) requestSkillTypeMap.put(request, requestDao.getSkill(requestDao.getRequest(request.getId_R()).getId_S()));

        model.addAttribute("requests", requestSkillTypeMap);
        return "request/similarRequests";
    }

    @RequestMapping(value="/addfin")
    public String addRequestFin() {
        return "redirect:myrequests";
    }

    @RequestMapping(value = "/add")
    public String addRequest(Model model) {
        model.addAttribute("request", new Request());
        model.addAttribute("skills", skillTypeDao.getSkillTypes());
        return "request/add";
    }

    @RequestMapping(value = "/add", method = RequestMethod.POST)
    public String processAddSubmit(
            @ModelAttribute("request") Request request,
            BindingResult bindingResult, HttpSession session) {
        requestDao.addRequest(request, (Student) session.getAttribute("student"));
        request = requestDao.getLastRequest();
        session.setAttribute("request", request);
        return "redirect:../offer/similarOffers";
    }

    @RequestMapping(value = "/update/{id_R}", method = RequestMethod.GET)
    public String editRequest(Model model, @PathVariable int id_R, HttpSession session) {
        model.addAttribute("request", requestDao.getRequest(id_R));
        model.addAttribute("skills", skillTypeDao.getSkillTypes());
        session.setAttribute("id_R", id_R);
        return "request/update";
    }

    @RequestMapping(value = "/update", method = RequestMethod.POST)
    public String processUpdateSubmit(
            @ModelAttribute("request") Request request,
            BindingResult bindingResult, HttpSession session) {
        if (bindingResult.hasErrors())
            return "request/update";
        int id_R = (int) session.getAttribute("id_R");
        request.setId_R(id_R);
        LocalDate localDate = LocalDate.now();
        if (request.getEndDate().equals(localDate))
            requestDao.disableRequest(request.getId_R());
        requestDao.updateRequest(request);
        return "redirect:myrequests";
    }

    @RequestMapping(value="/delete/{id_R}")
    public String processDelete(@PathVariable int id_R, HttpSession session) {
        if (session.getAttribute("student") == null) {
            session.setAttribute("nextUrl", "/request/delete");
            return "login";
        }
        requestDao.disableRequest(id_R);
        return "redirect:../myrequests";
    }
}