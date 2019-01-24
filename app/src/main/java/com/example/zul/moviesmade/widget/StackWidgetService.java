package com.example.zul.moviesmade.widget;

import android.content.Intent;
import android.widget.RemoteViewsService;

/**
 * copyright zul
 **/

public class StackWidgetService extends RemoteViewsService {
    @Override
    public RemoteViewsFactory onGetViewFactory(Intent intent) {
        return new StackRemoteViewsFactory(this.getApplicationContext(), intent);
    }
}
