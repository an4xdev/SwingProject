package edu.uws.ii.project.controllers;

import edu.uws.ii.project.services.categories.ICategoryService;
import edu.uws.ii.project.services.difficulties.IDifficultyService;
import edu.uws.ii.project.services.events.IEventService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/admin")
public class AdminController {

    private final IDifficultyService difficultyService;
    private final ICategoryService categoryService;
    private final IEventService eventService;

    @Autowired
    public AdminController(IDifficultyService difficultyService, ICategoryService categoryService, IEventService eventService) {
        this.difficultyService = difficultyService;
        this.categoryService = categoryService;
        this.eventService = eventService;
    }

    @GetMapping
    public String admin(Model model) {
        model.addAttribute("difficulties", difficultyService.count());
        model.addAttribute("categories", categoryService.count());
        model.addAttribute("events", eventService.count());
        return "admin_panel";
    }

    @GetMapping("/manage/{type}")
    public String manage(@PathVariable String type, Model model) {
        switch (type) {
            case "difficulty":
                model.addAttribute("items", difficultyService.findManageDTO());
                model.addAttribute("type", "Difficulties");
                break;
            case "category":
                model.addAttribute("items", categoryService.findManageDTO());
                model.addAttribute("type", "Categories");
                break;
            case "event":
                model.addAttribute("items", eventService.findManageDTO());
                model.addAttribute("type", "Events");
                break;
            default:
                return "redirect:/admin";
        }
        model.addAttribute("typeAdd", type.substring(0, 1).toUpperCase() + type.substring(1));
        model.addAttribute("typeForm", type);
        return "manage";
    }

    @PostMapping("/manage/add/{type}")
    public String add(@PathVariable String type, @RequestParam String name) {
        switch (type) {
            case "difficulty":
                difficultyService.save(name);
                break;
            case "category":
                categoryService.save(name);
                break;
            case "event":
                eventService.save(name);
                break;
        }
        return "redirect:/admin/manage/" + type;
    }

    @GetMapping("/manage/edit/{type}/{id}")
    public String editForm(@PathVariable String type, @PathVariable Long id, Model model) {
        switch (type) {
            case "difficulty":
                model.addAttribute("item", difficultyService.findById(id));
                model.addAttribute("typeForm", "difficulty");
                break;
            case "category":
                model.addAttribute("item", categoryService.findById(id));
                model.addAttribute("typeForm", "category");
                break;
            case "event":
                model.addAttribute("item", eventService.findById(id));
                model.addAttribute("typeForm", "event");
                break;
            default:
                return "redirect:/admin";
        }
        model.addAttribute("type", type.substring(0, 1).toUpperCase() + type.substring(1));
        return "manage_form";
    }

    @PutMapping("/manage/edit/{type}/{id}")
    public String edit(@PathVariable String type, @PathVariable Long id, @RequestParam String name) {
        switch (type) {
            case "difficulty":
                difficultyService.update(id, name);
                break;
            case "category":
                categoryService.update(id, name);
                break;
            case "event":
                eventService.update(id, name);
                break;
        }
        return "redirect:/admin/manage/" + type;
    }

    @DeleteMapping("/manage/delete/{type}/{id}")
    public String delete(@PathVariable String type, @PathVariable Long id) {
        switch (type) {
            case "difficulty":
                difficultyService.deleteById(id);
                break;
            case "category":
                categoryService.deleteById(id);
                break;
            case "event":
                eventService.deleteById(id);
                break;
        }
        return "redirect:/admin/manage/" + type;
    }
}
