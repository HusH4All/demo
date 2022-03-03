package com.example.demo.controller;

import com.example.demo.dao.OfferDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/offer")
public class OfferController {

    private OfferDao offer;

    @Autowired
    public void setOfferDao(OfferDao offerDao) {
        this.offer = offerDao;
    }

    @RequestMapping("/list")
    public String listOffers(Model model) {
        model.addAttribute("offers", offer.getOffers());
        return "offer/list";
    }
}
