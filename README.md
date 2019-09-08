
# Android Assignment sample App 

This repository contains a detailed sample app (display photo with headings and descriptions using REST service) that implements MVVM architecture using Retrofit, Viewmodel, lifecycle and Recyclerview.

#The app has following packages:

1. base: It contains all the base class which is easier to inheritance.
2. dashboard: collection of pojo, view and viewmodel class.
3. helper: Added common properties like : adapter, extention function, custombinding.
4. network: Initialise retrofit, httplogger and lifecycle. 
5. utils: Utility classes.

#Library reference resources:
1. retrofit'2.5.0': https://github.com/radenadri/retrofit-gson-example
2. constraint_layout'1.1.3': https://github.com/googlesamples/android-ConstraintLayoutExamples
3. lifecycle'2.0.0': https://github.com/riggaroo/android-arch-components-lifecycle
4. sdp'1.0.6': https://github.com/intuit/sdp 
5. glide'4.9.0': https://github.com/bumptech/glide
5. shimmer:0.1.0 = https://github.com/facebook/shimmer-android

#Concept:

# Orientation handling working in two ways (Both logic are working here, you can switch any logic between this two component):
1. Manifest configChanges
2. ViewModel

# Android architecture components:
1. MVVM architecture: ViewModel, Livedata and lifecycle. 

# Null Properties
1. In API, somewhere data is giving me null so i replace this to empty string.

# PlaceHolder
1. In API, somewhere image url is empty so i added placeholder (grey colour).

            
