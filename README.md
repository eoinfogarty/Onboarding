Onboarding
==========
[![Android Arsenal](https://img.shields.io/badge/Android%20Arsenal-Onboarding-green.svg?style=true)](https://android-arsenal.com/details/3/4094)


A beautiful way to introduce users to you app

![Demo](graphics/example.gif)

Using a regular `ViewPager` with a custom transformer with callbacks we can achieve this effect

### Interface
```java
    public interface SceneChangeListener {

        void enterScene(@Nullable ImageView sharedElement, float position);

        void centerScene(@Nullable ImageView sharedElement);

        void exitScene(@Nullable ImageView sharedElement, float position);

        void notInScene();
    }
```

We then a fragment class that implements the callbacks to react to movement

### Fragment
```java
public abstract class BaseSceneFragment extends Fragment
        implements SceneTransformer.SceneChangeListener {

    protected static final String KEY_POSITION = "KEY_POSITION";

    // we have to set a position tag to the root layout of every scene fragment
    // this is so the transformer will know who to make a callback to
    protected void setRootPositionTag(@NonNull View root) {
        root.setTag(getArguments().getInt(KEY_POSITION));
    }

    @Override
    public abstract void enterScene(@Nullable ImageView sharedElement, float position);

    @Override
    public abstract void centerScene(@Nullable ImageView sharedElement);

    @Override
    public abstract void exitScene(@Nullable ImageView sharedElement, float position);

    @Override
    public abstract void notInScene();

    ...
}

```

#License
```
Copyright (C) 2016 Eoin Fogarty

Licensed under the Apache License, Version 2.0 (the "License");
you may not use this file except in compliance with the License.
You may obtain a copy of the License at

http://www.apache.org/licenses/LICENSE-2.0

Unless required by applicable law or agreed to in writing, software
distributed under the License is distributed on an "AS IS" BASIS,
WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
See the License for the specific language governing permissions and
limitations under the License.
```
