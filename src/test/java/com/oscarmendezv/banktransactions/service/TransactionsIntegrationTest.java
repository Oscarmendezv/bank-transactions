package com.oscarmendezv.banktransactions.service;


import com.fasterxml.jackson.databind.ObjectMapper;
import com.oscarmendezv.banktransactions.util.TestUtils;
import com.oscarmendezv.banktransactions.web.model.Transaction;
import com.oscarmendezv.banktransactions.web.model.input.StatusRequest;
import com.oscarmendezv.banktransactions.web.model.output.StatusDTO;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.web.context.WebApplicationContext;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;


@SpringBootTest
@AutoConfigureMockMvc
@ActiveProfiles("integration")
@RunWith(SpringRunner.class)
@DirtiesContext(classMode = DirtiesContext.ClassMode.BEFORE_EACH_TEST_METHOD)
public class TransactionsIntegrationTest {

  @Autowired private TransactionsService transactionsService;
  @Autowired private WebApplicationContext webApplicationContext;
  @Autowired private ObjectMapper mapper;
  @Autowired private MockMvc mockMvc;

  @Test
  public void givenNotStoredTransaction_ShouldBeStoredCorrectly() throws Exception {
    Transaction body = TestUtils.loadObject(getClass().getClassLoader()
        .getResource("integration/creation/notStoredTransaction.json"), Transaction.class);

    mockMvc.perform(post("/transactions")
        .content(mapper.writeValueAsBytes(body))
        .contentType(MediaType.APPLICATION_JSON)
        .accept(MediaType.APPLICATION_JSON))
        .andExpect(status().isCreated());
  }

  @Test
  public void givenStoredTransaction_ShouldFailStoring() throws Exception {
    Transaction body = TestUtils.loadObject(getClass().getClassLoader()
        .getResource("integration/creation/storedTransaction.json"), Transaction.class);

    mockMvc.perform(post("/transactions")
        .content(mapper.writeValueAsBytes(body))
        .contentType(MediaType.APPLICATION_JSON)
        .accept(MediaType.APPLICATION_JSON))
        .andExpect(status().isBadRequest());
  }

  @Test
  public void givenNotStoredTransaction_WhenAccountBalanceBecomesNegative_ShouldFail() throws Exception {
    Transaction body = TestUtils.loadObject(getClass().getClassLoader()
        .getResource("integration/creation/notStoredTransactionWithNegativeAmount.json"), Transaction.class);

    mockMvc.perform(post("/transactions")
        .content(mapper.writeValueAsBytes(body))
        .contentType(MediaType.APPLICATION_JSON)
        .accept(MediaType.APPLICATION_JSON))
        .andExpect(status().isBadRequest());
  }

  @Test
  public void givenStoredTransaction_WhenPerformingGet_ShouldReturnTransaction() throws Exception {
    mockMvc.perform(get("/transactions/12345A")
        .contentType(MediaType.APPLICATION_JSON)
        .accept(MediaType.APPLICATION_JSON))
        .andExpect(status().isOk());
  }

  @Test
  public void givenNotStoredTransaction_WhenPerformingGet_ShouldFail() throws Exception {

    mockMvc.perform(get("/transactions/12345D")
        .contentType(MediaType.APPLICATION_JSON)
        .accept(MediaType.APPLICATION_JSON))
        .andExpect(status().isNotFound());
  }

  @Test
  public void findAllTransactions_ShouldReturnTransactions() throws Exception {

    mockMvc.perform(get("/transactions")
        .contentType(MediaType.APPLICATION_JSON)
        .accept(MediaType.APPLICATION_JSON))
        .andExpect(status().isOk())
        .andExpect(jsonPath("$.length()").value(3));
  }

  @Test
  public void findAllTransactions_WhenFilteredByIban_ShouldReturnLessTransactions() throws Exception {

    mockMvc.perform(get("/transactions?account_iban=ES9820385778983000760236")
        .contentType(MediaType.APPLICATION_JSON)
        .accept(MediaType.APPLICATION_JSON))
        .andExpect(status().isOk())
        .andExpect(jsonPath("$.length()").value(2));
  }

  @Test
  public void givenWrongChannel_ShouldFail() throws Exception {
    StatusRequest body = TestUtils.loadObject(getClass().getClassLoader()
        .getResource("integration/status/input/incorrectChannel.json"), StatusRequest.class);

    mockMvc.perform(post("/transactions/status")
        .content(mapper.writeValueAsBytes(body))
        .contentType(MediaType.APPLICATION_JSON)
        .accept(MediaType.APPLICATION_JSON))
        .andExpect(status().isBadRequest());
  }

  @Test
  public void givenNotStoredTransaction_ShouldReturnInvalidStatus() throws Exception {
    StatusRequest body = TestUtils.loadObject(getClass().getClassLoader()
        .getResource("integration/status/input/notStoredTransaction.json"), StatusRequest.class);
    StatusDTO expected = TestUtils.loadObject(getClass().getClassLoader()
        .getResource("integration/status/output/notStoredTransactionOutput.json"), StatusDTO.class);

    mockMvc.perform(post("/transactions/status")
        .content(mapper.writeValueAsBytes(body))
        .contentType(MediaType.APPLICATION_JSON)
        .accept(MediaType.APPLICATION_JSON))
        .andExpect(status().isOk())
        .andExpect(content().json(mapper.writeValueAsString(expected), true));
  }

