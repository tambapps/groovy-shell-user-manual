# Shell Works (pro feature)

A Shell Work is a script that is executed in the background and can run
even while your aren't on the app, or the app is closed. It uses
[Android WorkManager](https://developer.android.com/topic/libraries/architecture/workmanager)
to allow running code in background.

With the power of Android Works, a Shell work can be

- scheduled, meaning that you can specify a date at which it should run
- periodic, meaning that the work will run every XX minutes for example
- silent, meaning that you won't get notified when the work is running/finished. 
  Note that you'll still get notified if the shell works ends up with an error (an uncaught exception was thrown)


In a Shell work, you can set a notification that will be displayed when finished.
If set, the notification will show even if the work is silent.


Within a Shell Work Script, you can use several variables/functions

- isNetworkAvailable -> boolean to check if network is available
- setMessage(String) -> set the message of the notification will the shell work is running
  (only used if shell is not silent)
- setFinalTitle(String) -> set the title of the final notification, notification that will be displayed
  when the shell work is finished, even if silent
- setFinalMessage(String) -> set the message of the final notification, notification that will be displayed
  when the shell work is finished, even if silent
- setNotificationBigText(Boolean) -> set whether the final should be larger to display a long message