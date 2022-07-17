package anpopo.spring.framework.practice.controller;

import anpopo.spring.framework.practice.dto.CreateDeveloper;
import anpopo.spring.framework.practice.dto.DeveloperDetailDto;
import anpopo.spring.framework.practice.dto.DeveloperDto;
import anpopo.spring.framework.practice.dto.EditDeveloper;
import anpopo.spring.framework.practice.service.DMakerService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
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
    public List<DeveloperDto> getAllDevelopers() {
        log.info("GET /developers HTTP/1.1");
        return dMakerService.getAllDevelopers();
    }

    @GetMapping("/developer/{memberId}")
    public DeveloperDetailDto getDeveloperDetail(@PathVariable("memberId") final String memberId) {
        log.info("GET /developer HTTP/1.1");
        return dMakerService.getDeveloperDetail(memberId);
    }

    @PostMapping("/create-developer")
    public CreateDeveloper.Response createDevelopers(@RequestBody final CreateDeveloper.@Valid Request request) {
//        log.info("GET /create-developers HTTP/1.1");
        log.info("create request : {}", request);
        return dMakerService.createDeveloper(request);
    }

    @PutMapping("/developer/{memberId}")
    public DeveloperDetailDto updateDeveloper(
            @PathVariable("memberId") final String memberId,
            @RequestBody EditDeveloper.@Valid Request request
    ) {
//        log.info("GET /create-developers HTTP/1.1");
        log.info("create request : {}", request);
        return dMakerService.updateDeveloper(memberId, request);
    }

    @DeleteMapping("/developer/{memberId}")
    public DeveloperDetailDto deleteDeveloper(
            @PathVariable("memberId") final String memberId
    ) {
        return dMakerService.deleteDeveloper(memberId);
    }


}
