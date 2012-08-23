Jelly Beans Notification Framework and review of some useful libraries and facilities
===
Source code, apk and presentation on github - https://github.com/romansky/androidtalk0812
<br/>
Roman Landenband, Founder @ Tourrific

!

Jelly Beans Notification Framework
---

* Notification != feedback
* Be careful not to overuse
* Included with the new v4 (ver10) BC package! [2]
* Read the very good UX overview page [1]

[1] - [Official UX page](developer.android.com/design/patterns/notifications.htmli)
<br/>
[2] - [BC package release notes](http://developer.android.com/tools/extras/support-library.html#Notes)

!

Challenges, writing Android applications
---

* Version fragmentation (ActionBarSherlock)
* Decoupling and re-usability (Fragments)
* Activity life-cycle - pushing us to create singletons/global state (OTTO, Fragments)
* No easy/simple way to work with JSON (Google GSON)

!

Fragments
---

* Introduced via API11(3.0) to allow reuse of UI elements
* Allows easier UI design and implementation for bigger screens (tablets)
* Simplifies Activity life-cycle (via setRetainInstance(true))

!

ActionBarSherlock
---

* Action Bar implementation for platforms that don't have it (>1.6)
* ICS themes (although they are under ABS namespace)
* Split menu support (ported to pre 3.0)
* Comes the the BC package, thus support fragments

(for difference with "Action Bar Compat(Google)" see http://stackoverflow.com/questions/7844517/different-between-actionbarsherlock-and-action-bar-compatibility)

!

OTTO
---

* A pub/sub framework, global(unlike the implementation in RoboGuice)
* Relatively unobtrusive, small footprint

!

Google GSON
---

* "casts" JSON objects into native JAVA equivalent structures
* Visa-versa

!

A To-Do App that uses all of these
---

Lats see how an application looks like when you combine the power of the libraries we looked at..