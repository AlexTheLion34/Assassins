package com.itmo.assassins.service.user;

import com.itmo.assassins.model.request.RequestDifficulty;
import com.itmo.assassins.model.user.Cabman;
import com.itmo.assassins.model.user.Executor;
import com.itmo.assassins.model.user.Gunsmith;
import com.itmo.assassins.model.user.Master;

import java.util.List;

public interface UserTeamService {
    Master findMasterForRequest();
    Cabman findCabmanForRequest();
    List<Cabman> findCabmenExceptGiven(Cabman cabman);
    Gunsmith findGunsmithForRequest();
    List<Gunsmith> findGunsmithsExceptGiven(Gunsmith gunsmith);
    Executor findExecutorForRequest(RequestDifficulty difficulty);
    List<Executor> findExecutorsExceptGiven(Executor executor, RequestDifficulty difficulty);
}
