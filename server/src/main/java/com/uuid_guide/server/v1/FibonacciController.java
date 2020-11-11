package com.uuid_guide.server.v1;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController()
@RequestMapping("/fibonacci/v1")
public class FibonacciController {
    
    @GetMapping("/{nthNumber}")
    int getById(@PathVariable int nthNumber) {
        return Fibonacci.fibonacciLoop(nthNumber);
    }
}
