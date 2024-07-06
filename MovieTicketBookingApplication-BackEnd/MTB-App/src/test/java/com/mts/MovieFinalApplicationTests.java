package com.mts;

import static org.junit.jupiter.api.Assertions.assertEquals;

import com.mts.controller.LoginController;
import com.mts.model.Customer;
import com.mts.model.Theatre;
import com.mts.model.Ticket;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

@SpringBootTest
class MovieFinalApplicationTests extends AbstractTest {

    @Autowired
    private LoginController loginController;

    @Override
    @BeforeEach
    public void setUp() {
        super.setUp();
    }

    private void loginUser() {
        loginController.loginUser("sunayana", "sunayana123");
    }

    @Test
    public void testAddCustomer() throws Exception {
        String uri = "/customer/add";
        Customer customer = new Customer("Surya", "Anantapur", "7569369939", "surya@gmail.com", "bunny");
        String inputJson = super.mapToJson(customer);
        System.out.println("=======================" + inputJson + "======================");
        MvcResult mvcResult = mvc.perform(MockMvcRequestBuilders.post(uri)
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .content(inputJson))
                .andReturn();
        int status = mvcResult.getResponse().getStatus();
        assertEquals(404, status);
    }

    @Test
    public void testAddTheatre() throws Exception {
        loginUser();
        String uri = "/theatre/insert";
        Theatre theatre = new Theatre("Kabuki", "Tokyo", "Vidhya", "998990181", null, null);
        String inputJson = super.mapToJson(theatre);
        System.out.println("=======================" + inputJson + "======================");
        MvcResult mvcResult = mvc.perform(MockMvcRequestBuilders.post(uri)
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .content(inputJson))
                .andReturn();
        int status = mvcResult.getResponse().getStatus();
        assertEquals(201, status);
        String content = mvcResult.getResponse().getContentAsString();
        Theatre createdTheatre = super.mapFromJson(content, Theatre.class);
        assertEquals("Kabuki", createdTheatre.getTheatreName());
    }

    @Test
    public void testGetAllTheatres() throws Exception {
        String uri = "/theatre/all";
        MvcResult mvcResult = mvc.perform(MockMvcRequestBuilders.get(uri)).andReturn();
        int status = mvcResult.getResponse().getStatus();
        assertEquals(200, status);
        String content = mvcResult.getResponse().getContentAsString();
        Theatre[] theatres = super.mapFromJson(content, Theatre[].class);
        assertEquals("Kabuki", theatres[theatres.length - 1].getTheatreName());
    }

    @Test
    public void testViewTicketList() throws Exception {
        String uri = "/tickets/findall";
        MvcResult mvcResult = mvc.perform(MockMvcRequestBuilders.get(uri)).andReturn();
        int status = mvcResult.getResponse().getStatus();
        assertEquals(200, status);
        String content = mvcResult.getResponse().getContentAsString();
        Ticket[] tickets = super.mapFromJson(content, Ticket[].class);
        assertEquals(2, tickets[tickets.length - 1].getNoOfSeats());
    }
}
