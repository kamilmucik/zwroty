/*
 * Copyright (C) 2013-2015 2048FX 
 * Jose Pereda, Bruno Borges & Jens Deters
 * All rights reserved.
 *
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program.  If not, see <http://www.gnu.org/licenses/>.
 */

package pl.estrix.zwrotpaczek;

import java.io.IOException;
import static java.lang.String.format;
import java.net.URISyntaxException;
import java.util.*;
import java.util.logging.Logger;
import javafx.beans.property.BooleanProperty;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.image.Image;
import com.gluonhq.charm.down.common.PlatformFactory;
import pl.estrix.zwrotpaczek.service.CheckConnectionTask;
import pl.estrix.zwrotpaczek.service.SynchronizeProductTask;
import pl.estrix.zwrotpaczek.service.UpdateTimmerTask;

public class PlatformService {

    public static final String DESKTOP = "Desktop";
    public static final String ANDROID = "Android";
    public static final String IOS = "iOS";

    private static final Logger LOG = Logger.getLogger(PlatformService.class.getName());

    private static PlatformService instance;

    private static Timer timer = new Timer();
    private static TimerTask checkConnectionTask;
//    private static TimerTask synchronizeProductTask;
    private static TimerTask updateTimmerTask;

    public static synchronized PlatformService getInstance() {
        if (instance == null) {
            instance = new PlatformService();
        }
        return instance;
    }

    private final ServiceLoader<PlatformProvider> serviceLoader;
    private PlatformProvider provider;

    private PlatformService() {
        serviceLoader = ServiceLoader.load(PlatformProvider.class);
        try {
            Iterator<PlatformProvider> iterator = serviceLoader.iterator();
            System.out.println("interator: " + iterator);
            while (iterator.hasNext()) {
                System.out.println("provider: " + provider);
                if (provider == null) {
                    provider = iterator.next();

                    System.out.println("provider: " + provider);
                    LOG.info(format("Using PlatformProvider: %s", provider.getClass().getName()));
                } else {
                    LOG.info(format("This PlatformProvider is ignored: %s", iterator.next().getClass().getName()));
                }
            }
        } catch (Exception e) {
            throw new ServiceConfigurationError("Failed to access + ", e);
        }
        if (provider == null) {
            LOG.severe("No PlatformProvider implementation could be found!");
        }


        checkConnectionTask = new CheckConnectionTask();
//        synchronizeProductTask = new SynchronizeProductTask();
        updateTimmerTask = new UpdateTimmerTask();
        timer.schedule(checkConnectionTask, 1000, 15000); // co 5 sekund
//        timer.schedule(synchronizeProductTask, 1000, 5000); // co 5 sekund
        timer.schedule(updateTimmerTask, 1000, 500); // co 0,5 sekund

    }

    public void launchURL(String url) {
        try {
            PlatformFactory.getPlatform().launchExternalBrowser(url);
        } catch (IOException | URISyntaxException ex) {
            LOG.severe(ex.getMessage());
        }
    }

    public ObservableList<Image> getIcons() {
        return provider == null ? FXCollections.<Image>observableArrayList() : provider.getIcons();
    }

    public BooleanProperty stopProperty() {
        return provider == null ? new SimpleBooleanProperty() : provider.stopProperty();
    }

    public BooleanProperty pauseProperty() {
        return provider == null ? new SimpleBooleanProperty() : provider.pauseProperty();
    }

    public void exit() {
        if (provider != null) {
            timer.cancel();
            provider.exit();
        }
    }

    public void setFullScreen(boolean flag) {
        if (provider != null) {
//            provider.setFullScreen(flag);
        }
    }

    public void setSwitchOffKeyboard(boolean b) {
        if (provider != null) {
            provider.setSwitchOffKeyboard(b);
        }

    }
}
