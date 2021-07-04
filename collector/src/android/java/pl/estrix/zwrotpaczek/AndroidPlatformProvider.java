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

import android.util.Log;
import android.view.Window;
import android.view.WindowManager;
import javafx.beans.property.BooleanProperty;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.image.Image;
import javafxports.android.FXActivity;
import com.gluonhq.charm.down.common.Platform;
import com.gluonhq.charm.down.common.PlatformFactory;
import pl.estrix.zwrotpaczek.PlatformProvider;

public class AndroidPlatformProvider implements PlatformProvider {

    private final BooleanProperty stop = new SimpleBooleanProperty();
    private final BooleanProperty pause = new SimpleBooleanProperty();

    {
        Log.v("Provider", "Temp dir");
        System.setProperty("java.io.tmpdir", FXActivity.getInstance().getCacheDir().getAbsolutePath());
//        System.setProperty("com.sun.javafx.isEmbedded", "true");
//        System.setProperty("com.sun.javafx.touch", "true");
//        System.setProperty("com.sun.javafx.virtualKeyboard", "javafx");

//        PlatformFactory.getPlatform().setOnLifecycleEvent((Platform.LifecycleEvent param) -> {
//            switch (param) {
//                case START:
//                    pause.set(false);
//                    stop.set(false);
//                    break;
//                case PAUSE:
//                    pause.set(true);
//                    break;
//                case RESUME:
//                    pause.set(false);
//                    stop.set(false);
//                    break;
//                case STOP:
//                    stop.set(true);
//                    break;
//            }
//            return null;
//        });

    }

    @Override
    public ObservableList<Image> getIcons() {
        return FXCollections.<Image>observableArrayList();
    }

    @Override
    public BooleanProperty stopProperty() {
        return stop;
    }

    @Override
    public BooleanProperty pauseProperty() {
        return pause;
    }

    @Override
    public void exit() {
        FXActivity.getInstance().finish();
    }

    @Override
    public void setFullScreen(boolean flag) {
        FXActivity.getInstance().runOnUiThread(() -> {
            Window window = FXActivity.getInstance().getWindow();
            if (flag) {
                window.addFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON);
            } else {
                window.clearFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON);
            }
        });
    }

    @Override
    public void setSwitchOffKeyboard(boolean flag) {
        FXActivity.getInstance().runOnUiThread(() -> {
            Window window = FXActivity.getInstance().getWindow();
            if (flag) {
                window.setSoftInputMode(WindowManager.LayoutParams.TYPE_PHONE);
            } else {
                window.setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN);
            }
        });
    }
}
