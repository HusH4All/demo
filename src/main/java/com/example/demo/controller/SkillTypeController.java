package com.example.demo.controller;

import com.example.demo.dao.SkillTypeDao;
import com.example.demo.model.SkillType;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@Controller
@RequestMapping("/offer")
public class SkillTypeController {

    private SkillTypeDao skillTypeDao;

    @Autowired
    public void setOfferDao(SkillTypeDao skillTypeDao) {
        this.skillTypeDao = skillTypeDao;
    }

    @RequestMapping("/list")
    public String listSkillTypes(Model model) {
        model.addAttribute("offers", skillTypeDao.getSkillTypes());
        return "skilltype/list";
    }

    @RequestMapping(value="/add")
    public String addSkillType(Model model) {
        model.addAttribute("offer", new SkillType());
        return "skilltype/add";
    }

    @RequestMapping(value="/add", method= RequestMethod.POST)
    public String processAddSubmit(@ModelAttribute("skilltype") SkillType skillType, BindingResult bindingResult) {
        if (bindingResult.hasErrors())
            return "offer/add";
        skillTypeDao.addSkillType(skillType);
        return "redirect:list";
    }
    @RequestMapping(value="/update/{id_S}", method = RequestMethod.GET)
    public String editSkillType(Model model, @PathVariable String id_S) {
        model.addAttribute("skilltype", skillTypeDao.getSkillType(id_S));
        return "skilltype/update";
    }

    @RequestMapping(value="/update", method = RequestMethod.POST)
    public String processUpdateSubmit(
            @ModelAttribute("skilltype") SkillType skillType,
            BindingResult bindingResult) {
        if (bindingResult.hasErrors())
            return "skilltype/update";
        skillTypeDao.updateSkillType(skillType);
        return "redirect:list";
    }

    @RequestMapping(value="/delete/{id_S}")
    public String processDelete(@PathVariable String id_S) {
        skillTypeDao.deleteSkilType(id_S);
        return "redirect:../list";
    }
}
