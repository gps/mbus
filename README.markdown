## MBus Android

mbus is an Android client for the [University of Michigan's Magic Bus](http://mbus.pts.umich.edu/) system. It's written in Java, for Android SDK 1.5 or greater.

###Goals
- View Magic Bus data quickly. 
- Get to arrival information with as few touches as possible.
- Favorite route/bus stop pair, and view only favorites.
- Determine my location and display arrival info for stops near me.
- View current locations of all buses on a map. (This is really to make the app look fancy. I'd probably never use it myself, and hence may not ever get written.)

While the Magic Bus site has a text-only version that can be viewed on the Android browser, it has several shortcomings, which is why I'm bothering with this.

Drawbacks:

- It takes 5-6 clicks to get to the information you need.

- You need to click on teensy hyperlinks which is hard to do on such a small screen. 

- You also typically only ever care about 2-3 stops, and the website has no notion of favorites.

When it's -10C outside, you *really* want to get your hand back in your gloves/pockets ASAP.

Why not just write a new mobile website instead of a native Android app? This enables other smartphone users (iPhone + BlackBerry, etc) to use it.

- A native client will always be nicer to use (at least in my opinion). 

- Some of the features I want can't be done easily with a web app (or so I think).

- There are already two native iPhone app for this (the second one seems to be nicer): [one](http://itunes.apple.com/us/app/mbus/id321927057?mt=8) and [two](http://itunes.apple.com/us/app/campus-to-campus/id322705603?mt=8)

- I don't know enough web development (especially mobile) to do this right, if it is possible.

Why Android 1.5 (as opposed to >= 1.6 which offers different sized screen support)? 

I own a Moto Cliq, which as of this writing runs Android 1.5. Doesn't look like Motorola is going to update to 1.6 anytime soon, and I want to be able to use the app myself. Hopefully, other handset users won't be hit too hard by the varying screen sizes.

###Design
There are 2 projects. One is a test harness for the Magic Bus Data package. The other is the Android application itself.
 
I decided to split the code that retrieves and parses the Magic Bus feed into it's own package, so it can be used by other people for other projects. I also wanted to be able to test it separately and quickly, especially if the feed format changes in the future.

I'm still not done designing the UI of the Android app. When I finish it, I'll write about it here.

###Licensing
This project will be released under the GPL. I'll get the GPL licensing stuff in at some point. In the meantime, feel free to use the code. I take no responsibility for any consequences of using this code, especially bad consequences. I would appreciate if you let me know if you use the code here. 

###Contact
I am best reached by email: gopalkri AT umich DOT edu OR gopalkrishnaps AT gmail DOT com
If you're interested, here's my [webpage](http://www.umich.edu/~gopalkri) (not quite up to date all the time). If you want to help with the dev work, let me know. I do this for fun in my free time, so I could use help :)

###Feedback/Thoughts
I'd love to hear your feedback/thoughts. Email me about this. 
