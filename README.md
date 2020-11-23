[![](https://jitpack.io/v/OferDrori/ConvenientRate.svg)](https://jitpack.io/#OferDrori/ConvenientRate)
# ConvenientRateUsDialog-Android

A library for simple implementation of smart ranking.
The user will see a dialog alart.
If the user gives a high score, he will be transferred to the Google store. If he gives a low score, he will only receive a dialog that he can write why he disappointed.
after the user rate the app the dialog alart will not appear anymore.

<img src="https://github.com/OferDrori/ConvenientRate/blob/master/Rate%20Us.jpeg" width="288">

<img src="https://github.com/OferDrori/ConvenientRate/blob/master/Rate%20Us%20on%20google.jpeg" width="288">

<img src="https://github.com/OferDrori/ConvenientRate/blob/master/help%20us%20to%20improve.jpeg" width="288">

Step 1. Add it in your root build.gradle at the end of repositories:
```
allprojects {
    repositories {
	maven { url 'https://jitpack.io' }
    }
}
```

Step 2. Add the dependency:

```
dependencies {
	implementation 'implementation 'com.github.OferDrori:ConvenientRate:1.0.0'
  
}
```
## Usage

###### StepProgress Constructor:
```java
ConvenientRate.Rate(MainActivity.this
                , new ConvenientRate.CallBack_UserRating() {
                    @Override
                    public void userRating(int rating,String msg) {

                        Log.d("111",msg);
                        Log.d("111", String.valueOf(rating));
                    }
                }
        );
```        
        
 ## Credits
The library concept came from my android teacher Guy .
https://github.com/guy-4444/SmartRateUsDialog-Android        
        
