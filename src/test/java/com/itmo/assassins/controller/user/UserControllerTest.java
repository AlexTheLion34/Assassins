package com.itmo.assassins.controller.user;

import com.itmo.assassins.model.request.Request;
import com.itmo.assassins.model.user.*;
import com.itmo.assassins.service.user.UserSecurityService;
import com.itmo.assassins.service.user.UserService;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.test.web.servlet.MockMvc;

import java.util.ArrayList;
import java.util.Collections;

import static org.hamcrest.Matchers.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;

import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(UserController.class)
@AutoConfigureMockMvc(addFilters = false)
class UserControllerTest {

    @MockBean
    private UserService userService;

    @MockBean
    private UserSecurityService securityService;

    @MockBean
    private UserDetailsService userDetailsService;

    @Autowired
    private MockMvc mockMvc;

    @Test
    void testLoginPage() throws Exception {
        mockMvc.perform(get("/login"))
                .andExpect(status().isOk())
                .andExpect(view().name("login"))
                .andExpect(forwardedUrl("/WEB-INF/jsp/login.jsp"));
    }


    @Test
    void testProfilePageForCustomer() throws Exception {

        Customer customer = new Customer();
        customer.setRole(UserRole.CUSTOMER);
        customer.setRequests(new ArrayList<>(Collections.singletonList(new Request())));

        Mockito.when(securityService.getLoggedInUserName())
                        .thenReturn("username");

        Mockito.when(userService.findUserByUserName("username"))
                        .thenReturn(customer);

        mockMvc.perform(get("/profile"))
                .andExpect(status().is(200))
                .andExpect(view().name("profile"))
                .andExpect(forwardedUrl("/WEB-INF/jsp/profile.jsp"))
                .andExpect(model().attribute("requests", hasSize(1)))
                .andExpect(model().attribute("user", is(customer)))
                .andExpect(model().attribute("customer", is(customer)));

        Mockito.verify(securityService, Mockito.times(1))
                .getLoggedInUserName();

        Mockito.verify(userService, Mockito.times(1))
                .findUserByUserName("username");
    }

    @Test
    void testProfilePageForExecutorWithNotNullRequests() throws Exception {

        Executor executor = new Executor();
        executor.setRole(UserRole.EXECUTOR);
        executor.setCurrentTask(new Request());

        Mockito.when(securityService.getLoggedInUserName())
                .thenReturn("username");

        Mockito.when(userService.findUserByUserName("username"))
                .thenReturn(executor);

        mockMvc.perform(get("/profile"))
                .andExpect(status().is(200))
                .andExpect(view().name("profile"))
                .andExpect(forwardedUrl("/WEB-INF/jsp/profile.jsp"))
                .andExpect(model().attribute("tasks", hasSize(1)))
                .andExpect(model().attribute("user", is(executor)));
    }

    @Test
    void testProfilePageForExecutorWithNullRequests() throws Exception {

        Executor executor = new Executor();
        executor.setRole(UserRole.EXECUTOR);

        Mockito.when(securityService.getLoggedInUserName())
                .thenReturn("username");

        Mockito.when(userService.findUserByUserName("username"))
                .thenReturn(executor);

        mockMvc.perform(get("/profile"))
                .andExpect(status().is(200))
                .andExpect(view().name("profile"))
                .andExpect(forwardedUrl("/WEB-INF/jsp/profile.jsp"))
                .andExpect(model().attribute("tasks", hasSize(0)))
                .andExpect(model().attribute("user", is(executor)));
    }

    @Test
    void testProfilePageForMaster() throws Exception {

        Master master = new Master();
        master.setRole(UserRole.MASTER_ASSASSIN);
        master.setRequests(Collections.emptyList());

        Mockito.when(securityService.getLoggedInUserName())
                .thenReturn("username");

        Mockito.when(userService.findUserByUserName("username"))
                .thenReturn(master);

        mockMvc.perform(get("/profile"))
                .andExpect(status().is(200))
                .andExpect(view().name("profile"))
                .andExpect(forwardedUrl("/WEB-INF/jsp/profile.jsp"))
                .andExpect(model().attribute("tasks", hasSize(0)))
                .andExpect(model().attribute("user", is(master)));
    }

    @Test
    void testProfilePageForCabman() throws Exception {

        Cabman cabman = new Cabman();
        cabman.setRole(UserRole.CABMAN);
        cabman.setRequests(Collections.emptyList());

        Mockito.when(securityService.getLoggedInUserName())
                .thenReturn("username");

        Mockito.when(userService.findUserByUserName("username"))
                .thenReturn(cabman);

        mockMvc.perform(get("/profile"))
                .andExpect(status().is(200))
                .andExpect(view().name("profile"))
                .andExpect(forwardedUrl("/WEB-INF/jsp/profile.jsp"))
                .andExpect(model().attribute("tasks", hasSize(0)))
                .andExpect(model().attribute("user", is(cabman)));
    }

    @Test
    void testProfilePageForGunsmith() throws Exception {

        Gunsmith gunsmith = new Gunsmith();
        gunsmith.setRole(UserRole.GUNSMITH);
        gunsmith.setRequests(Collections.emptyList());

        Mockito.when(securityService.getLoggedInUserName())
                .thenReturn("username");

        Mockito.when(userService.findUserByUserName("username"))
                .thenReturn(gunsmith);

        mockMvc.perform(get("/profile"))
                .andExpect(status().is(200))
                .andExpect(view().name("profile"))
                .andExpect(forwardedUrl("/WEB-INF/jsp/profile.jsp"))
                .andExpect(model().attribute("tasks", hasSize(0)))
                .andExpect(model().attribute("user", is(gunsmith)));
    }
}