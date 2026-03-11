package ipss.web2.examen.controllers.api;

import org.springframework.test.web.servlet.ResultActions;

import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;

/**
 * Shared assertions for top-level API response envelope fields.
 */
class ApiResponseEnvelopeTestSupport {

    protected ResultActions assertSuccessEnvelope(ResultActions result, String expectedMessage) throws Exception {
        return result
                .andExpect(jsonPath("$.success").value(true))
                .andExpect(jsonPath("$.message").value(expectedMessage))
                .andExpect(jsonPath("$.timestamp").exists())
                .andExpect(jsonPath("$.data").exists());
    }

    protected ResultActions assertErrorEnvelope(ResultActions result, String expectedErrorCode) throws Exception {
        return result
                .andExpect(jsonPath("$.success").value(false))
                .andExpect(jsonPath("$.message").exists())
                .andExpect(jsonPath("$.errorCode").value(expectedErrorCode))
                .andExpect(jsonPath("$.timestamp").exists());
    }
}
