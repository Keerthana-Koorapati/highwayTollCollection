# highwayTollCollection

Highway Toll collection

● There are 8 places along a National Highway. Let the names of the places
be A, B, C, D, E, F, G and H.
● Assume the points are in a straight line along the Highway.
● Toll booths are located at B, E and G in the highway.
● Number of vehicles pass through the toll and whenever a vehicle passes
through a toll, an amount of Toll is collected for a single journey, based
on the type of vehicle, as follows :
  ○ Toll B : Car = Rs.20, Truck = Rs.40, Bus = Rs.60
  ○ Toll E : Car = Rs.30, Truck = Rs.50, Bus = Rs.70
  ○ Toll G : Car = Rs.25, Truck = Rs.45, Bus = Rs.65
● 20% discount in toll amount is applicable to VIP users (if they use Car /
Truck)
● Vehicles are identified by their vehicle numbers 1, 2, 3, etc.,
● Toll amount will be collected for a vehicle whenever it passes through a
toll booth even if it matches with source and destination points. For
example, if a vehicle starts at B and reaches E, then toll will be collected
at B & E booths. At the same time, if a vehicle starts from A and reaches
C, then it has pay toll at booth 'B' only
● A vehicle can have multiple rides e.g., A to C, D to F, etc.,
Write a program that does the following :
1. Toll Payment : Given a start point, destination, vehicle type and vehicle
number & vip user status, calculate and print the toll amount to be paid &
toll booths to pass
2. Toll Collection Summary : Print toll collection summary of all toll booths
3. Print all vehicle's travel summary


Sample Data for Toll Payment

Input 1
Vehicle No:1
Type : Car
VIP user (y/n) : y
Start Point : A
Destination : D

Output
Toll booths : B
Amount : 20
Discount : 4
Total : 16

Input 2
Vehicle No : 2
Type : Truck
VIP user (y/n) : n
Start Point : A
Destination : E

Output
Toll booths : B, E
Amount : 90
Discount : 0
Total : 90

Input 3
Vehicle No : 3
Type : Bus
VIP user (y/n) : n
Start Point : D
Destination : F

Output
Toll booths : E
Amount : 70
Discount : 0
Total : 70

Input 4
Vehicle No : 1
Type : Car
VIP user (y/n) : n
Start Point : D
Destination : F

Output
Toll booths : E
Amount : 30
Discount : 0
Total : 30

Question 2 Sample Output for Toll Collection Summary
Toll Point : B
Vehicle No :1  16
Vehicle No :2  40
Total : 56

Toll Point : E
Vehicle No:1  30
Vehicle No: 2  50
Vehicle No: 3  70
Total : 150

Toll Point : G
Total : 0

Question 3 Sample Output for all vehicle's travel summary along with
toll paid
Vehicle No: 1, Car
A --> D Toll Paid : 16, VIP User - Yes
D --> F Toll Paid : 30, VIP User - No
Vehicle No: 2, Truck
A --> E Toll paid : 90, VIP User - No
Vehicle No: 3, Bus
D --> F Toll paid : 70, VIP User - No
