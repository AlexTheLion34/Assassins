package com.itmo.assassins.controller.request;

import com.itmo.assassins.model.request.Request;
import com.itmo.assassins.model.request.RequestDifficulty;
import com.itmo.assassins.model.request.RequestInfo;
import com.itmo.assassins.model.user.*;
import com.itmo.assassins.service.request.RequestDifficultyComputationService;
import com.itmo.assassins.service.request.RequestService;
import com.itmo.assassins.service.user.UserSecurityService;
import com.itmo.assassins.service.user.UserService;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentMatchers;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.test.web.servlet.MockMvc;

import java.util.Optional;

import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.any;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.model;

@WebMvcTest(RequestController.class)
@AutoConfigureMockMvc(addFilters = false)
class RequestControllerTest {

    @MockBean
    private RequestService requestService;

    @MockBean
    private UserService userService;

    @MockBean
    private UserSecurityService securityService;

    @MockBean
    private UserDetailsService userDetailsService;

    @MockBean
    private RequestDifficultyComputationService difficultyComputationService;

    @Autowired
    private MockMvc mockMvc;

    @Test
    void testGetAddRequest() throws Exception {

        Mockito.when(securityService.getLoggedInUserName()).thenReturn("username");

        Customer customer = new Customer();

        Mockito.when(userService.findUserByUserName("username")).thenReturn(customer);
        Mockito.when(userService.countMaxAffordablePrice(customer)).thenReturn(300L);

        mockMvc.perform(get("/add-request"))
                .andExpect(status().isOk())
                .andExpect(view().name("request"))
                .andExpect(forwardedUrl("/WEB-INF/jsp/request.jsp"))
                .andExpect(model().attribute("requestInfo", is(any(RequestInfo.class))))
                .andExpect(model().attribute("maxPrice", 300L));
    }

    @Test
    void testGetAddRequestWithMaxPriceZero() throws Exception {

        Mockito.when(securityService.getLoggedInUserName()).thenReturn("username");

        Customer customer = new Customer();

        Mockito.when(userService.findUserByUserName("username")).thenReturn(customer);
        Mockito.when(userService.countMaxAffordablePrice(customer)).thenReturn(0L);

        mockMvc.perform(get("/add-request"))
                .andExpect(status().isOk())
                .andExpect(view().name("balance-error"))
                .andExpect(forwardedUrl("/WEB-INF/jsp/balance-error.jsp"));
    }

    @Test
    void testPostAddRequest() throws Exception {

        RequestInfo requestInfo = new RequestInfo();

        Mockito.when(securityService.getLoggedInUserName()).thenReturn("username");

        Customer customer = new Customer();

        Mockito.when(userService.findUserByUserName("username")).thenReturn(customer);

        Mockito.when(difficultyComputationService.computeDifficulty(requestInfo))
                .thenReturn(RequestDifficulty.HARD);

        mockMvc.perform(post("/add-request").sessionAttr("requestInfo", requestInfo))
                .andExpect(status().is(302))
                .andExpect(view().name("redirect:/profile"))
                .andExpect(redirectedUrl("/profile"));

        Mockito.verify(requestService, Mockito.times(1))
                .createRequest(ArgumentMatchers.any(), ArgumentMatchers.any());
    }

    @Test
    void testPostAddRequestWithException() throws Exception {

        RequestInfo requestInfo = new RequestInfo();

        Mockito.when(securityService.getLoggedInUserName()).thenReturn("username");

        Customer customer = new Customer();

        Mockito.when(userService.findUserByUserName("username")).thenReturn(customer);

        Mockito.when(difficultyComputationService.computeDifficulty(requestInfo))
                .thenReturn(RequestDifficulty.HARD);

        Mockito.doThrow(RuntimeException.class)
                .when(requestService)
                .createRequest(ArgumentMatchers.any(), ArgumentMatchers.any());

        mockMvc.perform(post("/add-request").sessionAttr("requestInfo", requestInfo))
                .andExpect(status().is(200))
                .andExpect(view().name("executor-error"))
                .andExpect(forwardedUrl("/WEB-INF/jsp/executor-error.jsp"));

    }

    @Test
    void testViewRequest() throws Exception {

        Request request = new Request();

        Mockito.when(requestService.getRequestById(1))
                .thenReturn(Optional.of(request));

        Mockito.when(securityService.getLoggedInUserName()).thenReturn("username");

        Customer customer = new Customer();

        Mockito.when(userService.findUserByUserName("username")).thenReturn(customer);

        mockMvc.perform(get("/view-request?id=1"))
                .andExpect(status().isOk())
                .andExpect(view().name("view-request"))
                .andExpect(forwardedUrl("/WEB-INF/jsp/view-request.jsp"))
                .andExpect(model().attribute("user", customer))
                .andExpect(model().attribute("request", request));
    }

    @Test
    void testChangeRequest() throws Exception {

        Request request = new Request();
        Executor executor = new Executor();
        Cabman cabman = new Cabman();
        Gunsmith gunsmith = new Gunsmith();
        request.setExecutor(executor);
        request.setGunsmith(gunsmith);
        request.setCabman(cabman);

        Mockito.when(requestService.getRequestById(1))
                .thenReturn(Optional.of(request));

        Mockito.when(securityService.getLoggedInUserName()).thenReturn("username");

        Customer customer = new Customer();

        Mockito.when(userService.findUserByUserName("username")).thenReturn(customer);

        mockMvc.perform(get("/change-request?id=1"))
                .andExpect(status().isOk())
                .andExpect(view().name("change-request"))
                .andExpect(forwardedUrl("/WEB-INF/jsp/change-request.jsp"))
                .andExpect(model().attribute("user", customer))
                .andExpect(model().attribute("id", "1"))
                .andExpect(model().attribute("executor", executor))
                .andExpect(model().attribute("cabman", cabman))
                .andExpect(model().attribute("gunsmith", gunsmith));
    }

    @Test
    void testConfirmRequest() throws Exception {

        Request request = new Request();
        Executor executor = new Executor();
        Cabman cabman = new Cabman();
        Gunsmith gunsmith = new Gunsmith();
        request.setExecutor(executor);
        request.setGunsmith(gunsmith);
        request.setCabman(cabman);

        Mockito.when(requestService.getRequestById(1))
                .thenReturn(Optional.of(request));

        Mockito.when(securityService.getLoggedInUserName()).thenReturn("username");

        Master master = new Master();

        Mockito.when(userService.findUserByUserName("username")).thenReturn(master);

        mockMvc.perform(get("/confirm-request?requestId=1"))
                .andExpect(status().is(302))
                .andExpect(view().name("redirect:/profile"))
                .andExpect(redirectedUrl("/profile"));

        Mockito.verify(requestService, Mockito.times(1))
                .confirmRequest(request, master);
    }

    @Test
    void testGetPutRatingForRequest() throws Exception {

        Request request = new Request();

        Mockito.when(requestService.getRequestById(1))
                .thenReturn(Optional.of(request));

        Mockito.when(securityService.getLoggedInUserName()).thenReturn("username");

        Customer customer = new Customer();

        Mockito.when(userService.findUserByUserName("username")).thenReturn(customer);

        mockMvc.perform(get("/evaluate?id=1"))
                .andExpect(status().isOk())
                .andExpect(view().name("evaluate-request"))
                .andExpect(forwardedUrl("/WEB-INF/jsp/evaluate-request.jsp"))
                .andExpect(model().attribute("request", request));
    }
}