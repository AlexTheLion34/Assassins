package com.itmo.assassins.controller.request;

import com.itmo.assassins.model.request.Request;
import com.itmo.assassins.model.request.RequestInfo;
import com.itmo.assassins.model.user.Customer;
import com.itmo.assassins.model.user.User;
import com.itmo.assassins.service.request.RequestDifficultyComputationService;
import com.itmo.assassins.service.request.RequestService;
import com.itmo.assassins.service.user.UserSecurityService;
import com.itmo.assassins.service.user.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import org.springframework.web.bind.annotation.RequestParam;

import java.util.Optional;

@Controller
public class RequestController {

    private final RequestService requestService;

    private final UserService userService;

    private final UserSecurityService securityService;

    private final RequestDifficultyComputationService difficultyComputationService;

    @Autowired
    public RequestController(RequestService requestService,
                             UserService userService,
                             UserSecurityService securityService,
                             RequestDifficultyComputationService difficultyComputationService) {

        this.requestService = requestService;
        this.userService = userService;
        this.securityService = securityService;
        this.difficultyComputationService = difficultyComputationService;
    }

    @RequestMapping(value = "/add-request", method = RequestMethod.GET)
    public String addRequest(ModelMap model) {

        model.addAttribute("requestInfo", new RequestInfo());

        return "request";
    }

    @RequestMapping(value = "/add-request", method = RequestMethod.POST)
    public String addRequest(RequestInfo requestInfo, BindingResult result) {

        if (result.hasErrors()) {
            System.out.println(result);
            return "request";
        }

        requestInfo.setDifficulty(difficultyComputationService.computeDifficulty(requestInfo));

        User currentUser = userService.findUserByUserName(securityService.getLoggedInUserName());

        requestService.createRequest(requestInfo, (Customer) currentUser);

        return "redirect:/profile";
    }

    @RequestMapping(value = "/view-request", method = RequestMethod.GET)
    public String viewRequest(ModelMap model, @RequestParam String id) {

        Optional<Request> request = requestService.getRequestById(Long.parseLong(id));

        request.ifPresent(req -> {
            model.addAttribute("request", req);
            model.addAttribute("user", userService.findUserByUserName(securityService.getLoggedInUserName()));
        });

        return "view-request";
    }

//    @RequestMapping(value = "/change-request", method = RequestMethod.GET)
//    public String changeRequest(ModelMap model, @RequestParam String id) {
//
//        Optional<Request> request = requestService.getRequestById(Long.parseLong(id));
//
//        request.ifPresent(req -> {
//
//            User currentUser = userService.findUserByUserName(securityService.getLoggedInUserName());
//            model.put("user", currentUser);
//            model.put("id", id);
//
//            switch (currentUser.getRole()) {
//                case MASTER_ASSASSIN:
//
//                    RequestTeam requestTeam = req.getRequestTeam();
//                    List<User> team = new ArrayList<>();
//
//                    team.add(requestTeam.getExecutor());
//                    team.add(requestTeam.getGunsmith());
//                    team.add(requestTeam.getCabman());
//
//                    model.put("requestTeam", team);
//                    break;
//                case GUNSMITH:
//                    System.out.println("kek");
//                    break;
//                default:
//                    break;
//            }
//        });
//
//        return "change-request";
//    }
//
//    @RequestMapping(value = "/change-team", method = RequestMethod.GET)
//    public String changeMember(ModelMap model,
//                               @RequestParam String requestId,
//                               @RequestParam String role) {
//
//        Optional<Request> request = requestService.getRequestById(Long.parseLong(requestId));
//
//        request.ifPresent(req -> {
//
//            User currentUser = userService.findUserByUserName(securityService.getLoggedInUserName());
//            model.put("user", currentUser);
//            model.put("id", requestId);
//
//            switch (UserRole.valueOf(role)) {
//                case EXECUTOR:
//                    model.put("title", "Ассассины");
//                    model.put("team", userService.findUserByRole(EXECUTOR));
//                    break;
//                case GUNSMITH:
//                    model.put("title", "Оружейники");
//                    model.put("team", userService.findUserByRole(UserRole.GUNSMITH));
//                    break;
//                default:
//                    model.put("title", "Извозчики");
//                    model.put("team", userService.findUserByRole(UserRole.CABMAN));
//                    break;
//            }
//        });
//
//        return "change-team";
//    }
//
//    @RequestMapping(value = "/change-team", method = RequestMethod.POST)
//    public String changeMemberPost(ModelMap model,
//                               @RequestParam String requestId,
//                               @RequestParam String role) {
//
//        System.out.println(requestId);
//        System.out.println(role);
//
//        //TODO:- добить отправку Post
//
//        return "a";
//    }
}
