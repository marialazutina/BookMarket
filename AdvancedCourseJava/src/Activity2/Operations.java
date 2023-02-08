package Activity2;

import static Activity2.TransportManagement.*;

public class Operations {

    public static void main(String[] args) {

        Road road1 = new Road("Riga", "Tallinn", 312);
        Road road2 = new Road("London", "Manchester", 321);
        Road road3 = new Road("Berlin", "Warsaw", 574);

        Transport transport1 = new Transport(15.0f,50.0);
        Transport transport2 = new Transport(7.0f,70.0);
        Transport transport3 = new Transport(5.0f,45.0);


        initSystem(3,3);
        System.out.println("=====================================================");
        setTransport(transport1,0);
        setTransport(transport2, 1);
        setTransport(transport3,2);
        setRoad(road1, 0);
        setRoad(road2,1);
        setRoad(road3,2);
        getTransports();
        getRoad();


//        for (Road road: allRoad){
//            if(!road.getArrived()){
//                transport1.move(road);
//            }
//        }

        transport1.move(road3);
        System.out.print("Your journey was successful: ");
        System.out.println(road3.getArrived());
    }
}
