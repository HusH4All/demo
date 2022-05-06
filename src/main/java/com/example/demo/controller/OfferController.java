package com.example.demo.controller;

import com.example.demo.dao.OfferDao;
import com.example.demo.dao.SkillTypeDao;
import com.example.demo.model.Offer;
import com.example.demo.model.SkillType;
import com.example.demo.model.Student;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.web.servlet.server.Session;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import javax.servlet.http.HttpSession;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Controller
@RequestMapping("/offer")
public class OfferController {

    private OfferDao offerDao;
    private SkillTypeDao skillTypeDao;

    @Autowired
    public void setOfferDao(OfferDao offerDao) {
        this.offerDao = offerDao;
    }

    @RequestMapping("/list")
    public String listOffers(HttpSession session, Model model) {
        Map<Offer, SkillType> offerSkillTypeMap = new HashMap<>();
        if (session.getAttribute("student") == null) {
            for (Offer offer : offerDao.getOffers(null)) offerSkillTypeMap.put(offer, offerDao.getSkill(offerDao.getOffer(offer.getId_O()).getId_S()));
        }
        else for (Offer offer : offerDao.getOffers((Student) session.getAttribute("student"))) offerSkillTypeMap.put(offer, offerDao.getSkill(offerDao.getOffer(offer.getId_O()).getId_S()));

        model.addAttribute("offers", offerSkillTypeMap);
        return "offer/list";
    }

    @RequestMapping("/myoffers")
    public String listMyOffers(HttpSession session, Model model) {
        Map<Offer, SkillType> offerSkillTypeMap = new HashMap<>();
        for (Offer offer : offerDao.getMyOffers((Student) session.getAttribute("student"))) offerSkillTypeMap.put(offer, offerDao.getSkill(offerDao.getOffer(offer.getId_O()).getId_S()));

        model.addAttribute("offers", offerSkillTypeMap);
        return "offer/list";
    }


    @RequestMapping(value="/add")
    public String addOffer(Model model) {
        model.addAttribute("offer", new Offer());
        return "offer/add";
    }

    @RequestMapping(value="/add", method= RequestMethod.POST)
    public String processAddSubmit(
            @ModelAttribute("offer") Offer offer,
            BindingResult bindingResult, HttpSession session) {
        if (bindingResult.hasErrors())
            return "offer/add";
        String id =  offerDao.lastOffer().id_O;
        int num_id = Integer.parseInt(id);
        num_id++;
        id = String.valueOf(num_id);
        offerDao.addOffer(offer, id, (Student) session.getAttribute("student"));
        return "redirect:list";
    }

    @RequestMapping(value="/update/{id_O}", method = RequestMethod.GET)
    public String editOffer(Model model, @PathVariable String id_O) {
        model.addAttribute("offer", offerDao.getOffer(id_O));
        return "offer/update";
    }

    @RequestMapping(value="/update", method = RequestMethod.POST)
    public String processUpdateSubmit(
            @ModelAttribute("offer") Offer offer,
            BindingResult bindingResult) {
        if (bindingResult.hasErrors())
            return "offer/update";
        offerDao.updateOffer(offer);
        return "redirect:list";
    }

    @RequestMapping(value="/delete/{id_O}")
    public String processDelete(@PathVariable String id_O) {
        offerDao.deleteOffer(id_O);
        return "redirect:../list";
    }
}
