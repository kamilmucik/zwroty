package pl.estrix.zwrotpaczek.service;

import pl.estrix.zwrotpaczek.controller.scan.ScanController;

import java.util.Date;
import java.util.TimerTask;

public class UpdateTimmerTask extends TimerTask {

    @Override
    public void run() {
        ScanController.getInstance().scanCode(new Date().getTime());
    }
}
