package com.itmo.assassins.service.impl.user;

import com.itmo.assassins.model.request.RequestDifficulty;
import com.itmo.assassins.model.user.*;
import com.itmo.assassins.service.user.UserService;
import com.itmo.assassins.service.user.UserTeamService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.EnumSource;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Import;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.Arrays;
import java.util.Collections;
import java.util.HashSet;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(SpringExtension.class)
@Import(UserTeamServiceImpl.class)
class UserTeamServiceImplTest {

    @MockBean
    private UserService userService;

    @Autowired
    private UserTeamService userTeamService;

    @Test
    void testFindMasterForRequest() {

        Mockito.when(userService.findUserByRole(UserRole.MASTER_ASSASSIN))
                .thenReturn(new HashSet<>(Collections.singletonList(new Master())));

        userTeamService.findMasterForRequest();

        Mockito.verify(userService, Mockito.times(1))
                .findUserByRole(UserRole.MASTER_ASSASSIN);
    }

    @Test
    void testFindMasterForRequestWithException() {
        assertThrows(IllegalArgumentException.class, () -> userTeamService.findMasterForRequest());
    }

    @Test
    void testFindCabmanForRequest() {

        Mockito.when(userService.findUserByRole(UserRole.CABMAN))
                .thenReturn(new HashSet<>(Collections.singletonList(new Cabman())));

        userTeamService.findCabmanForRequest();

        Mockito.verify(userService, Mockito.times(1))
                .findUserByRole(UserRole.CABMAN);
    }

    @Test
    void testFindCabmanForRequestWithException() {
        assertThrows(IllegalArgumentException.class, () -> userTeamService.findCabmanForRequest());
    }

    @Test
    void testFindGunsmithForRequest() {

        Mockito.when(userService.findUserByRole(UserRole.GUNSMITH))
                .thenReturn(new HashSet<>(Collections.singletonList(new Gunsmith())));

        userTeamService.findGunsmithForRequest();

        Mockito.verify(userService, Mockito.times(1))
                .findUserByRole(UserRole.GUNSMITH);
    }

    @Test
    void testFindGunsmithForRequestWithException() {
        assertThrows(IllegalArgumentException.class, () -> userTeamService.findGunsmithForRequest());
    }

    @ParameterizedTest
    @EnumSource(value = RequestDifficulty.class)
    void testFindExecutorForRequest(RequestDifficulty requestDifficulty) {

        switch (requestDifficulty) {
            case LOW:

                Executor executor1 = new Executor();
                executor1.setRating(3.0);

                Mockito.when(userService.findExecutorsByBusy())
                        .thenReturn(new HashSet<>(Collections.singletonList(executor1)));

                break;
            case MEDIUM:
                Executor executor2 = new Executor();
                executor2.setRating(4.0);

                Mockito.when(userService.findExecutorsByBusy())
                        .thenReturn(new HashSet<>(Collections.singletonList(executor2)));
                break;
            default:
                Executor executor3 = new Executor();
                executor3.setRating(4.8);

                Mockito.when(userService.findExecutorsByBusy())
                        .thenReturn(new HashSet<>(Collections.singletonList(executor3)));
                break;
        }

        userTeamService.findExecutorForRequest(requestDifficulty);

        Mockito.verify(userService, Mockito.times(1))
                .findExecutorsByBusy();
    }

    @ParameterizedTest
    @EnumSource(value = RequestDifficulty.class)
    void testFindExecutorForRequestWithException(RequestDifficulty requestDifficulty) {

        Mockito.when(userService.findExecutorsByBusy())
                .thenReturn(Collections.emptySet());

        assertThrows(IllegalArgumentException.class,
                () -> userTeamService.findExecutorForRequest(requestDifficulty));
    }

    @Test
    void findCabmenExceptGiven() {

        Cabman cabman = new Cabman();

        Mockito.when(userService.findUserByRole(UserRole.CABMAN))
                .thenReturn(new HashSet<>(Collections.singletonList(cabman)));

        assertTrue(userTeamService.findCabmenExceptGiven(cabman).isEmpty());
    }

    @Test
    void findGunsmithExceptGiven() {

        Gunsmith gunsmith = new Gunsmith();

        Mockito.when(userService.findUserByRole(UserRole.GUNSMITH))
                .thenReturn(new HashSet<>(Collections.singletonList(gunsmith)));

        assertTrue(userTeamService.findGunsmithsExceptGiven(gunsmith).isEmpty());
    }

    @ParameterizedTest
    @EnumSource(RequestDifficulty.class)
    void findExecutorExceptGiven(RequestDifficulty requestDifficulty) {

        Executor executor = new Executor();
        executor.setBusy(false);

        switch (requestDifficulty) {
            case LOW:

                Executor executor1 = new Executor();
                executor1.setRating(3.0);
                executor.setRating(3.0);

                Mockito.when(userService.findExecutorsByBusy())
                        .thenReturn(new HashSet<>(Arrays.asList(executor1, executor)));

                break;
            case MEDIUM:
                Executor executor2 = new Executor();
                executor2.setRating(4.0);
                executor.setRating(4.0);

                Mockito.when(userService.findExecutorsByBusy())
                        .thenReturn(new HashSet<>(Arrays.asList(executor2, executor)));
                break;
            default:
                Executor executor3 = new Executor();
                executor3.setRating(4.8);
                executor.setRating(4.8);

                Mockito.when(userService.findExecutorsByBusy())
                        .thenReturn(new HashSet<>(Arrays.asList(executor3, executor)));
                break;
        }

        assertEquals(1, userTeamService.findExecutorsExceptGiven(executor, requestDifficulty).size());
    }
}