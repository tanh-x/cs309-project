package com.kewargs.cs309.components;

import android.content.Context;
import android.view.View;

sealed interface ViewComponent<T extends View> permits InflatableComponent {
    <R extends View> R findViewById(int id);
}
