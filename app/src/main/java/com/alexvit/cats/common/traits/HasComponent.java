package com.alexvit.cats.common.traits;

import com.alexvit.cats.common.base.BaseComponent;

public interface HasComponent<Component extends BaseComponent> {

    Component buildComponent();

    void inject(Component component);

}
