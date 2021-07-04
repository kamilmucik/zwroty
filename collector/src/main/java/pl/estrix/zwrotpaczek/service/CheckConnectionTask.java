package pl.estrix.zwrotpaczek.service;

import pl.estrix.zwrotpaczek.SessionManager;

import java.util.TimerTask;

public class CheckConnectionTask extends TimerTask {

    @Override
    public void run() {
        SessionManager.connectionStatus.setValue(RestService.getInstance().ping());
    }
}
