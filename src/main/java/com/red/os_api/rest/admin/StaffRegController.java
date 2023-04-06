package com.red.os_api.rest.admin;

import com.red.os_api.entity.req_resp.AuthResponse;
import com.red.os_api.entity.req_resp.CustomerDetailsResponse;
import com.red.os_api.entity.req_resp.MasterResponse;
import com.red.os_api.entity.req_resp.RegisterRequest;
import com.red.os_api.service.AuthService;
import io.swagger.models.auth.In;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/store/api/master")
@RequiredArgsConstructor
public class StaffRegController {

    private final AuthService authService;


    @PostMapping("/register")
    public ResponseEntity<MasterResponse> register(@RequestBody RegisterRequest registerRequest) {
        return authService.insert(registerRequest);
    }

    @PostMapping("/deleteById")
    public ResponseEntity<String> register(@RequestParam Integer id) {
        return authService.deleteById(id);
    }

    @GetMapping("getAll")
    public ResponseEntity<List<MasterResponse>> getAll(){
        return authService.getAll();
    }

    @PutMapping("/update")
    public ResponseEntity<MasterResponse> update(@RequestBody RegisterRequest registerRequest) {
        return authService.insert(registerRequest);
    }



}
