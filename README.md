# Google Image Search

This is an image search application powered by [Google Image Search API](https://developers.google.com/image-search/v1/jsondevguide) (FYI it is deprecated).  Please see *CodePath* Android Observer Group [Week 2](http://courses.codepath.com/courses/intro_to_android/week/2#!module) material, covering user interaction and navigation flows within apps.


## User Stories:

The following user stories are completed:

* [x] User can enter a search query that will display a grid of image results from the Google Image API.
* [x] User can click on "settings" which allows selection of advanced search options to filter results
* [x] User can configure advanced search filters such as:
	* Size (small, medium, large, extra-large)
	* Color filter (black, blue, brown, gray, green, etc...)
	* Type (faces, photo, clip art, line art)
	* Site (espn.com)
* [x] Subsequent searches will have any filters applied to the search results
* [x] User can tap on any image in results to see the image full-screen
* [x] User can scroll down “infinitely” to continue loading more image results (up to 8 pages)



![GIF Walkthrough](https://cloud.githubusercontent.com/assets/929507/6425505/e3abb03a-bee0-11e4-96a8-299c0df84106.gif).

GIF created with [LiceCap](http://www.cockos.com/licecap/).


## Learning

1. [Intents](http://guides.codepath.com/android/Using-Intents-to-Create-Flows) - "An intent is a message that can be thought of as a request that is given to either an activity within your own app, an external application, or a built-in Android service." "The Intent object itself is a class that represents a particular "request" including the topic of the request and any request "parameters" which are called the Bundle." In this case, the search options data between the main activity and the settings activity.

2. Set EndlessScrollListener() on GridView 

3. Create image asset, spinner, etc.

4. Use ActionBarActivity.  Add the items under menu.

5. [Android Activity lifecycle](https://plus.google.com/+StefanCaliaro/posts/XLjXncMdT55?pid=6120569443807311266&oid=107292960535792201556)



## Libraries

This app leverages two third-party libraries:

 * [Android AsyncHTTPClient](http://loopj.com/android-async-http/) - For asynchronous network requests
 * [Picasso](http://square.github.io/picasso/) - For remote image loading

## Development Environment

 * Android Studio 1.0.1

 * App tested with BlueStacks (4.4.2 support) on Windows 7

