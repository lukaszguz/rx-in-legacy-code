package pl.upaid.domain.model.web;

import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.context.request.async.DeferredResult;

@RestController
@AllArgsConstructor
@RequestMapping("/processors")
class ProcessorController {

    private final Processor processor;

    @GetMapping("/raw")
    String something() {
        return "";
    }
    
}
