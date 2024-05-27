import effective.mobile.test.account.dto.transfer.TransferRequestDto;
import effective.mobile.test.account.entity.Account;
import effective.mobile.test.account.service.AccountServiceImpl;
import effective.mobile.test.account.storage.AccountRepository;
import effective.mobile.test.config.security.JwtService;
import effective.mobile.test.exception.model.BadRequestException;
import effective.mobile.test.user.entity.User;
import effective.mobile.test.user.storage.UserRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;
import static java.util.List.of;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class AccountServiceTest {

    @Mock
    private UserRepository userRepository;

    @Mock
    private AccountRepository accountRepository;

    @Mock
    private JwtService jwtService;

    @InjectMocks
    private AccountServiceImpl accountService;

    private User sender;
    private User recipient;
    private Account senderAccount;
    private Account recipientAccount;
    private TransferRequestDto transferRequestDto;

    @BeforeEach
    void setUp() {
        senderAccount = Account.builder().balance(1000.0).build();
        recipientAccount = Account.builder().balance(500.0).build();

        List<String> senderEmails = of("sender.email1@yandex.ru", "sender.email2@yandex.ru");
        List<String> recipientEmails = of("recipient.email1@yandex.ru", "recipient.email2@yandex.ru");
        List<String> senderPhones = of("+7(910)614-24-30", "+7(901)144-11-23");
        List<String> recipientPhones = of("8(915)144-24-20", "+7(967)101-11-11");

        sender = User.builder()
                .id(1)
                .emails(senderEmails)
                .phones(senderPhones)
                .login("sender")
                .account(senderAccount)
                .build();

        recipient = User.builder()
                .id(2)
                .phones(recipientPhones)
                .emails(recipientEmails)
                .login("recipient")
                .account(recipientAccount)
                .build();

        transferRequestDto = TransferRequestDto.builder()
                .amount(200.0)
                .email("recipient@example.com")
                .build();
    }

    @Test
    void transferMoney_successfulTransfer() {
        when(jwtService.extractUserId(anyString())).thenReturn(sender.getId());
        when(userRepository.findById(sender.getId())).thenReturn(Optional.of(sender));
        when(userRepository.findUserByEmailsContains(transferRequestDto.getEmail())).thenReturn(Optional.of(recipient));

        accountService.transferMoney("Bearer token", transferRequestDto);

        verify(accountRepository, times(1)).save(senderAccount);
        verify(accountRepository, times(1)).save(recipientAccount);
    }

    @Test
    void transferMoney_recipientNotFound() {
        when(jwtService.extractUserId(anyString())).thenReturn(sender.getId());
        when(userRepository.findById(sender.getId())).thenReturn(Optional.of(sender));
        when(userRepository.findUserByEmailsContains(transferRequestDto.getEmail())).thenReturn(Optional.empty());

        assertThrows(BadRequestException.class, () -> accountService.transferMoney("Bearer token", transferRequestDto));

        verify(accountRepository, never()).save(any(Account.class));
    }

    @Test
    void transferMoney_insufficientFunds() {
        transferRequestDto.setAmount(2000.0);
        when(jwtService.extractUserId(anyString())).thenReturn(sender.getId());
        when(userRepository.findById(sender.getId())).thenReturn(Optional.of(sender));
        when(userRepository.findUserByEmailsContains(transferRequestDto.getEmail())).thenReturn(Optional.of(recipient));

        assertThrows(BadRequestException.class, () -> accountService.transferMoney("Bearer token", transferRequestDto));

        verify(accountRepository, never()).save(any(Account.class));
    }

    @Test
    void transferMoney_transferToSelf() {
        transferRequestDto.setEmail(sender.getEmails().get(0));
        when(jwtService.extractUserId(anyString())).thenReturn(sender.getId());
        when(userRepository.findById(sender.getId())).thenReturn(Optional.of(sender));
        when(userRepository.findUserByEmailsContains(transferRequestDto.getEmail())).thenReturn(Optional.of(sender));

        assertThrows(BadRequestException.class, () -> accountService.transferMoney("Bearer token", transferRequestDto));

        verify(accountRepository, never()).save(any(Account.class));
    }
}