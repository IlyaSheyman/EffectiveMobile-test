package effective.mobile.test.account.controller;

import effective.mobile.test.account.dto.transfer.TransferRequestDto;
import effective.mobile.test.account.service.AccountService;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/account")
@RequiredArgsConstructor
@Slf4j
@Tag(name = "Private controller", description = "For banking operations allowed only for authorized users")
public class AccountController {

    private final AccountService service;
    @PostMapping("/transfer")
    @Transactional
    public void transferMoney(@RequestHeader(name = "Authorization") String token,
                              @RequestBody @Valid TransferRequestDto transferRequest) {
        log.info("request to transfer money");
        service.transferMoney(token, transferRequest);
    }
}