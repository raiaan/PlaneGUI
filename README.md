# PlaneGUI
quade qupter android gui
android app to control the motion of the quade qupter this gui consist of 4 parts that send it's value at the beginning of the activity or at changing one of this values
throttle slidebar : 
it's enhanced slidebar to control throttle value , the default value is 1000 and the values range is from 1000 to 2000
Yaw slidebar :
it's enhanced slidebar to control Yaw value , the default value is 1000 , and the values  range is from 1000 to 2000
pitch and roll area : 
it's square to controll those two values , as X and Y axis  
x axis represent roll value which is begin form 1000, and end at 2000 , with 1500 default value .
y axis represent pitch value which is also begin with 1000 , and end at 2000 with 1500 deualt value
start / default value button :
this button send default value of the four value to the serial port 
those defualt value is 1000 for Yaw and Throttle and 1500 for roll and pitch
Dependancy :
we used usb serial liberary to send those values through the serial port of the phone 
 Technique Used in this app :-
touch listener Class :
used to handle user moving touch and adjust the values of yaw ,throttle ,roll , and pitch acording to this moving action 
