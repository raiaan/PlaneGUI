# PlaneGUI
<h2>quade qupter android gui</h2>
<p> <strong>single activity android app to control the motion of the quade qupter this gui consist of 4 parts that send it's value at the beginning of the activity or at changing one of this values </strong></p>
<h3>throttle slidebar : </h3>
<p>it's enhanced slidebar to control throttle value , the default value is 1000 and the values range is from 1000 to 2000</p>
<h3>Yaw slidebar : </h3>
<p>it's enhanced slidebar to control Yaw value , the default value is 1000 , and the values  range is from 1000 to 2000</p>
<h3>pitch and roll area : </h3>
<p>it's square to controll those two values , as X and Y axis  <br/>
<strong>x axis : </strong>represent roll value which is begin form 1000, and end at 2000 , with 1500 default value . <br/>
<strong>y axis : </strong>represent pitch value which is also begin with 1000 , and end at 2000 with 1500 deualt value</p>
<h3>start / default value button :</h3>
<p>this button send default value of the four value to the serial port 
those defualt value is 1000 for Yaw and Throttle and 1500 for roll and pitch</p>
<h2>Dependancy :</h2>
<p>we used usb serial liberary to send those values through the serial port of the phone </p>
<ul><h2> Technique Used in this app :-</h2>
<li>touch listener Class :<br/>
used to handle user moving touch and adjust the values of yaw ,throttle ,roll , and pitch acording to this moving action </li><ul>
