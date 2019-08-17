package parking;

import mockit.Expectations;
import mockit.integration.junit4.JMockit;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;

import java.util.Arrays;
import java.util.List;

@RunWith(JMockit.class)
public class VipParkingStrategyJMockitTest {

    @Test
    public void testCalculateHourlyPrice_givenSunday_thenPriceIsDoubleOfSundayPrice(){

        /* Exercise 6: Write test case for VipParkingStrategy calculateHourlyPrice
        * by using JMockit to mock static method */
        Car car = new Car("A Jimmy Car");
        ParkingLot parkingLot = new ParkingLot("PK 1", 1);
        List<ParkingLot> parkingLots = Arrays.asList(parkingLot);

        VipParkingStrategy vipParkingStrategy = new VipParkingStrategy();
        vipParkingStrategy.park(parkingLots, car);

        new Expectations(parkingLot) {
            {
                parkingLot.getBasicHourlyPrice();
                result = 50;
            }
        };

        int price = vipParkingStrategy.calculateHourlyPrice();

        Assert.assertEquals(price, 100);
    }

    @Test
    public void testCalculateHourlyPrice_givenNotSunday_thenPriceIsDoubleOfNonSundayPrice(){

        /* Exercise 6: Write test case for VipParkingStrategy calculateHourlyPrice
         * by using JMockit to mock static method */
        Car car = new Car("A Jimmy Car");
        ParkingLot parkingLot = new ParkingLot("PK 1", 1);
        List<ParkingLot> parkingLots = Arrays.asList(parkingLot);

        VipParkingStrategy vipParkingStrategy = new VipParkingStrategy();
        vipParkingStrategy.park(parkingLots, car);

        new Expectations(parkingLot) {
            {
                parkingLot.getBasicHourlyPrice();
                result = 40;
            }
        };

        int price = vipParkingStrategy.calculateHourlyPrice();

        Assert.assertEquals(price, 80);

    }
}
