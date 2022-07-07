package anpopo.spring.framework.practice.controller;

import anpopo.spring.framework.practice.dto.CreateDeveloper;
import anpopo.spring.framework.practice.service.DMakerService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;
import java.util.Collections;
import java.util.List;

/**
 * @author anpopo
 */
@RequiredArgsConstructor
@Slf4j
@RestController
public class DMakerController {

    private final DMakerService dMakerService;

    // GET /developers HTTP/1.1
    @GetMapping("/developers")
    public void getAllDevelopers() {
        log.info("GET /developers HTTP/1.1");
    }

    @PostMapping("/create-developer")
    public List<String> createDevelopers(@RequestBody CreateDeveloper.@Valid Request request) {
//        log.info("GET /create-developers HTTP/1.1");

        dMakerService.createDeveloper(request);
        return Collections.singletonList("Olaf");
    }
}
