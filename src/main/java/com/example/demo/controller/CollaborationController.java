package com.example.demo.controller;

import com.example.demo.dao.CollaborationDao;
import com.example.demo.model.Collaboration;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@Controller
@RequestMapping("/collaboration")
public class CollaborationController {

    private CollaborationDao collaborationDao;

    @Autowired
    public void setCollaborationDao(CollaborationDao collaborationDao) {
        this.collaborationDao = collaborationDao;
    }

    @RequestMapping("/list")
    public String listCollaborations(Model model) {
        model.addAttribute("collaboration", collaborationDao.getCollaborations());
        return "collaboration/list";
    }

    @RequestMapping(value="/add")
    public String addCollaboration(Model model) {
        model.addAttribute("collaboration", new Collaboration());
        return "collaboration/add";
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