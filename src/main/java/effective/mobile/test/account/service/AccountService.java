package effective.mobile.test.account.service;

import effective.mobile.test.account.dto.transfer.TransferRequestDto;

public interface AccountService {
    void transferMoney(String token, TransferRequestDto transferRequest);
}
