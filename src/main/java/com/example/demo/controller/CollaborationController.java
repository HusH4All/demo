package com.example.demo.controller;

import com.example.demo.dao.CollaborationDao;
import com.example.demo.dao.OfferDao;
import com.example.demo.dao.RequestDao;
import com.example.demo.model.Collaboration;
import com.example.demo.model.Offer;
import com.example.demo.model.Request;
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

@Controller
@RequestMapping("/collaboration")
public class CollaborationController {

    private CollaborationDao collaborationDao;
    private RequestDao requestDao;
    private OfferDao offerDao;

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

    @RequestMapping("/list")
    public String listCollaborations(Model model) {
        model.addAttribute("collaboration", collaborationDao.getCollaborations());
        return "collaboration/list";
    }

    @RequestMapping(value="/add/{id_O}")
    public String addCollaborationOffer(HttpSession session, @PathVariable int id_O) {
        Offer offer = offerDao.getOffer(id_O);
        Student user = (Student) session.getAttribute("student");
        Request request = new Request(user.getId_al(), offer.getId_S(), offer.getStartDate(), offer.getEndDate(), false);
        requestDao.addRequest(request, user);
        request = requestDao.getLastRequest();
        Collaboration collaboration = new Collaboration(request.getId_R(), offer.getId_O(), offer.getStartDate(), offer.getEndDate(), true);
        collaborationDao.addCollaboration(collaboration);
        return "redirect:../list";
    }

    @RequestMapping(value="/add", method= RequestMethod.POST)
    public String processAddSubmit(
            @ModelAttribute("collaboration") Collaboration collaboration,
            BindingResult bindingResult) {
        if (bindingResult.hasErrors())
            return "collaboration/add";
        collaborationDao.addCollaboration(collaboration);
        return "redirect:list";
    }

    @RequestMapping(value="/update/{id_C}", method = RequestMethod.GET)
    public String editCollaboration(Model model, @PathVariable String id_C) {
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

    @RequestMapping(value="/delete/{id_C}")
    public String processDelete(@PathVariable String id_C) {
        collaborationDao.deleteCollaboration(id_C);
        return "redirect:../list";
    }
}