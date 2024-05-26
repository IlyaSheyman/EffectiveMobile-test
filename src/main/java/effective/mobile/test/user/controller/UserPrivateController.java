package effective.mobile.test.user.controller;

import effective.mobile.test.constants.Constants;
import effective.mobile.test.user.dto.data.EmailDto;
import effective.mobile.test.user.dto.data.PhoneDto;
import effective.mobile.test.user.dto.user.UserSearchDto;
import effective.mobile.test.user.dto.user.UserUpdateDto;
import effective.mobile.test.user.service.UserService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/user")
@RequiredArgsConstructor
@Slf4j
@Tag(name = "Private controller", description = "For operations allowed only for authorized users")
public class UserPrivateController {

    private final UserService service;

    @Operation(summary = "Add new phone number")
    @PutMapping("/phone")
    @Transactional
    public UserUpdateDto addPhoneNumber(@RequestHeader(name = "Authorization") String token,
                                        @RequestBody PhoneDto dto) {
        log.info("request to add new phone number");
        return service.addPhoneNumber(token, dto);
    }

    @Operation(summary = "Delete phone number")
    @DeleteMapping("/phone")
    @Transactional
    @ResponseStatus(HttpStatus.ACCEPTED)
    public void deletePhoneNumber(@RequestHeader(name = "Authorization") String token,
                                  @RequestBody PhoneDto dto) {
        log.info("request to delete phone number");
        service.deletePhoneNumber(token, dto);
    }

    @Operation(summary = "Add new email")
    @PutMapping("/email")
    @Transactional
    public UserUpdateDto addEmail(@RequestHeader(name = "Authorization") String token,
                                  @RequestBody EmailDto dto) {
        log.info("request to add new new email");
        return service.addEmail(token, dto);
    }

    @Operation(summary = "Delete email")
    @DeleteMapping("/email")
    @Transactional
    @ResponseStatus(HttpStatus.ACCEPTED)
    public void deleteEmail(@RequestHeader(name = "Authorization") String token,
                            @RequestBody EmailDto dto) {
        log.info("request to delete email");
        service.deleteEmail(token, dto);
    }

    @Operation(summary = "Find users")
    @GetMapping
    public List<UserSearchDto> findUsers(@RequestBody String filterValue,
                                         @RequestParam(name = "filter") Constants.Filter filter,
                                         @RequestParam(name = "page", defaultValue = "0") int page,
                                         @RequestParam(name = "size", defaultValue = "10") int size) {
        log.info("Request to find users with filter: " + filter + " and value: " + filterValue);
        return service.findUsers(filter, filterValue, page, size);
    }
}