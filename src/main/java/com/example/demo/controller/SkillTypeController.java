package com.example.demo.controller;

import com.example.demo.dao.CollaborationDao;
import com.example.demo.dao.OfferDao;
import com.example.demo.dao.RequestDao;
import com.example.demo.dao.SkillTypeDao;
import com.example.demo.model.Collaboration;
import com.example.demo.model.Offer;
import com.example.demo.model.Request;
import com.example.demo.model.SkillType;
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
@RequestMapping("/skilltype")
public class SkillTypeController {

    private SkillTypeDao skillTypeDao;
    private OfferDao offerDao;
    private RequestDao requestDao;
    private CollaborationDao collaborationDao;

    @Autowired
    public void setSkillTypeDao(SkillTypeDao skillTypeDao) {
        this.skillTypeDao = skillTypeDao;
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

    @RequestMapping("/list")
    public String listSkillTypes(Model model) {
        model.addAttribute("skilltypes", skillTypeDao.getSkillTypes());
        return "skilltype/list";
    }

    @RequestMapping(value="/add")
    public String addSkillType(Model model) {
        model.addAttribute("skilltype", new SkillType());
        return "skilltype/add";
    }

    @RequestMapping(value="/add", method= RequestMethod.POST)
    public String processAddSubmit(@ModelAttribute("skilltype") SkillType skillType, BindingResult bindingResult) {
        if (bindingResult.hasErrors())
            return "skilltype/add";
        skillTypeDao.addSkillType(skillType);
        return "redirect:list";
    }
    @RequestMapping(value="/update/{id_S}", method = RequestMethod.GET)
    public String editSkillType(Model model, @PathVariable int id_S, HttpSession session) {
        model.addAttribute("skilltype", skillTypeDao.getSkillType(id_S));
        session.setAttribute("skilltype", skillTypeDao.getSkillType(id_S));
        return "skilltype/update";
    }

    @RequestMapping(value="/update", method = RequestMethod.POST)
    public String processUpdateSubmit(
            @ModelAttribute("skilltype") SkillType skillType,
            BindingResult bindingResult, HttpSession session) {
        if (bindingResult.hasErrors())
            return "skilltype/update";
        SkillType skillType1 = (SkillType) session.getAttribute("skilltype");
        skillType1.setAll(skillType);
        skillTypeDao.updateSkillType(skillType1);
        return "redirect:list";
    }

    @RequestMapping(value="/delete/{id_S}")
    public String processDelete(@PathVariable int id_S) {
        offerDao.disableWithSkill(id_S);
        requestDao.disableWithSkill(id_S);
        skillTypeDao.deleteSkilType(id_S);
        return "redirect:../list";
    }
}
