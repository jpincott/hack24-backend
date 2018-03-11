package com.experian.hack.backend.web

import com.experian.hack.backend.node.Customer
import com.experian.hack.backend.repository.CustomerRepository
import com.experian.hack.backend.repository.OpportunityRepository
import com.experian.hack.backend.repository.WorkerRepository
import org.springframework.http.MediaType
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders
import org.springframework.test.web.servlet.result.MockMvcResultHandlers
import org.springframework.test.web.servlet.result.MockMvcResultMatchers
import org.springframework.test.web.servlet.setup.MockMvcBuilders
import spock.lang.Specification

class CustomerControllerSpec extends Specification {

    def mockCustomers = Mock(CustomerRepository)
    def mockOpportunities = Mock(OpportunityRepository)
    def mockWorkers = Mock(WorkerRepository)

    def mvc = MockMvcBuilders.standaloneSetup(new CustomerController(mockWorkers, mockOpportunities, mockCustomers)).build()

    def "shouldCreateOpportunity"() throws Exception {

        given:
        def customer = new Customer().setEmail('email@example.com')
        1 * mockOpportunities.save(_) >> { it[0] }
        1 * mockCustomers.findByEmail(_) >> { Optional.of(customer) }
        1 * mockCustomers.save(customer)
        0 * _

        mvc.perform(MockMvcRequestBuilders.post("/customers/me/opportunities")
                .header("email", "email@example.com")
                .contentType(MediaType.APPLICATION_JSON)
                .content('{"description":"A cool job"}')
        ).andDo(MockMvcResultHandlers.print())
                .andExpect(MockMvcResultMatchers.status().isOk());

    }
}