  @Test
  public void givenStoredTransaction_whenCheckedFromClientOrAtmAndDateIsBeforeToday_ShouldReturnSettledStatus() throws Exception {
    StatusRequest body = TestUtils.loadObject(getClass().getClassLoader()
        .getResource("integration/status/input/clientWithPreviousDate.json"), StatusRequest.class);
    StatusDTO expected = TestUtils.loadObject(getClass().getClassLoader()
        .getResource("integration/status/output/clientWithPreviousDateOutput.json"), StatusDTO.class);

    mockMvc.perform(post("/transactions/status")
        .content(mapper.writeValueAsBytes(body))
        .contentType(MediaType.APPLICATION_JSON)
        .accept(MediaType.APPLICATION_JSON))
        .andExpect(status().isOk())
        .andExpect(content().json(mapper.writeValueAsString(expected), true));
  }

  @Test
  public void givenStoredTransaction_whenCheckedFromInternalAndDateIsBeforeToday_ShouldReturnSettledStatus() throws Exception {
    StatusRequest body = TestUtils.loadObject(getClass().getClassLoader()
        .getResource("integration/status/input/internalWithPreviousDate.json"), StatusRequest.class);
    StatusDTO expected = TestUtils.loadObject(getClass().getClassLoader()
        .getResource("integration/status/output/internalWithPreviousDateOutput.json"), StatusDTO.class);

    mockMvc.perform(post("/transactions/status")
        .content(mapper.writeValueAsBytes(body))
        .contentType(MediaType.APPLICATION_JSON)
        .accept(MediaType.APPLICATION_JSON))
        .andExpect(status().isOk())
        .andExpect(content().json(mapper.writeValueAsString(expected), true));
  }

  @Test
  public void givenStoredTransaction_whenCheckedFromClientOrAtmAndDateIsToday_ShouldReturnPendingStatus() throws Exception {
    StatusRequest body = TestUtils.loadObject(getClass().getClassLoader()
        .getResource("integration/status/input/clientWithTodaysDate.json"), StatusRequest.class);
    StatusDTO expected = TestUtils.loadObject(getClass().getClassLoader()
        .getResource("integration/status/output/clientWithTodaysDateOutput.json"), StatusDTO.class);

    mockMvc.perform(post("/transactions/status")
        .content(mapper.writeValueAsBytes(body))
        .contentType(MediaType.APPLICATION_JSON)
        .accept(MediaType.APPLICATION_JSON))
        .andExpect(status().isOk())
        .andExpect(content().json(mapper.writeValueAsString(expected), true));
  }

  @Test
  public void givenStoredTransaction_whenCheckedFromInternalAndDateIsToday_ShouldReturnPendingStatus() throws Exception {
    StatusRequest body = TestUtils.loadObject(getClass().getClassLoader()
        .getResource("integration/status/input/internalWithTodaysDate.json"), StatusRequest.class);
    StatusDTO expected = TestUtils.loadObject(getClass().getClassLoader()
        .getResource("integration/status/output/internalWithTodaysDateOutput.json"), StatusDTO.class);

    mockMvc.perform(post("/transactions/status")
        .content(mapper.writeValueAsBytes(body))
        .contentType(MediaType.APPLICATION_JSON)
        .accept(MediaType.APPLICATION_JSON))
        .andExpect(status().isOk())
        .andExpect(content().json(mapper.writeValueAsString(expected), true));
  }

  @Test
  public void givenStoredTransaction_whenCheckedFromClientAndDateIsAfterToday_ShouldReturnFutureStatus() throws Exception {
    StatusRequest body = TestUtils.loadObject(getClass().getClassLoader()
        .getResource("integration/status/input/clientWithFutureDate.json"), StatusRequest.class);
    StatusDTO expected = TestUtils.loadObject(getClass().getClassLoader()
        .getResource("integration/status/output/clientWithFutureDateOutput.json"), StatusDTO.class);

    mockMvc.perform(post("/transactions/status")
        .content(mapper.writeValueAsBytes(body))
        .contentType(MediaType.APPLICATION_JSON)
        .accept(MediaType.APPLICATION_JSON))
        .andExpect(status().isOk())
        .andExpect(content().json(mapper.writeValueAsString(expected), true));
  }

  @Test
  public void givenStoredTransaction_whenCheckedFromAtmAndDateIsAfterToday_ShouldReturnPendingStatus() throws Exception {
    StatusRequest body = TestUtils.loadObject(getClass().getClassLoader()
        .getResource("integration/status/input/atmWithFutureDate.json"), StatusRequest.class);
    StatusDTO expected = TestUtils.loadObject(getClass().getClassLoader()
        .getResource("integration/status/output/atmWithFutureDateOutput.json"), StatusDTO.class);

    mockMvc.perform(post("/transactions/status")
        .content(mapper.writeValueAsBytes(body))
        .contentType(MediaType.APPLICATION_JSON)
        .accept(MediaType.APPLICATION_JSON))
        .andExpect(status().isOk())
        .andExpect(content().json(mapper.writeValueAsString(expected), true));
  }

  @Test
  public void givenStoredTransaction_whenCheckedFromInternalAndDateIsAfterToday_ShouldReturnFutureStatus() throws Exception {
    StatusRequest body = TestUtils.loadObject(getClass().getClassLoader()
        .getResource("integration/status/input/internalWithFutureDate.json"), StatusRequest.class);
    StatusDTO expected = TestUtils.loadObject(getClass().getClassLoader()
        .getResource("integration/status/output/internalWithFutureDateOutput.json"), StatusDTO.class);

    mockMvc.perform(post("/transactions/status")
        .content(mapper.writeValueAsBytes(body))
        .contentType(MediaType.APPLICATION_JSON)
        .accept(MediaType.APPLICATION_JSON))
        .andExpect(status().isOk())
        .andExpect(content().json(mapper.writeValueAsString(expected), true));
  }
}
