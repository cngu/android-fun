# Android Fun

A sample project to demonstrate various Android features and APIs.

##Examples
* 

##Device Support
Only phones and tablets running Android 4.0 (API level 14+) are supported. 

> NOTE: Because various v7 support libraries (i.e. appcompat-v7, recyclerview-v7, cardview-v7) are utilized, this app should work for all devices using API level 7+. However, this has not been tested.

###Tested Devices
* Galaxy Nexus
* Nexus 5
* Nexus 7

##Implementation Notes
To add new a new demo topic:

1. Add a new id for your new topic in ```res/values/id_demo_fragment.xml```

2. Add a new topic entry at the desired location in the topic hierarchy UI in ```res/layout/ui_topic_hierarchy.xml```

3. Create a new Fragment class extending ```DemoFragment``` to contain your demo code

4. Update ```DemoFragmentFactory``` to construct and return your new fragment when the requested id argument matches the id you added in step 1
