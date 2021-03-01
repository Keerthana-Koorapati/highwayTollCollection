import java.util.*;

import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.function.Function;
import java.util.function.Predicate;
import java.util.stream.Collectors;

class Places{
    int pId;
    double total;
    boolean isToll;
    ArrayList<Vehicles> vehicles;

    Places(int pid, boolean isToll){
        pId = pid;
        total = 0;
        this.isToll = isToll;
        vehicles = new ArrayList<Vehicles>();
    }

    public boolean getToll(){
        return isToll;
    }

    public void setToll(boolean isToll){
        this.isToll = isToll;
    }

    public int getPId(){
        return pId;
    }

    public void calculateTotal(){
        total = 0;
        List<Vehicles> distinctElements = vehicles.stream()
                    .filter( distinctByKey(v -> v.getVehicleId()) )
                    .collect( Collectors.toList() );

        boolean printOnce = false;

        for(Vehicles v: distinctElements){
            for(Trips t: v.getTrips()){
                for(int tollbooth: t.getTollBooths()){
                    if(tollbooth == pId){
                        if(!printOnce){
                            System.out.println("Toll no: "+pId);
                            printOnce = true;
                        }
                        FeeForEachTollandTrip tt = new FeeForEachTollandTrip(t.getVip());
                        total += tt.calculateToll(v, pId);
                        tt.printTotal(v);
                    }
                }
            }
        }

        if(total!=0){
            System.out.println("#################################");
            System.out.println("Total is: " + total);
            System.out.println("#################################");
        }
        

    }
    public static <T> Predicate<T> distinctByKey(Function<? super T, Object> keyExtractor) 
    {
        Map<Object, Boolean> map = new ConcurrentHashMap<>();
        return t -> map.putIfAbsent(keyExtractor.apply(t), Boolean.TRUE) == null;
    }

    public double getTotal(){
        return total;
    }

    public ArrayList<Vehicles> getVehicles(){
        return vehicles;
    }

}
class Vehicles{
    int vehicleId;
    String vType;
    double total;
    ArrayList<Trips> trips = new ArrayList<Trips>();
    
    Vehicles(int vid, String vtype, boolean isvip){
        vehicleId = vid;
        vType = vtype;
    }

    public int getVehicleId(){
        return vehicleId;
    }

    public String getVehicleType(){
        return vType;
    }

    public ArrayList<Trips> getTrips(){
        return trips;
    }

    public void calculateTotal(){
        for(Trips t1: trips){
            total += t1.getTotal();
        }
    }

    public double getTotal(){
        return total;
    }
 
}

class Trips{
    //need to change this to chars later
    int start;
    int end;

    boolean isVip;

    
    double total = 0.0;
    
    //may require this to be an array list of chars.
    ArrayList<Integer> tollbooths = new ArrayList<Integer>();

    ArrayList<FeeForEachTollandTrip> tolltrips = new ArrayList<>();

    Trips(int start, int end, boolean isvip){
        this.start = start;
        this.end = end;
        isVip = isvip;
    }

    public int getStart(){
        return start;
    }

    public int getEnd(){
        return end;
    }

    public double getTotal(){
        return total;
    }

    public boolean getVip(){
        return isVip;
    }

    public ArrayList<Integer> getTollBooths(){
        return tollbooths;
    }

    public void calculateToll(Vehicles v, ArrayList<Places> p){
        //calculate tolls between s and d
        for(Places p1: p){
            if(p1.getToll() && p1.getPId()>= start && p1.getPId() <= end){
                tollbooths.add(p1.getPId());
                FeeForEachTollandTrip tt = new FeeForEachTollandTrip(isVip);
                tolltrips.add(tt);
                total += tt.calculateToll(v, p1.getPId());
            }
        }
        
        //printint the output
        /*
        Toll booths : B Amount : 20 Discount : 4 Total : 16
        */
        System.out.print("Tollbooths were: ");
        for(int i =0; i<tollbooths.size();i++){
            System.out.print(" " + tollbooths.get(i));
        }


        System.out.println();
        int amount;
        if(isVip)
            amount = (int) (total/0.8);
        else    
            amount = (int)total;
        System.out.println("Amount is " + amount);
        double discount = 0;
        if(isVip)
            discount = 0.2 * amount;
        System.out.println("Discount is " + discount);
        System.out.println("Total is: " +total);

        //checking if vehicle has already passed the toll before
        //if yes, nothing to be done.
        //if not, add the vehicle to the toll
        for(Places p1: p){
            if(p1.getToll() && tollbooths.contains(p1.getPId())){
                for(Vehicles v1: p1.getVehicles()){
                    if(v1.getVehicleId() == v.getVehicleId()){
                        break;
                    }
                }
                p1.vehicles.add(v);
            }
        }
    }

}

