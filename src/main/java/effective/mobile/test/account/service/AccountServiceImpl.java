package effective.mobile.test.account.service;

import effective.mobile.test.account.dto.request.TransferRequestDto;
import effective.mobile.test.account.entity.Account;
import effective.mobile.test.account.storage.AccountRepository;
import effective.mobile.test.user.entity.User;
import effective.mobile.test.user.storage.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

import static effective.mobile.test.constants.Constants.INCOME_INDEX;
import static effective.mobile.test.constants.Constants.MAX_BALANCE_INDEX;

@Service
@RequiredArgsConstructor
@Slf4j
public class AccountServiceImpl implements AccountService {

    private final UserRepository userRepository;
    private final AccountRepository accountRepository;

    @Scheduled(fixedRate = 60000)
    public void updateBalances() {
        List<User> users = userRepository.findAll();
        for (User user : users) {
            Account account = user.getAccount();
            if (account != null) {
                double currentBalance = account.getBalance();
                double newBalance = currentBalance * INCOME_INDEX;
                double maxBalance = account.getInitialDeposit() * MAX_BALANCE_INDEX;
                account.setBalance(Math.min(newBalance, maxBalance));

                accountRepository.save(account);
            }
        }
        System.out.println("Balances updated at: " + LocalDateTime.now());
    }

    @Override
    public void transferMoney(String token, TransferRequestDto dto) {
        // Найти отправителя и получателя
        User sender = userRepository.findByUsername(senderUsername);
        User recipient = userRepository.findByUsername(recipientUsername);

        // Проверить, что пользователи существуют
        if (sender == null || recipient == null) {
            throw new IllegalArgumentException("Sender or recipient not found");
        }

        // Получить счета отправителя и получателя
        Account senderAccount = sender.getAccount();
        Account recipientAccount = recipient.getAccount();

        // Проверить достаточность средств на счете отправителя
        if (senderAccount.getBalance() < amount) {
            throw new IllegalArgumentException("Insufficient funds");
        }

        // Выполнить перевод
        senderAccount.setBalance(senderAccount.getBalance() - amount);
        recipientAccount.setBalance(recipientAccount.getBalance() + amount);

        // Обновить счета в базе данных
        accountRepository.save(senderAccount);
        accountRepository.save(recipientAccount);
    }
}