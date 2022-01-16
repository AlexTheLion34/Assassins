package com.itmo.assassins.controller.request;

import com.itmo.assassins.model.request.Request;
import com.itmo.assassins.model.request.RequestArsenal;
import com.itmo.assassins.model.request.RequestRoadEquipment;
import com.itmo.assassins.service.request.RequestService;
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

import static org.hamcrest.Matchers.any;
import static org.hamcrest.Matchers.is;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.model;

@WebMvcTest(EquipRequestController.class)
@AutoConfigureMockMvc(addFilters = false)
class EquipRequestControllerTest {

    @MockBean
    private RequestService requestService;

    @MockBean
    private UserDetailsService userDetailsService;

    @Autowired
    private MockMvc mockMvc;

    @Test
    void testGetEquipArsenal() throws Exception {

        mockMvc.perform(get("/add-arsenal?id=1"))
                .andExpect(status().is(200))
                .andExpect(view().name("add-arsenal"))
                .andExpect(forwardedUrl("/WEB-INF/jsp/add-arsenal.jsp"))
                .andExpect(model().attribute("requestId", is("1")))
                .andExpect(model().attribute("requestArsenal", is(any(RequestArsenal.class))));
    }

    @Test
    void testPostEquipArsenal() throws Exception {

        RequestArsenal arsenal = new RequestArsenal();

        Request request = new Request();

        Mockito.when(requestService.getRequestById(1))
                        .thenReturn(Optional.of(request));

        mockMvc.perform(post("/add-arsenal?requestId=1")
                        .flashAttr("arsenal", arsenal))
                .andExpect(status().is(302))
                .andExpect(view().name("redirect:/profile"))
                .andExpect(redirectedUrl("/profile"));

        Mockito.verify(requestService, Mockito.times(1))
                .addArsenal(ArgumentMatchers.any(), ArgumentMatchers.any());
    }

    @Test
    void testGetEquipRoadEquipment() throws Exception {

        mockMvc.perform(get("/add-road-eq?id=1"))
                .andExpect(status().is(200))
                .andExpect(view().name("add-equipment"))
                .andExpect(forwardedUrl("/WEB-INF/jsp/add-equipment.jsp"))
                .andExpect(model().attribute("requestId", is("1")))
                .andExpect(model().attribute("requestRoadEq", is(any(RequestRoadEquipment.class))));
    }

    @Test
    void testPostEquipRoadEquipment() throws Exception {

        RequestRoadEquipment equipment = new RequestRoadEquipment();

        Request request = new Request();

        Mockito.when(requestService.getRequestById(1))
                .thenReturn(Optional.of(request));

        mockMvc.perform(post("/add-road-eq?requestId=1")
                        .flashAttr("equipment", equipment))
                .andExpect(status().is(302))
                .andExpect(view().name("redirect:/profile"))
                .andExpect(redirectedUrl("/profile"));

        Mockito.verify(requestService, Mockito.times(1))
                .addRoadEquipment(ArgumentMatchers.any(), ArgumentMatchers.any());
    }
}