class FeeForEachTollandTrip{
    //calculate amount to pay based on vtype
    /*Toll B : Car = Rs.20, Truck = Rs.40, Bus = Rs.60
    Toll E : Car = Rs.30, Truck = Rs.50, Bus = Rs.70
    Toll G : Car = Rs.25, Truck = Rs.45, Bus = Rs.65*/

    double discount = 0;
    int amount = 0;
    double total = 0;
    boolean isVip = false;

    FeeForEachTollandTrip(boolean isVip){
        this.isVip = isVip;
    }    

    public double calculateToll(Vehicles v, int tollbooth){
        if(v.vType.equalsIgnoreCase("car")){
            
            if(tollbooth==2){
                amount += 20;
            }
            else if(tollbooth == 5){
                amount += 30;
            }
            else if(tollbooth == 7){
                amount += 25;
            }
            
        }
        else if(v.vType.equalsIgnoreCase("truck")){
            if(tollbooth==2){
                amount += 40;
            }
            else if(tollbooth == 5){
                amount += 50;
            }
            else if(tollbooth == 7){
                amount += 45;
            }
        }
        else if(v.vType.equalsIgnoreCase("bus")){
            if(tollbooth == 2){
                amount += 60;
            }
            else if(tollbooth == 5){
                amount += 70;
            }
            else if(tollbooth == 7){
                amount += 65;
            }
        }

        if(isVip){
            discount = (0.2 * amount);
        }
        //apply discount and add to total
        total = amount - discount;

        return total;
    }

    public void printTotal(Vehicles v){
        System.out.println("Vehicle no: " + v.getVehicleId() + "---" + total);
    }
    
}

public class highwayTollCollection {
    static ArrayList<Vehicles> v = new ArrayList<>();
    static ArrayList<Places> p = new ArrayList<>();
    
    public static void main(String args[]){

        p.add(new Places(1,false));
        p.add(new Places(2,true));
        p.add(new Places(3,false));
        p.add(new Places(4,false));
        p.add(new Places(5,true));
        p.add(new Places(6,false));
        p.add(new Places(7,true));
        p.add(new Places(8,false));        
        
        while(true){
            Scanner s = new Scanner(System.in);
            System.out.println("1. Toll Payment");
            System.out.println("2. Toll Collection Summary");
            System.out.println("3. Vehicle Travel Summary");
            System.out.println("4. Exit");
            int choice = s.nextInt();
            switch(choice){
                case 1:
                    calculateTollFees();
                    //increase the respective tollbooths amount
                    break;
                case 2:
                    for(Places p1: p){
                        if(p1.getToll()){
                            p1.calculateTotal();
                        }
                    }
                    break;
                case 3:
                    for(Vehicles v1 : v){
                        System.out.print("Vehicle number and type : "+v1.getVehicleId()+ " " + v1.getVehicleType()+"\n");
                        for(Trips t1 : v1.getTrips()){
                            System.out.println(t1.getStart() + "--->" + t1.getEnd() + " Toll Fees paid: "+ t1.getTotal() + " VIP?: "+ t1.getVip());
                        }
                        System.out.println();
                    }
                    break;
                case 4:
                    return;
                default:
                    System.out.println("Enter valid option");
                    break;
            }
        }
    }
    public static void calculateTollFees(){
    
        System.out.println("Enter vehicle no");
        Scanner s = new Scanner(System.in);
        int vid = s.nextInt();

        System.out.println("Enter vehicle type");
        s = new Scanner(System.in);
        String vtype = s.nextLine();

        System.out.println("Are you a VIP?");
        s = new Scanner(System.in);
        Character vip = s.next().charAt(0);
        boolean isvip = false;
        Character y = 'y';
        if(vip.equals(y)){
            isvip = true;
        }
        System.out.println("Enter start point (1-8)");
        s = new Scanner(System.in);
        int start = s.nextInt();

        System.out.println("Enter end point (1-8)");
        s = new Scanner(System.in);
        int end = s.nextInt();

        //checking if vehicle already exists, if not, creating a new instance
        int i;
        for(i = 0; i<v.size();i++){
            if(v.get(i).getVehicleId() == vid){
                break;
            }
        }
        if(i>= v.size()){
            v.add(new Vehicles(vid, vtype, isvip));
        }
        //adding the trip
        for(Vehicles v1 : v){
            if(v1.getVehicleId() == vid){
                Trips t = new Trips(start, end, isvip);
                v1.trips.add(t);
                t.calculateToll(v1,p);
                break;
            }
        }
    }
}
