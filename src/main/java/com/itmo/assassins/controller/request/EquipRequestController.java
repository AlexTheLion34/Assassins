package com.itmo.assassins.controller.request;

import com.itmo.assassins.model.request.Request;
import com.itmo.assassins.model.request.RequestArsenal;
import com.itmo.assassins.model.request.RequestRoadEquipment;
import com.itmo.assassins.service.request.RequestService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.Optional;

@Controller
public class EquipRequestController {

    private final RequestService requestService;

    @Autowired
    public EquipRequestController(RequestService requestService) {
        this.requestService = requestService;
    }

    @RequestMapping(value = "/add-arsenal", method = RequestMethod.GET)
    public String equipArsenal(@RequestParam String id, ModelMap model) {
        model.addAttribute("requestId", id);
        model.addAttribute("requestArsenal", new RequestArsenal());
        return "add-arsenal";
    }

    @RequestMapping(value = "/add-arsenal", method = RequestMethod.POST)
    public String equipArsenal(RequestArsenal arsenal, @RequestParam String requestId) {

        Optional<Request> request = requestService.getRequestById(Long.parseLong(requestId));

        request.ifPresent(req -> requestService.addArsenal(req, arsenal));

        return "redirect:/profile";
    }

    @RequestMapping(value = "/add-road-eq", method = RequestMethod.GET)
    public String equipRoadEquipment(@RequestParam String id, ModelMap model) {
        model.addAttribute("requestId", id);
        model.addAttribute("requestRoadEq", new RequestRoadEquipment());
        return "add-equipment";
    }

    @RequestMapping(value = "/add-road-eq", method = RequestMethod.POST)
    public String equipRoadEquipment(RequestRoadEquipment equipment, @RequestParam String requestId) {

        Optional<Request> request = requestService.getRequestById(Long.parseLong(requestId));

        request.ifPresent(req -> requestService.addRoadEquipment(req, equipment));

        return "redirect:/profile";
    }
}
