package com.kewargs.cs309.components;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;


abstract sealed class InflatableComponent<T extends View>
    implements ViewComponent<T>
    permits CourseCardComponent, SectionCardComponent {

    protected LayoutInflater inflater;
    protected int layout;
    protected T stub;

    protected InflatableComponent(int componentLayout, LayoutInflater inflater) {
        this.inflater = inflater;
        this.layout = componentLayout;
    }

    public void bindTo(ViewGroup parentView) {
        parentView.addView(this.render());
    }

    @SuppressWarnings("unchecked")
    protected T render() {
        this.stub = (T) inflater.inflate(layout, null);
        return stub;
    }

    public <R extends View> R findViewById(int id) { return stub.findViewById(id); }
}
