package anpopo.spring.framework.practice.controller;

import anpopo.spring.framework.practice.dto.DeveloperDto;
import anpopo.spring.framework.practice.service.DMakerService;
import anpopo.spring.framework.practice.type.DeveloperLevel;
import anpopo.spring.framework.practice.type.DeveloperSkillType;
import org.hamcrest.CoreMatchers;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import java.nio.charset.StandardCharsets;
import java.util.Arrays;

import static org.mockito.BDDMockito.given;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;

@WebMvcTest(DMakerController.class)
class DMakerControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private DMakerService dMakerService;

    protected MediaType mediaType = new MediaType(MediaType.APPLICATION_JSON.getType(), MediaType.APPLICATION_JSON.getSubtype(), StandardCharsets.UTF_8);

    @Test
    void getAllDevelopers() throws Exception {
        DeveloperDto juniorDeveloper = DeveloperDto.builder()
                .developerLevel(DeveloperLevel.JUNIOR)
                .developerSkillType(DeveloperSkillType.BACK_END)
                .memberId("memberId1")
                .build();

        DeveloperDto seniorDeveloper = DeveloperDto.builder()
                .developerLevel(DeveloperLevel.SENIOR)
                .developerSkillType(DeveloperSkillType.BACK_END)
                .memberId("memberId2")
                .build();


        given(dMakerService.getAllDevelopers())
                .willReturn(Arrays.asList(juniorDeveloper, seniorDeveloper));


        mockMvc.perform(MockMvcRequestBuilders.get("/developers")
                        .contentType(mediaType))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andDo(print())
                .andExpect(
                        MockMvcResultMatchers.jsonPath(
                                "$.[0].developerSkillType",
                                CoreMatchers.is(DeveloperSkillType.BACK_END.name())
                        )
                )
                .andExpect(
                        MockMvcResultMatchers.jsonPath(
                                "$.[0].developerLevel",
                                CoreMatchers.is(DeveloperLevel.JUNIOR.name())
                        )
                )
                .andExpect(
                        MockMvcResultMatchers.jsonPath(
                                "$.[1].developerSkillType",
                                CoreMatchers.is(DeveloperSkillType.BACK_END.name())
                        )
                )
                .andExpect(
                        MockMvcResultMatchers.jsonPath(
                                "$.[1].developerLevel",
                                CoreMatchers.is(DeveloperLevel.SENIOR.name())
                        )
                );


    }
}