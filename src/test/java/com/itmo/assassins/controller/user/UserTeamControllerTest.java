package com.itmo.assassins.controller.user;

import com.itmo.assassins.model.request.Request;
import com.itmo.assassins.model.request.RequestDifficulty;
import com.itmo.assassins.model.request.RequestInfo;
import com.itmo.assassins.model.user.Cabman;
import com.itmo.assassins.model.user.Executor;
import com.itmo.assassins.model.user.Gunsmith;
import com.itmo.assassins.model.user.User;
import com.itmo.assassins.service.request.RequestService;
import com.itmo.assassins.service.user.UserSecurityService;
import com.itmo.assassins.service.user.UserService;
import com.itmo.assassins.service.user.UserTeamService;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentMatchers;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.test.web.servlet.MockMvc;

import java.util.Collections;
import java.util.Optional;

import static org.hamcrest.Matchers.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(UserTeamController.class)
@AutoConfigureMockMvc(addFilters = false)
class UserTeamControllerTest {

    @MockBean
    private RequestService requestService;

    @MockBean
    private UserService userService;

    @MockBean
    private UserTeamService teamService;

    @MockBean
    private UserDetailsService userDetailsService;

    @MockBean
    private UserSecurityService securityService;

    @Autowired
    private MockMvc mockMvc;


    @Test
    void testGetChangeMemberExecutor() throws Exception {

        Request request = new Request();

        Executor executor = new Executor();
        request.setExecutor(executor);

        RequestInfo requestInfo = new RequestInfo();
        requestInfo.setDifficulty(RequestDifficulty.HARD);
        request.setRequestInfo(requestInfo);

        Mockito.when(requestService.getRequestById(1))
                .thenReturn(Optional.of(request));

        Mockito.when(securityService.getLoggedInUserName())
                        .thenReturn("username");

        Mockito.when(userService.findUserByUserName("username"))
                        .thenReturn(executor);

        Executor foundExecutor = new Executor();

        Mockito.when(teamService.findExecutorsExceptGiven(executor, requestInfo.getDifficulty()))
                        .thenReturn(Collections.singletonList(foundExecutor));

        mockMvc.perform(get("/change-team?requestId=1&role=EXECUTOR"))
                .andExpect(status().isOk())
                .andExpect(view().name("change-team"))
                .andExpect(forwardedUrl("/WEB-INF/jsp/change-team.jsp"))
                .andExpect(model().attribute("team", hasSize(1)))
                .andExpect(model().attribute("user", is(any(Executor.class))))
                .andExpect(model().attribute("id", is("1")))
                .andExpect(model().attribute("title", is("Ассассины")));
    }

    @Test
    void testGetChangeMemberCabman() throws Exception {

        Request request = new Request();

        Cabman cabman = new Cabman();
        request.setCabman(cabman);


        Mockito.when(requestService.getRequestById(1))
                .thenReturn(Optional.of(request));

        Mockito.when(securityService.getLoggedInUserName())
                .thenReturn("username");

        Mockito.when(userService.findUserByUserName("username"))
                .thenReturn(cabman);

        Cabman foundCabman = new Cabman();

        Mockito.when(teamService.findCabmenExceptGiven(cabman))
                .thenReturn(Collections.singletonList(foundCabman));

        mockMvc.perform(get("/change-team?requestId=1&role=CABMAN"))
                .andExpect(status().isOk())
                .andExpect(view().name("change-team"))
                .andExpect(forwardedUrl("/WEB-INF/jsp/change-team.jsp"))
                .andExpect(model().attribute("team", hasSize(1)))
                .andExpect(model().attribute("user", is(any(Cabman.class))))
                .andExpect(model().attribute("id", is("1")))
                .andExpect(model().attribute("title", is("Извозчики")));
    }

    @Test
    void testGetChangeMemberGunsmith() throws Exception {

        Request request = new Request();

        Gunsmith gunsmith = new Gunsmith();
        request.setGunsmith(gunsmith);


        Mockito.when(requestService.getRequestById(1))
                .thenReturn(Optional.of(request));

        Mockito.when(securityService.getLoggedInUserName())
                .thenReturn("username");

        Mockito.when(userService.findUserByUserName("username"))
                .thenReturn(gunsmith);

        Gunsmith foundGunsmith = new Gunsmith();

        Mockito.when(teamService.findGunsmithsExceptGiven(gunsmith))
                .thenReturn(Collections.singletonList(foundGunsmith));

        mockMvc.perform(get("/change-team?requestId=1&role=GUNSMITH"))
                .andExpect(status().isOk())
                .andExpect(view().name("change-team"))
                .andExpect(forwardedUrl("/WEB-INF/jsp/change-team.jsp"))
                .andExpect(model().attribute("team", hasSize(1)))
                .andExpect(model().attribute("user", is(any(Gunsmith.class))))
                .andExpect(model().attribute("id", is("1")))
                .andExpect(model().attribute("title", is("Оружейники")));
    }

    @Test
    void testPostChangeMember() throws Exception {

        Request request = new Request();

        Mockito.when(requestService.getRequestById(1L))
                .thenReturn(Optional.of(request));

        User user = new User();

        Mockito.when(userService.findUserById(1L))
                .thenReturn(user);

        mockMvc.perform(post("/change-team?requestId=1&userToChangeId=1"))
                .andExpect(status().is(302))
                .andExpect(view().name("redirect:/change-request?id=1"))
                .andExpect(redirectedUrl("/change-request?id=1"));

        Mockito.verify(requestService, Mockito.times(1))
                .changeRequestTeam(request, user);
    }
}