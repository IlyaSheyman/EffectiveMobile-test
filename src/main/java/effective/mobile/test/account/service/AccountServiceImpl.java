package effective.mobile.test.account.service;

import effective.mobile.test.account.dto.transfer.TransferRequestDto;
import effective.mobile.test.account.entity.Account;
import effective.mobile.test.account.storage.AccountRepository;
import effective.mobile.test.config.security.JwtService;
import effective.mobile.test.exception.model.BadRequestException;
import effective.mobile.test.exception.model.NotFoundException;
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

/**
 * Service implementation for handling account operations such as balance updates and money transfers.
 */
@Service
@RequiredArgsConstructor
@Slf4j
public class AccountServiceImpl implements AccountService {

    private final UserRepository userRepository;
    private final AccountRepository accountRepository;
    private final JwtService jwtService;

    /**
     * Scheduled task that runs every minute to update balances of all users.
     * Increases the balance by 5% but not more than 207% of the initial deposit.
     */
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
        log.info("Balances updated at: " + LocalDateTime.now());
    }

    /**
     * Transfers money from the authenticated user's account to another user's account.
     * Validates the recipient, ensures the sender has enough balance, and updates the accounts.
     *
     * @param token the authentication token of the sender
     * @param dto the transfer request containing recipient details and amount
     */
    @Override
    public synchronized void transferMoney(String token, TransferRequestDto dto) {
        User sender = extractUserFromToken(token);
        Account senderAccount = sender.getAccount();

        User recipient = findRecipient(dto);

        if (recipient == null) {
            throw new BadRequestException("Recipient not found");
        }

        if (recipient.getId() == sender.getId()) {
            throw new BadRequestException("You cannot transfer money to yourself");
        }

        Account recipientAccount = recipient.getAccount();

        if (senderAccount.getBalance() < dto.getAmount()) {
            throw new BadRequestException("Insufficient funds");
        }

        senderAccount.setBalance(senderAccount.getBalance() - dto.getAmount());
        recipientAccount.setBalance(recipientAccount.getBalance() + dto.getAmount());

        accountRepository.save(senderAccount);
        accountRepository.save(recipientAccount);

        log.info("Transfer completed: {} -> {}, amount: {}", sender.getLogin(), recipient.getLogin(), dto.getAmount());
    }

    /**
     * Finds the recipient user based on the transfer request details.
     *
     * @param dto the transfer request containing recipient details
     * @return the recipient user, or null if not found
     */
    private User findRecipient(TransferRequestDto dto) {
        if (dto.getEmail() != null) {
            return userRepository.findUserByEmailsContains(dto.getEmail()).orElse(null);
        } else if (dto.getRecipientNumber() != null) {
            return userRepository.findUserByPhonesContains(dto.getRecipientNumber()).orElse(null);
        }
        return null;
    }

    /**
     * Extracts the authenticated user from the token.
     *
     * @param userToken the authentication token
     * @return the authenticated user
     */
    private User extractUserFromToken(String userToken) {
        userToken = userToken.substring(7);
        return getUserById(jwtService.extractUserId(userToken));
    }

    /**
     * Retrieves a user by their ID.
     *
     * @param userId the user ID
     * @return the user
     * @throws NotFoundException if the user is not found
     */
    public User getUserById(int userId) {
        return userRepository
                .findById(userId)
                .orElseThrow(() -> new NotFoundException("User not found"));
    }
}
