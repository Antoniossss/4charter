package charter.customer;

import charter.WsTestBase;
import org.jeasy.random.EasyRandom;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;

import java.util.List;
import java.util.stream.Collectors;

import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.verifyNoInteractions;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(CustomerWs.class)
class CustomerWsTest extends WsTestBase {

    @MockBean
    private CustomerService customerService;
    protected EasyRandom er = new EasyRandom();

    @Test
    public void canListCustomers() throws Exception {
        List<Customer> data = er.objects(Customer.class, 2).collect(Collectors.toList());
        when(customerService.listCustomers()).thenReturn(data);

        mockMvc.perform(get("/customer/list"))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].name").value(data.get(0).getName()));
    }

    @Test
    public void canCreateCustomer() throws Exception {
        Customer customerDto = Customer.builder().name("Sebastian").build();
        Customer persisted = new Customer("Sebastian");
        persisted.setId(1234L);
        when(customerService.saveOrUpdate(eq(customerDto))).thenReturn(persisted);
        mockMvc.perform(post("/customer")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsBytes(customerDto)))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.name").value("Sebastian"))
                .andExpect(jsonPath("$.id").value("1234"));
    }

    @Test
    public void failsEarlyForMissingBody() throws Exception {
        mockMvc.perform(post("/customer").contentType(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isBadRequest());
        verifyNoInteractions(customerService);
    }

    @Test
    public void overridesIdInDto() throws Exception {
        Customer customerDto = Customer.builder().name("Sebastian").build();
        customerDto.setId(111111L);

        Customer expected = new Customer("Sebastian");
        expected.setId(34567L);
        when(customerService.saveOrUpdate(eq(expected))).thenReturn(expected);
        //path id has a priority here
        mockMvc.perform(put("/customer/34567")
                        .content(objectMapper.writeValueAsBytes(customerDto))
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value("34567"));


    }

}
