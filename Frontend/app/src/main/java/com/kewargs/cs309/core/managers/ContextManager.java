package com.kewargs.cs309.core.managers;

import android.content.Context;

final class ContextManager {
    Context context = null;

    private ContextManager() { }

    static synchronized ContextManager create(Context providedContext) {
        ContextManager manager = new ContextManager();
        manager.context = providedContext.getApplicationContext();
        return manager;
    }
}
