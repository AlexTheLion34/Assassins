package com.itmo.assassins.controller.request;

import com.itmo.assassins.model.request.Request;
import com.itmo.assassins.model.request.RequestInfo;
import com.itmo.assassins.model.request.RequestStatus;
import com.itmo.assassins.model.user.*;
import com.itmo.assassins.service.request.RequestDifficultyComputationService;
import com.itmo.assassins.service.request.RequestService;
import com.itmo.assassins.service.user.UserSecurityService;
import com.itmo.assassins.service.user.UserService;
import org.hibernate.Hibernate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;
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
            model.addAttribute("user",
                    userService.findUserByUserName(securityService.getLoggedInUserName()));
        });

        return "view-request";
    }

    @RequestMapping(value = "/change-request", method = RequestMethod.GET)
    public String changeRequest(ModelMap model, @RequestParam String id) {

        Optional<Request> request = requestService.getRequestById(Long.parseLong(id));

        request.ifPresent(req -> {

            User currentUser = userService.findUserByUserName(securityService.getLoggedInUserName());
            model.put("user", currentUser);
            model.put("id", id);

            switch (currentUser.getRole()) {
                case MASTER_ASSASSIN:
                    model.put("executor", req.getExecutor());
                    model.put("cabman", req.getCabman());
                    model.put("gunsmith", req.getGunsmith());
                    break;
                case GUNSMITH:
                    System.out.println("kek");
                    break;
                default:
                    break;
            }
        });

        return "change-request";
    }

    @RequestMapping(value = "/change-team", method = RequestMethod.GET)
    public String changeMember(ModelMap model, @RequestParam String requestId, @RequestParam String role) {

        Optional<Request> request = requestService.getRequestById(Long.parseLong(requestId));

        request.ifPresent(req -> {

            User currentUser = userService.findUserByUserName(securityService.getLoggedInUserName());
            model.put("user", currentUser);
            model.put("id", requestId);

            switch (UserRole.valueOf(role)) {
                case EXECUTOR:
                    model.put("title", "Ассассины");
                    model.put("team", userService.findUserByRole(UserRole.EXECUTOR));
                    break;
                case GUNSMITH:
                    model.put("title", "Оружейники");
                    model.put("team", userService.findUserByRole(UserRole.GUNSMITH));
                    break;
                default:
                    model.put("title", "Извозчики");
                    model.put("team", userService.findUserByRole(UserRole.CABMAN));
                    break;
            }
        });

        return "change-team";
    }

    // TODO: - move logic to request service from two below methods

    @RequestMapping(value = "/change-team", method = RequestMethod.POST)
    public String changeMemberPost(@RequestParam String requestId, @RequestParam String userToChangeId) {

        Optional<Request> request = requestService.getRequestById(Long.parseLong(requestId));

        request.ifPresent(req -> {

            User userToChange = Hibernate.unproxy(userService.findUserById(Long.parseLong(userToChangeId)), User.class);

            switch (userToChange.getRole()) {
                case EXECUTOR:
                    req.getExecutor().setBusy(false);
                    ((Executor) userToChange).setBusy(true);
                    req.setExecutor(((Executor) userToChange));
                    break;
                case CABMAN:
                    req.setCabman((Cabman) userToChange);
                    break;
                case GUNSMITH:
                    req.setGunsmith((Gunsmith) userToChange);
                    break;
            }
            requestService.saveRequest(req);
        });

        return "redirect:/change-request?id=" + requestId;
    }

    @RequestMapping(value = "/confirm-request", method = RequestMethod.GET)
    public String confirmRequest(@RequestParam String requestId, @RequestParam String role) {

        Optional<Request> request = requestService.getRequestById(Long.parseLong(requestId));

        User currentUser = userService.findUserByUserName(securityService.getLoggedInUserName());

        request.ifPresent(req -> {

            switch (UserRole.valueOf(role)) {
                case MASTER_ASSASSIN:
                    List<Request> masterRequests = ((Master) currentUser).getRequests();
                    masterRequests.remove(req);
                    ((Master) currentUser).setRequests(masterRequests);
                    req.getRequestInfo().setStatus(RequestStatus.PACKING_1);
                    req.setMaster(null);
                    break;
                case CABMAN:
                    List<Request> cabmanRequests = ((Cabman) currentUser).getRequests();
                    cabmanRequests.remove(req);
                    ((Cabman) currentUser).setRequests(cabmanRequests);
                    req.getRequestInfo().setStatus(RequestStatus.PACKING_2);
                    req.setCabman(null);
                    break;
                case GUNSMITH:
                    List<Request> gunsmithRequests = ((Gunsmith) currentUser).getRequests();
                    gunsmithRequests.remove(req);
                    ((Gunsmith) currentUser).setRequests(gunsmithRequests);
                    req.getRequestInfo().setStatus(RequestStatus.EXECUTING);
                    req.setGunsmith(null);
                    break;
            }
            requestService.saveRequest(req);
            //userService.saveUser(currentUser); проверить, нужно ли???
        });

        return "redirect:/profile";
    }
}
