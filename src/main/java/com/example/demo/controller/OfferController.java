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

    @Autowired
    public void setOfferDao(OfferDao offerDao) {
        this.offerDao = offerDao;
    }

    @RequestMapping("/list")
    public String listOffers(HttpSession session, Model model) {
        Map<Offer, SkillType> offerSkillTypeMap = new HashMap<>();
        if (session.getAttribute("student") == null) {
            for (Offer offer : offerDao.getOffers(null)) {
                if (offerDao.getOffer(offer.getId_O()).getActive())
                    offerSkillTypeMap.put(offer, offerDao.getSkill(offerDao.getOffer(offer.getId_O()).getId_S()));
            }
        }
        else for (Offer offer : offerDao.getOffers((Student) session.getAttribute("student"))){
            if (offerDao.getOffer(offer.getId_O()).getActive())
                offerSkillTypeMap.put(offer, offerDao.getSkill(offerDao.getOffer(offer.getId_O()).getId_S()));
        }

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

    @RequestMapping(value="/addfin")
    public String addOfferFin() {
        return "redirect:myoffers";
    }

    @RequestMapping(value="/add")
    public String addOffer(Model model) {
        model.addAttribute("offer", new Offer());
        model.addAttribute("skills", offerDao.getSkillTypes());
        return "offer/add";
    }

    @RequestMapping(value="/add", method= RequestMethod.POST)
    public String processAddSubmit(
            @ModelAttribute("offer") Offer offer,
            BindingResult bindingResult, HttpSession session) {
        if (bindingResult.hasErrors())
            return "offer/add";
        offerDao.addOffer(offer, (Student) session.getAttribute("student"));
        offer = offerDao.getLastOffer();
        session.setAttribute("offer", offer);
        return "redirect:../request/similarRequests";
    }

    @RequestMapping(value="/update/{id_O}", method = RequestMethod.GET)
    public String updateOffer(Model model, @PathVariable int id_O) {
        model.addAttribute("offer", offerDao.getOffer(id_O));
        model.addAttribute("skills", offerDao.getSkillTypes());
        return "offer/update";
    }

    @RequestMapping(value="/update", method = RequestMethod.POST)
    public String processUpdateSubmit(
            @ModelAttribute("offer") Offer offer,
            BindingResult bindingResult){
        if (bindingResult.hasErrors())
            return "offer/update";
        offerDao.updateOffer(offer);
        return "redirect:myoffers";
    }

    @RequestMapping(value="/delete/{id_O}")
    public String processDelete(@PathVariable int id_O, HttpSession session) {
        if (session.getAttribute("student") == null) {
            session.setAttribute("nextUrl", "/offer/delete");
            return "login";
        }
        offerDao.deleteOffer(id_O);
        return "redirect:../list";
    }
}
