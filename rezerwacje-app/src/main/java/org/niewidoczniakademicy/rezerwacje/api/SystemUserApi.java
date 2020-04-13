package org.niewidoczniakademicy.rezerwacje.api;

import lombok.AllArgsConstructor;
import org.niewidoczniakademicy.rezerwacje.core.model.rest.user.AddSystemUserRequest;
import org.niewidoczniakademicy.rezerwacje.core.model.rest.user.AddSystemUserResponse;
import org.niewidoczniakademicy.rezerwacje.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("system-user")
@AllArgsConstructor(onConstructor = @__(@Autowired))
public class SystemUserApi {

    private final UserService userService;

    @PostMapping
    public ResponseEntity<AddSystemUserResponse> addRecruitmentPeriod(@RequestBody AddSystemUserRequest request) {
        AddSystemUserResponse response = userService.saveSystemUser(request);
        return ResponseEntity.ok().body(response);
    }
}
