package com.kewargs.cs309.components;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

abstract sealed class InflatableComponent<T extends View>
    implements ViewComponent<T>
    permits CourseCardComponent {

    protected LayoutInflater inflater;
    protected T stub;

    protected InflatableComponent(int componentLayout, LayoutInflater inflater) {
        this.inflater = inflater;
    }

    public void bindTo(ViewGroup parentView) {
        parentView.addView(this.render());
    }

    protected abstract T render();

    public <R extends View> R findViewById(int id) { return stub.findViewById(id); }
}
