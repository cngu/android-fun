# Android Fun

A sample project to demonstrate various Android features and APIs.

##Examples
* 

##Device Support
Only devices running Android 4.0 (API level 14+) are supported. 

> NOTE: Because appcompat-v7 is utilized, this app should work for all devices using API level 7+. However, this has not been tested.

##Implementation Notes
To add new a new demo topic:

1. Add a new id for your new topic in ```res/values/id_demo_fragment.xml```

2. Add a new topic entry at the desired location in the topic hierarchy UI in ```res/layout/ui_topic_hierarchy.xml```

3. Create a new Fragment class extending ```DemoFragment``` to contain your demo code

4. Update ```DemoFragmentFactory``` to construct and return your new fragment when the requested id argument matches the id you added in step 1
