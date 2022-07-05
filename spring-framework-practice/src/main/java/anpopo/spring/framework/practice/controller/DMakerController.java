package anpopo.spring.framework.practice.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

/**
 * @author 안세형
 */

@Slf4j
@RestController
public class DMakerController {

    // GET /developers HTTP/1.1
    @GetMapping("/developers")
    public void getAllDevelopers() {
        log.info("GET /developers HTTP/1.1");
    }
}
